package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
=======

>>>>>>> 67ab91a0ed6ff97ea6de62120c15fb2aee2be801
import sistem.operasional.sioperasional.model.UserModel;

@Repository
public interface UserDB extends JpaRepository<UserModel, String> {
<<<<<<< HEAD
=======

	UserModel findByUsername(String  username);
>>>>>>> 67ab91a0ed6ff97ea6de62120c15fb2aee2be801
}
