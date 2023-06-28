package cs3500.pa05.controller;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduledItem;
import cs3500.pa05.model.Time;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Controls the popup window and process associated with editing an item - loads separate fxml files
 * depending on circumstances and delegates the changes made back to the MindChangesPopupController
 */
public class EditItemPopupController implements PopupController {
  private final MindChangesPopupController mindChangesController;
  private final Stage stage;

  private final Popup editPopup;

  @FXML
  private TextField updateTitle;
  @FXML
  private TextArea updateDescription;
  @FXML
  private TextField updateHour;
  @FXML
  private TextField updateMinute;
  @FXML
  private TextField updateDuration;
  @FXML
  private Button ok;
  @FXML
  private Button cancel;

  /**
   * creates an instance of this popupcontroller on a given stage, delegating to the given
   * MindChangesController
   *
   * @param stage stage to be displayed on
   * @param mindChangesController instance of a mindChangesController to delegate to
   */
  public EditItemPopupController(Stage stage, MindChangesPopupController mindChangesController) {
    this.mindChangesController = mindChangesController;
    this.editPopup = new Popup();
    this.stage = stage;
  }

  /**
   * Returns a boolean value representing whether the ScheduledItem being edited is an event,
   * otherwise it's a task
   *
   * @return editingEvent is the item being edited of type event
   */
  private boolean isEditingEvent() {
    return (mindChangesController.getSelected() instanceof Event);
  }

  /**
   * initializes the popup and loads the fxml file
   *
   * @throws IOException if unable to access fxml file
   */
  @Override
  public void initPopup() throws IOException {
    FXMLLoader editLoader;
    if (isEditingEvent()) {
      editLoader = new FXMLLoader(getClass().getClassLoader().getResource(
          "editEventPopup.fxml"));
    } else {
      editLoader = new FXMLLoader(getClass().getClassLoader().getResource(
          "editTaskPopup.fxml"));
    }
    editLoader.setController(this);
    Scene editScene = editLoader.load();
    editPopup.getContent().add(editScene.getRoot());
  }

  /**
   * Displays the popup
   */
  @Override
  public void showPopup() {
    this.editPopup.show(stage);
    ScheduledItem selected = mindChangesController.getSelected();
    updateTitle.setText(selected.getName());
    updateDescription.setText(selected.getDescription());
    if (isEditingEvent()) {
      Time startTime = ((Event) selected).getStartTime();
      updateHour.setText(String.valueOf(startTime.getHour()));

      //displays the minute in a two digit format (hour:00)
      updateMinute.setText(String.format("%02d", startTime.getMinute()));
      updateDuration.setText(String.valueOf(((Event) selected).getDuration()));
    }
  }

  /**
   * adds event handlers to the popup's intractable objects
   *
   * @param e Event Handler
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    ok.setOnAction(e);
    cancel.setOnAction(e);
  }


  /**
   * runs the controller and controls action handlers for button presses. calls to update
   * the associated scheduledItem based on the text field inputs.
   *
   */
  @Override
  public void run() {
    addOnActionHandlers(event -> {
      if (event.getSource() == ok) {
        String updatedName;
        if (!updateTitle.getText().isEmpty()) {

          updatedName = updateTitle.getText();

          String updatedDesc = updateDescription.getText();

          if (isEditingEvent()) {
            if (isValidTime()) {
              Time updatedTime = new Time(Integer.parseInt(updateHour.getText()),
                  Integer.parseInt(updateMinute.getText()));
              int updatedDuration = Integer.parseInt(updateDuration.getText());
              mindChangesController.updateEvent(updatedName, updatedDesc, updatedTime,
                  updatedDuration);

            } else {
              AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid time! Please enter a "
                  + "valid time!");
            }

          } else {
            mindChangesController.updateTask(updatedName, updatedDesc);
          }
        } else {
          AlertUtils.showAlert(Alert.AlertType.ERROR, "Title must not be empty!");
        }
      }

      editPopup.hide();
    });

  }


  /**
   * returns whether the time entered by the user is valid
   *
   * @return if the time entered is valid.
   */
  private boolean isValidTime() {

    boolean isHourEmpty = updateHour.getText().isEmpty();
    boolean isMinuteEmpty = updateMinute.getText().isEmpty();

    if (isHourEmpty || isMinuteEmpty) {
      return false;
    }

    String hourText = this.updateHour.getText();
    String minuteText = this.updateMinute.getText();

    int hour;
    int minute;

    try {
      hour = Integer.parseInt(hourText);
      minute = Integer.parseInt(minuteText);
    } catch (NumberFormatException e) {
      return false;
    }

    return (hour < 24 && hour >= 0)
        && (minute < 60 && minute >= 0);
  }

}


