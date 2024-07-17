package aigc.backend.restcontrollers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import aigc.backend.Utils.BooleanApiResponse;
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
         if (!response.equals("false")) {
            return ResponseEntity.ok().body(response);
         }
         BooleanApiResponse message = new BooleanApiResponse();
         message.setSuccess(true);
         message.setMessage("Empty cart");
         return ResponseEntity.ok().body(message.toJsonString());
    }
    

    @PostMapping("/cart/save")
    public ResponseEntity<String> saveCart(@RequestBody String cartItems) throws JsonProcessingException{
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }
         
         BooleanApiResponse response = new BooleanApiResponse();
         if (cartService.saveCart(cartItems, userId)){
            response.setSuccess(true);
            response.setMessage("200 OK");
            return ResponseEntity.ok().body(response.toJsonString());
         }
         response.setSuccess(false);
         response.setMessage("Bad request");
         return ResponseEntity.badRequest().body(response.toJsonString());
        }
        

   @DeleteMapping("/cart/clear")
   public ResponseEntity<String> clearCart() throws JsonProcessingException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
         if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userDataService.getProfileByUserName(userDetails.getUsername()).getId().toString();
         }

         BooleanApiResponse response = new BooleanApiResponse();
         if (cartService.clearCart(userId)){
            response.setSuccess(true);
            response.setMessage("200 OK");
            return ResponseEntity.ok().body(response.toJsonString());
         }
         response.setSuccess(false);
         response.setMessage("Bad request");
         return ResponseEntity.badRequest().body(response.toJsonString());
        }
    
}
