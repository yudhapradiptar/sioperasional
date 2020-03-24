package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.KategoriItemModel;

@Repository
public interface KategoriItemDB extends JpaRepository<KategoriItemModel, Long> {

}
