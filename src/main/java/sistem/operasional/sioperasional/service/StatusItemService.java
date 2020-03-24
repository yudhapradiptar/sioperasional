package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.StatusItemModel;

import java.util.List;

public interface StatusItemService {
    StatusItemModel getStatusItemByIdStatusItem (Long idStatusItem);

    List<StatusItemModel> getListStatusItem();
}
