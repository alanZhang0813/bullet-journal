package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.DayOfWeek;

/**
 * Represents a record for a task Json
 *
 * @param name Name of the task
 * @param description Task Description
 * @param weekDay Day of the week
 * @param isComplete is the task complete?
 */
public record TaskJson(
    @JsonProperty("type") String type,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("weekDay") DayOfWeek weekDay,
    @JsonProperty("isComplete") boolean isComplete) {
}
