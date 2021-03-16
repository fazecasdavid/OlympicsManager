package ro.ubb.olympics.dto;

import lombok.AllArgsConstructor;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.utils.Pair;

import java.util.List;

/**
 * DTO for the report on the number of sponsorships for each competition.
 */
@AllArgsConstructor
public class CompetitionSponsorshipsReportDTO {

    final List<Pair<Competition, Integer>> competitionSponsorships;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        competitionSponsorships
            .forEach(pair -> stringBuilder.append(String.format("%s: %d (sponsors)\n", pair.getFirst(), pair.getSecond())));

        return stringBuilder.substring(0, Math.max(stringBuilder.length() - 1, 0));
    }

}
