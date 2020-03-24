package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.KategoriItemModel;

import java.util.List;

public interface KategoriItemService {
    List<KategoriItemModel> getKategoriItemList();

    void createKategoriItem(KategoriItemModel kategoriItemModel);

    KategoriItemModel getKategoriItemByIdKategoriItem(Long idKategoriItem);

    void deleteKategoriItem(KategoriItemModel kategoriItemModel);
}
