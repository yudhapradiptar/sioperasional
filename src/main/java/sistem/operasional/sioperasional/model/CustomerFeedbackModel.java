package sistem.operasional.sioperasional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="customer_feedback")
public class CustomerFeedbackModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomerFeedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "training", referencedColumnName = "idTraining", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TrainingModel training;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "nilai_kerapihan", nullable = false)
    private int nilaiKerapihan;

    @NotNull
    @Column(name = "nilai_tepat_waktu", nullable = false)
    private int nilaiTepatWaktu;

    @NotNull
    @Column(name = "nilai_simpatik", nullable = false)
    private int nilaiSimpatik;

    @NotNull
    @Column(name = "nilai_kesiapan", nullable = false)
    private int nilaiKesiapan;

    @NotNull
    @Column(name = "nilai_penanggapan", nullable = false)
    private int nilaiPenanggapan;

    @NotNull
    @Column(name = "nilai_kesopanan", nullable = false)
    private int nilaiKesopanan;

    @NotNull
    @Column(name = "nilai_mengetahui_kebutuhan", nullable = false)
    private int nilaiMengetahuiKebutuhan;

    @NotNull
    @Column(name = "nilai_perhatian", nullable = false)
    private int nilaiPerhatian;

    @Size(max=200)
    @Column(name="alasan_puas", nullable = true)
    private String alasanPuas;

    @Size(max=200)
    @Column(name="alasan_tidak_puas", nullable = true)
    private String alasanTidakPuas;

    @Size(max=200)
    @Column(name="fitur_membantu", nullable = true)
    private String fiturMembantu;

    @Size(max=200)
    @Column(name="saran", nullable = true)
    private String saran;

    public Long getIdCustomerFeedback() {
        return idCustomerFeedback;
    }

    public void setIdCustomerFeedback(Long idCustomerFeedback) {
        this.idCustomerFeedback = idCustomerFeedback;
    }

    public TrainingModel getTraining() {
        return training;
    }

    public void setTraining(TrainingModel training) {
        this.training = training;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNilaiKerapihan() {
        return nilaiKerapihan;
    }

    public void setNilaiKerapihan(int nilaiKerapihan) {
        this.nilaiKerapihan = nilaiKerapihan;
    }

    public int getNilaiTepatWaktu() {
        return nilaiTepatWaktu;
    }

    public void setNilaiTepatWaktu(int nilaiTepatWaktu) {
        this.nilaiTepatWaktu = nilaiTepatWaktu;
    }

    public int getNilaiSimpatik() {
        return nilaiSimpatik;
    }

    public void setNilaiSimpatik(int nilaiSimpatik) {
        this.nilaiSimpatik = nilaiSimpatik;
    }

    public int getNilaiKesiapan() {
        return nilaiKesiapan;
    }

    public void setNilaiKesiapan(int nilaiKesiapan) {
        this.nilaiKesiapan = nilaiKesiapan;
    }

    public int getNilaiPenanggapan() {
        return nilaiPenanggapan;
    }

    public void setNilaiPenanggapan(int nilaiPenanggapan) {
        this.nilaiPenanggapan = nilaiPenanggapan;
    }

    public int getNilaiKesopanan() {
        return nilaiKesopanan;
    }

    public void setNilaiKesopanan(int nilaiKesopanan) {
        this.nilaiKesopanan = nilaiKesopanan;
    }

    public int getNilaiMengetahuiKebutuhan() {
        return nilaiMengetahuiKebutuhan;
    }

    public void setNilaiMengetahuiKebutuhan(int nilaiMengetahuiKebutuhan) {
        this.nilaiMengetahuiKebutuhan = nilaiMengetahuiKebutuhan;
    }

    public int getNilaiPerhatian() {
        return nilaiPerhatian;
    }

    public void setNilaiPerhatian(int nilaiPerhatian) {
        this.nilaiPerhatian = nilaiPerhatian;
    }

    public String getAlasanPuas() {
        return alasanPuas;
    }

    public void setAlasanPuas(String alasanPuas) {
        this.alasanPuas = alasanPuas;
    }

    public String getAlasanTidakPuas() {
        return alasanTidakPuas;
    }

    public void setAlasanTidakPuas(String alasanTidakPuas) {
        this.alasanTidakPuas = alasanTidakPuas;
    }

    public String getFiturMembantu() {
        return fiturMembantu;
    }

    public void setFiturMembantu(String fiturMembantu) {
        this.fiturMembantu = fiturMembantu;
    }

    public String getSaran() {
        return saran;
    }

    public void setSaran(String saran) {
        this.saran = saran;
    }
}
