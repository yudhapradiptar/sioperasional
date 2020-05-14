package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.model.JenisItemModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.repository.ItemDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemDB itemDB;

    @Qualifier("itemServiceImpl")
    @Autowired
    ItemService itemService;

    @Override
    public List<ItemModel> getItemByPurchaseOrder (PurchaseOrderModel purchaseOrder){
        return itemDB.findAllByPurchaseOrder(purchaseOrder);
    }

    @Override
    public void createItem(ItemModel itemModel) {
        itemDB.save(itemModel);
    }

    @Override
    public List<ItemModel> getItemList() {
        return itemDB.findAll();
    }

    @Override
    public List<ItemModel> geItemListByTanggalKeluarNull() {
        return itemDB.findItemByTanggalKeluar(null);
    }

    @Override
    public List<ItemModel> getItemListByKategoriItem(KategoriItemModel kategoriItemModel){
        return itemDB.findItemModelByKategoriItem(kategoriItemModel);
    }

    @Override
    public List<ItemModel> getItemListByJenisItem(JenisItemModel jenisItemModel){
        return itemDB.findItemModelByJenisItem(jenisItemModel);
    }

    @Override
    public List<ItemModel> getItemListByNomorDeliveryOrder(String nomorDeliveryOrder) {
        return itemDB.findItemByDeliveryOrder(nomorDeliveryOrder);
    }

    @Override
    public List<ItemModel> geItemListByTanggalKeluarNullAndNotRusak() {
        return itemDB.findItemByTanggalKeluarAndNotRusak(0);
    }

    public ItemModel getItemDetailByIdItem(Long idItem) {
        return itemDB.findById(idItem).get();
    }

    @Override
    public ItemModel updateStatusItem(ItemModel itemModel) {
        System.out.println("start");
        ItemModel newItemModel = itemDB.findById(itemModel.getIdItem()).get();

        // System.out.println(newItemModel.getStatusItem());
        // newItemModel.setRusak(itemModel.isRusak());
        // itemDB.save(newItemModel);
        try {
            newItemModel.setRusak(itemModel.isRusak());
            newItemModel.setStatusItem(itemModel.getStatusItem());
            itemDB.save(newItemModel);
            return newItemModel;
        } catch (NullPointerException nullException) {
            return null;
        }
        // return newItemModel;
    }

    @Override
    public List<ItemModel> getItemListAvailable(String nomorDeliveryOrder) {
        return itemDB.findItemAvailableForUpdate(nomorDeliveryOrder, 0, null);
    }

    @Override
    public List<ItemModel> getItemListByTanggalKeluarNullAndNotRusak() {
        return itemDB.findItemByTanggalKeluarAndNotRusak(0);
    }
}
