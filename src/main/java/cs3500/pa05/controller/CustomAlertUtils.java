package cs3500.pa05.controller;

import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Utility class for handling custom alerts, which extend the base functionality provided by the
 * AlertUtils class. CustomAlertUtils provides the ability to display a custom alert dialog with a
 * message, and an number of buttons that execute a Runnable when clicked. These button labels and
 * corresponding actions are passed in as a Map. The alert dialog will always stay on top and come
 * to the front of all other windows.
 */
public class CustomAlertUtils extends AlertUtils {
  /**
   * Displays an alert dialog with the specified error message and custom buttons.
   *
   * @param alertType   the type of alert to be displayed
   * @param message     the error message to be displayed in the alert dialog
   * @param buttonSpecs a map of button types (as Strings) to a runnable that
   *                    will set as the button in the alert's action event.
   */
  public static void showCustomAlert(Alert.AlertType alertType, String message,
                                     Map<String, Runnable> buttonSpecs) {
    Alert alert = new Alert(alertType, message, ButtonType.CANCEL);
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.setAlwaysOnTop(true);
    stage.toFront();

    // Create a custom dialogue pane
    DialogPane dialogPane = alert.getDialogPane();

    // Add the message label to the dialogue pane
    dialogPane.setHeaderText(message);

    VBox buttonContainer = new VBox();
    for (Map.Entry<String, Runnable> entry : buttonSpecs.entrySet()) {

      String label = entry.getKey();

      ButtonType buttonType = new ButtonType(label);
      Button button = new Button(label);

      // set each buttons OnAction to return its buttonType
      button.setOnAction(event -> {
        alert.setResult(buttonType);
      });
      buttonContainer.getChildren().add(button);
    }

    //add all buttons to the dialogue pane
    dialogPane.setContent(buttonContainer);

    //once a response is received, handle accordingly.
    alert.showAndWait().ifPresent(response -> {
      //if the response is CANCEL, will simply close the alert
      if (response == ButtonType.CANCEL) {
        alert.close();
      } else { //else, will run the associated runnable action.
        Runnable action = buttonSpecs.get(response.getText());
        action.run();
        alert.close();
      }
    });

  }


}
