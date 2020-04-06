package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.model.JenisItemModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import java.util.List;

@Repository
public interface ItemDB extends JpaRepository<ItemModel, Long> {
    List<ItemModel> findAllByPurchaseOrder (PurchaseOrderModel purchaseOrder);
    List<ItemModel> findItemByTanggalKeluar(Object object);
    List<ItemModel> findItemModelByKategoriItem(KategoriItemModel kategoriItemModel);
    List<ItemModel> findItemModelByJenisItem(JenisItemModel jenisItemModel);

}
