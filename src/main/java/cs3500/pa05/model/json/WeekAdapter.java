package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Week;
import java.util.List;

/**
 * A class to adapt the Week model object for JSON serialization/deserialization
 * with Jackson.
 */
public class WeekAdapter {
  private final String weekTitle;
  private final List<Day> days;
  private final Integer maxTasksPerDay;
  private final Integer maxEventsPerDay;

  /**
   * Constructs a WeekAdapter from a Week object.
   *
   * @param week the Week object to adapt
   */
  public WeekAdapter(Week week) {
    weekTitle = week.getWeekTitle();
    days = week.getDays();
    maxTasksPerDay = week.getMaxTasksPerDay();
    maxEventsPerDay = week.getMaxEventsPerDay();
  }

  /**
   * Constructs a WeekAdapter from individual fields, typically used by Jackson
   * during deserialization.
   *
   * @param weekTitle the title of the week
   * @param days the list of Day objects
   * @param maxTasksPerDay the maximum number of tasks per day
   * @param maxEventsPerDay the maximum number of events per day
   */
  @JsonCreator
  public WeekAdapter(@JsonProperty("weekTitle") String weekTitle,
                     @JsonProperty("days") List<Day> days,
                     @JsonProperty("maxTasksPerDay") Integer maxTasksPerDay,
                     @JsonProperty("maxEventsPerDay") Integer maxEventsPerDay) {
    this.weekTitle = weekTitle;
    this.days = days;
    this.maxTasksPerDay = maxTasksPerDay;
    this.maxEventsPerDay = maxEventsPerDay;
  }

  /**
   * Gets this week's title
   *
   * @return the title of the week
   */
  public String getWeekTitle() {
    return weekTitle;
  }

  /**
   * Gets this week's list of days
   *
   * @return the list of Day objects
   */
  public List<Day> getDays() {
    return days;
  }

  /**
   * Gets the task limit
   *
   * @return the maximum number of tasks per day
   */
  public Integer getMaxTasksPerDay() {
    return maxTasksPerDay;
  }

  /**
   * Gets the event limit
   *
   * @return the maximum number of events per day
   */
  public Integer getMaxEventsPerDay() {
    return maxEventsPerDay;
  }
}
