package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

@Repository
public interface TrainingDB extends JpaRepository<TrainingModel, Long> {
    List<TrainingModel> findByTrainer(UserModel trainer);
}
