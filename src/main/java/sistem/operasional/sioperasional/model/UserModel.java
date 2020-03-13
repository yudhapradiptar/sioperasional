package sistem.operasional.sioperasional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="pengguna")
public class UserModel implements Serializable {
    @Id
    @Size(max=15)
    @Column(name="username")
    private String username;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Size(max=200)
    @Column(name="password", nullable = false)
    @JsonIgnore
    private String password;

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

    /**
     * @param listDeliveryOrder the listDeliveryOrder to set
     */
    public void setListDeliveryOrder(List<DeliveryOrderModel> listDeliveryOrder) {
        this.listDeliveryOrder = listDeliveryOrder;
    }

    /**
     * @param listPurchaseOrder the listPurchaseOrder to set
     */
    public void setListPurchaseOrder(List<PurchaseOrderModel> listPurchaseOrder) {
        this.listPurchaseOrder = listPurchaseOrder;
    }

    /**
     * @param listTrainingCreated the listTrainingCreated to set
     */
    public void setListTrainingCreated(List<TrainingModel> listTrainingCreated) {
        this.listTrainingCreated = listTrainingCreated;
    }
    
    /**
     * @param listTrainingTrained the listTrainingTrained to set
     */
    public void setListTrainingTrained(List<TrainingModel> listTrainingTrained) {
        this.listTrainingTrained = listTrainingTrained;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param role the role to set
     */
    public void setRole(RoleModel role) {
        this.role = role;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the listDeliveryOrder
     */
    public List<DeliveryOrderModel> getListDeliveryOrder() {
        return listDeliveryOrder;
    }

    /**
     * @return the listPurchaseOrder
     */
    public List<PurchaseOrderModel> getListPurchaseOrder() {
        return listPurchaseOrder;
    }

    /**
     * @return the listTrainingCreated
     */
    public List<TrainingModel> getListTrainingCreated() {
        return listTrainingCreated;
    }

    /**
     * @return the listTrainingTrained
     */
    public List<TrainingModel> getListTrainingTrained() {
        return listTrainingTrained;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the role
     */
    public RoleModel getRole() {
        return role;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
