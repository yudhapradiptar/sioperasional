package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;

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

    @Override
    public void addDeliveryOrder(DeliveryOrderModel deliveryOrderModel) {
        deliveryOrderDB.save(deliveryOrderModel);
    }

    @Override
    public DeliveryOrderModel changeDeliveryOrder(DeliveryOrderModel deliveryOrderModel) {
        DeliveryOrderModel newDeliveryOrderModel = deliveryOrderDB.findByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());

        try {
            newDeliveryOrderModel.setCreator(deliveryOrderModel.getCreator());
            newDeliveryOrderModel.setListItem(deliveryOrderModel.getListItem());
            newDeliveryOrderModel.setNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());
            newDeliveryOrderModel.setNomorInvoice(deliveryOrderModel.getNomorInvoice());
            newDeliveryOrderModel.setOutlet(deliveryOrderModel.getOutlet());
            newDeliveryOrderModel.setStatusDO(deliveryOrderModel.getStatusDO());
            newDeliveryOrderModel.setSubscribed(deliveryOrderModel.getSubscribed());
            newDeliveryOrderModel.setTanggalSubscribeEnd(deliveryOrderModel.getTanggalSubscribeEnd());
            newDeliveryOrderModel.setTanggalSubscribeStart(deliveryOrderModel.getTanggalSubscribeStart());
            newDeliveryOrderModel.setTanggalCreate(deliveryOrderModel.getTanggalCreate());
            deliveryOrderDB.save(newDeliveryOrderModel);
            return newDeliveryOrderModel;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

}