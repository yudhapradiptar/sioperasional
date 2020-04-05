package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.TrainingDB;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDB trainingDB;

}
