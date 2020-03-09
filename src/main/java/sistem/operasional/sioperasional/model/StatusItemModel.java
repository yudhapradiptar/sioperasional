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
@Table(name="status_item")
public class StatusItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatusItem;

    @NotNull
    @Size(max=200)
    @Column(name="namaStatusItem", nullable = false)
    private String namaStatusItem;

    @OneToMany(mappedBy = "statusItem", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ItemModel> listItem;

    public Long getIdStatusItem() {
        return idStatusItem;
    }

    public void setIdStatusItem(Long idStatusItem) {
        this.idStatusItem = idStatusItem;
    }

    public String getNamaStatusItem() {
        return namaStatusItem;
    }

    public void setNamaStatusItem(String namaStatusItem) {
        this.namaStatusItem = namaStatusItem;
    }

    public List<ItemModel> getListItem() {
        return listItem;
    }

    public void setListItem(List<ItemModel> listItem) {
        this.listItem = listItem;
    }
}
