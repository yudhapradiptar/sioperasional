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
@Table(name="jenis_outlet")
public class JenisOutletModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJenisOutlet;

    @NotNull
    @Size(max=200)
    @Column(name="namaJenisOutlet", nullable = false)
    private String namaJenisOutlet;

    @OneToMany(mappedBy = "jenisOutlet", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<OutletModel> listOutlet;

    public Long getIdJenisOutlet() {
        return idJenisOutlet;
    }

    public void setIdJenisOutlet(Long idJenisOutlet) {
        this.idJenisOutlet = idJenisOutlet;
    }

    public String getNamaJenisOutlet() {
        return namaJenisOutlet;
    }

    public void setNamaJenisOutlet(String namaJenisOutlet) {
        this.namaJenisOutlet = namaJenisOutlet;
    }

    public List<OutletModel> getListOutlet() {
        return listOutlet;
    }

    public void setListOutlet(List<OutletModel> listOutlet) {
        this.listOutlet = listOutlet;
    }
}
