package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;
import sistem.operasional.sioperasional.repository.CustomerFeedbackDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService{

    @Autowired
    CustomerFeedbackDB customerFeedbackDB;

    @Qualifier("customerFeedbackServiceImpl")
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Override
    public List<CustomerFeedbackModel> getAllCustomerFeedback() {
        return customerFeedbackDB.findAll();
    }

    @Override
    public CustomerFeedbackModel getCustomerFeedbackById(Long idCustomerFeedback){
        return customerFeedbackDB.findById(idCustomerFeedback).get();
    }

    @Override
    public List<CustomerFeedbackModel> getCustomerFeedbackByIdTraining(Long idTraining){
        return customerFeedbackDB.findByTraining(idTraining);
    }

    @Override
    public void addCustomerFeedback(CustomerFeedbackModel customerFeedback){
        customerFeedbackDB.save(customerFeedback);
    }


}
