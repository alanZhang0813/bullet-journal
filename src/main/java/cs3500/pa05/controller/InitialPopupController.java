package cs3500.pa05.controller;

import java.io.File;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;


/**
 * Handles displaying popup and handling events for the initial popup shown to a user upon
 * opening the program - presents the choice of opening a week from a file or creating a new week
 * and handles delegating associated processes based on button choice.
 */
public class InitialPopupController implements PopupController {

  private final BulletJournalController mainController;
  @FXML private Button openWeek;
  @FXML private Button newWeek;

  private final Stage stage;
  private final Popup makeEvent;

  SetPasswordPopupController setPassword;
  EnterPasswordPopupController enterPassword;


  /**
   * Creates an instance of an initialPopupController with the fiven main controller and stage
   *
   * @param controller BulletJournalController for the current view
   * @param stage stage displayed on
   */
  public InitialPopupController(BulletJournalController controller, Stage stage) {
    makeEvent = new Popup();
    this.stage = stage;
    this.mainController = controller;
  }


  /**
   * runs the controller, based on user button choice calls for opening a file or
   * beginning a new week and setting a password to take place.
   *
   * @throws IOException if fails to open an fxml file
   */
  @Override
  public void run() throws IOException {
    addOnActionHandlers(event -> {
      if (event.getSource() == openWeek) {
        makeEvent.hide();
        File file = mainController.askForFile();
        try {
          mainController.askForPasswordEntry();
          mainController.deserialize(file);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else if (event.getSource() == newWeek) {
        makeEvent.hide();
        try {
          mainController.askForPasswordChoice();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  /**
   * initializes the new event popup
   *
   * @throws IOException if unable to load fxml file content for this popup.
   */
  @Override
  public void initPopup() throws IOException {
    FXMLLoader eventLoader = new FXMLLoader(getClass().getClassLoader().getResource(
        "initialPopup.fxml"));
    eventLoader.setController(this);
    Scene sceneTask = eventLoader.load();
    makeEvent.getContent().add(sceneTask.getRoot());
  }

  /**
   * Displays the popup
   */
  @Override
  public void showPopup() {
    this.makeEvent.show(stage);
  }

  /**
   * adds event handlers to the popup's interactable objects
   *
   * @param e Event Handler
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    newWeek.setOnAction(e);
    openWeek.setOnAction(e);
  }

}
