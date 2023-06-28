package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents when a ScheduledItem is scheduled for in 24-hour time. Intended for use as a sorting
 * mechanism in display
 */
public class Time {

  @JsonProperty("hour") private int hour;
  @JsonProperty("minute") private int minute;

  /**
   * Constructor for a time object
   *
   * @param hour hour of the day ( in 24 hour time )
   * @param minute minute of the day
   */
  @JsonCreator
  public Time(@JsonProperty("hour") int hour,
              @JsonProperty("minute") int minute) {
    this.hour = hour;
    this.minute = minute;
  }

  /**
   * get this time's the hour of the day
   *
   * @return hour of the day
   */
  @JsonProperty("hour")
  public int getHour() {
    return hour;
  }

  /**
   * get this time's the minute of the day
   *
   * @return minute of the day
   */
  @JsonProperty("minute")
  public int getMinute() {
    return minute;
  }

  /**
   * With this time as a starting time, and the given integer as a duration in
   * minutes, returns the time at which the duration would be over
   *
   * @param duration duration of an event after the start time
   * @return endTime an end time based on the given starting time and duration
   */
  public Time deriveEndTime(int duration) {
    int totalMinutes = this.hour * 60 + this.minute + duration;
    // calculate new hour and minute
    int newHour = totalMinutes / 60 % 24;
    int newMinute = totalMinutes % 60;
    return new Time(newHour, newMinute);
  }

  /**
   * Returns a String representation of this time with leading zeros for single digit
   * hours or minutes
   *
   * @return this time as a string
   */
  public String toString() {
    return String.format("%02d:%02d", this.hour, this.minute);
  }


  /**
   * Sets the hour of this time to the given hour
   *
   * @param hour field of this time
   */
  @JsonProperty("hour")
  public void setHour(int hour) {
    this.hour = hour;
  }

  /**
   * Sets the minute of this time to the given minute
   *
   * @param minute field of this time
   */
  @JsonProperty("minute")
  public void setMinute(int minute) {
    this.minute = minute;
  }
}
