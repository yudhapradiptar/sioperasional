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

    private static final String filePath = System.getProperty("user.home");

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
    public List<PurchaseOrderModel> getAll() {
        return purchaseOrderDB.findAll();
    }

    @Override
    public Optional<PurchaseOrderModel> getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder){
        return purchaseOrderDB.findById(nomorPurchaseOrder);
    }

    @Override
    public PurchaseOrderModel addPurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        return purchaseOrderDB.save(purchaseOrderModel);
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

    public boolean createPdf(PurchaseOrderModel purchaseOrderModel, ServletContext context,
                             HttpServletRequest request, HttpServletResponse response) {

        Document document = new Document(PageSize.A4,15,15,45,30);
        try
        {
            boolean exists = new File(filePath).exists();
            File file = new File(filePath);
            if(!exists)
            {
                boolean temp = new File(filePath).mkdirs();
            }

            PdfPTable tableUpper = new PdfPTable(2);
            tableUpper.setWidthPercentage(80);
            tableUpper.setSpacingBefore(10);
            tableUpper.setSpacingAfter(10);

            float[] columnWidthsUpper = {2f,2f};
            tableUpper.setWidths(columnWidthsUpper);

            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(file+"/"+purchaseOrderModel.getNomorPurchaseOrder()+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Font mainFontSizeNine = FontFactory.getFont("Arial",9, BaseColor.BLACK);
            Font mainFontBold = FontFactory.getFont("Arial",10, 1,  BaseColor.BLACK);
            Font mainFontBoldUnderline = FontFactory.getFont("Arial",10, 5,  BaseColor.BLACK);

            Font tableHeader = FontFactory.getFont("Arial", 10, 1, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9,BaseColor.BLACK);

            Image postImage = Image.getInstance("classpath:/static/img/postPdf.jpg");
            PdfPCell image = new PdfPCell(postImage);
            image.setBorderColor(BaseColor.BLACK);
            image.setPaddingLeft(10);
            image.setHorizontalAlignment(Element.ALIGN_LEFT);
            image.setVerticalAlignment(Element.ALIGN_LEFT);
            image.setExtraParagraphSpace(5f);
            image.setBorder(Rectangle.NO_BORDER);
            tableUpper.addCell(image);

            String alamat = "Menara Prima Lt 6, Kawasan, Jl. Mega Kuningan Barat Jl. DR. Ide Anak Agung Gde Agung No.2, RT.5/RW.2, Kuningan, East Kuningan, Setiabudi, South Jakarta City, Jakarta 12950";
            PdfPCell address = new PdfPCell(new Paragraph(alamat, mainFontSizeNine));
            address.setBorderColor(BaseColor.BLACK);
            address.setPaddingLeft(10);
            address.setHorizontalAlignment(Element.ALIGN_RIGHT);
            address.setVerticalAlignment(Element.ALIGN_RIGHT);
            address.setExtraParagraphSpace(5f);
            address.setBorder(Rectangle.NO_BORDER);
            tableUpper.addCell(address);

            document.add(tableUpper);

            Paragraph purchaseOrderHeader = new Paragraph("PURCHASE ORDER", mainFontBold);
            purchaseOrderHeader.setAlignment(Element.ALIGN_LEFT);
            purchaseOrderHeader.setIndentationLeft(50);
            purchaseOrderHeader.setIndentationRight(50);
            purchaseOrderHeader.setSpacingAfter(20);
            document.add(purchaseOrderHeader);

            String dateFormated = dateToStringHelper(purchaseOrderModel.getTanggalOpen());
            String tanggalOpen = String.format("Date%8s "+dateFormated, ":");
            Paragraph date = new Paragraph(tanggalOpen, mainFont);
            date.setAlignment(Element.ALIGN_LEFT);
            date.setIndentationLeft(50);
            date.setIndentationRight(50);
            date.setSpacingAfter(0);
            document.add(date);

            String creator = String.format("Name%6s "+purchaseOrderModel.getCreator().getNama(), ":");
            Paragraph name = new Paragraph(creator, mainFont);
            name.setAlignment(Element.ALIGN_LEFT);
            name.setIndentationLeft(50);
            name.setIndentationRight(50);
            name.setSpacingAfter(0);
            document.add(name);

            String poNumber = String.format("PO No%5s "+purchaseOrderModel.getNomorPurchaseOrder(), ":");
            Paragraph nomerPO = new Paragraph(poNumber, mainFont);
            nomerPO.setAlignment(Element.ALIGN_LEFT);
            nomerPO.setIndentationLeft(50);
            nomerPO.setIndentationRight(50);
            nomerPO.setSpacingAfter(0);
            document.add(nomerPO);

            String vendor = String.format("Vendor Information%10s "+purchaseOrderModel.getVendor().getNamaVendor()+", "+purchaseOrderModel.getVendor().getAlamatVendor(), ":");
            Paragraph infoVendor = new Paragraph(vendor, mainFontBold);
            infoVendor.setAlignment(Element.ALIGN_LEFT);
            infoVendor.setIndentationLeft(50);
            infoVendor.setIndentationRight(50);
            infoVendor.setSpacingAfter(20);
            document.add(infoVendor);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(80);
            table.setSpacingBefore(10);
            table.setSpacingAfter(0);

            float[] columnWidths = {1f,1f,1f,1f,1f};
            table.setWidths(columnWidths);

            PdfPCell qtyHeader = new PdfPCell(new Paragraph("QTY",tableHeader));
            qtyHeader.setBorderColor(BaseColor.BLACK);
            qtyHeader.setPaddingLeft(10);
            qtyHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            qtyHeader.setVerticalAlignment(Element.ALIGN_CENTER);
            qtyHeader.setExtraParagraphSpace(5f);
            qtyHeader.setBorder(Rectangle.NO_BORDER);
            qtyHeader.setBorder(Rectangle.BOTTOM);
            table.addCell(qtyHeader);

            PdfPCell descriptionHeader = new PdfPCell(new Paragraph("DESCRIPTION",tableHeader));
            descriptionHeader.setBorderColor(BaseColor.BLACK);
            descriptionHeader.setPaddingLeft(10);
            descriptionHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            descriptionHeader.setVerticalAlignment(Element.ALIGN_CENTER);
            descriptionHeader.setExtraParagraphSpace(5f);
            descriptionHeader.setBorder(Rectangle.NO_BORDER);
            descriptionHeader.setBorder(Rectangle.BOTTOM);
            table.addCell(descriptionHeader);

            PdfPCell unitPriceHeader = new PdfPCell(new Paragraph("UNIT PRICE (RP)",tableHeader));
            unitPriceHeader.setBorderColor(BaseColor.BLACK);
            unitPriceHeader.setPaddingLeft(10);
            unitPriceHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            unitPriceHeader.setVerticalAlignment(Element.ALIGN_CENTER);
            unitPriceHeader.setExtraParagraphSpace(5f);
            unitPriceHeader.setBorder(Rectangle.NO_BORDER);
            unitPriceHeader.setBorder(Rectangle.BOTTOM);
            table.addCell(unitPriceHeader);

            PdfPCell taxedHeader = new PdfPCell(new Paragraph("TAXED",tableHeader));
            taxedHeader.setBorderColor(BaseColor.BLACK);
            taxedHeader.setPaddingLeft(10);
            taxedHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            taxedHeader.setVerticalAlignment(Element.ALIGN_CENTER);
            taxedHeader.setExtraParagraphSpace(5f);
            taxedHeader.setBorder(Rectangle.NO_BORDER);
            taxedHeader.setBorder(Rectangle.BOTTOM);
            table.addCell(taxedHeader);

            PdfPCell totalAmmountHeader = new PdfPCell(new Paragraph("AMOUNT (RP)",tableHeader));
            totalAmmountHeader.setBorderColor(BaseColor.BLACK);
            totalAmmountHeader.setPaddingLeft(10);
            totalAmmountHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalAmmountHeader.setVerticalAlignment(Element.ALIGN_CENTER);
            totalAmmountHeader.setExtraParagraphSpace(5f);
            totalAmmountHeader.setBorder(Rectangle.NO_BORDER);
            totalAmmountHeader.setBorder(Rectangle.BOTTOM);
            table.addCell(totalAmmountHeader);

            int totalAmmount = 0;
            List<ItemPOModel> listItemPOModel = purchaseOrderModel.getListItemPO();
            for (ItemPOModel item : listItemPOModel) {

                int jumlah = item.getJumlahItem();
                PdfPCell itemTable = new PdfPCell(new Paragraph(jumlah+"", mainFont));
                itemTable.setBorderColor(BaseColor.BLACK);
                itemTable.setPaddingLeft(10);
                itemTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                itemTable.setVerticalAlignment(Element.ALIGN_CENTER);
                itemTable.setBackgroundColor(BaseColor.WHITE);
                itemTable.setExtraParagraphSpace(5f);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

                String deskripsi = item.getJenisItem().getNamaJenisItem() + " - " + item.getKategoriItem().getNamaKategoriItem();
                itemTable = new PdfPCell(new Paragraph((deskripsi).toUpperCase(), mainFont));
                itemTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

                int hargaUnit = item.getHargaSatuan();
                String hargaUnitFormated = String.format("%,d", hargaUnit);
                itemTable = new PdfPCell(new Paragraph(hargaUnitFormated, mainFont));
                itemTable.setHorizontalAlignment(Element.ALIGN_CENTER);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

                String pajak = "10%";
                itemTable = new PdfPCell(new Paragraph(pajak, mainFont));
                itemTable.setHorizontalAlignment(Element.ALIGN_CENTER);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

                int totalHarga = hargaUnit*jumlah;
                totalAmmount += totalHarga;
                int totalHargaTaxed = totalHarga+(totalHarga/10);
                String totalHargaTaxedFormated = String.format("%,d", totalHargaTaxed);
                itemTable = new PdfPCell(new Paragraph(totalHargaTaxedFormated, mainFont));
                itemTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

            }
            document.add(table);

            String totalAmmountFormated = String.format("%,d", totalAmmount);
            String totalAmountFormatedString = String.format("TOTAL%25s", totalAmmountFormated);
            Paragraph totalAmount = new Paragraph(totalAmountFormatedString , mainFontBoldUnderline);
            totalAmount.setAlignment(Element.ALIGN_RIGHT);
            totalAmount.setIndentationRight(55);
            totalAmount.setSpacingAfter(80);
            totalAmount.setExtraParagraphSpace(5f);
            document.add(totalAmount);

            String signName = String.format("%s", purchaseOrderModel.getCreator().getNama());
            String requestedBy = String.format("%s", "Requested by");
            String currDate = String.format("%s", dateToStringHelper(purchaseOrderModel.getTanggalOpen()));

            Paragraph requestedByOutput = new Paragraph(requestedBy , mainFont);
            requestedByOutput.setAlignment(Element.ALIGN_RIGHT);
            requestedByOutput.setIndentationRight(55);
            requestedByOutput.setSpacingAfter(60);
            requestedByOutput.setExtraParagraphSpace(5f);
            document.add(requestedByOutput);

            Paragraph signNameOutput = new Paragraph(signName , mainFont);
            signNameOutput.setAlignment(Element.ALIGN_RIGHT);
            signNameOutput.setIndentationRight(55);
            signNameOutput.setSpacingAfter(0);
            signNameOutput.setExtraParagraphSpace(5f);
            document.add(signNameOutput);

            Paragraph currDateOutput = new Paragraph(currDate , mainFont);
            currDateOutput.setAlignment(Element.ALIGN_RIGHT);
            currDateOutput.setIndentationRight(55);
            currDateOutput.setSpacingAfter(0);
            currDateOutput.setExtraParagraphSpace(5f);
            document.add(currDateOutput);

            document.close();
            return true;

        }
        catch(Exception ex)
        {
            logger.info("ERROR: " + ex);
            return false;
        }


    }

    protected String dateToStringHelper(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
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
