package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a scheduled Task in a bullet journal.
 */
public class Task extends ScheduledItem {
  @JsonProperty("type") private String type;

  @JsonProperty("isComplete") private boolean isComplete;

  /**
   * Constructor for a Task
   *
   * @param name        name of the Task
   * @param description description of the task
   * @param weekDay     day of the week the task will take place
   */
  @JsonCreator
  public Task(@JsonProperty("name") String name,
              @JsonProperty("description") String description,
              @JsonProperty("weekDay") DayOfWeek weekDay) {
    super(name, description, weekDay);
    isComplete = false;
    this.type = "task";
  }

  @Override
  public String toString() {
    return "Name: " + this.getName() + System.lineSeparator()
        + "Description: " + this.getDescription() + System.lineSeparator()
        + "Day: " + this.getWeekDay() + System.lineSeparator()
        + "Completed? : " + this.isComplete;
  }

  /**
   * Sets this tasks completed status to true
   */
  public void markAsComplete() {
    this.isComplete = true;
  }


  /**
   * Sets this tasks completed status to false
   */
  public void markAsIncomplete() {
    this.isComplete = false;
  }

  /**
   * Sets the completion of this Task to the given status
   *
   * @param isCompleteStatus whether this task is complete
   */
  @JsonProperty("isComplete")
  public void setIsComplete(boolean isCompleteStatus) {
    this.isComplete = isCompleteStatus;
  }

  /**
   * Returns the completion status of this task
   *
   * @return isComplete is this task complete
   */
  @JsonProperty("isComplete")
  public boolean getIsComplete() {
    return isComplete;
  }

  /**
   * Sets the type of this scheduledItem to "task" for JSON parsing
   *
   * @param type String type for JSON purposes
   */
  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets the type of this scheduled item
   *
   * @return type returns "task" to represent this item's status as a task.
   */
  @JsonProperty("type")
  public String getType() {
    return this.type;
  }


}
