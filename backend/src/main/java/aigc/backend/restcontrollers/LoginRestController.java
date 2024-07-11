package aigc.backend.restcontrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aigc.backend.Utils.BooleanApiResponse;
import aigc.backend.models.LoginRequest;
import aigc.backend.models.User;
import aigc.backend.services.JwtService;
import aigc.backend.services.UserAuthenticateService;


@Controller
@RequestMapping("/api")
public class LoginRestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserAuthenticateService userAuthenticateService;
    

    // @PostMapping("/login/process")
    // public String login(@ModelAttribute LoginRequest loginrequest, HttpServletResponse response) throws JsonProcessingException {
        
    //     User registeredUser = userAuthenticateService.authenticate(loginrequest);
        

    //     ResponseCookie[] cookies = jwtService.generateJwtCookie(registeredUser);

    //     // response.setHeader(HttpHeaders.SET_COOKIE, jwtService.generateJwtCookie(registeredUser).toString());

    //     for (ResponseCookie cookie : cookies) {
    //         response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    //     }
        

    //     return "redirect:https://localhost:4200/#/profile";
    // }

    @PostMapping("/login/process")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginrequest) throws JsonProcessingException {
        
        User registeredUser = userAuthenticateService.authenticate(loginrequest);
        ResponseCookie[] cookies = jwtService.generateJwtCookie(registeredUser);

        HttpHeaders headers = new HttpHeaders();
        for (ResponseCookie cookie : cookies) {
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        
        BooleanApiResponse response = new BooleanApiResponse();
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        if (registeredUser != null) {
            response.setSuccess(true);
            response.setMessage("Successfully logged in");
            json = mapper.writeValueAsString(response);
            return new ResponseEntity<String>(json, headers, HttpStatus.OK);
        } else {
            response.setSuccess(false);
            response.setMessage("Failed to log in. Please try again later.");
            return new ResponseEntity<String>(json, headers, HttpStatus.FORBIDDEN);
        }
        
        
    }


}
