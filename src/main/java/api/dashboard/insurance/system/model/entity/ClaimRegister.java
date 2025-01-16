package api.dashboard.insurance.system.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "claim_register", schema = "public")
public class ClaimRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_identity")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Identity user;

    @Column(name = "claim_no")
    private String claimNo;

    @Column(name = "entry_date")
    private String entryDate;

    @Column(name = "estimate_loss")
    private String estimateLoss;
}
