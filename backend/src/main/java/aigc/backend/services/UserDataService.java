package aigc.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aigc.backend.models.User;
import aigc.backend.models.UserUpdateDTO;
import aigc.backend.repositories.UserRepository;

@Service
public class UserDataService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getProfileByUserName(String username){

        User user = repo.findByUsername(username);
        if (user != null) {
            return repo.findByUsername(username);
        }
        throw new UsernameNotFoundException("User not found in the database");
    }

    
   public void updateProfileById(String id, UserUpdateDTO updatedProfile){
        updatedProfile.setPassword(bCryptPasswordEncoder.encode(updatedProfile.getPassword()));
        repo.updateUserProfileById(id, updatedProfile);
   }
}
