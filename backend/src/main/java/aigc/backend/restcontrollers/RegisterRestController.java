package aigc.backend.restcontrollers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aigc.backend.Utils.BooleanApiResponse;
import aigc.backend.models.User;
import aigc.backend.services.UserAuthenticateService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping("/api")
public class RegisterRestController {

    @Autowired
    private UserAuthenticateService userAuthenticateService;
 
    @PostMapping("/register/process")
    public ResponseEntity<String> registerUser(@RequestBody String payload) throws JsonProcessingException {
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();
        User user = new User();
        user.setUsername(json.getString("username"));
        user.setPassword(json.getString("password"));
        user.setFirstname(json.getString("firstname"));
        user.setLastname(json.getString("lastname"));
        user.setEmail(json.getString("email"));

        BooleanApiResponse response = new BooleanApiResponse();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        if (userAuthenticateService.registerNewUser(user)){
            
            response.setSuccess(true);
            response.setMessage("Registered successfully");
            jsonString = mapper.writeValueAsString(response);
            return ResponseEntity.ok().body(jsonString);
        }
        return ResponseEntity.badRequest().body(jsonString);
    }
}
 