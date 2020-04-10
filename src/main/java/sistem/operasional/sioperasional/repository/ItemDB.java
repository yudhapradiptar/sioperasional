package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.model.MerekItemModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;

@Repository
public interface ItemDB extends JpaRepository<ItemModel, Long> {
    List<ItemModel> findAllByPurchaseOrder (PurchaseOrderModel purchaseOrder);
    List<ItemModel> findItemByTanggalKeluar(Object object);
    List<ItemModel> findItemModelByKategoriItem(KategoriItemModel kategoriItemModel);
    List<ItemModel> findItemModelByMerekItem(MerekItemModel merekItemModel);
    List<ItemModel> findByIdItem(Long idItem);

	@Query(value = "SELECT * FROM item WHERE nomor_delivery_order IS NULL or nomor_delivery_order = ?1", nativeQuery = true)
	List<ItemModel> findItemByDeliveryOrder(String nomorDeliveryOrder, Object object);

}
