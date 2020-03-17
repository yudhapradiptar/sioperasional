package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.UserDB;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDB userDB;

    @Override
    public UserModel getUserCurrentLoggedIn() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";

            if (principal instanceof UserDetails) { 
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }
        return userDB.findByUsername(username);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userDB.findByUsername(username);
    }


}