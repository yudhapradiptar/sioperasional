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
@Table(name="kategori_item")
public class KategoriItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKategoriItem;

    @NotNull
    @Size(max=200)
    @Column(name="namaKategoriItem", nullable = false)
    private String namaKategoriItem;

    @OneToMany(mappedBy = "kategoriItem", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemModel> listItem;

    public Long getIdKategoriItem() {
        return idKategoriItem;
    }

    public void setIdKategoriItem(Long idKategoriItem) {
        this.idKategoriItem = idKategoriItem;
    }

    public String getNamaKategoriItem() {
        return namaKategoriItem;
    }

    public void setNamaKategoriItem(String namaKategoriItem) {
        this.namaKategoriItem = namaKategoriItem;
    }

    public List<ItemModel> getListItem() {
        return listItem;
    }

    public void setListItem(List<ItemModel> listItem) {
        this.listItem = listItem;
    }
}
