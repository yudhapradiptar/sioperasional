package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.TrainingModel;

@Repository
public interface TrainingDB extends JpaRepository<TrainingModel, Long> {
    
}
