package ro.ubb.olympics.dto;

import lombok.AllArgsConstructor;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.utils.Pair;

import java.util.List;

/**
 * DTO for the report on the number of athletes that participated in a competition.
 */
@AllArgsConstructor
public class CompetitionParticipationReportDTO {

    final List<Pair<Competition, Integer>> competitionParticipation;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        competitionParticipation
            .forEach(pair -> stringBuilder.append(String.format("%s: %d (athletes)\n", pair.getFirst(), pair.getSecond())));

        return stringBuilder.substring(0, Math.max(stringBuilder.length() - 1, 0));
    }

}