package cs3500.pa05.controller;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Task;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;



/**
 * One of the relevant popups for BulletJournal
 * Shown to the user when the New Task button is chosen or Ctrl+T
 */
public class NewTaskPopupController implements PopupController {

  private final BulletJournalController mainController;

  //choice of day for task
  private DayOfWeek dayChoice;

  //choosing a day for a new task
  @FXML private MenuButton chooseAdayTask;
  @FXML private MenuItem mondayTask;
  @FXML private MenuItem tuesdayTask;
  @FXML private MenuItem wednesdayTask;
  @FXML private MenuItem thursdayTask;
  @FXML private MenuItem fridayTask;
  @FXML private MenuItem saturdayTask;
  @FXML private MenuItem sundayTask;


  //text entry during task creation
  @FXML private TextField enterTaskTitle;
  @FXML private TextArea enterTaskDescription;

  @FXML private Button doneButton;

  private final Popup makeTask;
  private final Stage stage;

  /**
   * Constructor for NewTaskPopupController, which delegates actions and references
   * BulletJournalController methods when necessary
   *
   * @param mainController Controller object it stems from, used for its methods
   * @param stage Stage object in order to display the popup
   */
  public NewTaskPopupController(BulletJournalController mainController, Stage stage) {
    this.mainController = mainController;
    this.makeTask = new Popup();
    this.stage = stage;
  }

  /**
   * Displays the makeTask popup
   */
  @Override
  public void showPopup() {
    this.makeTask.show(stage);
  }

  /**
   * adds handlers to each button/menu item
   *
   * @param e event handler
   */
  @Override
  public void addOnActionHandlers(EventHandler e) {
    mondayTask.setOnAction(e);
    tuesdayTask.setOnAction(e);
    wednesdayTask.setOnAction(e);
    thursdayTask.setOnAction(e);
    fridayTask.setOnAction(e);
    saturdayTask.setOnAction(e);
    sundayTask.setOnAction(e);
    doneButton.setOnAction(e);
  }

  /**
   * Initializes the popup with the FXMLLoader and .fxml files
   *
   * @throws IOException if the output or input is invalid
   */
  public void initPopup() throws IOException {
    FXMLLoader taskLoader = new FXMLLoader(getClass().getClassLoader().getResource(
        "createTaskPopup.fxml"));
    taskLoader.setController(this);
    Scene sceneTask = taskLoader.load();
    makeTask.getContent().add(sceneTask.getRoot());
  }


  /**
   * handles events made and executes them
   *
   * @throws IOException if failed
   */
  @Override
  public void run() throws IOException {
    addOnActionHandlers(event -> {
      //handling dropdown menu selection
      if (event.getSource() == mondayTask) {
        this.chooseAdayTask.setText(mondayTask.getText());
        this.dayChoice = DayOfWeek.MONDAY;
      } else if (event.getSource() == tuesdayTask) {
        this.chooseAdayTask.setText(tuesdayTask.getText());
        this.dayChoice = DayOfWeek.TUESDAY;
      } else if (event.getSource() == wednesdayTask) {
        this.chooseAdayTask.setText(wednesdayTask.getText());
        this.dayChoice = DayOfWeek.WEDNESDAY;
      } else if (event.getSource() == thursdayTask) {
        this.chooseAdayTask.setText(thursdayTask.getText());
        this.dayChoice = DayOfWeek.THURSDAY;
      } else if (event.getSource() == fridayTask) {
        this.chooseAdayTask.setText(fridayTask.getText());
        this.dayChoice = DayOfWeek.FRIDAY;
      } else if (event.getSource() == saturdayTask) {
        this.chooseAdayTask.setText(saturdayTask.getText());
        this.dayChoice = DayOfWeek.SATURDAY;
      } else if (event.getSource() == sundayTask) {
        this.chooseAdayTask.setText(sundayTask.getText());
        this.dayChoice = DayOfWeek.SUNDAY;
      } else if (event.getSource() == doneButton) {
        boolean isValid = validateTaskInputs();
        if (isValid) {
          createAndScheduleTask();
        }
        reset();
      }
    });

  }


  /**
   * Checks whether inputs in fields would make for a valid task
   *
   * @return boolean value representing whether input in text fields is valid
   */
  private boolean validateTaskInputs() {
    if (this.dayChoice == null) {
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Task must have a valid day!");
      return false;
    }
    if (this.enterTaskTitle.getText().isEmpty()) {
      AlertUtils.showAlert(Alert.AlertType.WARNING, "Task must have a title!");
      return false;
    }
    return true;
  }



  /**
   * Hides this popup and delegates back to the BulletJournalController to create and schedule a
   * task based on input.
   */
  private void createAndScheduleTask() {
    makeTask.hide();

    String taskTitle = this.enterTaskTitle.getText();

    String description = this.enterTaskDescription.getText();


    String link = this.mainController.extractLink(description);

    if (!link.equals("")) {
      this.enterTaskDescription.setText(
          this.enterTaskDescription.getText().replace(link, ""));
    }

    String taskDescription = this.enterTaskDescription.getText();

    if (enterTaskDescription.getText() != null) {
      this.mainController.writeToGivenListView(dayChoice,
          taskTitle + "\n" + taskDescription);
    } else {
      this.mainController.writeToGivenListView(dayChoice, taskTitle);
    }
    Task taskCreated = new Task(taskTitle, taskDescription, this.dayChoice);
    this.mainController.writeToTodoList(taskCreated);
    mainController.scheduleItemInWeek(taskCreated, dayChoice);

    if (!link.equals("")) {
      int index = mainController.getWeek().getDayOfWeek(
          dayChoice).getListOfScheduled().indexOf(taskCreated);
      this.mainController.writeHyperlink(dayChoice, link, index);
    }
  }

  /**
   * Resets all text fields when the user is prompted to enter data for a new task.
   */
  private void reset() {
    this.chooseAdayTask.setText("Choose a day:");
    this.enterTaskTitle.setText(null);
    this.enterTaskDescription.setText(null);
  }
}