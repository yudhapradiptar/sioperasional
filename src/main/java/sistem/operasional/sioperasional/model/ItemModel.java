package sistem.operasional.sioperasional.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="item")
public class ItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idItem")
    private Long idItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idKategoriItem", referencedColumnName = "idKategoriItem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KategoriItemModel kategoriItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMerekItem", referencedColumnName = "idMerekItem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MerekItemModel merekItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomorPurchaseOrder", referencedColumnName = "nomorPurchaseOrder", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PurchaseOrderModel purchaseOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomorDeliveryOrder", referencedColumnName = "nomorDeliveryOrder", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private DeliveryOrderModel deliveryOrder;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalDatang", nullable = false)
    private Date tanggalDatang;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalKeluar", nullable = true)
    private Date tanggalKeluar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalRefund", nullable = true)
    private Date tanggalRefund;

    @NotNull
    @Column(name = "isRusak", nullable = false)
    private boolean isRusak;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStatusItem", referencedColumnName = "idStatusItem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusItemModel statusItem;

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    /**
     * @return the idItem
     */
    public Long getIdItem() {
        return idItem;
    }

    public KategoriItemModel getKategoriItem() {
        return kategoriItem;
    }

    public void setKategoriItem(KategoriItemModel kategoriItem) {
        this.kategoriItem = kategoriItem;
    }

    public MerekItemModel getMerekItem() {
        return merekItem;
    }

    public void setMerekItem(MerekItemModel merekItem) {
        this.merekItem = merekItem;
    }

    public PurchaseOrderModel getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderModel purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setDeliveryOrder(DeliveryOrderModel deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public DeliveryOrderModel getDeliveryOrder() {
        return deliveryOrder;
    }

    public Date getTanggalDatang() {
        return tanggalDatang;
    }

    public void setTanggalDatang(Date tanggalDatang) {
        this.tanggalDatang = tanggalDatang;
    }

    public Date getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(Date tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }


    public Date getTanggalRefund() {
        return tanggalRefund;
    }

    public void setTanggalRefund(Date tanggalRefund) {
        this.tanggalRefund = tanggalRefund;
    }

    public void setRusak(boolean rusak) {
        isRusak = rusak;
    }

    public boolean isRusak() {
        return isRusak;
    }

    public StatusItemModel getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(StatusItemModel statusItem) {
        this.statusItem = statusItem;
    }

}
