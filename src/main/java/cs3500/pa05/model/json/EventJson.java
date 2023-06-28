package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Time;

/**
 * Represents an Event to be stored in a bojo file.
 *
 * @param name name of the event
 * @param description description of the event
 * @param day day the event will take place
 * @param startTime Time the event will take place
 * @param duration duration of the event
 */
public record EventJson(
    @JsonProperty("type") String type,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("weekDay") DayOfWeek day,
    @JsonProperty("startTime") Time startTime,
    @JsonProperty("duration") int duration) {
}
