package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.repository.TrainingDB;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    TrainingDB trainingDB;

    @Override
    public List<TrainingModel> getAllTraining() {
        return trainingDB.findAll();
    }

    @Override
    public List<TrainingModel> getListTrainingByTrainer(UserModel trainer) {
        return trainingDB.findByTrainer(trainer);
    }

    @Override
    public TrainingModel getTrainingByIdTraining(String idTraining) {
        return trainingDB.findByIdTraining(idTraining).get();
    }
    

    @Override
    public void deleteTraining(TrainingModel training) {
        trainingDB.delete(training);
    }

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

    @Override
    public String tanggalFormat(Date tanggal) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggal);
        int tanggalToInt = calendar.get(Calendar.DATE);
        String tanggalString = Integer.toString(tanggalToInt);
        int bulan = calendar.get(Calendar.MONTH)+1;
        String namaBulan = "";
        switch (bulan) {
            case 1:
                namaBulan+="Januari";
                break;
            case 2:
                namaBulan+="Feburari";
                break;
            case 3:
                namaBulan+="Maret";
                break;
            case 4:
                namaBulan+="April";
                break;
            case 5:
                namaBulan+="Mei";
                break;
            case 6:
                namaBulan+="Juni";
                break;
            case 7:
                namaBulan+="Juli";
                break;
            case 8:
                namaBulan+="Agustus";
                break;
            case 9:
                namaBulan+="September";
                break;
            case 10:
                namaBulan+="Oktober";
                break;
            case 11:
                namaBulan+="November";
                break;
            case 12:
                namaBulan+="Desember";
                break;
        }
        int tahun = calendar.get(Calendar.YEAR);
        String tahunString = Integer.toString(tahun);
        return tanggalString + ' ' + namaBulan + ' ' + tahunString;
    }

    @Qualifier("trainingServiceImpl")
    @Autowired
    TrainingService trainingService;

    @Override
    public void createTraining(TrainingModel trainingModel) {
        trainingDB.save(trainingModel);
    }

    @Override
    public List<TrainingModel> getTrainingList() {
        return trainingDB.findAll();
    }

    @Override
    public TrainingModel getTrainingDetail(String idTraining) {
        return trainingDB.findByIdTraining(idTraining).get();
    }

    @Override
    public TrainingModel updateTraining(TrainingModel trainingModel, String status) {
        System.out.println("start");
        TrainingModel newTrainingModel = trainingDB.findByIdTraining(trainingModel.getIdTraining()).get();
        // System.out.println(newItemModel.getStatusItem());
        // newItemModel.setRusak(itemModel.isRusak());
        // itemDB.save(newItemModel);
        try {
            // String temp = status;
            // if (temp.equals("approve")){
            //     temp = "disetujui";
            // }
            // else {
            //     temp = "ditolak";
            // }
            // System.out.println("Temp");
            // System.out.println(temp);
            newTrainingModel.setStatusTraining(status);
            trainingDB.save(newTrainingModel);
            return newTrainingModel;
        } catch (NullPointerException nullException) {
            return null;
        }
        // return newItemModel;
    }    

    @Override
    public List<TrainingModel> getListTrainingByTrainerAndToday(String idUser, String todaysDate) {
        List<TrainingModel> listTrainingToday = new ArrayList<>();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00").format(LocalDateTime.now());
        String pattern = "yyyy-MM-dd 00:00:00";
        DateFormat df = new SimpleDateFormat(pattern);
        for(TrainingModel trainingModel : getAllTraining()){
            String tanggalTraining = df.format(trainingModel.getTanggalTraining());
            if(trainingModel.getTrainer().getId().equals(idUser) && tanggalTraining.equals(todaysDate)){
                listTrainingToday.add(trainingModel);
            }
        }
        return listTrainingToday;
    }

    @Override
    public List<TrainingModel> getAllTrainingByStatusTraining(String string) {
        return trainingDB.findAllByStatusTraining(string);
    }

}
