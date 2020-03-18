package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistem.operasional.sioperasional.model.UserModel;


public interface UserDB extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}