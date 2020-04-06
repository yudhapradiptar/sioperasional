package sistem.operasional.sioperasional.service;

import java.util.List;
import java.util.Optional;

import sistem.operasional.sioperasional.model.JenisOutletModel;

public interface JenisOutletService {

	List<JenisOutletModel> getJenisOutletList();

	Optional<JenisOutletModel> getJenisOutletById(Long id);

	void addJenisOutlet(JenisOutletModel jenisOutletModel);

	JenisOutletModel changeJenisOutlet(JenisOutletModel jenisOutletModel);
}
