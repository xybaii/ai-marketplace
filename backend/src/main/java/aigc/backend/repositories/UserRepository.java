package aigc.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import aigc.backend.models.User;
import aigc.backend.models.UserUpdateDTO;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_FIND_USER_BY_USERNAME = """
            SELECT * FROM users WHERE username = ?
            """;
    private final static String SQL_SAVE_USER_REGISTRATION = """
            INSERT INTO users (username, password, firstname, lastname, email, authorities) VALUES (?, ?, ?, ?, ?, ?)
            """;
    private final static String SQL_UPDATE_USER_PROFILE_BY_ID = """
            UPDATE users SET password = ?, firstname = ?, lastname = ?, email = ? WHERE id = ?
            """;



    public User findByUsername(String username){

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USER_BY_USERNAME, username);
        if (rs.next()) {
            return new User().rsToModel(rs);
        } else {
            throw new UsernameNotFoundException("User not found in the database");
        }
    }

    public Boolean saveUser(User registrationRequest) {
        try {
            jdbcTemplate.update(SQL_SAVE_USER_REGISTRATION, registrationRequest.getUsername(), registrationRequest.getPassword(), registrationRequest.getFirstname(), registrationRequest.getLastname(), registrationRequest.getEmail(), "ROLE_USER");
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserProfileById(String id, UserUpdateDTO updatedProfile) {
        jdbcTemplate.update(SQL_UPDATE_USER_PROFILE_BY_ID, updatedProfile.getPassword(), updatedProfile.getFirstname(), updatedProfile.getLastname(), updatedProfile.getEmail(), id);
    }

   

}
