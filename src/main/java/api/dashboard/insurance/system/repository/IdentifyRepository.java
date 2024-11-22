package api.dashboard.insurance.system.repository;

import api.dashboard.insurance.system.model.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IdentifyRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findByUsername(String username);
}
