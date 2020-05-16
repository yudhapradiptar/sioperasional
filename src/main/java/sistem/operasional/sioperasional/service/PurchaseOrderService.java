package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface PurchaseOrderService {
    List<PurchaseOrderModel> getAll();

    PurchaseOrderModel getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder);

    PurchaseOrderModel addPurchaseOrder(PurchaseOrderModel purchaseOrderModel);

    boolean createPdf(PurchaseOrderModel purchaseOrderModel, ServletContext context,
                             HttpServletRequest request, HttpServletResponse response);

    List<PurchaseOrderModel> getPurchaseOrderListByNotDisetujui();

}

