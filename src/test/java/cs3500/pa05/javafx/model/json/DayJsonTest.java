package cs3500.pa05.javafx.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduledItem;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.json.DayJson;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the DayJson class.
 */
class DayJsonTest {

  private DayJson exampleDayJson;
  private DayOfWeek dayOfWeek;
  private List<ScheduledItem> listOfTodo;
  private Integer maxTasks;
  private Integer maxEvents;

  /**
   * Sets up the test fixture before each test method.
   */
  @BeforeEach
  void setup() {
    dayOfWeek = dayOfWeek.MONDAY;
    maxTasks = 2;
    maxEvents = 3;
    listOfTodo = Arrays.asList(new Event("game",
        "play games", dayOfWeek.MONDAY, new Time(2, 15), 60));
    exampleDayJson = new DayJson(dayOfWeek, listOfTodo, 2, 3);
  }

  /**
   * Tests the dayOfWeek method.
   */
  @Test
  void dayOfWeek() {
    DayOfWeek expected = dayOfWeek;
    assertEquals(exampleDayJson.dayOfWeek(), expected);
  }

  /**
   * Tests the listOfTodo method.
   */
  @Test
  void listOfTodo() {
    List<ScheduledItem> expected = listOfTodo;
    assertEquals(exampleDayJson.listOfTodo(), expected);
  }

  /**
   * Tests the maxTasks method.
   */
  @Test
  void maxTasks() {
    Integer expected = maxTasks;
    assertEquals(exampleDayJson.maxTasks(), expected);
  }

  /**
   * Tests the maxEvents method.
   */
  @Test
  void maxEvents() {
    Integer expected = maxEvents;
    assertEquals(exampleDayJson.maxEvents(), expected);
  }
}
