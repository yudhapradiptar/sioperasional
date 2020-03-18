package sistem.operasional.sioperasional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistem.operasional.sioperasional.model.ItemModel;

@Repository
public interface ItemDB extends JpaRepository<ItemModel, String> {

	List<ItemModel> findItemByTanggalKeluar(Object object);

}
