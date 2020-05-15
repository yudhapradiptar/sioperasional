package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAllTraining();

    List<TrainingModel> getListTrainingByTrainer(UserModel trainer);

    TrainingModel getTrainingByIdTraining(String idTraining);

    void createTraining(TrainingModel training);

    void deleteTraining(TrainingModel training);

    TrainingModel editTraining(TrainingModel training);

    String tanggalFormat(Date tanggal);

	List<TrainingModel> getListTrainingByTrainerAndToday(String idUser, String date);

	List<TrainingModel> getAllTrainingByStatusTraining(String string);

}
