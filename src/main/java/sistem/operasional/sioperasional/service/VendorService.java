package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.VendorModel;

import java.util.List;
import java.util.Optional;

public interface VendorService {
    List<VendorModel> getVendorList();

    Optional<VendorModel> getVendorByIdVendor(Long idVendor);

    void addVendor(VendorModel vendorModel);

    void deleteVendor(VendorModel jenisItemModel);
}
