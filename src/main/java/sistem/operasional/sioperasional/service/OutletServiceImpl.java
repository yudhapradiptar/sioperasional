package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.OutletModel;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.OutletDB;

@Service
public class OutletServiceImpl implements OutletService {

    @Autowired
    private OutletDB outletDB;

    @Override
    public List<OutletModel> getOutletList() {
        return outletDB.findAll();
    }

    @Override
    public Optional<OutletModel> getOutletByIdOutlet(Long idOutlet) {
        return outletDB.findById(idOutlet);
    }

    @Override
    public void addOutlet(OutletModel outletModel) {
        outletDB.save(outletModel);
    }

    @Override
    public OutletModel changeOutlet(OutletModel outletModel) {
        OutletModel newOutletModel = outletDB.findById(outletModel.getIdOutlet()).get();

        try {
            newOutletModel.setAlamatOutlet(outletModel.getAlamatOutlet());
            newOutletModel.setEmailOutlet(outletModel.getEmailOutlet());
            newOutletModel.setJenisOutlet(outletModel.getJenisOutlet());
            newOutletModel.setNoTelpOutlet(outletModel.getNoTelpOutlet());
            newOutletModel.setNamaOutlet(outletModel.getNamaOutlet());
            newOutletModel.setAktif(outletModel.isAktif());

            outletDB.save(newOutletModel);
            return newOutletModel;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    
}