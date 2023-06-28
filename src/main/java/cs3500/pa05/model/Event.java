package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a scheduled Event in a bullet journal.
 */
public class Event extends ScheduledItem {

  @JsonProperty("startTime") private Time startTime;
  @JsonProperty("duration") private int duration;
  @JsonProperty("type") private String type;

  /**
   * Constructor for an Event
   *
   * @param name name of the event
   * @param description description of the event
   * @param weekDay day the event will be taking place
   * @param startTime time the event will continue
   * @param duration how long the event will take (minutes)
   */
  @JsonCreator
  public Event(@JsonProperty("name") String name,
               @JsonProperty("description") String description,
               @JsonProperty("weekDay") DayOfWeek weekDay,
               @JsonProperty("startTime") Time startTime,
               @JsonProperty("duration") int duration) {
    super(name, description, weekDay);
    this.startTime = startTime;
    this.duration = duration;
    this.type = "event";
  }

  /**
   * Returns this event's start time.
   *
   * @return startTime the time at which this event starts
   */
  @JsonProperty("startTime")
  public Time getStartTime() {
    return startTime;
  }

  /**
   * Returns this event's duration
   *
   * @return duration how long this event is scheduled to last in minutes.
   */
  public int getDuration() {
    return duration;
  }


  /**
   * Returns a string value representing this day.
   *
   * @return String representing this day's stats
   */
  @Override
  public String toString() {
    return
        "Event Name: " + this.getName() + System.lineSeparator()
        + "Description: " + this.getDescription() + System.lineSeparator()
        + "Day Of Week: " + this.getWeekDay() + System.lineSeparator()
        + "Start Time: " + this.getStartTime() + System.lineSeparator()
        + "Duration: " + this.getDuration();
  }

  /**
   * Sets this event's starting time to the new time.
   *
   * @param newTime time to set this event's starting time to
   */
  @JsonProperty("startTime")
  public void setTime(Time newTime) {
    this.startTime = newTime;
  }

  /**
   * Sets this event's duration to the new duration.
   *
   * @param newDuration new duration to set this event's duration to
   */
  public void setDuration(int newDuration) {
    this.duration = newDuration;
  }

  /**
   * sets the type of this scheduledItem to an event
   *
   * @param type type "event" representing an event for JSON handling
   */
  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  /**
   * gets the type of this ScheduledItem
   *
   * @return type "event" representing an event for JSON handling
   */
  @JsonProperty("type")
  public String getType() {
    return this.type;
  }
}
