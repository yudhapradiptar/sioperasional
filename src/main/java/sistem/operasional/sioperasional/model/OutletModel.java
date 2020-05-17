package sistem.operasional.sioperasional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "outlet")
public class OutletModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOutlet;

    @NotNull
    @Size(max = 200)
    @Column(name = "namaOutlet", nullable = false)
    private String namaOutlet;

    @NotNull
    @Size(max = 200)
    @Column(name = "emailOutlet", nullable = false)
    private String emailOutlet;

    @NotNull
    @Column(name = "noTelpOutlet", nullable = false)
    private String noTelpOutlet;

    @NotNull
    @Size(max = 200)
    @Column(name = "alamatOutlet", nullable = false)
    private String alamatOutlet;

    @NotNull
    @Column(name = "isAktif", nullable = false)
    private boolean isAktif;

    @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<DeliveryOrderModel> listDeliveryOrderOutlet;

    @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<TrainingModel> listTrainingOutlet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idJenisOutlet", referencedColumnName = "idJenisOutlet", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisOutletModel jenisOutlet;

    public Long getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(Long idOulet) {
        this.idOutlet = idOulet;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public String getEmailOutlet() {
        return emailOutlet;
    }

    public void setEmailOutlet(String emailOutlet) {
        this.emailOutlet = emailOutlet;
    }

    public String getNoTelpOutlet() {
        return noTelpOutlet;
    }

    public void setNoTelpOutlet(String noTelpOutlet) {
        this.noTelpOutlet = noTelpOutlet;
    }

    public String getAlamatOutlet() {
        return alamatOutlet;
    }

    public void setAlamatOutlet(String alamatOutlet) {
        this.alamatOutlet = alamatOutlet;
    }

    public List<DeliveryOrderModel> getListDeliveryOrderOutlet() {
        return listDeliveryOrderOutlet;
    }

    public void setListDeliveryOrderOutlet(List<DeliveryOrderModel> listDeliveryOrderOutlet) {
        this.listDeliveryOrderOutlet = listDeliveryOrderOutlet;
    }

    public List<TrainingModel> getListTrainingOutlet() {
        return listTrainingOutlet;
    }

    public void setListTrainingOutlet(List<TrainingModel> listTrainingOutlet) {
        this.listTrainingOutlet = listTrainingOutlet;
    }

    public JenisOutletModel getJenisOutlet() {
        return jenisOutlet;
    }

    public void setJenisOutlet(JenisOutletModel jenisOutlet) {
        this.jenisOutlet = jenisOutlet;
    }

    /**
     * @param isAktif the isAktif to set
     */
    public void setAktif(boolean isAktif) {
        this.isAktif = isAktif;
    }

    public boolean isAktif() {
        return isAktif;
    }
}
