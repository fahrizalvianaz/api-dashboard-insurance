package api.dashboard.insurance.system.model.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "identity", schema = "public")
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_identity")
    private Long idUser;

    @Column(unique = true, nullable = false)
    private String username;


}
