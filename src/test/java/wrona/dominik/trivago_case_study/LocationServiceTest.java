package wrona.dominik.trivago_case_study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import wrona.dominik.trivago_case_study.api.model.Item;
import wrona.dominik.trivago_case_study.api.model.Location;
import wrona.dominik.trivago_case_study.repository.LocationRepository;
import wrona.dominik.trivago_case_study.service.LocationService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class LocationServiceTest {
    @Autowired
    private LocationRepository repository;
    @InjectMocks
    private LocationService service;

    @BeforeEach
    public void setUp() {
        this.service = new LocationService(repository);

        repository.save(itemPreparedForTesting());
    }

    @Test
    void addCreatesLocationCorrectly() {
        Item item = new Item();
        Location location = new Location();

        item.setLocation(location);
        location.setItem(item);

        Location savedItem = service.add(location);

        assertNotNull(savedItem);
    }

    private Location itemPreparedForTesting() {

        return new Location(
                1,
                "City",
                "State",
                "Country",
                "12345",
                "Address",
                new Item()
        );
    }

}
