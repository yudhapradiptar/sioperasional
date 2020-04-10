package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.JenisOutletModel;
import sistem.operasional.sioperasional.model.OutletModel;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.JenisOutletDB;

@Service
public class JenisOutletServiceImpl implements JenisOutletService {

    @Autowired
    private JenisOutletDB jenisOutletDB;

    @Override
    public List<JenisOutletModel> getJenisOutletList() {
        return jenisOutletDB.findAll();
    }

    @Override
    public Optional<JenisOutletModel> getJenisOutletById(Long id) {
        return jenisOutletDB.findById(id);
    }

    @Override
    public void addJenisOutlet(JenisOutletModel jenisOutletModel) {
        jenisOutletDB.save(jenisOutletModel);
    }

    @Override
    public JenisOutletModel changeJenisOutlet(JenisOutletModel jenisOutletModel) {
        JenisOutletModel newJenisOutletModel = jenisOutletDB.findById(jenisOutletModel.getIdJenisOutlet()).get();
        try {
            newJenisOutletModel.setNamaJenisOutlet(jenisOutletModel.getNamaJenisOutlet());
            jenisOutletDB.save(newJenisOutletModel);
            return newJenisOutletModel;
        } catch (NullPointerException nullException) {
            return null;
        }
    }
}