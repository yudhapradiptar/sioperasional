package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.ItemPOModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;

public interface ItemPOService {
    List<ItemPOModel> getItemPObyPurchaseOrder (PurchaseOrderModel purchaseOrder);

    List<ItemPOModel> getAllItemPO();

    ItemPOModel addItemPO(ItemPOModel itemPOModel);

}
