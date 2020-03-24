package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;
import java.util.Optional;


public interface PurchaseOrderService {
    List<PurchaseOrderModel> getAll();
    Optional<PurchaseOrderModel> getById(long id);
    PurchaseOrderModel save(PurchaseOrderModel purchaseOrderModel);
}
