package aigc.backend.restcontrollers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aigc.backend.services.CartService;
import aigc.backend.services.UserDataService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/cart")
    public ResponseEntity<String> getCartItems() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }
         String response = cartService.getCart(userId);
         return ResponseEntity.ok().body(response);
    }
    

    @PostMapping("/cart/save")
    public ResponseEntity<String> saveCart(@RequestBody String cartItems) throws JsonProcessingException{
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }
         
         try {
            cartService.saveCart(cartItems, userId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, String> response = new HashMap<>();
        response.put("msg", "cart data saved to mongo");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);
        return ResponseEntity.ok().body(json.toString());
        }

        @DeleteMapping("/cart/clear")
        public ResponseEntity<String> clearCart() throws JsonProcessingException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }

         cartService.clearCart(userId);
         // System.out.println("cart cleared");
         // JsonObjectBuilder json = Json.createObjectBuilder().add("msg", "cart cleared");
         Map<String, String> response = new HashMap<>();
         response.put("msg", "cart data deleted from mongo");
         ObjectMapper mapper = new ObjectMapper();
         String json = mapper.writeValueAsString(response);
        return ResponseEntity.ok().body(json.toString());

        }
    
}
