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

@Entity
@Table(name = "delivery_order")
public class DeliveryOrderModel implements Serializable {
    @Id
    @Size(max=200)
    @Column(name="nomorDeliveryOrder")
    private String nomorDeliveryOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOutlet", referencedColumnName = "idOutlet", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OutletModel outlet;

    @NotNull
    @Size(max = 200)
    @Column(name = "nomorInvoice", nullable = true)
    private String nomorInvoice;

    @NotNull
    @Size(max = 200)
    @Column(name = "statusDO", nullable = false)
    private String statusDO;

    @NotNull
    @Column(name = "isSubscribed", nullable = false)
    private boolean isSubscribed;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalSubscribeStart", nullable = true)
    private Date tanggalSubscribeStart;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalSubscribeEnd", nullable = true)
    private Date tanggalSubscribeEnd;


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

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
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
}
