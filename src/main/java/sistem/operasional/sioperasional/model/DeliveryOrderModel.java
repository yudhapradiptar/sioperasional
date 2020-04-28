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
@Table(name = "delivery_order")
public class DeliveryOrderModel implements Serializable {
    @Id
    @Size(max=200)
    @Column(name="nomorDeliveryOrder")
    private String nomorDeliveryOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", referencedColumnName = "username", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOutlet", referencedColumnName = "idOutlet", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OutletModel outlet;

    @Size(max = 200)
    @Column(name = "nomorInvoice", nullable = true)
    private String nomorInvoice;

    @Size(max = 200)
    @Column(name = "statusDO", nullable = true)
    private String statusDO;

    @NotNull
    @Column(name = "isSubscribed", nullable = false)
    private boolean isSubscribed;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalSubscribeStart", nullable = true)
    private Date tanggalSubscribeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalSubscribeEnd", nullable = true)
    private Date tanggalSubscribeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalCreate", nullable = false)
    private Date tanggalCreate;

    @OneToMany(mappedBy = "deliveryOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemModel> listItem;

    public String getNomorDeliveryOrder() {
        return nomorDeliveryOrder;
    }

    public void setNomorDeliveryOrder(String nomorDeliveryOrder) {
        this.nomorDeliveryOrder = nomorDeliveryOrder;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public OutletModel getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletModel outlet) {
        this.outlet = outlet;
    }

    public String getNomorInvoice() {
        return nomorInvoice;
    }

    public void setNomorInvoice(String nomorInvoice) {
        this.nomorInvoice = nomorInvoice;
    }

    public String getStatusDO() {
        return statusDO;
    }

    public void setStatusDO(String statusDO) {
        this.statusDO = statusDO;
    }

    public Date getTanggalSubscribeStart() {
        return tanggalSubscribeStart;
    }

    public void setTanggalSubscribeStart(Date tanggalSubscribeStart) {
        this.tanggalSubscribeStart = tanggalSubscribeStart;
    }

    public Date getTanggalSubscribeEnd() {
        return tanggalSubscribeEnd;
    }

    public void setTanggalSubscribeEnd(Date tanggalSubscribeEnd) {
        this.tanggalSubscribeEnd = tanggalSubscribeEnd;
    }

    /**
     * @param isSubscribed the isSubscribed to set
     */
    public void setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    /**
     * @param tanggalCreate the tanggalCreate to set
     */
    public void setTanggalCreate(Date tanggalCreate) {
        this.tanggalCreate = tanggalCreate;
    }

    /**
     * @return the tanggalCreate
     */
    public Date getTanggalCreate() {
        return tanggalCreate;
    }

    /**
     * @param listItem the listItem to set
     */
    public void setListItem(List<ItemModel> listItem) {
        this.listItem = listItem;
    }

    /**
     * @return the listItem
     */
    public List<ItemModel> getListItem() {
        return listItem;
    }    
}
