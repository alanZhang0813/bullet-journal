package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents a scheduled item (an event or task) established by the user at a certain time in the
 * bullet journal, containing an item name and description of what the aim to accomplish is.
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Event.class, name = "event"),
    @JsonSubTypes.Type(value = Task.class, name = "task")
})
public abstract class ScheduledItem {
  private String name;
  private String description;
  private DayOfWeek weekDay;

  /**
   * Constructor for an AbstractItem
   *
   * @param name A name describing this scheduled item.
   * @param description A description of the scheduled item.
   * @param weekDay the day of the week that this scheduled item takes place on.
   */
  @JsonCreator
  public ScheduledItem(@JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("day") DayOfWeek weekDay) {
    this.name = name;
    this.description = description;
    this.weekDay = weekDay;
  }

  /**
   * Returns the name of this item
   *
   * @return name the name of this scheduled item
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the description of this item

   * @return description the description of this item
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns this item's day
   *
   * @return day the day this item is scheduled for
   */
  @JsonProperty("weekDay")
  public DayOfWeek getWeekDay() {
    return weekDay;
  }

  /**
   * Returns a String representation of this item
   *
   * @return toString a string representation of this item's fields.
   */
  public abstract String toString();

  /**
   * Sets the name of this scheduled item to the given name
   *
   * @param newName name to replace current name with
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Sets the description of this scheduledItem to the given description.
   *
   * @param newDesc description to replace current description with.
   */
  public void setDescription(String newDesc) {
    this.description = newDesc;
  }


  /**
   * sets te day of the week to this day
   *
   * @param weekDay day of the week to set to
   */
  @JsonProperty("weekDay")
  public void setWeekDay(DayOfWeek weekDay) {
    this.weekDay = weekDay;
  }

}
