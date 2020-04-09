package sistem.operasional.sioperasional.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name="pengguna")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotEmpty(message = "The above field must not be blank.")
    @Size(max=15)
    @Column(name="username", unique = true)
    private String username;

   
    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Size(max=200)
    @Column(name="password", nullable = false)
    @JsonIgnore
    private String password;

    @NotEmpty(message = "The above field must not be blank.")
    @NotNull
    @Size(max = 20)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotEmpty(message = "The above field must not be blank.")
    @NotNull
    @Size(max = 20)
    @Column (name = "status", nullable = false)
    private String status;

    @NotEmpty(message = "The above field must not be blank.")
    @NotNull
    @Size(max = 20)
    @Column(name = "kode", nullable = false)
    private String kode;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRole", referencedColumnName = "idRole", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RoleModel role;


    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PurchaseOrderModel> listPurchaseOrder;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<DeliveryOrderModel> listDeliveryOrder;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<TrainingModel> listTrainingCreated;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<TrainingModel> listTrainingTrained;

    public void setListDeliveryOrder(final List<DeliveryOrderModel> listDeliveryOrder) {
        this.listDeliveryOrder = listDeliveryOrder;
    }

    public void setListPurchaseOrder(final List<PurchaseOrderModel> listPurchaseOrder) {
        this.listPurchaseOrder = listPurchaseOrder;
    }

    public void setListTrainingCreated(final List<TrainingModel> listTrainingCreated) {
        this.listTrainingCreated = listTrainingCreated;
    }

    public void setListTrainingTrained(final List<TrainingModel> listTrainingTrained) {
        this.listTrainingTrained = listTrainingTrained;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRole(final RoleModel role) {
        this.role = role;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<DeliveryOrderModel> getListDeliveryOrder() {
        return listDeliveryOrder;
    }

    public List<PurchaseOrderModel> getListPurchaseOrder() {
        return listPurchaseOrder;
    }

    public List<TrainingModel> getListTrainingCreated() {
        return listTrainingCreated;
    }

    public List<TrainingModel> getListTrainingTrained() {
        return listTrainingTrained;
    }

    public String getPassword() {
        return password;
    }

    public RoleModel getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(final String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return this.kode;
    }

    public void setKode(final String kode) {
        this.kode = kode;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
