package sistem.operasional.sioperasional.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.RoleModel;
import sistem.operasional.sioperasional.repository.RoleDB;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDB roleDB;

    public List<RoleModel> findAll(){
        return roleDB.findAll();
    }
}