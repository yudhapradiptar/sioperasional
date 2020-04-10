package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.JenisItemModel;
import sistem.operasional.sioperasional.repository.JenisItemDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class JenisItemServiceImpl implements JenisItemService{
    @Autowired
    JenisItemDB jenisItemDB;

    @Override
    public List<JenisItemModel> getJenisItemList(){
        return jenisItemDB.findAll();
    }

    @Override
    public void createJenisItem(JenisItemModel jenisItemModel){
        jenisItemDB.save(jenisItemModel);
    }

    @Override
    public JenisItemModel getJenisItemByIdJenisItem(Long idJenisItem){
        return jenisItemDB.findById(idJenisItem).get();
    }

    @Override
    public void deleteJenisItem(JenisItemModel jenisItemModel){
        jenisItemDB.delete(jenisItemModel);
    }
}
