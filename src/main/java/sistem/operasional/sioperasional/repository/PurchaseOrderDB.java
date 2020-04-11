package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;


@Repository
public interface PurchaseOrderDB extends JpaRepository<PurchaseOrderModel, String> {

}
