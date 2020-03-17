package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.UserModel;

public interface UserService {

	UserModel getUserCurrentLoggedIn();

	UserModel getUserByUsername(String username);
}
