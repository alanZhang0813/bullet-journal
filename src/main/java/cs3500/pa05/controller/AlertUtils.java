package cs3500.pa05.controller;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Handles functionality involving display and handling of invalid input alerts across controllers
 */
public class AlertUtils {

  /**
   * Displays an alert dialog with the specified error message.
   * The alert dialog is shown as an error type.
   *
   * @param message the error message to be displayed in the alert dialog
   * @param alertType type of the alert
   */
  public static void showAlert(Alert.AlertType alertType, String message) {
    Alert alert = new Alert(alertType, message, ButtonType.OK);
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.setAlwaysOnTop(true);
    stage.toFront();
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        alert.close();
      }
    });
  }

}
