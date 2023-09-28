package wrona.dominik.trivago_case_study.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wrona.dominik.trivago_case_study.api.model.Item;
import wrona.dominik.trivago_case_study.api.model.ReputationBadge;
import wrona.dominik.trivago_case_study.repository.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ItemService
 * responsible for business logic operations on {@link Item} model
 */
@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    /**
     * Saves given item to repository
     *
     * @param item item object to be saved
     * @return saved item
     */
    public Item add(Item item) {
        return repository.save(item);
    }

    /**
     * Finds all items that exist in repository
     *
     * @return found items
     */
    public List<Item> findAll() {
        return repository.findAll();
    }

    /**
     * Finds item with given id if exists in repository
     *
     * @param id id of an item to be found
     * @return found item if present
     */
    public Optional<Item> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Reduces availability of given item
     *
     * @param item item from which availability is about to be reduced
     */
    public void reduceAvailability(Item item) {
        Item bookedItem = findById(item.getId()).get();
        int availability = bookedItem.getAvailability();

        if (availability >= 1) {
            bookedItem.setAvailability(availability - 1);
            repository.save(item);
        }
    }

    /**
     * Sets reputation badge {@link Item#getReputationBadge()} depending on reputation {@link Item#getReputation()}
     * If reputation is <= 500 the value is RED
     * If reputation is <= 799 the value is YELLOW
     * Otherwise the value is GREEN
     *
     * @param item item that reputation badge is about to be set
     */
    public void setReputationBadge(Item item) {
        int reputation = item.getReputation();

        if (reputation <= 500) {
            item.setReputationBadge(ReputationBadge.RED);
        } else if (reputation <= 799) {
            item.setReputationBadge(ReputationBadge.YELLOW);
        } else {
            item.setReputationBadge(ReputationBadge.GREEN);
        }
    }

    /**
     * Finds all items with given hotelier name that exist in repository
     *
     * @param hotelier hotelier name to search
     * @return items with given hotelier name found in repository
     */
    public List<Item> getAllByHotelier(String hotelier) {
        return repository
                .findAll()
                .stream()
                .filter(item -> item.getName().equals(hotelier))
                .collect(Collectors.toList());
    }

    /**
     * Finds all items with given rating that exist in repository
     *
     * @param rating rating value to search
     * @return items with given rating found in repository
     */
    public List<Item> getAllByRating(int rating) {
        return repository
                .findAll()
                .stream()
                .filter(item -> item.getRating() == rating)
                .collect(Collectors.toList());
    }

    /**
     * Finds all items with given city that exist in repository
     *
     * @param city city to search
     * @return items with given city found in repository
     */
    public List<Item> getAllByCity(String city) {
        return repository
                .findAll()
                .stream()
                .filter(item -> item.getLocation().getCity().equals(city))
                .collect(Collectors.toList());
    }

    /**
     * Finds all items with given reputation badge that exist in repository
     *
     * @param reputationBadge hotelier name to search
     * @return items with given reputation badge found in repository
     */
    public List<Item> getAllByReputationBadge(String reputationBadge) {
        return repository
                .findAll()
                .stream()
                .filter(item -> item.getReputationBadge().name().equalsIgnoreCase(reputationBadge))
                .collect(Collectors.toList());
    }
}
