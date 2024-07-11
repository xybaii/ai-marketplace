package aigc.backend.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import aigc.backend.models.User;
import aigc.backend.models.UserUpdateDTO;
import aigc.backend.services.JwtService;
import aigc.backend.services.UserDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UserRestController {
    

    @Autowired
    private UserDataService service;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile() throws JsonProcessingException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = service.getProfileByUserName(userDetails.getUsername());
            if (user != null) {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(user);
                System.out.println(json);
                return ResponseEntity.ok().body(json);
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

    SecurityContextHolder.clearContext();

    ResponseCookie cookie = jwtService.getCleanJwtCookie(); 
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
    Map<String, String> response = new HashMap<>();
    response.put("Msg", "log out success");

    return new ResponseEntity<>(response, headers, HttpStatus.OK);

    
}

@PutMapping("/profile/edit/{id}")
public ResponseEntity<String> postMethodName(@PathVariable String id, @RequestBody UserUpdateDTO updatedProfile) {
    service.updateProfileById(id, updatedProfile);
    Map<String, String> msg = new HashMap<>();
    msg.put("msg", "successfully updated");
    return ResponseEntity.ok().body(msg.toString());
}

    
}
