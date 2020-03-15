package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.CustomerFeedbackModel;

import java.util.List;

public interface CustomerFeedbackService {
    List<CustomerFeedbackModel> getAllCustomerFeedback();

    CustomerFeedbackModel getCustomerFeedbackById(Long idCustomerFeedback);

    List<CustomerFeedbackModel> getCustomerFeedbackByIdTraining(Long idTraining);
}
