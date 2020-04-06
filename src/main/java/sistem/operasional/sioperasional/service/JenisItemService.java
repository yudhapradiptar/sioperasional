package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.JenisItemModel;

import java.util.List;

public interface JenisItemService {
    List<JenisItemModel> getJenisItemList();

    void createJenisItem(JenisItemModel jenisItemModel);

    JenisItemModel getJenisItemByIdJenisItem(Long idJenisItem);

    void deleteJenisItem(JenisItemModel jenisItemModel);
}
