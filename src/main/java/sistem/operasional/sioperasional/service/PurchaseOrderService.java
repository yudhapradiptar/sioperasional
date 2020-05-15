package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;


public interface PurchaseOrderService {
    List<PurchaseOrderModel> getAll();

    PurchaseOrderModel getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder);

    PurchaseOrderModel addPurchaseOrder(PurchaseOrderModel purchaseOrderModel);

	List<PurchaseOrderModel> getPurchaseOrderListByNotDisetujui();
}

