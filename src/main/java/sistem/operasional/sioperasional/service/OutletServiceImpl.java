package sistem.operasional.sioperasional.service;

import sistem.operasional.sioperasional.model.OutletModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistem.operasional.sioperasional.repository.OutletDB;

@Service
public class OutletServiceImpl implements OutletService {

    @Autowired
    private OutletDB outletDB;

    @Override
    public List<OutletModel> getOutletList() {
        return outletDB.findAll();
    }



}