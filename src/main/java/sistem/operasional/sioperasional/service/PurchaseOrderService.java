package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.PurchaseOrderModel;

public interface PurchaseOrderService {
    PurchaseOrderModel getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder);
}
