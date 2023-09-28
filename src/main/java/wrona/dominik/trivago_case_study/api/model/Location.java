package wrona.dominik.trivago_case_study.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 * Location model
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Validated
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    private String state;
    private String country;
    @Length(min = 5, max = 5)
    private String zip_code;
    private String address;
    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Item item;
}


