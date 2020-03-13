package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.ItemDB;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDB itemDB;

    @Override
    public List<ItemModel> getItemList() {
        return itemDB.findAll();
    }

}