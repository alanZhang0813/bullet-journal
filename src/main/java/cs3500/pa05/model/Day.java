package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the contents of a day of the week in the bullet journal
 */
public class Day {

  @JsonProperty("dayOfWeek") private DayOfWeek dayOfWeek;
  @JsonProperty("listOfScheduled") private List<ScheduledItem> listOfScheduled;
  @JsonProperty("maxTasks") private Integer maxTasks;
  @JsonProperty("maxEvents") private Integer maxEvents;
  @JsonProperty("events") private List<Event> events;
  @JsonProperty("tasks") private List<Task> tasks;

  /**
   * Constructor for a Day. Creates an instance of a Day given the day of the week it falls on.
   *
   * @param day day of the week
   */
  @JsonCreator
  public Day(
      @JsonProperty("dayOfWeek") DayOfWeek day) {
    this.dayOfWeek = day;
    this.listOfScheduled = new ArrayList<>();
    this.tasks = new ArrayList<>();
    this.events = new ArrayList<>();
    maxTasks = -1;
    maxEvents = -1;
  }

  /**
   * Handles creating a new event or task and assigns it to a day
   *
   * @param toBeScheduled item to be scheduled
   */
  public void scheduleItem(ScheduledItem toBeScheduled) {
    this.listOfScheduled.add(toBeScheduled);

    if (toBeScheduled instanceof Task) {
      this.tasks.add((Task) toBeScheduled);
    } else if (toBeScheduled instanceof Event) {
      this.events.add((Event) toBeScheduled);
    }
  }

  /**
   * Handles removing an event or task from a day
   *
   * @param tobeRemoved item to be removed
   */
  public void removeItem(ScheduledItem tobeRemoved) {
    this.listOfScheduled.remove(tobeRemoved);

    if (tobeRemoved instanceof Task) {
      this.tasks.remove(tobeRemoved);
    } else if (tobeRemoved instanceof Event) {
      this.events.remove(tobeRemoved);
    }
  }

  /**
   * Returns this day's day of the week
   *
   * @return dayOfWeek this day's day of the week
   */
  @JsonProperty("dayOfWeek")
  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  /**
   * sets the day of the week
   *
   * @param dayOfWeek given day of the week to set to
   */
  @JsonProperty("dayOfWeek")
  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  /**
   * Establishes a limit for the maximum amount of tasks the user can schedule for this day
   *
   * @param limit max amount of tasks
   */
  public void setMaxTasks(int limit) {
    this.maxTasks = limit;
  }

  /**
   * Establishes a limit for the maximum amount of events the user can schedule for this day
   *
   * @param limit max amounts of events
   */
  public void setMaxEvents(int limit) {
    this.maxEvents = limit;
  }

  /**
   * Returns a boolean value representing whether the amount of tasks scheduled by the user for this
   * day exceeds the maximum limit set by the user.
   * If the maximum number of tasks is -1 (meaning that a maximum has not been set yet),
   * this indicates that there is no set limit on the number of tasks in the day, and therefore the
   * number of tasks does not exceed a maximum.
   *
   * @return exceedsMax does the number of tasks exceed the max?
   */
  public boolean doTasksExceedMax() {
    return maxTasks != -1 && this.getTotalTasks() > this.maxTasks;
  }


  /**
   * Returns a boolean value representing whether the amount of tasks scheduled by the user for this
   * day exceeds the maximum limit set by the user
   *
   * @return exceedsMax does the number of events exceed the max?
   */
  public boolean doEventsExceedMax() {
    return maxEvents != -1 && this.getTotalEvents() > this.maxEvents;
  }

  /**
   * Returns all scheduled items in this day
   *
   * @return list of all scheduled items for this day
   */
  @JsonProperty("listOfScheduled")
  public List<ScheduledItem> getListOfScheduled() {
    return listOfScheduled;
  }

  /**
   * Sets the list of scheduled items for this day to a given list
   *
   * @param listOfScheduled list of all scheduled events
   */
  @JsonProperty("listOfScheduled")
  public void setListOfScheduled(List<ScheduledItem> listOfScheduled) {
    this.listOfScheduled = listOfScheduled;
  }

  /**
   * How many tasks are in this day's list of scheduled items?
   *
   * @return numberOfEvents number of events in this day's scheduled items
   */
  @JsonIgnore
  public int getTotalTasks() {
    int numberOfTasks = 0;
    for (ScheduledItem item : this.listOfScheduled) {
      if (item instanceof Task) {
        numberOfTasks++;
      }
    }

    return numberOfTasks;

  }


  /**
   * How many events are in this day's list of scheduled items?
   *
   * @return numberOfEvents number of events in this day's scheduled items
   */
  @JsonIgnore
  public int getTotalEvents() {
    int numberOfEvents = 0;
    for (ScheduledItem item : this.listOfScheduled) {
      if (item instanceof Event) {
        numberOfEvents++;
      }
    }
    return numberOfEvents;
  }

  /**
   * Returns the maximum number of tasks allowed to be scheduled on this day.
   *
   * @return maxTasks task limit for this day
   */
  @JsonProperty("maxTasks")
  public int getMaxTasks() {
    return this.maxTasks;
  }

  /**
   * Returns the maximum number of events allowed to be scheduled on this day.
   *
   * @return maxEvents event limit for this day
   */
  @JsonProperty("maxEvents")
  public int getMaxEvents() {
    return this.maxEvents;
  }

  /**
   * Gets the tasks for this day
   *
   * @return all tasks in this day's list of scheduled items
   */
  @JsonProperty("tasks")
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   * gets the events for this day
   *
   * @return events all events in this day's list of scheduled items.
   */
  @JsonProperty("events")
  public List<Event> getEvents() {
    return events;
  }

  /**
   * Sets the list of tasks in this day to the given list
   *
   * @param taskList list of tasks to be attributed to this day
   */
  @JsonProperty("tasks")
  public void setTasks(List<Task> taskList) {
    this.tasks = taskList;
  }


  /**
   * Sets the list of events in this day to the given list
   *
   * @param eventsList list of tasks to be attributed to this day
   */
  @JsonProperty("events")
  public void setEvents(List<Event> eventsList) {
    this.events = eventsList;
  }
}
