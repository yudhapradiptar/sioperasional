package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.ItemPOModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;

@Repository
public interface ItemPODB extends JpaRepository<ItemPOModel, Long> {
    List<ItemPOModel> findAllByPurchaseOrder (PurchaseOrderModel purchaseOrder);


}
