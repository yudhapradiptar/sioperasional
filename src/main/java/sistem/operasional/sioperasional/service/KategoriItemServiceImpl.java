package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.repository.KategoriItemDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KategoriItemServiceImpl implements KategoriItemService{
    @Autowired
    KategoriItemDB kategoriItemDB;

    @Override
    public List<KategoriItemModel> getKategoriItemList(){
        return kategoriItemDB.findAll();
    }

    @Override
    public void createKategoriItem(KategoriItemModel kategoriItemModel){
        kategoriItemDB.save(kategoriItemModel);
    }

    @Override
    public KategoriItemModel getKategoriItemByIdKategoriItem(Long idKategoriItem){
        return kategoriItemDB.findById(idKategoriItem).get();
    }

    @Override
    public void deleteKategoriItem(KategoriItemModel kategoriItemModel){
        kategoriItemDB.delete(kategoriItemModel);
    }
}
