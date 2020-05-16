package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAllTraining();

    List<TrainingModel> getListTrainingByTrainer(UserModel trainer);

    List<TrainingModel> getTrainingList();

    TrainingModel getTrainingByIdTraining(String idTraining);

    TrainingModel getTrainingDetail(String idTraining);

    TrainingModel updateTraining(TrainingModel trainingModel, String status);

    void createTraining(TrainingModel training);

    void deleteTraining(TrainingModel training);

    TrainingModel editTraining(TrainingModel training);

    String tanggalFormat(Date tanggal);

}
