package api.dashboard.insurance.system.repository;

import api.dashboard.insurance.system.model.entity.ClaimRegister;
import api.dashboard.insurance.system.model.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClaimRegisterRepository extends JpaRepository<ClaimRegister, Long> {
    Optional<ClaimRegister> findByUser(Identity user);
}
