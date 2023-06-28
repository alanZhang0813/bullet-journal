package cs3500.pa05.javafx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the task class
 */
class TaskTest {

  Task exampleTask;
  String name;
  String description;
  DayOfWeek day;

  @BeforeEach
  void setUp() {
    name = "write tests";
    description = "test model for pa5";
    day = DayOfWeek.MONDAY;
    exampleTask = new Task(name, description, day);
  }

  @Test
  void testToString() {
    String expected = "Name: " + name + System.lineSeparator()
        + "Description: " + description + System.lineSeparator()
        + "Day: " + day + System.lineSeparator()
        + "Completed? : false";

    assertEquals(expected, exampleTask.toString());
  }

  @Test
  void markAsComplete() {
    exampleTask.markAsComplete();

    String expected = "Name: " + name + System.lineSeparator()
        + "Description: " + description + System.lineSeparator()
        + "Day: " + day + System.lineSeparator()
        + "Completed? : true";

    assertEquals(expected, exampleTask.toString());
  }

  @Test
  void markAsIncomplete() {
    exampleTask.markAsComplete();
    exampleTask.markAsIncomplete();

    String expected = "Name: " + name + System.lineSeparator()
        + "Description: " + description + System.lineSeparator()
        + "Day: " + day + System.lineSeparator()
        + "Completed? : false";

    assertEquals(expected, exampleTask.toString());
  }

  @Test
  void testConstructor() {
    assertEquals(name, exampleTask.getName());
    assertEquals(description, exampleTask.getDescription());
    assertEquals(day, exampleTask.getWeekDay());
    assertFalse(exampleTask.getIsComplete());
  }

  @Test
  void setIsComplete() {
    exampleTask.setIsComplete(true);
    assertTrue(exampleTask.getIsComplete());
  }

  @Test
  void getIsComplete() {
    assertFalse(exampleTask.getIsComplete());
  }

  @Test
  void setName() {
    String newName = "new task name";
    exampleTask.setName(newName);
    assertEquals(newName, exampleTask.getName());
  }

  @Test
  void setDescription() {
    String newDescription = "new task description";
    exampleTask.setDescription(newDescription);
    assertEquals(newDescription, exampleTask.getDescription());
  }

  @Test
  void setWeekDay() {
    DayOfWeek newDay = DayOfWeek.TUESDAY;
    exampleTask.setWeekDay(newDay);
    assertEquals(newDay, exampleTask.getWeekDay());
  }

  @Test
  void setType() {
    String newType = "new task type";
    exampleTask.setType(newType);
    assertEquals(newType, exampleTask.getType());
  }


}