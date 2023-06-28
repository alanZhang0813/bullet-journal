package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.ScheduledItem;
import java.util.List;

/**
 * Record for a Day
 *
 * @param dayOfWeek current Day of the week
 * @param listOfTodo list of scheduled items
 * @param maxTasks max number of tasks before warning
 * @param maxEvents max number of events before warning
 */
public record DayJson(
    @JsonProperty("dayOfWeek") DayOfWeek dayOfWeek,
    @JsonProperty("listOfTodo") List<ScheduledItem> listOfTodo,
    @JsonProperty("maxTasks") Integer maxTasks,
    @JsonProperty("maxEvents") Integer maxEvents) {}
