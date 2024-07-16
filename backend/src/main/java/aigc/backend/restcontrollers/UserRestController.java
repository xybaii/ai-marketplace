package aigc.backend.restcontrollers;

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

import aigc.backend.Utils.BooleanApiResponse;
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
                return ResponseEntity.ok().body(json);
            } 
        }
        BooleanApiResponse response = new BooleanApiResponse();
        response.setSuccess(false);
        response.setMessage("UNAUTHORIZED");
        return ResponseEntity.status(401).body(response.toJsonString());
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws JsonProcessingException {

        BooleanApiResponse response = new BooleanApiResponse();
        try {
            SecurityContextHolder.clearContext();
            ResponseCookie cookie = jwtService.getCleanJwtCookie(); 
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        
            response.setSuccess(true);
            response.setMessage("200 OK");
            return new ResponseEntity<>(response.toJsonString(), headers, HttpStatus.OK);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("BAD REQUEST");
            return ResponseEntity.badRequest().body(response.toJsonString());
        }
        }

        @PutMapping("/profile/edit/{id}")
        public ResponseEntity<String> postMethodName(@PathVariable String id, @RequestBody UserUpdateDTO updatedProfile) throws JsonProcessingException {

            BooleanApiResponse response = new BooleanApiResponse();
            if (service.updateProfileById(id, updatedProfile)){
                response.setSuccess(true);
                response.setMessage("200 OK");
                return ResponseEntity.ok().body(response.toJsonString());
            }
            response.setSuccess(false);
            response.setMessage("BAD REQUEST");
            return ResponseEntity.badRequest().body(response.toJsonString());
        }

    
}
