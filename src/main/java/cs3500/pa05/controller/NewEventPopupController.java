package cs3500.pa05.controller;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Time;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * One of the relevant popups for BulletJournal
 * Shown to the user when the New Event button is chosen or Ctrl+E
 */
public class NewEventPopupController implements PopupController {

  private final BulletJournalController mainController;
  //choice of day for task
  private DayOfWeek dayChoice;

  //choosing a day for a new event
  @FXML
  private MenuButton chooseAdayEvent;
  @FXML
  private MenuItem mondayEvent;
  @FXML
  private MenuItem tuesdayEvent;
  @FXML
  private MenuItem wednesdayEvent;
  @FXML
  private MenuItem thursdayEvent;
  @FXML
  private MenuItem fridayEvent;
  @FXML
  private MenuItem saturdayEvent;
  @FXML
  private MenuItem sundayEvent;


  //text entry during task creation
  @FXML
  private TextField enterEventTitle;
  @FXML
  private TextArea enterEventDescription;
  @FXML
  private TextField enterEventHour;
  @FXML
  private TextField enterEventMinute;
  @FXML
  private TextField enterDurationHour;
  @FXML
  private TextField enterDurationMinute;

  @FXML
  private Button doneButton;

  //private Map<MenuItem, DayOfWeek> dayEventMap;

  private final Popup makeEvent;
  private final Stage stage;

  /**
   * Constructor for NewEventPopupController, which delegates actions and references
   * BulletJournalController methods when necessary
   *
   * @param mainController Controller object it stems from, used for its methods
   * @param stage          Stage object in order to display the popup
   */
  public NewEventPopupController(BulletJournalController mainController, Stage stage) {
    this.makeEvent = new Popup();
    this.stage = stage;
    this.mainController = mainController;
  }

  /**
   * initializes the new event popup
   *
   * @throws IOException if unable to load fxml file content for this popup.
   */
  @Override
  public void initPopup() throws IOException {
    FXMLLoader eventLoader = new FXMLLoader(getClass().getClassLoader().getResource(
        "createEventPopup.fxml"));
    eventLoader.setController(this);
    Scene sceneTask = eventLoader.load();
    makeEvent.getContent().add(sceneTask.getRoot());
  }

  /**
   * displays the popup window on the stage
   */
  @Override
  public void showPopup() {
    this.makeEvent.show(stage);
  }

  /**
   * Given an event handler, sets all buttons in the popup to handle actions.
   *
   * @param e event handler for
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    mondayEvent.setOnAction(e);
    tuesdayEvent.setOnAction(e);
    wednesdayEvent.setOnAction(e);
    thursdayEvent.setOnAction(e);
    fridayEvent.setOnAction(e);
    saturdayEvent.setOnAction(e);
    sundayEvent.setOnAction(e);
    doneButton.setOnAction(e);
  }

  /**
   * Inherited method that runs the controller - handles setting the dayChoice field to the
   * corresponding dropdown menu item, and once the "done" button is clicked, delegates
   * to helpers to check whether fields are valid and
   *
   * @throws IOException if the input or output is invalid
   */
  @Override
  public void run() throws IOException {
    addOnActionHandlers(event -> {
      //handling dropdown menu selection
      if (event.getSource() == mondayEvent) {
        this.chooseAdayEvent.setText(mondayEvent.getText());
        this.dayChoice = DayOfWeek.MONDAY;
      } else if (event.getSource() == tuesdayEvent) {
        this.chooseAdayEvent.setText(tuesdayEvent.getText());
        this.dayChoice = DayOfWeek.TUESDAY;
      } else if (event.getSource() == wednesdayEvent) {
        this.chooseAdayEvent.setText(wednesdayEvent.getText());
        this.dayChoice = DayOfWeek.WEDNESDAY;
      } else if (event.getSource() == thursdayEvent) {
        this.chooseAdayEvent.setText(thursdayEvent.getText());
        this.dayChoice = DayOfWeek.THURSDAY;
      } else if (event.getSource() == fridayEvent) {
        this.chooseAdayEvent.setText(fridayEvent.getText());
        this.dayChoice = DayOfWeek.FRIDAY;
      } else if (event.getSource() == saturdayEvent) {
        this.chooseAdayEvent.setText(saturdayEvent.getText());
        this.dayChoice = DayOfWeek.SATURDAY;
      } else if (event.getSource() == sundayEvent) {
        this.chooseAdayEvent.setText(sundayEvent.getText());
        this.dayChoice = DayOfWeek.SUNDAY;
      } else if (event.getSource() == doneButton) {
        // check all conditions and show the corresponding alert
        boolean isValid = validateEventInputs();
        if (isValid) {
          createAndScheduleEvent();
        }
        reset();
      }
    });


  }

  /**
   * Hides this popup and delegates back to the BulletJournalController to create and schedule an
   * event based on input.
   */
  private void createAndScheduleEvent() {
    //close popup window
    makeEvent.hide();
    //get event title, description, and time from text fields
    String eventTitle = this.enterEventTitle.getText();

    String description = this.enterEventDescription.getText();

    String link = this.mainController.extractLink(description);


    if (!link.equals("")) {
      this.enterEventDescription.setText(
          this.enterEventDescription.getText().replace(link, ""));
    }

    String eventDescription = this.enterEventDescription.getText();

    int startHour = Integer.parseInt(this.enterEventHour.getText());
    int startMinute = Integer.parseInt(this.enterEventMinute.getText());
    Time startTime = new Time(startHour, startMinute);
    int duration = getDurationFromInput();
    if (this.enterEventDescription.getText() != null) {
      //add String representation to list view for chosen day
      this.mainController.writeToGivenListView(dayChoice,
          eventTitle + "\n" + eventDescription + "\n" + startTime + " - "
              + startTime.deriveEndTime(duration).toString());
    } else {
      this.mainController.writeToGivenListView(dayChoice,
          eventTitle + "\n" + startTime + " - "
              + startTime.deriveEndTime(duration).toString());
    }
    Event eventCreated = new Event(eventTitle, eventDescription, this.dayChoice,
        startTime, duration);
    mainController.scheduleItemInWeek(eventCreated, dayChoice);

    if (!link.equals("")) {

      int index = mainController.getWeek().getDayOfWeek(dayChoice).getListOfScheduled().indexOf(
          eventCreated);

      this.mainController.writeHyperlink(dayChoice, link, index);
    }
  }

  /**
   * Checks whether inputs in fields would make for a valid event
   *
   * @return boolean value representing whether input in text fields is valid
   */
  private boolean validateEventInputs() {
    if (this.dayChoice == null) {
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Event must have a valid day!");
      return false;
    }
    if (!this.isValidTimeEntry()) {
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Event must have a valid time!");
      return false;
    }
    if (!this.isValidDuration()) {
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Event must not extend into the "
          + "next day");
      return false;
    }
    if (this.enterEventTitle.getText().isEmpty()) {
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Event must have a title!");
      return false;
    }
    return true;
  }

  /**
   * Returns whether the duration is valid - i.e. the event does not end after 23:59
   *
   * @return boolean representing whether a valid duration was entered for the event's starting time
   */
  private boolean isValidDuration() {
    Time startTime = new Time(Integer.parseInt(this.enterEventHour.getText()),
        Integer.parseInt(this.enterEventMinute.getText()));
    Time endTime = startTime.deriveEndTime(getDurationFromInput());
    return (endTime.getHour() < 24);
  }


  /**
   * Returns whether a valid time has been entered in the enterEventHour and enterEventMinute
   * text fields for an event start time.
   * An entry for a starting time is valid if:
   * - there is an integer entry for both hour and minute
   * - the entry for hour is less than 24
   * - the entry for minute is less than 60
   *
   * @return isValidTimeEntry boolean value representing whether an entry in the event time fields
   is a valid time.
   */
  private boolean isValidTimeEntry() {
    String hourText = this.enterEventHour.getText();
    String minuteText = this.enterEventMinute.getText();

    try {
      Integer.parseInt(hourText);
      Integer.parseInt(minuteText);
    } catch (NumberFormatException e) {
      return false;
    }

    return (Integer.parseInt(hourText) < 24 && Integer.parseInt(hourText) >= 0
        && Integer.parseInt(minuteText) < 60 && Integer.parseInt(minuteText) >= 0);
  }

  /**
   * Returns the event's duration in minutes, based on input in the text fields.
   * Allows for one or both to be left blank.
   *
   * @return integer representing duration of this event in minutes
   */
  private int getDurationFromInput() {
    String durationHourText = this.enterDurationHour.getText();
    String durationMinuteText = this.enterDurationMinute.getText();
    int durationHour = 0;
    int durationMinutes = 0;
    // Convert entered hour to minutes, if present
    if (!durationHourText.isEmpty()) {
      durationHour = Integer.parseInt(durationHourText) * 60;
    }
    // Convert entered minutes to int, if present
    if (!durationMinuteText.isEmpty()) {
      durationMinutes = Integer.parseInt(durationMinuteText);
    }
    return durationHour + durationMinutes;
  }

  /**
   * Resets all text fields when the user is prompted to enter data for a new event.
   */
  private void reset() {
    this.chooseAdayEvent.setText("Choose a day:");
    this.enterEventDescription.setText(null);
    this.enterDurationHour.setText(null);
    this.enterDurationMinute.setText(null);
    this.enterEventTitle.setText(null);
    this.enterEventHour.setText(null);
    this.enterEventMinute.setText(null);
  }

}

