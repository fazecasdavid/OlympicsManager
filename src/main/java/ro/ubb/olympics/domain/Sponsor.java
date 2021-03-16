package ro.ubb.olympics.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Class for the Sponsor Entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Sponsor extends BaseEntity<Long> {

    private String name;
    private String country;

    /**
     * Constructor for the Sponsor class
     *
     * @param id      -the id of the sponsor
     * @param name    -the name of the sponsor
     * @param country -the country of the sponsor
     */
    public Sponsor(final Long id, final String name, final String country) {
        super(id);
        this.name = name;
        this.country = country;
    }

}