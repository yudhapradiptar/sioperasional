package sistem.operasional.sioperasional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.OutletModel;

@Repository
public interface OutletDB extends JpaRepository<OutletModel, Long> {

}
