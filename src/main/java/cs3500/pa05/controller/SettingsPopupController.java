package cs3500.pa05.controller;

import cs3500.pa05.model.Week;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Handles displaying and interacting with the Settings popup in the week view -
 * implements PopupController to handle setup and display, delegates any changes
 * for week title and task/event limits back to the main BulletJournalController for
 * updates to the week model to be made accordingly.
 */
public class SettingsPopupController implements PopupController {

  private BulletJournalController mainController;
  private Stage stage;

  private Popup settings;

  @FXML
  private TextField setWeekTitle;
  @FXML
  private TextField tasksLimit;
  @FXML
  private TextField eventsLimit;

  @FXML
  private Button updateButton = new Button("Update");


  /**
   * Constructor for a SettingsPopupController
   *
   * @param mainController the main BulletJournalController for dependency in access
   *                       to the model and week view fxml file.
   * @param stage          the stage for the popup to be displayed on
   */
  public SettingsPopupController(BulletJournalController mainController, Stage stage) {
    this.mainController = mainController;
    this.settings = new Popup();
    this.stage = stage;
  }

  /**
   * Loads content from relevant fxml file and adds a button for closing the popup
   *
   * @throws IOException if unable to load content
   */
  @Override
  public void initPopup() throws IOException {
    FXMLLoader taskLoader = new FXMLLoader(getClass().getClassLoader().getResource(
        "weekSettingsPopup.fxml"));
    taskLoader.setController(this);
    Scene sceneTask = taskLoader.load();
    settings.getContent().add((Node) sceneTask.getRoot());
    settings.getContent().add(updateButton);
  }

  /**
   * Shows the settings popup on the stage
   */
  @Override
  public void showPopup() {
    this.settings.show(stage);
  }

  /**
   * Adds action handler to the button closing the window
   *
   * @param e EventHandler object that the button responds to
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    updateButton.setOnAction(e);

  }

  /**
   * Handles all action events depending on the button pressed
   *
   * @throws IOException if unable to run other controllers
   */
  @Override
  public void run() throws IOException {

    addOnActionHandlers(event -> {
      settings.hide();


      String newWeekTitle = setWeekTitle.getText();
      if (!newWeekTitle.isEmpty()) {
        //set week title to given title
        this.mainController.updateWeekTitle(newWeekTitle);
      }

      int weekTaskLimit = mainController.getWeek().getMaxTasksPerDay();
      int weekEventLimit = mainController.getWeek().getMaxEventsPerDay();

      //handling errors for trying to close window with invalid input
      if (!this.tasksLimit.getText().isEmpty()) {
        try {
          int tasksLimit = Integer.parseInt(this.tasksLimit.getText());
          if (tasksLimit < 0) {
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Task Limit must be positive!");
          }
          this.mainController.setTasksLimit(tasksLimit);
        } catch (NumberFormatException e) {
          AlertUtils.showAlert(Alert.AlertType.ERROR, "Task Limit must be a valid integer!");
        }
      } else {
        this.mainController.setTasksLimit(weekTaskLimit);
      }
      if (!this.eventsLimit.getText().isEmpty()) {
        try {
          int eventsLimit = Integer.parseInt(this.eventsLimit.getText());
          if (eventsLimit < 0) {
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Event Limit must be positive!");
          }
          this.mainController.setEventsLimit(eventsLimit);
        } catch (NumberFormatException e) {
          AlertUtils.showAlert(Alert.AlertType.ERROR, "Event Limit must be a valid integer!");
        }
      } else {
        this.mainController.setEventsLimit(weekEventLimit);
      }
    });

  }

  /**
   * After loading in a Bujo, we want to load in the settings that were contained in the
   * file. This includes the max events, max tasks, and title. These are all contained in
   * the week object that gets passed in.
   *
   * @param week Week object that comes from Bujo file
   */
  public void loadSettings(Week week) {
    this.eventsLimit.setText(week.getMaxEventsPerDay().toString());
    this.tasksLimit.setText(week.getMaxTasksPerDay().toString());
    this.setWeekTitle.setText(week.getWeekTitle());
  }



}
