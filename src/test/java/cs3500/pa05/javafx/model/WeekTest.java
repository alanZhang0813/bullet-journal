package cs3500.pa05.javafx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.json.WeekAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the week class
 */
class WeekTest {

  private Week exampleWeek;

  @BeforeEach
  void setUp() {
    exampleWeek = new Week("Test Week");
  }

  @Test
  void getWeekTitle() {
    assertEquals("Test Week", exampleWeek.getWeekTitle());
  }

  @Test
  void getDays() {
    List<Day> days = exampleWeek.getDays();
    assertEquals(7, days.size());
  }

  @Test
  void getDayOfWeek() {
    Day exampleDay = exampleWeek.getDayOfWeek(DayOfWeek.MONDAY);
    assertEquals(DayOfWeek.MONDAY, exampleDay.getDayOfWeek());
  }

  @Test
  void getMaxTasksPerDay() {
    assertEquals(-1, exampleWeek.getMaxTasksPerDay());
    exampleWeek.setMaxTasksPerDay(5);
    assertEquals(5, exampleWeek.getMaxTasksPerDay());
  }

  @Test
  void getMaxEventsPerDay() {
    assertEquals(-1, exampleWeek.getMaxEventsPerDay());
    exampleWeek.setMaxEventsPerDay(5);
    assertEquals(5, exampleWeek.getMaxEventsPerDay());
  }

  @Test
  void setMaxTasksPerDay() {
    exampleWeek.setMaxTasksPerDay(10);
    assertEquals(10, exampleWeek.getMaxTasksPerDay());
    Day exampleDay = exampleWeek.getDayOfWeek(DayOfWeek.MONDAY);
    assertEquals(10, exampleDay.getMaxTasks());
  }

  @Test
  void setMaxEventsPerDay() {
    exampleWeek.setMaxEventsPerDay(10);
    assertEquals(10, exampleWeek.getMaxEventsPerDay());
    Day exampleDay = exampleWeek.getDayOfWeek(DayOfWeek.MONDAY);
    assertEquals(10, exampleDay.getMaxEvents());
  }

  @Test
  void setWeekTitle() {
    exampleWeek.setWeekTitle("New Week");
    assertEquals("New Week", exampleWeek.getWeekTitle());
  }

  @Test
  void setDays() {
    List<Day> newDays = Arrays.asList(new Day(DayOfWeek.MONDAY), new Day(DayOfWeek.FRIDAY));
    exampleWeek.setDays(newDays);

    assertEquals(2, exampleWeek.getDays().size());
    assertEquals(DayOfWeek.MONDAY, exampleWeek.getDays().get(0).getDayOfWeek());
  }

  @Test
  void getDayOfWeekReturnNull() {
    assertNull(exampleWeek.getDayOfWeek(null));
  }

  @Test
  void setWeekAdapter() {
    // Initialize a new WeekAdapter with a different name, an empty list of days,
    // and maxTasksPerDay and maxEventsPerDay set to 5.
    WeekAdapter adapter = new WeekAdapter("New Week", new ArrayList<>(), 5, 5);

    // Now we use this adapter to set values on the exampleWeek object.
    exampleWeek.set(adapter);

    // Check that the values were correctly set.
    assertEquals("New Week", exampleWeek.getWeekTitle());
    assertEquals(new ArrayList<>(), exampleWeek.getDays());
    assertEquals(5, exampleWeek.getMaxTasksPerDay());
    assertEquals(5, exampleWeek.getMaxEventsPerDay());
  }
}