package wrona.dominik.trivago_case_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wrona.dominik.trivago_case_study.api.model.Location;

/**
 * Location repository
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
