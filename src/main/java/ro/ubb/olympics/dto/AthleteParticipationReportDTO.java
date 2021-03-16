package ro.ubb.olympics.dto;

import lombok.AllArgsConstructor;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.utils.Pair;

import java.util.List;

/**
 * DTO for the report on the number of participations for each athlete.
 */
@AllArgsConstructor
public class AthleteParticipationReportDTO {

    final List<Pair<Athlete, Integer>> athleteParticipation;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        athleteParticipation
            .forEach(pair -> stringBuilder.append(String.format("%s: %d (participations)\n", pair.getFirst(), pair.getSecond())));

        return stringBuilder.substring(0, Math.max(stringBuilder.length() - 1, 0));
    }

}