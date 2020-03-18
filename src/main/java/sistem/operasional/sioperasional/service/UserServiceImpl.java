package sistem.operasional.sioperasional.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.repository.UserDB;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDB userDB;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
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



}
