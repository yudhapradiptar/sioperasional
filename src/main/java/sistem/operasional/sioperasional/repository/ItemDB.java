package sistem.operasional.sioperasional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;

@Repository
public interface ItemDB extends JpaRepository<ItemModel, String> {

	List<ItemModel> findItemByTanggalKeluar(Object object);

	@Query(value = "SELECT * FROM item WHERE nomor_delivery_order IS NULL or nomor_delivery_order = ?1", nativeQuery = true)
	List<ItemModel> findItemByDeliveryOrder(String nomorDeliveryOrder, Object object);

	@Query(value = "SELECT * FROM item WHERE tanggal_keluar IS NULL and is_rusak = ?1", nativeQuery = true)
	List<ItemModel> findItemByTanggalKeluarAndNotRusak(int i);


}
