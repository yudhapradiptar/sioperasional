package sistem.operasional.sioperasional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    @Autowired
    PurchaseOrderDB PORepository;

    @Override
    public List<PurchaseOrderModel> getAll() {
        return PORepository.findAll();
    }

    @Override
    public Optional<PurchaseOrderModel> getById(long id) {
        return PORepository.findById(id);
    }

    @Override
    public PurchaseOrderModel save(PurchaseOrderModel purchaseOrderModel) {
        return PORepository.save(purchaseOrderModel);
    }
}
