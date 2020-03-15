package sistem.operasional.sioperasional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name="training")
public class TrainingModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTraining;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOutlet", referencedColumnName = "idOutlet", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OutletModel outlet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel trainer;

    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<CustomerFeedbackModel> listCustomerFeedback;

    @NotNull
    @Size(max = 200)
    @Column(name = "keterangan", nullable = false)
    private String keteranganTraining;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalRequest", nullable = false)
    private Date tanggalRequest;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "tanggalTraining", nullable = false)
    private Date tanggalTraining;

    @NotNull
    @Size(max=200)
    @Column(name="waktu_mulai", nullable = false)
    @JsonProperty("waktu_mulai")
    private String waktuMulai;

    @NotNull
    @Size(max=200)
    @Column(name="waktu_selesai", nullable = false)
    @JsonProperty("waktu_selesai")
    private String waktuSelesai;

    @NotNull
    @Size(max = 200)
    @Column(name = "bayar", nullable = false)
    private String bayar;

    @NotNull
    @Size(max = 200)
    @Column(name = "statusTraining", nullable = false)
    private String statusTraining;

    public Long getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(Long idTraining) {
        this.idTraining = idTraining;
    }

    public OutletModel getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletModel outlet) {
        this.outlet = outlet;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public UserModel getTrainer() {
        return trainer;
    }

    public void setTrainer(UserModel trainer) {
        this.trainer = trainer;
    }

    public String getKeteranganTraining() {
        return keteranganTraining;
    }

    public void setKeteranganTraining(String keteranganTraining) {
        this.keteranganTraining = keteranganTraining;
    }

    public Date getTanggalRequest() {
        return tanggalRequest;
    }

    public void setTanggalRequest(Date tanggalRequest) {
        this.tanggalRequest = tanggalRequest;
    }

    public Date getTanggalTraining() {
        return tanggalTraining;
    }

    public void setTanggalTraining(Date tanggalTraining) {
        this.tanggalTraining = tanggalTraining;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getStatusTraining() {
        return statusTraining;
    }

    public void setStatusTraining(String statusTraining) {
        this.statusTraining = statusTraining;
    }


}
