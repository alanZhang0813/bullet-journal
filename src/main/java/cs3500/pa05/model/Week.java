package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.json.WeekAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a week in the bullet journal, containing a title and the full 7 days
 */
public class Week {

  private String weekTitle;
  private List<Day> days;
  private Integer maxTasksPerDay;
  private Integer maxEventsPerDay;

  /**
   * Constructor for a week object
   *
   * @param weekTitle name of the week
   */
  @JsonCreator
  public Week(
      @JsonProperty("weekTitle") String weekTitle) {
    this.weekTitle = weekTitle;
    maxEventsPerDay = -1;
    maxTasksPerDay = -1;
    initDays();
  }

  /**
   * May need an empty constructor so that
   */
  public Week() {
    this.weekTitle = "This Week";
    maxEventsPerDay = -1;
    maxTasksPerDay = -1;
    initDays();
  }

  /**
   * Initializes days for each day of the week and adds them to this week's list of days
   */
  private void initDays() {
    days = new ArrayList<>();
    for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
      days.add(new Day(dayOfWeek));
    }

  }

  /**
   * Copies over all relevant information about
   *
   * @param other the other week that we want to set this version to
   */
  public void set(WeekAdapter other) {
    this.setWeekTitle(other.getWeekTitle());
    this.setDays(other.getDays());
    this.setMaxTasksPerDay(other.getMaxTasksPerDay());
    this.setMaxEventsPerDay(other.getMaxEventsPerDay());
  }

  /**
   * Get title of week
   *
   * @return title of week
   */
  @JsonProperty("weekTitle")
  public String getWeekTitle() {
    return weekTitle;
  }

  /**
   * Sets title to provided string
   *
   * @param weekTitle new week title
   */
  public void setWeekTitle(String weekTitle) {
    this.weekTitle = weekTitle;
  }

  /**
   * gets this Week's list of days
   *
   * @return list of days
   */
  @JsonProperty("days")
  public List<Day> getDays() {
    return days;
  }

  /**
   * Sets this week's list of days to the given list during deserialization
   *
   * @param days days to set this week's days to
   */
  public void setDays(List<Day> days) {
    this.days = days;
  }

  /**
   * Returns requested day of the week

   * @param dayOfWeek DayOfWeek enum value representing this day

   * @return day of the week requested
   */
  public Day getDayOfWeek(DayOfWeek dayOfWeek) {
    for (Day day : this.days) {
      if (day.getDayOfWeek().equals(dayOfWeek)) {
        return day;
      }
    }
    return null;
  }


  /**
   * returns the max tasks per day
   *
   * @return max number of tasks allowed per day
   */
  public Integer getMaxTasksPerDay() {
    return maxTasksPerDay;
  }

  /**
   * Sets this week's task limit
   *
   * @param maxTasksPerDay maximum taks limit per day in this week.
   */
  public void setMaxTasksPerDay(int maxTasksPerDay) {
    this.maxTasksPerDay = maxTasksPerDay;
    for (Day day : this.days) {
      day.setMaxTasks(this.maxTasksPerDay);
    }
  }

  /**
   * returns the max events per day
   *
   * @return max number of events allowed per day
   */
  public Integer getMaxEventsPerDay() {
    return maxEventsPerDay;
  }

  /**
   * Sets the max events per day to the given value
   *
   * @param maxEventsPerDay max events per day to set
   */
  public void setMaxEventsPerDay(int maxEventsPerDay) {
    this.maxEventsPerDay = maxEventsPerDay;
    for (Day day : this.days) {
      day.setMaxEvents(this.maxEventsPerDay);
    }
  }





}
