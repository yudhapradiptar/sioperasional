package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.StatusItemModel;

@Repository
public interface StatusItemDB extends JpaRepository<StatusItemModel, Long> {
    StatusItemModel findByIdStatusItem (Long idStatusItem);
}
