package cs3500.pa05.controller;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Handles displaying the popup and handling the process for setting a password upon
 * opening a new week.
 */
public class SetPasswordPopupController implements PopupController {

  private final BulletJournalController bulletJournalController;
  private final Stage stage;
  private final Popup makeEvent;
  @FXML private PasswordField choosePassword;
  @FXML private Button ok;


  /**
   * Creates an instance of a set password popup controller, taking into account
   * the main BulletJournalController to delegate to and the stage to display on.
   *
   * @param bulletJournalController BulletJournalController to delegate to
   * @param stage stage for this popup to be displayed on
   */
  public SetPasswordPopupController(Stage stage, BulletJournalController bulletJournalController) {
    this.stage = stage;
    this.bulletJournalController = bulletJournalController;
    this.makeEvent = new Popup();
  }


  /**
   * runs the controller
   *
   * @throws IOException if fails
   */
  @Override
  public void run() throws IOException {
    addOnActionHandlers(event -> {
      if (event.getSource() == ok) {
        if (!choosePassword.getText().isEmpty()) {
          bulletJournalController.setPassword(choosePassword.getText());
          makeEvent.hide();
        } else {
          AlertUtils.showAlert(Alert.AlertType.ERROR, "Password cannot be blank!");
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
        "choosePassword.fxml"));
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
    ok.setOnAction(e);
  }
}
