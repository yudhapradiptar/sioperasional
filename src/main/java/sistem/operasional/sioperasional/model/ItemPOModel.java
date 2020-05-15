package sistem.operasional.sioperasional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Entity
@Table(name = "itemPO")
public class ItemPOModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemPO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomorPurchaseOrder", referencedColumnName = "nomorPurchaseOrder", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PurchaseOrderModel purchaseOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idKategoriItem", referencedColumnName = "idKategoriItem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KategoriItemModel kategoriItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idJenisItem", referencedColumnName = "idJenisItem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisItemModel jenisItem;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private int jumlahItem;

//    @Null
//    @Column(name = "hargaSatuan", nullable = true)
//    private int hargaSatuan;

    public Long getIdItemPO() {
        return idItemPO;
    }

    public void setIdItemPO(Long idItemPO) {
        this.idItemPO = idItemPO;
    }

    public PurchaseOrderModel getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderModel purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public KategoriItemModel getKategoriItem() {
        return kategoriItem;
    }

    public void setKategoriItem(KategoriItemModel kategoriItem) {
        this.kategoriItem = kategoriItem;
    }

    public JenisItemModel getJenisItem() {
        return jenisItem;
    }

    public void setJenisItem(JenisItemModel jenisItem) {
        this.jenisItem = jenisItem;
    }

    public int getJumlahItem() {
        return jumlahItem;
    }

    public void setJumlahItem(int jumlahItem) {
        this.jumlahItem = jumlahItem;
    }

//    public int getHargaItem() {
//        return hargaSatuan;
//    }
//
//    public void setHargaItem(int hargaSatuan) {
//        this.hargaSatuan = hargaSatuan;
//    }
}
