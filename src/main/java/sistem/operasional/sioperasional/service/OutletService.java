package sistem.operasional.sioperasional.service;

import java.util.List;
import java.util.Optional;

import sistem.operasional.sioperasional.model.OutletModel;

public interface OutletService {

	List<OutletModel> getOutletList();

	Optional<OutletModel> getOutletByIdOutlet(Long idOutlet);

	void addOutlet(OutletModel outletModel);

	OutletModel changeOutlet(OutletModel outletModel);

}
