package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.DeliveryOrderDB;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {
    
    @Autowired
    private DeliveryOrderDB deliveryOrderDB;

    @Override
    public List<DeliveryOrderModel> getDeliveryOrderList() {
        return deliveryOrderDB.findAllByOrderByNomorDeliveryOrderAsc();
    }

    @Override
    public DeliveryOrderModel getDeliveryOrderByNomorDeliveryOrder(String nomor) {
        return deliveryOrderDB.findByNomorDeliveryOrder(nomor);
    }

}