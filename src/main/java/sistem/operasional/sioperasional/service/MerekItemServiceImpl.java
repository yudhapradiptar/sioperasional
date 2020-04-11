package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.MerekItemModel;
import sistem.operasional.sioperasional.repository.MerekItemDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MerekItemServiceImpl implements MerekItemService{
    @Autowired
    MerekItemDB merekItemDB;

    @Override
    public List<MerekItemModel> getMerekItemList(){
        return merekItemDB.findAll();
    }

    @Override
    public void createMerekItem(MerekItemModel merekItemModel){
        merekItemDB.save(merekItemModel);
    }

    @Override
    public MerekItemModel getMerekItemByIdMerekItem(Long idMerekItem){
        return merekItemDB.findById(idMerekItem).get();
    }

    @Override
    public void deleteMerekItem(MerekItemModel merekItemModel){
        merekItemDB.delete(merekItemModel);
    }
}
