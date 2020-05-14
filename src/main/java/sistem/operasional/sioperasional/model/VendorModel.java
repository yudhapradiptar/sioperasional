package sistem.operasional.sioperasional.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "vendor")
public class VendorModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVendor;

    @NotNull
    @Size(max = 200)
    @Column(name = "namaVendor", nullable = false)
    private String namaVendor;

    @NotNull
    @Size(max = 200)
    @Column(name = "emailVendor", nullable = false)
    private String emailVendor;

    @NotNull
    @Column(name = "noTelpVendor", nullable = false)
    private String noTelpVendor;

    @NotNull
    @Size(max = 200)
    @Column(name = "alamatVendor", nullable = false)
    private String alamatVendor;

    public Long getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(Long idVendor) {
        this.idVendor = idVendor;
    }

    public String getNamaVendor() {
        return namaVendor;
    }

    public void setNamaVendor(String namaVendor) {
        this.namaVendor = namaVendor;
    }

    public String getEmailVendor() {
        return emailVendor;
    }

    public void setEmailVendor(String emailVendor) {
        this.emailVendor = emailVendor;
    }

    public String getNoTelpVendor() {
        return noTelpVendor;
    }

    public void setNoTelpVendor(String noTelpVendor) {
        this.noTelpVendor = noTelpVendor;
    }

    public String getAlamatVendor() {
        return alamatVendor;
    }

    public void setAlamatVendor(String alamatVendor) {
        this.alamatVendor = alamatVendor;
    }
}
