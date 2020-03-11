package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.StatusItemModel;
import sistem.operasional.sioperasional.repository.StatusItemDB;

import javax.transaction.Transactional;

@Service
@Transactional
public class StatusItemServiceImpl implements StatusItemService{
    @Autowired
    StatusItemDB statusItemDB;
    public StatusItemModel getStatusItemByIdStatusItem (Long idStatusItem){
        return statusItemDB.findByIdStatusItem(idStatusItem);
    }
}
