package aigc.backend.restcontrollers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import aigc.backend.models.CheckoutRequest;
import aigc.backend.models.PurchaseOrder;

import aigc.backend.services.PurchaseService;
import aigc.backend.services.StripeService;
import aigc.backend.services.UserDataService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/purchase")
public class PurchaseRestController {
    
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private StripeService stripeService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Value("${stripe.api.publicKey}")
    private String stripePublicKey;
    

    @PostMapping("/create-session")
    @JsonIgnoreProperties(ignoreUnknown = true) 
    public ResponseEntity<String> createCheckoutSession(@RequestBody CheckoutRequest checkoutRequest) throws StripeException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }

       Session session = stripeService.createCheckoutSession(
            checkoutRequest.getItemNames(),
            checkoutRequest.getItemPrices(),
            checkoutRequest.getItemQuantities(),
            userId
        );
      
        String sessionId = session.getId();        
        JsonObject response = Json.createObjectBuilder().add("id", sessionId).build();
        return ResponseEntity.ok().body(response.toString());
    }

    private String userId = null;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestHeader("Stripe-Signature") String sigHeader, @RequestBody String payload) throws Exception {
    
        Event event;
    
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        } 
    
        switch (event.getType()) {
            case "charge.updated":
                System.out.println("got charge webhook");
                return handleChargeSucceeded(event);
            case "checkout.session.completed":
                System.out.println("got session webhook");
                return handleCheckoutSessionCompleted(event);            
            default:

                return ResponseEntity.ok().body("Unhandled event type: " + event.getType());
        }
    }
    
    private ResponseEntity<String> handleChargeSucceeded(Event event) throws StripeException, Exception {

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();
        Map<String, String> response = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        if (stripeObject instanceof Charge) {
            Charge charge = (Charge) stripeObject;
            
            if (userId != null) {
                purchaseService.createChargeRecord(charge, userId);                
                response.put("response", "handle charge success");
                
                String json = mapper.writeValueAsString(response);
                System.out.println("success handle charge");
                return ResponseEntity.ok().body(json.toString());
            } else {
                response.put("response", "fail to handle charge");
                String json = mapper.writeValueAsString(response);
                System.out.println("fail handle charge");
                return ResponseEntity.badRequest().body(json.toString());
            }
        }
        return ResponseEntity.badRequest().body("Invalid Stripe object received for charge.succeeded event");
    }
    
    private ResponseEntity<String> handleCheckoutSessionCompleted(Event event) throws JsonProcessingException {

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();
    
        Map<String, String> response = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        if (stripeObject instanceof Session) {
            Session session = (Session) stripeObject;
            userId = session.getMetadata().get("user_id");
    
            if (userId != null) {                
                response.put("response", "handle session success");

                String json = mapper.writeValueAsString(response);
                System.out.println("success handle session");
                return ResponseEntity.ok().body(json.toString());
            } else {
                response.put("response", "handle session failed");
                String json = mapper.writeValueAsString(response);
                System.out.println("fail handle session");
                return ResponseEntity.badRequest().body(json.toString());
            }
        }
        response.put("response", "invalid stripe object received during checkout");
        String json = mapper.writeValueAsString(response);
        System.out.println("invalid stripe object");
        return ResponseEntity.badRequest().body(json.toString());
    }
    
    
    @GetMapping("/history")
    public ResponseEntity<String> getPurchaseHistory() throws JsonProcessingException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }
         
        List<PurchaseOrder> po = purchaseService.getPurchaseHistory(userId);
        ObjectMapper mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(po);
        return ResponseEntity.ok().body(response.toString());
    }

    @GetMapping("/public-key")
    public ResponseEntity<String> getStripePublicKey() throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        response.put("stripeKey", stripePublicKey);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);
        return ResponseEntity.ok().body(json);
    }
    
  




}
