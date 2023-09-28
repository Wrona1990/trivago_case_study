package wrona.dominik.trivago_case_study.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrona.dominik.trivago_case_study.api.model.Location;
import wrona.dominik.trivago_case_study.repository.LocationRepository;

/**
 * LocationService
 * responsible for business logic operations on {@link Location} model
 */
@Service
@AllArgsConstructor
public class LocationService {
    @Autowired
    private final LocationRepository repository;

    /**
     * Saves given location into repository
     *
     * @param location location object to be saved
     * @return saved location
     */
    public Location add(Location location) {

        return repository.save(location);
    }

}
