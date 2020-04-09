package sistem.operasional.sioperasional.service;

import java.util.List;

import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

public interface UserService {

<<<<<<< HEAD
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
=======
  UserModel getUserCurrentLoggedIn();
  UserModel getUserById(String id);
  UserModel getUserByUsername(String username);
  List<UserModel> getAllUser();
  UserModel addUser(UserModel user);
  public String encrypt(String password);
  boolean checkPass(UserModel user, String pass);
  UserModel findByUsername(String username);
  UserModel updateUser (UserModel user);
  boolean verifPass(String pass);
  boolean verifUser(UserModel user);
  UserModel changeUser(UserModel user);
>>>>>>> 949e12b42daf97540d4e99741e7afb0bf1c26496

}
