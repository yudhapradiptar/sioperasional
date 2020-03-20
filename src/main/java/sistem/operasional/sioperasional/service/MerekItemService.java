package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.MerekItemModel;

import java.util.List;

public interface MerekItemService {
    List<MerekItemModel> getMerekItemList();

    void createMerekItem(MerekItemModel merekItemModel);

    MerekItemModel getMerekItemByIdMerekItem(Long idMerekItem);

    void deleteMerekItem(MerekItemModel merekItemModel);
}
