package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.ItemPOModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.repository.ItemPODB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemPOServiceImpl implements ItemPOService{

    @Autowired
    ItemPODB itemPODB;

    @Qualifier("itemPOServiceImpl")
    @Autowired
    ItemPOService itemPOService;

    @Override
    public List<ItemPOModel> getItemPObyPurchaseOrder (PurchaseOrderModel purchaseOrder) {
        return itemPODB.findAllByPurchaseOrder(purchaseOrder);
    }

    @Override
    public List<ItemPOModel> getAllItemPO (){
        return itemPODB.findAll();
    }

    @Override
    public ItemPOModel addItemPO(ItemPOModel itemPOModel) {
        return itemPODB.save(itemPOModel);
    }

}
