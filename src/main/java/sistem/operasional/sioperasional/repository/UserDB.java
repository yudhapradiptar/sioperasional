package sistem.operasional.sioperasional.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.UserModel;

@Repository
public interface UserDB extends JpaRepository<UserModel, String> {

	UserModel findByUsername(String username);
	Optional<UserModel> findById(String id);

}
