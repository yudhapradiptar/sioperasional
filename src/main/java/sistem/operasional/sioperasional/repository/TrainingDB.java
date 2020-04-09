package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

@Repository
public interface TrainingDB extends JpaRepository<TrainingModel, Long> {
    List<TrainingModel> findByTrainer(UserModel trainer);
=======

import sistem.operasional.sioperasional.model.TrainingModel;

@Repository
public interface TrainingDB extends JpaRepository<TrainingModel, Long> {
    
>>>>>>> 949e12b42daf97540d4e99741e7afb0bf1c26496
}
