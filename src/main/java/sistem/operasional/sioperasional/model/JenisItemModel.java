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
@Table(name="jenis_item")
public class JenisItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJenisItem;

    @NotNull
    @Size(max=200)
    @Column(name="namaJenisItem", nullable = false)
    private String namaJenisItem;

    @OneToMany(mappedBy = "jenisItem", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemModel> listItem;

    public Long getIdJenisItem() {
        return idJenisItem;
    }

    public void setIdJenisItem(Long idJenisItem) {
        this.idJenisItem = idJenisItem;
    }

    public String getNamaJenisItem() {
        return namaJenisItem;
    }

    public void setNamaJenisItem(String namaJenisItem) {
        this.namaJenisItem = namaJenisItem;
    }

    public List<ItemModel> getListItem() {
        return listItem;
    }

    public void setListItem(List<ItemModel> listItem) {
        this.listItem = listItem;
    }
}
