package ro.ubb.olympics.dto;

import lombok.AllArgsConstructor;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.utils.Pair;

import java.util.List;

/**
 * DTO for the report on the amount of money contributed by each sponsor.
 */
@AllArgsConstructor
public class SponsorContributionReportDTO {

    final List<Pair<Sponsor, Integer>> sponsorContributions;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        sponsorContributions
            .forEach(pair -> stringBuilder.append(String.format("%s: %d (USD)\n", pair.getFirst(), pair.getSecond())));

        return stringBuilder.substring(0, Math.max(stringBuilder.length() - 1, 0));
    }

}