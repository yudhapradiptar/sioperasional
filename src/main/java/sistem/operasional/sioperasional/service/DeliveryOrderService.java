package sistem.operasional.sioperasional.service;

import java.util.List;
import java.util.Optional;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;

public interface DeliveryOrderService {

	List<DeliveryOrderModel> getDeliveryOrderList();

	DeliveryOrderModel getDeliveryOrderByNomorDeliveryOrder(String nomor);

	void addDeliveryOrder(DeliveryOrderModel deliveryOrderModel);

	DeliveryOrderModel changeDeliveryOrder(DeliveryOrderModel deliveryOrderModel);

	List<DeliveryOrderModel> getDeliveryOrderListBySubscribed();

	List<DeliveryOrderModel> getDeliveryOrderListBySubscribedAndTanggalSubcribeStartNull();

}
