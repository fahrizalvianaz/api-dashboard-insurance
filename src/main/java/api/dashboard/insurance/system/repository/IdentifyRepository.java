package api.dashboard.insurance.system.repository;

import api.dashboard.insurance.system.model.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;


public interface IdentifyRepository extends JpaRepository<Identity, Long> {

}
