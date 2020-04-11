package sistem.operasional.sioperasional.repository;
import sistem.operasional.sioperasional.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDB extends JpaRepository<RoleModel, Long> {
}
