package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.VendorModel;
import sistem.operasional.sioperasional.repository.VendorDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VendorServiceImpl implements VendorService{

    @Autowired
    VendorDB vendorDB;

    @Override
    public List<VendorModel> getVendorList() {
        return vendorDB.findAll();
    }

}
