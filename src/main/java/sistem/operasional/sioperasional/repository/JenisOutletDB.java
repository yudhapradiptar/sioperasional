package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.JenisOutletModel;

@Repository
public interface JenisOutletDB extends JpaRepository<JenisOutletModel, Long> {

}
