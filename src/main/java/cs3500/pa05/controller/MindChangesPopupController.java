package cs3500.pa05.controller;

import cs3500.pa05.model.DayOfWeek;
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
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Controller to manage the mind change popup in a bullet journal - provides th
 * option to edit the associated ScheduledItem clicked on or delete the item.
 */
public class MindChangesPopupController implements PopupController {
  private final BulletJournalController mainController;
  private final EditItemPopupController editItemPopupController;
  //the fields that represents the item that this popup is meant to represent
  private DayOfWeek day;
  private String content;
  private final Stage stage;
  private final Popup changeMind;

  @FXML private Button delete;
  @FXML private Button edit;
  @FXML private Button close;

  private ScheduledItem selected;
  private int selectedIndex;


  /**
   * Creates an instance of a mind changes popup controller, taking into account
   * the main BulletJournalController to delegate to and the stage to display on.
   *
   * @param mainController BulletJournalController to delegate to
   * @param stage stage for this popup to be displayed on
   */
  public MindChangesPopupController(BulletJournalController mainController, Stage stage) {
    this.changeMind = new Popup();
    this.mainController = mainController;
    this.stage = stage;
    this.editItemPopupController = new EditItemPopupController(stage, this);
  }

  /**
   * Initializes the popup by loading the "mindChangePopup.fxml" file and setting this class
   * as its controller.
   *
   * @throws IOException if the "mindChangePopup.fxml" file can't be loaded
   */
  @Override
  public void initPopup() throws IOException {
    FXMLLoader mindChangeLoader = new FXMLLoader(getClass().getClassLoader().getResource(
        "mindChangePopup.fxml"));
    mindChangeLoader.setController(this);
    Scene sceneTask = mindChangeLoader.load();
    changeMind.getContent().add(sceneTask.getRoot());
  }

  /**
   * Shows the popup on the provided stage.
   */
  @Override
  public void showPopup() {
    this.changeMind.show(stage);
  }

  /**
   * Adds event handlers to the buttons inside the popup.
   *
   * @param e the event handler that handles button click events
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    this.edit.setOnAction(e);
    this.delete.setOnAction(e);
    this.close.setOnAction(e);
  }

  /**
   * Adds the action handlers and shows the popup.
   *
   * @throws IOException if the popup can't be initialized
   */
  @Override
  public void run() throws IOException {
    addOnActionHandlers(event -> {
      if (event.getSource() == edit) {
        this.selected = mainController.getSelected(this.day, this.content);
        changeMind.hide();
        try {
          editItemPopupController.initPopup();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        editItemPopupController.showPopup();
        editItemPopupController.run();
      } else if (event.getSource() == delete) {
        AlertUtils.showAlert(Alert.AlertType.CONFIRMATION, "You are about to delete this item!");
        this.mainController.deleteSelected(this.getDay(), this.getContent());
        changeMind.hide();

      } else if (event.getSource() == close) {
        changeMind.hide();
      }
    });
  }

  /**
   * Sets the index of the selected item in the list view to be changing
   *
   * @param index index of the selected item
   */
  public void setSelectedIndex(int index) {
    this.selectedIndex = index;
  }

  /**
   * gets the day being changed
   *
   * @return day being changed
   */
  public DayOfWeek getDay() {
    return day;
  }


  /**
   * Sets the day of the week the selected item is associated with.
   *
   * @param day the day of the week
   */
  public void setDay(DayOfWeek day) {
    this.day = day;
  }



  /**
   * Gets the string content of the selected item being modified
   *
   * @return the content of the selected item
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets the content of the selected item.
   *
   * @param content the content of the selected item
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * gets the selected scheduledItem
   *
   * @return selected the currently selected ScheduledItem
   */
  public ScheduledItem getSelected() {
    return this.selected;
  }


  /**
   * Updates a task item with new details.
   *
   * @param newName the new name for the task
   * @param newDesc the new description for the task
   */
  public void updateTask(String newName, String newDesc) {

    this.mainController.updateTodoListWithEdit(selected, newName);

    this.selected.setName(newName);
    this.selected.setDescription(newDesc);
    mainController.updateListViewWithEdit(selected);

  }

  /**
   * Updates an event item with new details.
   *
   * @param newName the new name for the event
   * @param newDesc the new description for the event
   * @param newTime the new time for the event
   * @param newDuration the new duration for the event
   */
  public void updateEvent(String newName, String newDesc, Time newTime, int newDuration) {
    //update view
    //set fields
    this.selected.setName(newName);
    this.selected.setDescription(newDesc);
    ((Event) this.selected).setTime(newTime);
    ((Event) this.selected).setDuration(newDuration);
    mainController.updateListViewWithEdit(selected);

  }
  
}
