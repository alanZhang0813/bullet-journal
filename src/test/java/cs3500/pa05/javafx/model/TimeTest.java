package cs3500.pa05.javafx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the time class
 */
class TimeTest {
  Time morningTime;
  Time afternoonTime;


  @BeforeEach
  void setUp() {
    morningTime = new Time(9, 45);
    afternoonTime = new Time(13, 30);
  }

  @Test
  void getHour() {
    assertEquals(9, morningTime.getHour());
    assertEquals(13, afternoonTime.getHour());
  }

  @Test
  void getMinute() {
    assertEquals(45, morningTime.getMinute());
    assertEquals(30, afternoonTime.getMinute());
  }

  @Test
  void deriveEndTime() {
    Time morningTimeEnd = morningTime.deriveEndTime(180);
    Time afternoonTimeEnd = afternoonTime.deriveEndTime(90);
    assertEquals(12, morningTimeEnd.getHour());
    assertEquals(45, morningTimeEnd.getMinute());
    assertEquals(15, afternoonTimeEnd.getHour());
    assertEquals(00, afternoonTimeEnd.getMinute());
  }

  @Test
  void testToString() {
    assertEquals("09:45", morningTime.toString());
    assertEquals("13:30", afternoonTime.toString());
  }

  @Test
  void setHour() {
    morningTime.setHour(10);
    afternoonTime.setHour(14);
    assertEquals(10, morningTime.getHour());
    assertEquals(14, afternoonTime.getHour());
  }

  @Test
  void setMinute() {
    morningTime.setMinute(30);
    afternoonTime.setMinute(45);
    assertEquals(30, morningTime.getMinute());
    assertEquals(45, afternoonTime.getMinute());
  }
}