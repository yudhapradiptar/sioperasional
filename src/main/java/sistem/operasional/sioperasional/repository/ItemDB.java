package sistem.operasional.sioperasional.repository;

import jdk.jshell.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.*;

import java.util.List;

@Repository
public interface ItemDB extends JpaRepository<ItemModel, Long> {
	List<ItemModel> findAllByPurchaseOrder (PurchaseOrderModel purchaseOrder);
	List<ItemModel> findItemByTanggalKeluar(Object object);
	List<ItemModel> findItemModelByKategoriItem(KategoriItemModel kategoriItemModel);
	List<ItemModel> findItemModelByJenisItem(JenisItemModel jenisItemModel);

//	@Query(value = "SELECT * FROM item WHERE nomor_delivery_order IS NULL and is_rusak = ?2 or nomor_delivery_order = ?1", nativeQuery = true)
//	List<ItemModel> findItemAvailableForUpdate(String nomorDeliveryOrder, int i, Object object);

//	@Query(value = "SELECT * FROM item WHERE tanggal_keluar IS NULL and is_rusak = ?1", nativeQuery = true)
//	List<ItemModel> findItemByTanggalKeluarAndNotRusak(int i);
	List<ItemModel> findItemByDeliveryOrder(String nomorDeliveryOrder);
	List<ItemModel> findItemModelByStatusItem(StatusItemModel statusItemModel);


}