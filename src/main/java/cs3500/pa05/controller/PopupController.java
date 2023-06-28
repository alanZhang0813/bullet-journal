package cs3500.pa05.controller;

import java.io.IOException;
import javafx.event.EventHandler;

/**
 * Interface that the PopupController classes use
 */
public interface PopupController extends Controller {

  /**
   * initializes the new event popup
   *
   * @throws IOException if unable to load fxml file content for this popup.
   */
  void initPopup() throws IOException;

  /**
   * Displays the popup
   */
  void showPopup();

  /**
   * adds event handlers to the popup's interactable objects
   *
   * @param e Event Handler
   */
  void addOnActionHandlers(EventHandler e);

}
