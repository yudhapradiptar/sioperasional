package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.repository.ItemDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemDB itemDB;

    @Qualifier("itemServiceImpl")
    @Autowired
    ItemService itemService;

    @Override
    public List<ItemModel> getItemByPurchaseOrder (PurchaseOrderModel purchaseOrder){
        return itemDB.findAllByPurchaseOrder(purchaseOrder);
    }

    @Override
    public void createItem(ItemModel itemModel) {
        itemDB.save(itemModel);
    }



}
