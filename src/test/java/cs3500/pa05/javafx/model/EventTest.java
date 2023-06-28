package cs3500.pa05.javafx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the event class
 */
class EventTest {
  Event event;
  Time startTime;

  @BeforeEach
  void setUp() {
    startTime = new Time(11, 40);
    event = new Event("Go to OOD", "PA launch", DayOfWeek.MONDAY,
        startTime, 100);
  }

  @Test
  void getStartTime() {
    assertEquals(event.getStartTime(), startTime);
  }

  @Test
  void getDuration() {
    assertEquals(event.getDuration(), 100);
  }


  @Test
  void testToString() {
    String expected = "Event Name: Go to OOD\nDescription: PA launch\nDay Of Week: MONDAY\n"
        + "Start Time: 11:40\nDuration: 100";
    assertEquals(expected, event.toString());
  }

  @Test
  void setTime() {
    Time newTime = new Time(12, 30);
    event.setTime(newTime);
    assertEquals(newTime, event.getStartTime());
  }

  @Test
  void setDuration() {
    event.setDuration(120);
    assertEquals(120, event.getDuration());
  }

  @Test
  void setType() {
    event.setType("new_type");
    assertEquals("new_type", event.getType());
  }
}