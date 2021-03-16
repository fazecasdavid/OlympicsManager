package ro.ubb.olympics.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Class for the Participation entity.
 *
 * @author Rares
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Participation extends BaseEntity<Long> {

    private Long athleteId;
    private Long competitionId;
    private int rank;

    /**
     * Constructor for the Participation class
     *
     * @param id            ~ the id of the participation
     * @param athleteId     ~ the id of the athlete
     * @param competitionId ~ the id of the competition in which the athlete participates
     * @param rank          ~ the rank of the athlete in the competition
     */
    public Participation(final Long id, final Long athleteId, final Long competitionId, final int rank) {
        super(id);
        this.athleteId = athleteId;
        this.competitionId = competitionId;
        this.rank = rank;
    }

}