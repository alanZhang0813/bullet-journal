package cs3500.pa05.controller;

import java.io.File;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Class that represents the handling of shortcuts for the menu in the Bullet Journal
 */
public class MenuEventHandler implements EventHandler<KeyEvent> {

  private final BulletJournalController controller;
  private final Stage stage;

  /**
   * Constructor for a MenuEventHandler
   *
   * @param controller Bullet Journal Controller
   * @param stage Stage to modify
   */
  public MenuEventHandler(BulletJournalController controller, Stage stage) {
    this.controller = controller;
    this.stage = stage;
  }

  /**
   * Depending on the Key Event, handle() will perform a different action, executing the shortcut.
   *
   * @param event the event which occurred
   */
  @Override
  public void handle(KeyEvent event) {
    KeyCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
    KeyCombination ctrlO = new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN);
    KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
    KeyCombination ctrlF = new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN);
    KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
    KeyCombination ctrlE = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
    KeyCombination ctrlT = new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN);

    if (ctrlS.match(event)) {
      System.out.println("Save button clicked or Ctrl+S pressed!");
      this.controller.writeToDestination();

    } else if (ctrlO.match(event)) {
      File selectedFile = this.controller.askForFile();
      if (selectedFile != null) {
        // Handle the selected file, e.g., load its content
        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
      } else {
        System.out.println("No file selected.");
      }
      this.controller.deserialize(selectedFile);

    } else if (ctrlN.match(event)) {
      System.out.println("New button clicked or Ctrl+N pressed!");
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Opening a new week will override "
          + "any unsaved progress in this current week. Would you like to continue?");
      this.controller.reset();
    } else if (ctrlF.match(event)) {
      System.out.println("Fullscreen button clicked or Ctrl+F pressed!");
      this.controller.setFullScreen();
    } else if (ctrlZ.match(event)) {
      System.out.println("Delete button clicked or backspace pressed!");
      this.controller.undo();
    } else if (ctrlT.match(event)) {
      System.out.println("Task button clicked or Ctrl+T pressed!");
      this.controller.getNewTaskPopupController().showPopup();
      try {
        this.controller.getNewTaskPopupController().run();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else if (ctrlE.match(event)) {
      System.out.println("Event button clicked or Ctrl+E pressed!");
      this.controller.getNewEventPopupController().showPopup();
      try {
        this.controller.getNewEventPopupController().run();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
