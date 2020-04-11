package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.model.JenisItemModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;

public interface ItemService {
    List<ItemModel> getItemByPurchaseOrder (PurchaseOrderModel purchaseOrder);

    void createItem(ItemModel itemModel);

    List<ItemModel> getItemList();

    List<ItemModel> geItemListByTanggalKeluarNull();

    List<ItemModel> getItemListByKategoriItem(KategoriItemModel kategoriItemModel);
    
    ItemModel getItemDetailByIdItem(Long idItem);
    
    ItemModel updateStatusItem(ItemModel item);
  
	  List<ItemModel> geItemListByTanggalKeluarNullAndNotRusak();

    List<ItemModel> getItemListByNomorDeliveryOrder(String nomorDeliveryOrder);
    List<ItemModel> getItemListByJenisItem(JenisItemModel jenisItemModel);

	List<ItemModel> getItemListAvailable(String nomorDeliveryOrder);
}
