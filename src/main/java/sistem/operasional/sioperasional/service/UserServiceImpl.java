package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.repository.UserDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements  UserService{
    @Autowired
    UserDB userDB;

    @Override
    public List<UserModel> getAllUser(){
        return userDB.findAll();
    }
}
