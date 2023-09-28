package wrona.dominik.trivago_case_study;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wrona.dominik.trivago_case_study.api.model.Category;
import wrona.dominik.trivago_case_study.api.model.Item;
import wrona.dominik.trivago_case_study.api.model.Location;
import wrona.dominik.trivago_case_study.api.model.ReputationBadge;
import wrona.dominik.trivago_case_study.repository.ItemRepository;
import wrona.dominik.trivago_case_study.service.ItemService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@ExtendWith(SpringExtension.class)
class ItemServiceTest {
    @Autowired
    private ItemRepository repository;
    @InjectMocks
    private ItemService service;

    @BeforeEach
    public void setup() {
        this.service = new ItemService(repository);
        this.repository.save(itemPreparedForTesting("HotelierName"));
    }

    @AfterEach
    public void teardown() {
        this.repository.deleteAll();
        this.repository.flush();
    }

    @Test
    void findAllReturnsAllExistingItemsCorrectly() {
        List<Item> items = service.findAll();

        assertFalse(items.isEmpty());
    }

    @Test
    void addSavesItemCorrectly() {
        Item item = itemPreparedForTesting("HotelierName");
        Location location = new Location();

        item.setLocation(location);
        location.setItem(item);

        Item savedItem = service.add(item);

        assertNotNull(savedItem);
    }

    @Test
    void getAllByHotelierReturnsItemWithGivenHotelierNameCorrectly() {
        String hotelierName = "HotelierName";
        Item expectedItem = itemPreparedForTesting(hotelierName);
        Item itemUnderTest = service.getAllByHotelier(hotelierName).stream().findFirst().get();

        String expectedItemName = expectedItem.getName();
        String testedItemName = itemUnderTest.getName();

        assertEquals(expectedItemName, testedItemName);
    }

    @Test
    void getAllByHotelierDoesNotReturnAnyItemsWhenItemsWithGivenHotelierNameDoNotExist() {
        String hotelier = "HotelierName";
        Item expectedItem = itemPreparedForTesting("HotelierThatDoesNotExist");
        Item itemUnderTest = service.getAllByHotelier(hotelier).stream().findFirst().get();

        String expectedItemName = expectedItem.getName();
        String testedItemName = itemUnderTest.getName();

        assertNotEquals(expectedItemName, testedItemName);
    }

    @Test
    void reputationBadgeIsSetToGreenWhenProperReputationValueIsSet() {
        int reputationValue = 999;
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = itemPreparedForTestingFromRepository();

        expectedItem.setReputationBadge(ReputationBadge.GREEN);
        itemUnderTest.setReputation(reputationValue);
        service.setReputationBadge(itemUnderTest);

        itemUnderTest = service.getAllByReputationBadge("Green").stream().findFirst().get();

        assertEquals(expectedItem.getReputationBadge(), itemUnderTest.getReputationBadge());
    }

    @Test
    void reputationBadgeIsSetToYellowWhenProperReputationValueIsSet() {
        int reputationValue = 799;
        Item expectedItem = itemPreparedForTesting("HotelierName");
        expectedItem.setReputationBadge(ReputationBadge.YELLOW);

        Item itemUnderTest = itemPreparedForTestingFromRepository();
        itemUnderTest.setReputation(reputationValue);

        service.setReputationBadge(itemUnderTest);

        assertEquals(expectedItem.getReputationBadge(), itemUnderTest.getReputationBadge());
    }

    @Test
    void reputationBadgeIsSetToRedWhenProperReputationValueIsSet() {
        int reputationValue = 10;
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = itemPreparedForTestingFromRepository();

        expectedItem.setReputationBadge(ReputationBadge.RED);
        itemUnderTest.setReputation(reputationValue);
        service.setReputationBadge(itemUnderTest);

        assertEquals(expectedItem.getReputationBadge(), itemUnderTest.getReputationBadge());
    }

    @Test
    void reputationBadgeIsNotSetToRedWhenNotProperReputationValueIsSet() {
        int reputationValue = 899;
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = itemPreparedForTestingFromRepository();

        expectedItem.setReputationBadge(ReputationBadge.RED);
        itemUnderTest.setReputation(reputationValue);
        service.setReputationBadge(itemUnderTest);

        assertNotEquals(expectedItem.getReputationBadge(), itemUnderTest.getReputationBadge());
    }

    @Test
    void getAllByReputationBadgeReturnsItemWhenItemWithGivenReputationBadgeExist() {
        String reputationBadge = "YELLOW";
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = service.getAllByReputationBadge(reputationBadge).stream().findFirst().get();

        expectedItem.setReputationBadge(ReputationBadge.YELLOW);

        assertEquals(expectedItem.getReputationBadge(), itemUnderTest.getReputationBadge());
    }

    @Test
    void getAllByRatingReturnsItemWhenItemWithGivenRatingExist() {
        int rating = 2;
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = service.getAllByRating(rating).stream().findFirst().get();

        expectedItem.setRating(rating);

        assertEquals(expectedItem.getRating(), itemUnderTest.getRating());
    }

    @Test
    void getAllByRatingDoesNotReturnsAnyItemWhenItemWithGivenRatingDoesNotExist() {
        int rating = 3;
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = service.getAllByRating(2).stream().findFirst().get();

        expectedItem.setRating(rating);

        assertNotEquals(expectedItem.getRating(), itemUnderTest.getRating());
    }

    @Test
    void getAllByCityReturnsItemWhenItemWithGivenCityExist() {
        String city = "City";
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = itemPreparedForTestingFromRepository();

        itemUnderTest.getLocation().setCity(city);
        expectedItem.getLocation().setCity(city);

        itemUnderTest = service.getAllByCity(city).stream().findFirst().get();

        assertEquals(expectedItem.getLocation().getCity(), itemUnderTest.getLocation().getCity());
    }

    @Test
    void getAllByCityDoesNotReturnItemWhenItemWithGivenCityExist() {
        String city = "City";
        Item expectedItem = itemPreparedForTesting("HotelierName");
        Item itemUnderTest = itemPreparedForTestingFromRepository();

        itemUnderTest.getLocation().setCity(city);
        expectedItem.getLocation().setCity("CityThatDoesNotExist");

        itemUnderTest = service.getAllByCity(city).stream().findFirst().get();

        assertNotEquals(expectedItem.getLocation().getCity(), itemUnderTest.getLocation().getCity());
    }

    private Item itemPreparedForTestingFromRepository() {

        return service.getAllByHotelier("HotelierName").stream().findFirst().get();
    }

    private Item itemPreparedForTesting(String hotelier) {

        return new Item(
                1,
                hotelier,
                2,
                Category.HOTEL,
                new Location(),
                "https://www.image.com",
                1,
                ReputationBadge.YELLOW,
                10L,
                2
        );
    }

}