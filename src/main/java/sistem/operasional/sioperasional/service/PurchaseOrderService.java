package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


public interface PurchaseOrderService {
    List<PurchaseOrderModel> getAll();

    Optional<PurchaseOrderModel> getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder);

    List<PurchaseOrderModel> getPurchaseOrderListByNotDisetujui();

    PurchaseOrderModel changePurchaseOrder(PurchaseOrderModel purchaseOrderModel);

    void savePurchaseOrder(PurchaseOrderModel purchaseOrderModel);

    void deletePurchaseOrder(PurchaseOrderModel purchaseOrderModel);

}

