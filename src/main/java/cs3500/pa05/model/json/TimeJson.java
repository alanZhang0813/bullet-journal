package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record for a Time object
 *
 * @param hour hour of the day (24 hour clock)
 * @param minute minute of the day
 */
public record TimeJson(
    @JsonProperty("hour") int hour,
    @JsonProperty("minute") int minute) {
}
