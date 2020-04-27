package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.DeliveryOrderModel;
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
    public TrainingModel getTrainingByIdTraining(String idTraining){
        return trainingDB.findByIdTraining(idTraining).get();
    }

    @Override
    public void createTraining(TrainingModel training){trainingDB.save(training);}

    @Override
    public void deleteTraining(TrainingModel training){trainingDB.delete(training);}

    @Override
    public TrainingModel editTraining(TrainingModel training) {
        TrainingModel newTraining = trainingDB.findByIdTraining(training.getIdTraining()).get();

        try {
            newTraining.setBayar(training.getBayar());
            newTraining.setTanggalRequest(training.getTanggalRequest());
            newTraining.setStatusTraining("Menunggu Persetujuan");
            newTraining.setTanggalTraining(training.getTanggalTraining());
            newTraining.setWaktuMulai(training.getWaktuMulai());
            newTraining.setWaktuSelesai(training.getWaktuSelesai());
            newTraining.setTrainer(training.getTrainer());
            newTraining.setOutlet(training.getOutlet());
            newTraining.setKeteranganTraining(training.getKeteranganTraining());
            trainingDB.save(newTraining);

            return newTraining;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

}
