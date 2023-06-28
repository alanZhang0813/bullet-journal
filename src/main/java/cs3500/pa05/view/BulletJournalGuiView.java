package cs3500.pa05.view;

import cs3500.pa05.controller.BulletJournalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents the display of a bullet journal on the GUI
 */
public class BulletJournalGuiView {
  FXMLLoader loader;
  BulletJournalController controller;

  /**
   * Creates an instance of a BulletJournalGUIView and initializes the controller corresponding to
   * the fxml file loader field to the given controller.
   *
   * @param controller Controller object used to initialize the FXMLLoader object
   */
  public BulletJournalGuiView(BulletJournalController controller) {
    this.loader = new FXMLLoader();
    this.loader.setController(controller);
    this.loader.setLocation(getClass().getClassLoader().getResource("calendarOutline.fxml"));
  }

  /**
   * loads the scene
   *
   * @return the loaded scene
   */
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalStateException("Unable to load bullet journal layout");
    }
  }
}