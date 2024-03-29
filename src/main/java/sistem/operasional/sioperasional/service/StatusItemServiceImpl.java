package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.StatusItemModel;
import sistem.operasional.sioperasional.repository.StatusItemDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StatusItemServiceImpl implements StatusItemService{
    @Autowired
    StatusItemDB statusItemDB;

    @Override
    public StatusItemModel getStatusItemByIdStatusItem (Long idStatusItem){
        return statusItemDB.findByIdStatusItem(idStatusItem);
    }

    @Override
    public List<StatusItemModel> getListStatusItem(){
        return statusItemDB.findAll();
    }

    @Override
    public void createStatusItem(StatusItemModel statusItem) {
        statusItemDB.save(statusItem);
    }

    @Override
    public void deleteStatusItem(StatusItemModel statusItemModel){
        statusItemDB.delete(statusItemModel);
    }

}
