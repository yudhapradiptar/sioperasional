package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;

import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAllTraining();

    List<TrainingModel> getListTrainingByTrainer(UserModel trainer);

    TrainingModel getTrainingByIdTraining(String idTraining);

    void createTraining(TrainingModel training);

    void deleteTraining(TrainingModel training);

    TrainingModel editTraining(TrainingModel training);

}
