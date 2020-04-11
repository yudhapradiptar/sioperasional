package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.*;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<ItemModel> getItemListByNomorDeliveryOrder(String nomorDeliveryOrder);

    List<ItemModel> getItemByPurchaseOrder (PurchaseOrderModel purchaseOrder);

    void createItem(ItemModel itemModel);

    List<ItemModel> getItemList();

    List<ItemModel> geItemListByTanggalKeluarNull();

    List<ItemModel> getItemListByKategoriItem(KategoriItemModel kategoriItemModel);

    List<ItemModel> getItemListByMerekItem(MerekItemModel merekItemModel);
}
