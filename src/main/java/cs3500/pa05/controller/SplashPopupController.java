package cs3500.pa05.controller;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;


/**
 * Handles displaying the initial splash screen
 */
public class SplashPopupController implements PopupController {

  @FXML private Button startButton;

  private Stage stage;
  private final Popup makeEvent;


  /**
   * Creates an instance of a splash popup controller, taking into account
   * the stage to display on.
   *
   * @param stage stage for this popup to be displayed on
   */
  public SplashPopupController(Stage stage) {
    makeEvent = new Popup();
    this.stage = stage;
  }



  /**
   * runs the controller - when the button is pushed, the splash is hidden.
   *
   * @throws IOException if fails
   */
  @Override
  public void run() throws IOException {
    addOnActionHandlers(event -> {
      if (event.getSource() == startButton) {
        makeEvent.hide();
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
        "splashScreen.fxml"));
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
    startButton.setOnAction(e);
  }
}
