package sistem.operasional.sioperasional.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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

    @Size(max = 200)
    @Column(name = "nomorInvoice", nullable = true)
    private String nomorInvoice;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemPOModel> listItemPO;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalBayar", nullable = true)
    private Date tanggalBayar;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalOpen", nullable = false)
    private Date tanggalOpen;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalClose", nullable = true)
    private Date tanggalClose;

    @Column(name = "isDisetujui", nullable = true)
    private boolean isDisetujui;

    @NotNull
    @Column(name = "statusPO", nullable = false)
    private String statusPO;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemModel> Listitem;

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

    public List<ItemPOModel> getListItemPO() {
        return listItemPO;
    }

    public void setListItemPO(List<ItemPOModel> listItemPO) {
        this.listItemPO = listItemPO;
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

    public List<ItemModel> getListitem() {
        return Listitem;
    }

    public void setListitem(List<ItemModel> listitem) {
        Listitem = listitem;
    }
}
