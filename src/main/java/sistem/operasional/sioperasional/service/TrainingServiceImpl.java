package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.repository.TrainingDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService{
    @Autowired
    TrainingDB trainingDB;

    @Override
    public List<TrainingModel> getAllTraining(){
        return trainingDB.findAll();
    }
}
