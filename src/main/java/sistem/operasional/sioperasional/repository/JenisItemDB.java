package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.JenisItemModel;

@Repository
public interface JenisItemDB extends JpaRepository<JenisItemModel, Long> {
}
