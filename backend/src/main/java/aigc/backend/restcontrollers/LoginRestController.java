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
    

    @PostMapping("/login/process")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginrequest) throws JsonProcessingException {
        
        User registeredUser = userAuthenticateService.authenticate(loginrequest);
        ResponseCookie[] cookies = jwtService.generateJwtCookie(registeredUser);

        HttpHeaders headers = new HttpHeaders();
        for (ResponseCookie cookie : cookies) {
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        BooleanApiResponse response = new BooleanApiResponse();
        if (registeredUser != null) {
            response.setSuccess(true);
            response.setMessage("200 OK");
            return new ResponseEntity<String>(response.toJsonString(), headers, HttpStatus.OK);
        } else {
            response.setSuccess(false);
            response.setMessage("403 Forbidden");
            return new ResponseEntity<String>(response.toJsonString(), headers, HttpStatus.FORBIDDEN);
        }   
        
    }


}
