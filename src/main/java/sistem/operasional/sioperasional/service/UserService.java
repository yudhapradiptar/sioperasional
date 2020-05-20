package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

public interface UserService {
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
  UserModel changeUserPassword(UserModel user, String password);
  UserModel changeUserPasswordReset(UserModel user);
  UserModel tempUserStatus(UserModel userModel, String status);

}
