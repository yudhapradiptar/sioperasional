package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.VendorModel;

@Repository
public interface VendorDB extends JpaRepository<VendorModel, Long> {
}
