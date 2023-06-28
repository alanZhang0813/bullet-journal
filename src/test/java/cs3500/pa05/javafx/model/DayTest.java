package cs3500.pa05.javafx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduledItem;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Time;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Day class
 */
class DayTest {

  Day exampleMonday;
  Day exampleWednesday;
  Day exampleSaturday;

  ScheduledItem takeOutTrash;

  ScheduledItem appointment;
  Time appointmentTime;
  ScheduledItem euthanizeGrandma;
  Time timeToDie;


  /**
   Sets up the test environment before each test case.
   */
  @BeforeEach
  void setUp() {
    exampleMonday = new Day(DayOfWeek.MONDAY);
    exampleWednesday = new Day(DayOfWeek.WEDNESDAY);
    exampleSaturday = new Day(DayOfWeek.SATURDAY);

    takeOutTrash = new Task("Take Out Trash", "", DayOfWeek.MONDAY);

    appointmentTime = new Time(4, 20);
    appointment = new Event(
        "Dentist Appointment", "Cleaning", DayOfWeek.WEDNESDAY, appointmentTime, 1);

    timeToDie = new Time(6, 9);
    euthanizeGrandma = new Event(
        "Euthanize Grandma", "Put her down", DayOfWeek.SATURDAY, timeToDie, 1);

  }

  /**
   Tests the scheduleItem() method of the Day class.
   */
  @Test
  void scheduleItem() {
    assertEquals(0, exampleMonday.getTotalTasks());
    assertEquals(0, exampleMonday.getTotalEvents());
    assertEquals(0, exampleMonday.getListOfScheduled().size());
    exampleMonday.scheduleItem(takeOutTrash);
    assertEquals(1, exampleMonday.getTotalTasks());
    assertEquals(0, exampleMonday.getTotalEvents());
    assertEquals(1, exampleMonday.getListOfScheduled().size());
    exampleMonday.scheduleItem(euthanizeGrandma);
    assertEquals(1, exampleMonday.getTotalTasks());
    assertEquals(1, exampleMonday.getTotalEvents());
    assertEquals(2, exampleMonday.getListOfScheduled().size());
    List<ScheduledItem> newList = Arrays.asList(takeOutTrash, euthanizeGrandma,
        new Task("Test Pa", "Test pa05", DayOfWeek.WEDNESDAY));
    exampleMonday.setListOfScheduled(newList);
    assertEquals(3, exampleMonday.getListOfScheduled().size());

  }

  /**
   Tests the getDayOfWeek() method of the Day class.
   */
  @Test
  void getDayOfWeek() {
    assertEquals(DayOfWeek.MONDAY, exampleMonday.getDayOfWeek());
    assertEquals(DayOfWeek.WEDNESDAY, exampleWednesday.getDayOfWeek());
    assertEquals(DayOfWeek.SATURDAY, exampleSaturday.getDayOfWeek());
  }

  /**
   Tests the setMaxTasks() method of the Day class.
   */
  @Test
  void setMaxTasks() {
    exampleMonday.setMaxTasks(10);
    assertEquals(10, exampleMonday.getMaxTasks());
    exampleMonday.setMaxTasks(20);
    assertEquals(20, exampleMonday.getMaxTasks());
  }

  /**
   Tests the setMaxEvents() method of the Day class.
   */
  @Test
  void setMaxEvents() {
    exampleMonday.setMaxEvents(10);
    assertEquals(10, exampleMonday.getMaxEvents());
    exampleMonday.setMaxEvents(20);
    assertEquals(20, exampleMonday.getMaxEvents());
  }

  /**
   Tests the doTasksExceedMax() method of the Day class.
   */
  @Test
  void doTasksExceedMax() {
    assertFalse(exampleMonday.doTasksExceedMax());
    exampleMonday.setMaxTasks(1);
    exampleMonday.scheduleItem(takeOutTrash);
    assertFalse(exampleMonday.doTasksExceedMax());
    exampleMonday.scheduleItem(takeOutTrash);
    assertTrue(exampleMonday.doTasksExceedMax());

  }

  /**
   Tests the doEventsExceedMax() method of the Day class.
   */
  @Test
  void doEventsExceedMax() {
    assertFalse(exampleWednesday.doEventsExceedMax());
    exampleWednesday.setMaxEvents(1);
    exampleWednesday.scheduleItem(appointment);
    assertFalse(exampleWednesday.doEventsExceedMax());
    exampleWednesday.scheduleItem(appointment);
    assertTrue(exampleWednesday.doEventsExceedMax());
  }

  /**
   Tests the getListOfScheduled() method of the Day class.
   */
  @Test
  void getListOfTodo() {
    exampleMonday.scheduleItem(takeOutTrash);
    exampleMonday.scheduleItem(euthanizeGrandma);

    List<ScheduledItem> expected = Arrays.asList(takeOutTrash, euthanizeGrandma);
    assertEquals(expected, exampleMonday.getListOfScheduled());
  }

  /**
   Tests the getTotalTasks() method of the Day class.
   */
  @Test
  void getTotalTasks() {
    exampleMonday.scheduleItem(takeOutTrash);
    assertEquals(1, exampleMonday.getTotalTasks());

    exampleMonday.scheduleItem(takeOutTrash);
    assertEquals(2, exampleMonday.getTotalTasks());

    exampleMonday.scheduleItem(appointment);
    assertEquals(2, exampleMonday.getTotalTasks());
  }

  /**
   Tests the getTotalEvents() method of the Day class.
   */
  @Test
  void getTotalEvents() {
    exampleMonday.scheduleItem(appointment);
    assertEquals(1, exampleMonday.getTotalEvents());

    exampleMonday.scheduleItem(appointment);
    assertEquals(2, exampleMonday.getTotalEvents());

    exampleMonday.scheduleItem(takeOutTrash);
    assertEquals(2, exampleMonday.getTotalEvents());
  }

  /**
   Tests the getMaxTasks() method of the Day class.
   */
  @Test
  void getMaxTasks() {
    assertEquals(-1, exampleMonday.getMaxTasks());

    exampleMonday.setMaxTasks(5);
    assertEquals(5, exampleMonday.getMaxTasks());
  }

  /**
   Tests the getMaxEvents() method of the Day class.
   */
  @Test
  void getMaxEvents() {
    assertEquals(-1, exampleMonday.getMaxEvents());

    exampleMonday.setMaxEvents(5);
    assertEquals(5, exampleMonday.getMaxEvents());
  }

  /**
   Tests the removeItem() method of the Day class.
   */
  @Test
  void removeItem() {
    assertEquals(0, exampleMonday.getTotalTasks());
    assertEquals(0, exampleMonday.getTotalEvents());
    assertEquals(0, exampleMonday.getListOfScheduled().size());

    exampleMonday.scheduleItem(takeOutTrash);
    exampleMonday.scheduleItem(appointment);

    assertEquals(1, exampleMonday.getTotalTasks());
    assertEquals(1, exampleMonday.getTotalEvents());
    assertEquals(2, exampleMonday.getListOfScheduled().size());

    exampleMonday.removeItem(takeOutTrash);

    assertEquals(0, exampleMonday.getTotalTasks());
    assertEquals(1, exampleMonday.getTotalEvents());
    assertEquals(1, exampleMonday.getListOfScheduled().size());

    exampleMonday.removeItem(appointment);

    assertEquals(0, exampleMonday.getTotalTasks());
    assertEquals(0, exampleMonday.getTotalEvents());
    assertEquals(0, exampleMonday.getListOfScheduled().size());
  }

  /**
   Tests the setTasks() method of the Day class.
   */
  @Test
  void setTasks() {
    List<Task> tasks = Arrays.asList((Task) takeOutTrash);
    exampleMonday.setTasks(tasks);

    assertEquals(tasks, exampleMonday.getTasks());
  }

  /**
   Tests the setEvents() method of the Day class.
   */
  @Test
  void setEvents() {
    List<Event> events = Arrays.asList((Event) appointment);
    exampleMonday.setEvents(events);

    assertEquals(events, exampleMonday.getEvents());
  }

  /**
   Tests the getTasks() method of the Day class.
   */
  @Test
  void getTasks() {
    assertEquals(0, exampleMonday.getTasks().size());

    exampleMonday.scheduleItem(takeOutTrash);
    assertEquals(1, exampleMonday.getTasks().size());

    assertEquals(takeOutTrash, exampleMonday.getTasks().get(0));
  }

  /**
   Tests the getEvents() method of the Day class.
   */
  @Test
  void getEvents() {
    assertEquals(0, exampleMonday.getEvents().size());

    exampleMonday.scheduleItem(appointment);
    assertEquals(1, exampleMonday.getEvents().size());

    assertEquals(appointment, exampleMonday.getEvents().get(0));
  }

  /**
   Tests the setDayOfWeek() method of the Day class.
   */
  @Test
  void setDayOfWeek() {
    assertEquals(DayOfWeek.MONDAY, exampleMonday.getDayOfWeek());
    exampleMonday.setDayOfWeek(DayOfWeek.SUNDAY);
    assertEquals(DayOfWeek.SUNDAY, exampleMonday.getDayOfWeek());
  }
}