package wrona.dominik.trivago_case_study.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

/**
 * Item model
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Validated
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Length(min = 10)
    @Pattern(regexp = "^(?!.*(Free|Book|Website|Offer)).*$", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String name;
    @Min(0)
    @Max(5)
    private int rating;
    private Category category;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @URL
    private String image;
    @Min(0)
    @Max(1000)
    @JsonIgnore
    private int reputation;
    private ReputationBadge reputationBadge;
    private Long price;
    @Min(0)
    private int availability;

}
