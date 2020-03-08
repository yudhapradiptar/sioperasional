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
@Table(name="merek_item")
public class MerekItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMerekItem;

    @NotNull
    @Size(max=200)
    @Column(name="namaMerekItem", nullable = false)
    private String namaMerekItem;

    @OneToMany(mappedBy = "merekItem", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemModel> listItem;

    public Long getIdMerekItem() {
        return idMerekItem;
    }

    public void setIdMerekItem(Long idMerekItem) {
        this.idMerekItem = idMerekItem;
    }

    public String getNamaMerekItem() {
        return namaMerekItem;
    }

    public void setNamaMerekItem(String namaMerekItem) {
        this.namaMerekItem = namaMerekItem;
    }

    public List<ItemModel> getListItem() {
        return listItem;
    }

    public void setListItem(List<ItemModel> listItem) {
        this.listItem = listItem;
    }
}
