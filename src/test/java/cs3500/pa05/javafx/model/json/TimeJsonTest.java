package cs3500.pa05.javafx.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.json.TimeJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeJsonTest {
  TimeJson exampleTimeJson;
  int hour;
  int minute;


  @BeforeEach
  void setUp() {
    hour = 4;
    minute = 20;
    exampleTimeJson = new TimeJson(hour, minute);
  }

  @Test
  void hour() {
    int expected = hour;
    assertEquals(exampleTimeJson.hour(), expected);
  }

  @Test
  void minute() {
    int expected = minute;
    assertEquals(exampleTimeJson.minute(), expected);
  }
}