package sistem.operasional.sioperasional.service;
import sistem.operasional.sioperasional.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.UserDB;
import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sistem.operasional.sioperasional.service.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
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
    public List<UserModel> getAllUser(){
        return userDB.findAll();
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userDB.findByUsername(username);
    }

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        System.out.println(user.getPassword() +" "+ pass);
        user.setPassword(pass);

        return userDB.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public boolean checkPass(UserModel user, String password){
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        return enc.matches(password, user.getPassword());
    }

    @Override
    public UserModel findByUsername(String username){
        return userDB.findByUsername(username);
    }

    @Override
    public UserModel getUserById(String id){
        return userDB.findById(id).get();
    }

    @Override
    public UserModel updateUser(UserModel user){
        UserModel oldUser = userDB.findByUsername(user.getUsername());
        String password = encrypt(user.getPassword());
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(password);
        userDB.save(oldUser);
        return oldUser;
    }

    @Override
    public boolean verifPass(String pass){
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$";
        return Pattern.matches(regex,pass);
    }

    @Override
    public boolean verifUser(UserModel user){
        boolean hasil = true;
        List<UserModel> userDaftar = userDB.findAll();
        for (UserModel a : userDaftar) {
            if (a.getUsername().equals(user.getUsername())){
                hasil = false;
                break;
            }
        }
        return hasil;
    }

    @Override
    public UserModel changeUser(UserModel userModel) {
        UserModel targetUser = userDB.findByUsername(userModel.getUsername());
        try {
            targetUser.setNama(userModel.getNama());
            targetUser.setRole(userModel.getRole());
            targetUser.setKode(userModel.getKode());
            targetUser.setStatus(userModel.getStatus());
            targetUser.setUsername(userModel.getUsername());
            userDB.save(targetUser);
            return targetUser;
        } catch (NullPointerException nullException) {
            return null;
        }
    }
}