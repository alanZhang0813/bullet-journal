package cs3500.pa05.javafx.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.json.EventJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for event json
 */
class EventJsonTest {

  private EventJson exampleEventJson;
  private String name;
  private String description;
  private DayOfWeek day;
  private Time startTime;
  private int duration;

  /**
   * Sets up the necessary objects and variables for the tests.
   */
  @BeforeEach
  void setup() {
    name = "game";
    description = "play games";
    day = DayOfWeek.MONDAY;
    startTime = new Time(4, 0);
    duration = 60;
    exampleEventJson = new EventJson("event", name, description, day, startTime, duration);
  }

  /**
   * Tests the type method.
   */
  @Test
  void type() {
    String expected = "event";
    assertEquals(expected, exampleEventJson.type());
  }

  /**
   * Tests the name method.
   */
  @Test
  void name() {
    String expected = name;
    assertEquals(expected, exampleEventJson.name());
  }

  /**
   * Tests the description method.
   */
  @Test
  void description() {
    String expected = description;
    assertEquals(expected, exampleEventJson.description());
  }

  /**
   * Tests the day method.
   */
  @Test
  void day() {
    DayOfWeek expected = day;
    assertEquals(expected, exampleEventJson.day());
  }

}