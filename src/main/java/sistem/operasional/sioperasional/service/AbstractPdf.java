package sistem.operasional.sioperasional.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.AbstractView;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;


public abstract class AbstractPdf extends AbstractView {

    public AbstractPdf(){
        this.setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent(){
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        // Apply preferences and build metadata.
        Document document = new Document(PageSize.A4,15,15,45,30);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        prepareWriter(model, writer, request);

        // Build PDF document.
        document.open();
        createPdf(model, document, writer, request, response);
        document.close();

        // Flush to HTTP response.
        response.setHeader("Content-Disposition", "attachment");    // make browser to ask for download/display
        writeToResponse(response, baos);
    }

    protected  abstract void createPdf(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws DocumentException;

    private void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request) {
        writer.setViewerPreferences(getViewerPreferences());
    }

    private int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }
}
