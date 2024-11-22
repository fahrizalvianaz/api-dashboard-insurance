package api.dashboard.insurance.system.model.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

@Data
@Entity
@Table(name = "identity", schema = "public")
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "file_location")
    private String fileLocation;

    private Integer sheet;

    @Column(unique = true, nullable = false)
    private String token;
}
