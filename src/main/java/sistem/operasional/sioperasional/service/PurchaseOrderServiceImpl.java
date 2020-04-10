package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;

import javax.transaction.Transactional;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{
    @Autowired
    PurchaseOrderDB purchaseOrderDB;

    public PurchaseOrderModel getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder){
        return purchaseOrderDB.findById(nomorPurchaseOrder).get();
    }

    @Override
    public PurchaseOrderModel addPurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        return purchaseOrderDB.save(purchaseOrderModel);
    }

}
