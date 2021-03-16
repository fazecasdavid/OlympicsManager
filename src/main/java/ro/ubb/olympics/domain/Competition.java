package ro.ubb.olympics.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Class for the Competition entity.
 *
 * @author David
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Competition extends BaseEntity<Long> {

    private Date date;
    private String location;
    private String name;
    private String description;

    /**
     * Constructor for the Competition Class
     *
     * @param id          - the id of the competition
     * @param date        - the date the competition takes place
     * @param location    - the location the competition takes place
     * @param name        - the name of the competition
     * @param description - a short description of the competition
     */
    public Competition(final Long id, final Date date, final String location, final String name, final String description) {
        super(id);
        this.date = date;
        this.location = location;
        this.name = name;
        this.description = description;
    }

}