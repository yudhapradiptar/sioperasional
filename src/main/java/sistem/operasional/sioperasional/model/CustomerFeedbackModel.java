package sistem.operasional.sioperasional.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="customer_feedback")
public class CustomerFeedbackModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomerFeedback;

    @NotNull
    @Column(name = "score", nullable = false)
    private int score;

    @NotNull
    @Column(name = "pelatih", nullable = false)
    private String pelatih;

    public Long getIdCustomerFeedback() {
        return idCustomerFeedback;
    }

    public void setIdCustomerFeedback(Long idCustomerFeedback) {
        this.idCustomerFeedback = idCustomerFeedback;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPelatih() {
        return pelatih;
    }

    public void setPelatih(String pelatih) {
        this.pelatih = pelatih;
    }
}
