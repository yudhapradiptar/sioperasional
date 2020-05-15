package sistem.operasional.sioperasional.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sistem.operasional.sioperasional.controller.PurchaseOrderController;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.ItemPOModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{


    @Autowired
    PurchaseOrderDB purchaseOrderDB;

    @Override
    public List<PurchaseOrderModel> getAll() {
        return purchaseOrderDB.findAll();
    }

    @Override
    public PurchaseOrderModel getPurchaseOrderByNomorPurchaseOrder(String nomorPurchaseOrder){
        return purchaseOrderDB.findById(nomorPurchaseOrder).get();
    }

    @Override
    public PurchaseOrderModel addPurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        return purchaseOrderDB.save(purchaseOrderModel);
    }

    private static Logger logger = LogManager.getLogger(PurchaseOrderServiceImpl.class);

    private static final String filePath = System.getProperty("user.home")+"\\Downloads\\Purchase order\\";

    private static Image postImage = null;

    static {
        try {
            postImage = Image.getInstance("classpath:/static/img/post.jpg");
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean createPdf(PurchaseOrderModel purchaseOrderModel, ServletContext context,
                             HttpServletRequest request, HttpServletResponse response) {

        Document document = new Document(PageSize.A4,15,15,45,30);
        try
        {

            logger.info("FILE PATH DI SERVICE IMPL: " + filePath);
            boolean exists = new File(filePath).exists();
            logger.info("BOOLEAN DI SERVICE IMPL: " + exists);
            File file = new File(filePath);
            if(!exists)
            {
                boolean temp = new File(filePath).mkdirs();
                logger.info("BOOLEAN DI EXIST-nya SERVICE IMPL: " + temp);

            }

            PdfPTable tableUpper = new PdfPTable(2);
            tableUpper.setWidthPercentage(80);
            tableUpper.setSpacingBefore(10);
            tableUpper.setSpacingAfter(10);

            float[] columnWidthsUpper = {1f,1f};
            tableUpper.setWidths(columnWidthsUpper);



            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(file+"/"+purchaseOrderModel.getNomorPurchaseOrder()+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Font mainFontSizeNine = FontFactory.getFont("Arial",9, BaseColor.BLACK);
            Font mainFontBold = FontFactory.getFont("Arial",10, 1,  BaseColor.BLACK);

            Font tableHeader = FontFactory.getFont("Arial", 10, 1, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9,BaseColor.BLACK);

            String alamat = "Menara Prima Lt 6, Kawasan, Jl. Mega Kuningan Barat Jl. DR. Ide Anak Agung Gde Agung No.2, RT.5/RW.2, Kuningan, East Kuningan, Setiabudi, South Jakarta City, Jakarta 12950";
            Paragraph address = new Paragraph(alamat, mainFontSizeNine);
            address.setAlignment(Element.ALIGN_RIGHT);
            address.setIndentationLeft(400);
            address.setIndentationRight(20);
            address.setSpacingAfter(5);
            document.add(address);

            Image postImage = Image.getInstance("classpath:/static/img/post.jpg");
            document.add(postImage);

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
            table.setSpacingAfter(10);

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
                itemTable = new PdfPCell(new Paragraph((deskripsi+" Versi 2.0 Dengan Kekuatan Terbarunya WOW").toUpperCase(), mainFont));
                itemTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

                int hargaUnit = 2000000;
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
                int totalHargaTaxed = totalHarga+(totalHarga/10);
                String totalHargaTaxedFormated = String.format("%,d", totalHargaTaxed);
                itemTable = new PdfPCell(new Paragraph(totalHargaTaxedFormated, mainFont));
                itemTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.setBorder(Rectangle.NO_BORDER);
                itemTable.setBorder(Rectangle.BOTTOM);
                table.addCell(itemTable);

            }

            PdfPCell  currDate= new PdfPCell(new Paragraph("date: "+new Date(),tableHeader));
            currDate.setBorderColor(BaseColor.BLACK);
            currDate.setPaddingLeft(30);
            currDate.setHorizontalAlignment(Element.ALIGN_RIGHT);
            currDate.setVerticalAlignment(Element.ALIGN_CENTER);
            currDate.setBackgroundColor(BaseColor.GRAY);
            currDate.setExtraParagraphSpace(5f);
            table.addCell(currDate);

            document.add(table);
            document.close();
            return true;

        }
        catch(Exception ex)
        {

            logger.info("==========================PRINT ERROR=================");
            logger.info(ex);
            return false;
        }


    }

    protected String dateToStringHelper(Date date){
        date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(date);
        return strDate;
    }

}
