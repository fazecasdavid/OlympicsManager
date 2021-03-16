package ro.ubb.olympics.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Class for the Sponsorship entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Sponsorship extends BaseEntity<Long> {

    private Long competitionId;
    private Long sponsorId;
    private int moneyContribution;

    /**
     * Constructor for the Sponsorship class
     *
     * @param id                - the id of the sponsorship
     * @param competitionId     - the id of the competition being sponsored
     * @param sponsorId         - the entity who is sponsoring the competition
     * @param moneyContribution - the amount of money contributed to the competition
     */
    public Sponsorship(final Long id, final Long competitionId, final Long sponsorId, final int moneyContribution) {
        super(id);
        this.competitionId = competitionId;
        this.sponsorId = sponsorId;
        this.moneyContribution = moneyContribution;
    }

}