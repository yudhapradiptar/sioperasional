package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;

import java.util.List;
import java.util.Optional;




@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    @Autowired
    PurchaseOrderDB purchaseOrderDB;

    @Override
    public List<PurchaseOrderModel> getAll() {
        return purchaseOrderDB.findAll();
    }

    @Override
    public PurchaseOrderModel addPurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        return purchaseOrderDB.save(purchaseOrderModel);
    }

    @Override
    public PurchaseOrderModel getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder){
        return purchaseOrderDB.findById(nomorPurchaseOrder).get();

    }
}
