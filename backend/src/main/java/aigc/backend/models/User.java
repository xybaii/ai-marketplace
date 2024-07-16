package aigc.backend.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails{
    
    private Integer id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String firstname;
    private String lastname;
    private String email;
    
    public User() {
    }

    public User(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities,
            String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    User other = (User) obj;
    return Objects.equals(id, other.id) &&
            Objects.equals(username, other.username) &&
           Objects.equals(password, other.password) &&
           Objects.equals(authorities, other.authorities) &&
           Objects.equals(firstname, other.firstname) &&
           Objects.equals(lastname, other.lastname) &&
           Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, authorities, firstname, lastname, email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("null")
    public User rsToModel(SqlRowSet rs){
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setAuthorities(Arrays.stream(rs.getString("authorities").split(","))
                                  .map(SimpleGrantedAuthority::new)
                                  .collect(Collectors.toList()));
        return user;
    } 
    
}
