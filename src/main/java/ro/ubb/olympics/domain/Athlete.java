package ro.ubb.olympics.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Class for the Athlete entity.
 *
 * @author Sabina
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Athlete extends BaseEntity<Long> {

    private String firstName;
    private String lastName;
    private String country;
    private int age;

    /**
     * Constructor for the athlete class
     *
     * @param id        ~ the id of the athlete
     * @param firstName ~ the first name of the athlete
     * @param lastName  ~ the last name of the athlete
     * @param country   ~ the country of the athlete
     * @param age       ~ the age of the athlete
     */
    public Athlete(final Long id, final String firstName, final String lastName, final String country, final int age) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

}