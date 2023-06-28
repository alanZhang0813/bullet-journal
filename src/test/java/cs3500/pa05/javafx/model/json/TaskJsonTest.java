package cs3500.pa05.javafx.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.json.TaskJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskJsonTest {
  private TaskJson exampleTask;
  private String name;
  private String description;
  private DayOfWeek dayOfWeek;
  private boolean isComplete = false;


  @BeforeEach
  void setUp() {
    name = "Meow Like A Kitty";
    description = "Kitty Goes \"Meow\"";
    dayOfWeek = DayOfWeek.MONDAY;
    exampleTask = new TaskJson("task", name, description, dayOfWeek, isComplete);
    assertEquals("task", exampleTask.type());
    assertEquals(name, exampleTask.name());
    assertEquals(description, exampleTask.description());
    assertEquals(dayOfWeek, exampleTask.weekDay());
    assertEquals(isComplete, exampleTask.isComplete());
  }

  @Test
  void type() {
    assertEquals("task", exampleTask.type());
  }

  @Test
  void name() {
    assertEquals(name, exampleTask.name());
  }

  @Test
  void description() {
    assertEquals(description, exampleTask.description());
  }

  @Test
  void weekDay() {
    assertEquals(dayOfWeek, exampleTask.weekDay());
  }

  @Test
  void isComplete() {
    assertEquals(isComplete, exampleTask.isComplete());
  }
}