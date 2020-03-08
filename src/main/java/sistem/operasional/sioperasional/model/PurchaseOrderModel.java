package sistem.operasional.sioperasional.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrderModel implements Serializable {
    @Id
    @Size(max=200)
    @Column(name="nomorPurchaseOrder")
    private String nomorPurchaseOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVendor", referencedColumnName = "idVendor", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private VendorModel vendor;

    @NotNull
    @Size(max = 200)
    @Column(name = "nomorInvoice", nullable = true)
    private String nomorInvoice;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemPOModel> ListitemPO;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalBayar", nullable = false)
    private Date tanggalBayar;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalOpen", nullable = false)
    private Date tanggalOpen;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalClose", nullable = false)
    private Date tanggalClose;

    @NotNull
    @Column(name = "isDisetujui", nullable = false)
    private boolean isDisetujui;

    @NotNull
    @Size(max = 200)
    @Column(name = "statusPO", nullable = false)
    private String statusPO;

    public String getNomorPurchaseOrder() {
        return nomorPurchaseOrder;
    }

    public void setNomorPurchaseOrder(String nomorPurchaseOrder) {
        this.nomorPurchaseOrder = nomorPurchaseOrder;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public VendorModel getVendor() {
        return vendor;
    }

    public void setVendor(VendorModel vendor) {
        this.vendor = vendor;
    }

    public String getNomorInvoice() {
        return nomorInvoice;
    }

    public void setNomorInvoice(String nomorInvoice) {
        this.nomorInvoice = nomorInvoice;
    }

    public List<ItemPOModel> getListitemPO() {
        return ListitemPO;
    }

    public void setListitemPO(List<ItemPOModel> listitemPO) {
        ListitemPO = listitemPO;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Date tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public Date getTanggalOpen() {
        return tanggalOpen;
    }

    public void setTanggalOpen(Date tanggalOpen) {
        this.tanggalOpen = tanggalOpen;
    }

    public Date getTanggalClose() {
        return tanggalClose;
    }

    public void setTanggalClose(Date tanggalClose) {
        this.tanggalClose = tanggalClose;
    }

    public boolean isDisetujui() {
        return isDisetujui;
    }

    public void setDisetujui(boolean disetujui) {
        isDisetujui = disetujui;
    }

    public String getStatusPO() {
        return statusPO;
    }

    public void setStatusPO(String statusPO) {
        this.statusPO = statusPO;
    }


}
