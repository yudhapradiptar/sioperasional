package sistem.operasional.sioperasional.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sistem.operasional.sioperasional.controller.PurchaseOrderController;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.ItemPOModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    private static Logger logger = LogManager.getLogger(PurchaseOrderServiceImpl.class);

    private static final String filePath = System.getProperty("user.home"+"/");

    private static Image postImage = null;

    static {
        try {
            postImage = Image.getInstance("classpath:/static/img/postPdf.jpg");
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    PurchaseOrderDB purchaseOrderDB;

    @Override
    public void savePurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        purchaseOrderDB.save(purchaseOrderModel);
    }

    @Override
    public void deletePurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        purchaseOrderDB.delete(purchaseOrderModel);
    }

    @Override
    public List<PurchaseOrderModel> getAll() {
        return purchaseOrderDB.findAll();
    }

    @Override
    public Optional<PurchaseOrderModel> getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder){
        return purchaseOrderDB.findById(nomorPurchaseOrder);
    }

    @Override
    public PurchaseOrderModel changePurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        PurchaseOrderModel newPurchaseOrderModel = purchaseOrderDB.findById(purchaseOrderModel.getNomorPurchaseOrder()).get();

        Date newDate = new Date();

        try {
            newPurchaseOrderModel.setNomorInvoice(purchaseOrderModel.getNomorInvoice());
            newPurchaseOrderModel.setVendor(purchaseOrderModel.getVendor());
            newPurchaseOrderModel.setTanggalUpdate(newDate);
            newPurchaseOrderModel.setTanggalBayar(purchaseOrderModel.getTanggalBayar());
            purchaseOrderDB.save(newPurchaseOrderModel);
            return newPurchaseOrderModel;
        } catch (NullPointerException nullException) {
            return null;
        }
    }
    @Override
    public List<PurchaseOrderModel> getPurchaseOrderListByNotDisetujui(){
        List<PurchaseOrderModel> listPONotDisetujui = new ArrayList<>();
        for(PurchaseOrderModel purchaseOrderModel : getAll()){
            if(!purchaseOrderModel.isDisetujui()){
                listPONotDisetujui.add(purchaseOrderModel);
            }
        }
        return listPONotDisetujui;
    }

}
