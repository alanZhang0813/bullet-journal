package cs3500.pa05;

import cs3500.pa05.controller.BulletJournalController;
import cs3500.pa05.model.Week;
import cs3500.pa05.view.BulletJournalGuiView;
//import cs3500.pa05.view.SplashView;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main driver class for the Bullet Journal application - overrides the Application class
 * start() method to set the title of the stage, instantiate the model, main controller,
 * and view, calls for the stage scene to show the BulletJournal gui view and runs the
 * controller to implement interactivity and app functionality.
 */
public class Driver extends Application {
  /**
   * Starts the GUI for a Bullet Journal
   *
   * @param stage the JavaFX stage to add elements to
   */
  @Override
  public void start(Stage stage) {
    stage.setTitle("Bullet Journal");
    Week week = new Week();
    stage.setResizable(false);
    BulletJournalController controller = new BulletJournalController(week, stage);
    BulletJournalGuiView view = new BulletJournalGuiView(controller);

    try {
      stage.show();
      stage.setScene(view.load());
      controller.run();

    } catch (IllegalStateException | IOException e) {
      System.err.println("Unable to load GUI.");
      e.printStackTrace();
    }
  }

  /**
   * calls to launch the application in the JavaFx life cycle
   *
   * @param args command line arguments to be run.
   */
  public static void main(String[] args) {
    launch();
  }
}
