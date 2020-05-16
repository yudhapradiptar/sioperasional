package sistem.operasional.sioperasional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;

@Repository
public interface DeliveryOrderDB extends JpaRepository<DeliveryOrderModel, String> {

	List<DeliveryOrderModel> findAllByOrderByNomorDeliveryOrderAsc();

	DeliveryOrderModel findByNomorDeliveryOrder(String nomor);

//	@Query(value = "SELECT * FROM delivery_order WHERE is_subscribed = 1", nativeQuery = true)
//	List<DeliveryOrderModel> findDeliveryOrderByIsSubscribed();
//
//	@Query(value = "SELECT * FROM delivery_order WHERE is_subscribed = 1 AND tanggal_subscribe_start is null", nativeQuery = true)
//	List<DeliveryOrderModel> findDeliveryOrderByIsSubscribedAndTanggalSubscribeStartNull();

}
