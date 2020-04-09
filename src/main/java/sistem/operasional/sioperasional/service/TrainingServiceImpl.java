package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;
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

    @Override
    public List<TrainingModel> getListTrainingByTrainer(UserModel trainer){
        return trainingDB.findByTrainer(trainer);
    }

    @Override
    public TrainingModel getTrainingByIdTraining(Long idTraining){
        return trainingDB.findById(idTraining).get();
    }
=======

import sistem.operasional.sioperasional.repository.TrainingDB;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDB trainingDB;

>>>>>>> 949e12b42daf97540d4e99741e7afb0bf1c26496
}
