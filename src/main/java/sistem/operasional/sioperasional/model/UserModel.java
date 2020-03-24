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

    public String getUsername() {
        return username;
    }
}
