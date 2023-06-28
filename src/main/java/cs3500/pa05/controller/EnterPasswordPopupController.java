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
 * Controller for displaying and handling popup window associated with entering a pasword when
 * trying to open a particular file.
 */
public class EnterPasswordPopupController implements PopupController {
  
  private final BulletJournalController controller;
  
  private final Stage stage;
  private final Popup getPassword;

  @FXML private PasswordField enterPassword;
  @FXML private Button ok;

  /**
   * Creates an instance of an enterPassword controller delegating to the given mainController and
   * stage
   *
   * @param controller mainController calling access to this controller
   * @param stage stage for popup to be displayed on
   */
  public EnterPasswordPopupController(
      BulletJournalController controller, Stage stage) {
    this.controller = controller;
    this.stage = stage;
    getPassword = new Popup();
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
        if (this.enterPassword.getText().equals(controller.getPassword())) {
          this.getPassword.hide();
        } else {
          if (this.enterPassword.getText().isEmpty()) {
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Must enter a password!");
          } else {
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid password -  "
                + "Try again!");
          }
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
    FXMLLoader taskLoader = new FXMLLoader(getClass().getClassLoader().getResource(
        "enterPassword.fxml"));
    taskLoader.setController(this);
    Scene sceneTask = taskLoader.load();
    getPassword.getContent().add(sceneTask.getRoot());
  }

  /**
   * Displays the popup
   */
  @Override
  public void showPopup() {
    this.getPassword.show(stage);
  }

  /**
   * adds event handlers to the popup's interactable objects
   *
   * @param e Event Handler
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    ok.setOnAction(e);
    enterPassword.setOnAction(e);
  }
  
}
