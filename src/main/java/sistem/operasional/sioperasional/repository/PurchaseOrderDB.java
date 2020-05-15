package sistem.operasional.sioperasional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

@Repository
public interface PurchaseOrderDB extends JpaRepository<PurchaseOrderModel, String> {

	@Query(value = "SELECT * FROM purchase_order WHERE is_disetujui = false", nativeQuery = true)
	List<PurchaseOrderModel> findAllByNotDisetujui();

}
