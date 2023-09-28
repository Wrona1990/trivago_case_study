package wrona.dominik.trivago_case_study.api.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wrona.dominik.trivago_case_study.api.model.Item;
import wrona.dominik.trivago_case_study.service.ItemService;
import wrona.dominik.trivago_case_study.service.LocationService;

import java.util.List;
import java.util.Optional;

/**
 * Rest controller
 * providing endpoints for HTTP operations
 */
@Log4j2
@RestController
@RequestMapping(path = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private LocationService locationService;

    /**
     * Creates a new Item
     *
     * @param item json payload
     * @return item created
     */
    @RequestMapping(name = "/create", method = RequestMethod.POST)
    public ResponseEntity<Item> create(@Valid @RequestBody Item item) {
        locationService.add(item.getLocation());
        itemService.add(item);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * Finds an item by given id
     *
     * @param id id of an item to be found
     * @return item found
     */
    @RequestMapping(name = "/get", method = RequestMethod.GET)
    public ResponseEntity<Optional<Item>> getById(@RequestParam int id) {
        Optional<Item> item = itemService.findById(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Finds items by given hotel name
     *
     * @param hotelName name of items to be found
     * @return list of found items
     */
    @RequestMapping(value = "/getByHotel", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getByHotelName(@Valid @RequestParam String hotelName) {
        List<Item> items = itemService.getAllByHotelier(hotelName);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Finds items by given rating
     *
     * @param rating rating of items to be found
     * @return list of found items
     */
    @RequestMapping(value = "/getByRating", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getByRating(@RequestParam int rating) {
        List<Item> items = itemService.getAllByRating(rating);

        return new ResponseEntity<>(items, HttpStatus.FOUND);
    }

    /**
     * Finds items by given city
     *
     * @param city of items to be found
     * @return list of found items
     */
    @RequestMapping(value = "/getByCity", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getByCity(@RequestParam String city) {
        List<Item> items = itemService.getAllByCity(city);

        return new ResponseEntity<>(items, HttpStatus.FOUND);
    }

    /**
     * Finds items by given reputation badge
     *
     * @param reputationBadge of items to be found
     * @return list of found items
     */
    @RequestMapping(value = "/getByBadge", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getByReputationBadge(@RequestParam String reputationBadge) {
        List<Item> items = itemService.getAllByReputationBadge(reputationBadge);

        return new ResponseEntity<>(items, HttpStatus.FOUND);
    }

    /**
     * Books availability of an item by reducing {@link Item#getAvailability()}
     *
     * @param id of an item from which availability is going to be reduced
     * @return item from which availability has been reduced
     */
    @RequestMapping(value = "/book", method = RequestMethod.PUT)
    public ResponseEntity<Item> book(@RequestParam int id) throws Exception {
        Item item = itemService.findById(id).get();

        if (item.getAvailability() <= 0) {
            Exception lessThanZeroException = new RuntimeException("Availability cannot be less than zero");
            log.error(item, lessThanZeroException);

            throw lessThanZeroException;
        } else {
            itemService.reduceAvailability(item);

            return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
        }
    }

}
