package cs3500.pa05.javafx.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.Week;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.WeekAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the BujoJson class.
 */
class BujoJsonTest {

  private BujoJson exampleBujoJson;
  private WeekAdapter exampleWeek;
  private Week week;

  /**
   * Sets up the test fixture before each test method.
   */
  @BeforeEach
  void setup() {
    week = new Week();
    exampleWeek = new WeekAdapter(week);
    exampleBujoJson = new BujoJson(
        exampleWeek, "\"I got 99 problems, but a bitch ain't one.\" \n- Spider-Man", "password");
  }

  /**
   * Tests the week() method.
   */
  @Test
  void week() {
    WeekAdapter expected = exampleWeek;
    assertEquals(expected, exampleBujoJson.week());
  }

  /**
   * Tests the quotes() method.
   */
  @Test
  void quotes() {
    String expectedQuote = "\"I got 99 problems, but a bitch ain't one.\" \n- Spider-Man";
    assertEquals(expectedQuote, exampleBujoJson.quotes());
  }

  /**
   * Tests the password() method.
   */
  @Test
  void password() {
    String expectedPassword = "password";
    assertEquals(expectedPassword, exampleBujoJson.password());
  }
}
