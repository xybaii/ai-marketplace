package aigc.backend.restcontrollers;


import java.util.List;


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

import aigc.backend.Utils.BooleanApiResponse;
import aigc.backend.models.CheckoutRequest;
import aigc.backend.models.PurchaseOrder;

import aigc.backend.services.PurchaseService;
import aigc.backend.services.StripeService;
import aigc.backend.services.UserDataService;
import jakarta.json.Json;
import jakarta.json.JsonObject;




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
            checkoutRequest.getItemId(),
            userId
        );
      
        String sessionId = session.getId();    
        
        JsonObject response = Json.createObjectBuilder().add("id", sessionId).build();
        return ResponseEntity.ok().body(response.toString());
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestHeader("Stripe-Signature") String sigHeader, @RequestBody String payload) throws Exception {
    
        Event event;
    
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        } 

        if ("charge.updated".equals(event.getType())) {
            System.out.println("got charge webhook");
            return handleChargeSucceeded(event);
        } else {
            return ResponseEntity.ok().body("Unhandled eventtype: " + event.getType());
        }
    }
    
    private ResponseEntity<String> handleChargeSucceeded(Event event) throws StripeException, Exception {

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();

        BooleanApiResponse response = new BooleanApiResponse();
        if (stripeObject instanceof Charge) {
            Charge charge = (Charge) stripeObject;
                purchaseService.createChargeRecord(charge);                
                response.setSuccess(true);
                response.setMessage("200 OK");
                return ResponseEntity.ok().body(response.toJsonString());
            
        }
        response.setSuccess(false);
        response.setMessage("BAD REQUEST");
        return ResponseEntity.badRequest().body(response.toJsonString());
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
        if (po != null) {
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(po);
            return ResponseEntity.ok().body(response.toString());
        }
        BooleanApiResponse response = new BooleanApiResponse();
        response.setSuccess(false);
        response.setMessage("BAD REQUEST");
        return ResponseEntity.badRequest().body(response.toJsonString());

    }

}
