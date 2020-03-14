package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;

import java.util.List;

@Repository
public interface CustomerFeedbackDB extends JpaRepository<CustomerFeedbackModel, Long> {

}
