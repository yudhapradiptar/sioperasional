package sistem.operasional.sioperasional.service;

import java.util.List;
import java.util.Optional;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;

public interface ItemService {

	List<ItemModel> getItemList();

	List<ItemModel> geItemListByTanggalKeluarNull();

	List<ItemModel> getItemListByNomorDeliveryOrder(String nomorDeliveryOrder);

}
