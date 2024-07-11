package aigc.backend.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LoginResponse {
    
    private String jwtToken;
    private long expiresIn;

    public LoginResponse() {
    }

    public LoginResponse(String jwtToken, long expiresIn) {
        this.jwtToken = jwtToken;
        this.expiresIn = expiresIn;
       
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

 
    public long getExpiresIn() {
        return expiresIn;
    }


    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }


    
    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(this);
        return json;
    }

    
}
