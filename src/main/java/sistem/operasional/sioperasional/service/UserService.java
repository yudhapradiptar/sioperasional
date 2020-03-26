package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.UserModel;

<<<<<<< HEAD
import java.util.List;

public interface UserService {
    List<UserModel> getAllUser();
=======
public interface UserService {

	UserModel getUserCurrentLoggedIn();

	UserModel getUserByUsername(String username);
  UserModel addUser(UserModel user);
  public String encrypt(String password);
  boolean checkPass(UserModel user, String pass);
  UserModel findByUsername(String username);
  UserModel updateUser (UserModel user);
  boolean verifPass(String pass);
  boolean verifUser(UserModel user);

>>>>>>> 67ab91a0ed6ff97ea6de62120c15fb2aee2be801
}
