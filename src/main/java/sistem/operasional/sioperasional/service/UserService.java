package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

public interface UserService {

    UserModel getUserCurrentLoggedIn();
    List<UserModel> getAllUser();

    UserModel getUserByUsername(String username);
    UserModel addUser(UserModel user);
    public String encrypt(String password);
    boolean checkPass(UserModel user, String pass);
    UserModel findByUsername(String username);
    UserModel updateUser (UserModel user);
    boolean verifPass(String pass);
    boolean verifUser(UserModel user);

}
