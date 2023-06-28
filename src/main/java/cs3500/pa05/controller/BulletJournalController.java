package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduledItem;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.WeekAdapter;
import cs3500.pa05.view.BujoFileWriter;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller for handling Bullet Journal events in the week view window - calling for relevant
 * popup windows to be displayed to the screen and updating content in relevant listview fields for
 * the week.
 */
public class BulletJournalController implements Controller {
  private Week week;
  private final Stage stage;
  private final NewTaskPopupController newTaskPopupController;
  private final NewEventPopupController newEventPopupController;
  private final SettingsPopupController settingsPopupController;
  private final MindChangesPopupController mindChangesPopupController;

  private final SplashPopupController splashController;
  private final InitialPopupController initialPopupController;


  //fxml list views
  @FXML
  private ListView<CheckBox> todoList;
  //private Map <Task, CheckBox> toDoListEntries;
  private List<Task> listOfTodoTasks;

  //progress indicator in tasks to do list
  @FXML
  private ProgressBar toDoProgress;

  @FXML
  private ToolBar menuBat;

  @FXML
  private ListView<String> mondayList;
  @FXML
  private ListView<String> tuesdayList;
  @FXML
  private ListView<String> wednesdayList;
  @FXML
  private ListView<String> thursdayList;
  @FXML
  private ListView<String> fridayList;
  @FXML
  private ListView<String> saturdayList;
  @FXML
  private ListView<String> sundayList;

  @FXML
  private Label sundayRemaining;
  @FXML
  private Label mondayRemaining;
  @FXML
  private Label tuesdayRemaining;
  @FXML
  private Label wednesdayRemaining;
  @FXML
  private Label thursdayRemaining;
  @FXML
  private Label fridayRemaining;
  @FXML
  private Label saturdayRemaining;


  //menu items for "schedule something"
  @FXML
  private MenuItem newTask;
  @FXML
  private MenuItem newEvent;
  @FXML
  private Label totalTasks;
  @FXML
  private Label totalEvents;

  //menu bar buttons and their helper fields
  @FXML
  private Button newWeek;
  @FXML
  private Button openWeek;
  @FXML
  private Button save;
  @FXML
  private Button settings;
  @FXML
  private Button fullScreen;
  private boolean isFullScreen;
  @FXML
  private Button undo;
  private final List<DayOfWeek> addedDays;

  //other week information
  @FXML
  private Label weekTitle;
  @FXML
  private TextArea quoteArea;
  //private Map<DayOfWeek, ListView<String>> dayToListViewMap;


  //for opening purposes
  private File selectedFile;


  //panes
  @FXML
  private Pane monday;
  @FXML
  private Pane tuesday;
  @FXML
  private Pane wednesday;
  @FXML
  private Pane thursday;
  @FXML
  private Pane friday;
  @FXML
  private Pane saturday;
  @FXML
  private Pane sunday;
  @FXML
  private Pane todoPane;

  private String password;

  private final SetPasswordPopupController setPasswordController;
  private final EnterPasswordPopupController enterPasswordController;

  /**
   * Initializes controller based on week model and stage in which the content is displayed
   *
   * @param week  model data for the week being displayed in the bullet journal
   * @param stage stage for the week view to be displayed on
   */
  public BulletJournalController(Week week, Stage stage) {
    this.week = Objects.requireNonNull(week);
    this.stage = stage;
    this.newTaskPopupController = new NewTaskPopupController(this, stage);
    this.newEventPopupController = new NewEventPopupController(this, stage);
    this.settingsPopupController = new SettingsPopupController(this, stage);
    this.mindChangesPopupController = new MindChangesPopupController(this, stage);
    this.splashController = new SplashPopupController(stage);
    this.initialPopupController = new InitialPopupController(this, stage);
    this.enterPasswordController = new EnterPasswordPopupController(this, stage);
    this.setPasswordController = new SetPasswordPopupController(stage, this);


    this.addedDays = new ArrayList<>();
    this.listOfTodoTasks = new ArrayList<>();



    this.password = "";
  }


  /**
   * Adds handlers to each button and sets key shortcuts.
   *
   * @param handler Event Handler
   */
  public void addOnActionHandlers(EventHandler handler) {
    newTask.setOnAction(handler);
    newEvent.setOnAction(handler);
    newWeek.setOnAction(handler);
    openWeek.setOnAction(handler);
    save.setOnAction(handler);
    settings.setOnAction(handler);
    fullScreen.setOnAction(handler);
    undo.setOnAction(handler);

    //initializes each week's handling of clicks
    this.addListViewHandlers(mondayList);
    this.addListViewHandlers(tuesdayList);
    this.addListViewHandlers(wednesdayList);
    this.addListViewHandlers(thursdayList);
    this.addListViewHandlers(fridayList);
    this.addListViewHandlers(saturdayList);
    this.addListViewHandlers(sundayList);

    KeyCombination ctrlE = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
    newEvent.acceleratorProperty().set(ctrlE);
    KeyCombination ctrlT = new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN);
    newTask.acceleratorProperty().set(ctrlT);

    //separated out all the keyboard inputs, not counting the MenuItem ones
    //this is Ctrl+S, N, O
    stage.getScene().setOnKeyPressed(new MenuEventHandler(this, stage));
  }


  /**
   * Sets the content of a MindChangesPopupController to the data in the
   * ListView selected item and call to run the MindChangesPopupController
   *
   * @param list each day's list of items as a ListView object
   */
  //accurately responds when the content in a list is clicked, with accurate string info too
  private void addListViewHandlers(ListView<String> list) {
    list.setOnMouseClicked(event -> {
      String dayName = list.getId();
      String selectedItem = list.getSelectionModel().getSelectedItem();

      DayOfWeek dayOfWeek = getDayOfListView(dayName);

      if (!getListViewOfDay(dayOfWeek).getItems().isEmpty()) {
        this.mindChangesPopupController.setSelectedIndex(
            list.getSelectionModel().getSelectedIndex());
        this.mindChangesPopupController.setDay(getDayOfListView(dayName));
        this.mindChangesPopupController.setContent(selectedItem);
        this.mindChangesPopupController.showPopup();
        try {
          this.mindChangesPopupController.run();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  /**
   * Returns which DayOfWeek enum value it is after given a String dayName
   *
   * @param dayName The String fx:id of the selected ListView
   *
   * @return dayOfWeek The name of the
   */
  private DayOfWeek getDayOfListView(String dayName) {
    if (dayName.equals("mondayList")) {
      return DayOfWeek.MONDAY;
    }
    if (dayName.equals("tuesdayList")) {
      return DayOfWeek.TUESDAY;
    }
    if (dayName.equals("wednesdayList")) {
      return DayOfWeek.WEDNESDAY;
    }
    if (dayName.equals("thursdayList")) {
      return DayOfWeek.THURSDAY;
    }
    if (dayName.equals("fridayList")) {
      return DayOfWeek.FRIDAY;
    }
    if (dayName.equals("saturdayList")) {
      return DayOfWeek.SATURDAY;
    } else {
      return DayOfWeek.SUNDAY;
    }
  }


  /**
   * resets the content for the week being displayed to allow for handling of a new week.
   */
  public void reset() {
    week = new Week();
    addedDays.clear();
    this.listOfTodoTasks.clear();
    resetAllListViews();
    resetAllProgress();
    resetAllRemainingCounts();
    resetQuotesAndNotes();
    this.weekTitle.setText(week.getWeekTitle());
    this.todoList.getItems().clear();
  }


  /**
   * Returns the week associated with this bullet journal
   *
   * @return this week
   */
  public Week getWeek() {
    return this.week;
  }

  /**
   * Resets list content displayed in all listView JavaFX properties in the bulletJournal
   */
  private void resetAllListViews() {
    List<ListView<?>>
        listViewCollection = Arrays.asList(todoList, mondayList, tuesdayList, wednesdayList,
        thursdayList, fridayList, saturdayList, sundayList);

    for (ListView<?> listView : listViewCollection) {
      if (listView != null) {
        listView.getItems().clear();
      }
    }
  }

  /**
   * Resets the remaining counts displayed in the bullet journal
   */
  private void resetAllRemainingCounts() {
    List<Label> labelList = Arrays.asList(
        sundayRemaining, mondayRemaining, tuesdayRemaining,
        wednesdayRemaining, thursdayRemaining, fridayRemaining,
        saturdayRemaining);

    for (Label label : labelList) {
      label.setText("0");
    }

  }


  private void resetQuotesAndNotes() {
    this.quoteArea.clear();
    this.quoteArea.setPromptText("Words to live by...");
  }

  /**
   * Resets all progressIndicators
   */
  private void resetAllProgress() {
    List<Pane> listPanes = Arrays.asList(
        monday, tuesday, wednesday, thursday, friday, saturday, sunday, todoPane);

    for (Pane pane : listPanes) {
      for (Node childNode : pane.getChildren()) {
        if (childNode instanceof ProgressIndicator) {
          ProgressIndicator progressIndicator = (ProgressIndicator) childNode;
          //updates progress made with the list of tasks in this day, and this day's
          // progressIndicator
          progressIndicator.setProgress(0.0);
        }
      }
    }
  }

  /**
   * Handles all action events depending on the button pressed
   *
   * @throws IOException if unable to run other controllers
   */
  @Override
  public void run() throws IOException {
    //show splash
    newEventPopupController.initPopup();
    newTaskPopupController.initPopup();
    settingsPopupController.initPopup();
    mindChangesPopupController.initPopup();

    splashController.initPopup();
    initialPopupController.initPopup();

    enterPasswordController.initPopup();
    setPasswordController.initPopup();

    initialPopupController.run();
    initialPopupController.showPopup();
    splashController.showPopup();
    splashController.run();


    addOnActionHandlers(event -> {
      if (event.getTarget() == newEvent) {
        newEventPopupController.showPopup();
        try {
          newEventPopupController.run();
        } catch (IOException e) {
          throw new RuntimeException("unable to load event popup");
        }
      } else if (event.getTarget() == newTask) {
        newTaskPopupController.showPopup();
        try {
          newTaskPopupController.run();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else if (event.getTarget() == newWeek) {

        Map<String, Runnable> buttonSpecs = new HashMap<>();

        // "::" is a method reference. As writeToDestination
        // has a compatible signature with the Runnable interface's
        // "run()" method (takes in no arguments and returns no value),
        // we can pass it in as a runnable
        buttonSpecs.put("SAVE", this::writeToDestination);
        buttonSpecs.put("CONTINUE", this::handleOpeningNewWeek);


        CustomAlertUtils.showCustomAlert(
            Alert.AlertType.WARNING, "If you continue with creating a new week,"
                + "any unsaved progress will be lost. Continue?", buttonSpecs);
      } else if (event.getTarget() == openWeek) {
        selectedFile = this.askForFile();

        if (selectedFile != null) {
          this.deserialize(selectedFile);
          try {
            askForPasswordEntry();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

      } else if (event.getTarget() == save) {
        this.writeToDestination();
      } else if (event.getTarget() == settings) {
        settingsPopupController.showPopup();
        try {
          settingsPopupController.run();
        } catch (IOException e) {
          throw new RuntimeException("unable to open settings popup");
        }
      } else if (event.getTarget() == fullScreen) {
        this.setFullScreen();
      } else if (event.getTarget() == undo) {
        this.undo();
      }
    });
  }


  private void handleOpeningNewWeek() {
    this.reset();
    this.setPasswordController.showPopup();
    try {
      this.setPasswordController.run();
    } catch (IOException e) {
      throw new RuntimeException("unable to open settings popup");
    }
  }

  /**
   * Prompts the user for the bujo file to open
   * used by the Open Week / Ctrl+O function
   *
   * @return File that the user chose
   */
  public File askForFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose a .bujo file");
    return fileChooser.showOpenDialog(stage);
  }

  /**
   * with the file obtained from askForFile, take information from it and modify this current Bujo
   * with that
   *
   * @param openFile .bujo file being opened and having contents deserialized
   */
  public void deserialize(File openFile) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      //getting BujoJson from file
      JsonNode jsonNode = mapper.readTree(openFile);

      BujoJson bujoJson = mapper.convertValue(jsonNode, BujoJson.class);

      this.loadBulletJournal(bujoJson);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void loadBulletJournal(BujoJson bujo) {
    this.reset();
    WeekAdapter weekAdapter = bujo.week();
    setPassword(bujo.password());
    String quotes = bujo.quotes();
    this.week.set(weekAdapter);
    this.weekTitle.setText(this.week.getWeekTitle());
    this.quoteArea.setText(quotes);

    List<Task> weekTasks = new ArrayList<>();
    for (Day d : this.week.getDays()) {
      weekTasks.addAll(d.getTasks());
    }

    this.listOfTodoTasks.addAll(weekTasks);
    for (Task task : listOfTodoTasks) {
      String name = task.getName();
      CheckBox box = new CheckBox(name);

      box.setOnAction(event -> {
        if (box.isSelected()) {
          task.markAsComplete();
          displayCompletionStatusInDay(task, true);
        } else {
          task.markAsIncomplete();
          displayCompletionStatusInDay(task, false);
        }
        updateProgressMade(listOfTodoTasks, toDoProgress);
        updateRemainingCount(task.getWeekDay());
        updateProgressMade(this.week.getDayOfWeek(task.getWeekDay()).getTasks(),
            Objects.requireNonNull(getProgressIndicator(task.getWeekDay())));
      });


      box.setSelected(task.getIsComplete());
      this.todoList.getItems().add(box);
    }

    List<ListView<String>> dayViews = Arrays.asList(
        mondayList, tuesdayList, wednesdayList, thursdayList,
        fridayList, saturdayList, sundayList);

    for (ListView<String> view : dayViews) {
      DayOfWeek targetDayOfWeek = getDayOfListView(view.getId());
      Day day = this.week.getDayOfWeek(targetDayOfWeek);

      for (ScheduledItem item : day.getListOfScheduled()) {
        StringBuilder itemAsString =
            new StringBuilder(item.getName() + "\n" + item.getDescription());
        if (item instanceof Event) {
          Time time = ((Event) item).getStartTime();
          int duration = ((Event) item).getDuration();
          itemAsString.append("\n" + time + "-" + time.deriveEndTime(duration));
        }
        view.getItems().add(itemAsString.toString());

      }

      updateRemainingCount(targetDayOfWeek);
      updateProgressMade(day.getTasks(),
          Objects.requireNonNull(getProgressIndicator(targetDayOfWeek)));
      updateProgressMade(listOfTodoTasks, toDoProgress);

      this.settingsPopupController.loadSettings(this.week);
    }

    for (Task t : this.listOfTodoTasks) {
      displayCompletionStatusInDay(t, t.getIsComplete());
    }

  }


  /**
   * Opens a FileChooser and asks the user for destination, and then writes this content there
   */
  public void writeToDestination() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save to File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Bullet Journal Files", "*.bujo"));
    File selectedFile = fileChooser.showSaveDialog(stage);

    if (selectedFile != null) {
      // Use the selected file path for FileWriter or other operations
      String filePath = selectedFile.getAbsolutePath();

      String quoteContent;
      try {
        quoteContent = quoteArea.getText();
      } catch (NullPointerException e) {
        quoteContent = "";
      }

      WeekAdapter weekAdapter = new WeekAdapter(this.week);
      BujoJson currentBujo = new BujoJson(weekAdapter, quoteContent, password);
      BujoFileWriter bujoFileWriter = new BujoFileWriter(currentBujo);
      try {
        bujoFileWriter.write(filePath);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Used to remove the latest addition of a ScheduledItem (undo)
   */
  public void undo() {
    if (!this.addedDays.isEmpty()) {
      //isolate string to be removed from view
      DayOfWeek toBeRemovedDay = this.addedDays.remove(this.addedDays.size() - 1);
      Day targetDay = this.week.getDayOfWeek(toBeRemovedDay);
      //get the list view to remove from, and remove
      ListView<String> dayView = getListViewOfDay(toBeRemovedDay);
      int indexToRemove = dayView.getItems().size() - 1;
      dayView.getItems().remove(indexToRemove);

      //remove corresponding items from model
      List<ScheduledItem> listOfScheduledForDay = targetDay.getListOfScheduled();
      //scheduled item removed from the day
      ScheduledItem removed = listOfScheduledForDay.remove(indexToRemove);

      //update progress bars if it's a task
      if (removed instanceof Task) {

        //update day-view progress
        updateProgressMade(targetDay.getTasks(),
            Objects.requireNonNull(getProgressIndicator(toBeRemovedDay)));

        //update to-do list
        removeFromTodo(removed);
        updateProgressMade(listOfTodoTasks, toDoProgress);
      }
    } else {
      AlertUtils.showAlert(Alert.AlertType.ERROR, "No actions can be undone!");
    }
  }

  /**
   * Returns selected ScheduledItem in ListView - called when a cell in the
   * week view ListViews are clicked
   *
   * @param day     DayOfWeek that the selected content takes place in
   * @param content string content of selected list view item in the week view
   * @return selectedItem scheduled item corresponding to cell clicked in list view.
   */
  public ScheduledItem getSelected(DayOfWeek day, String content) {
    Day targetDay = this.week.getDayOfWeek(day);
    ListView<String> dayListView = getListViewOfDay(day);
    int indexOfViewTarget = dayListView.getItems().indexOf(content);
    List<ScheduledItem> contentOfTargetDay = targetDay.getListOfScheduled();
    return contentOfTargetDay.get(indexOfViewTarget);
  }


  /**
   * Based around when a list view item is deleted, updates the model for the week
   * to reflect this change with the corresponding scheduledItem to the list view test.
   *
   * @param day DayOfWeek that the selected content takes place in
   * @param content string content of selected list view item in the week view
   */
  public void deleteSelected(DayOfWeek day, String content) {
    //get corresponding Day of the day list view clicked
    ListView<String> dayListView = getListViewOfDay(day);
    //find index of list view selected item
    int indexOfViewTarget = dayListView.getItems().indexOf(content);

    //remove the content from the list view
    dayListView.getItems().remove(content);

    //get the corresponding day of the week
    Day targetDay = this.week.getDayOfWeek(day);
    List<ScheduledItem> contentOfTargetDay = targetDay.getListOfScheduled();

    //remove the content from the day of the week.
    ScheduledItem removed = contentOfTargetDay.remove(indexOfViewTarget);

    removeFromTodo(removed);
    updateRemainingCount(day);
  }

  /**
   * Helper method that removes the given item from the to-do list,
   * given the item is a task.
   *
   * @param toDelete item to delete.
   */
  private void removeFromTodo(ScheduledItem toDelete) {
    if (toDelete instanceof Task) {
      int indexToRemove = listOfTodoTasks.indexOf(toDelete);
      listOfTodoTasks.remove(toDelete);
      todoList.getItems().remove(indexToRemove);
    }
  }

  /**
   * Updates a task in the list of tasks to-do given a new name.
   *
   * @param task    task to modify
   * @param newName name to change to
   */
  public void updateTodoListWithEdit(ScheduledItem task, String newName) {
    int targetIndex = this.listOfTodoTasks.indexOf((Task) task);
    System.out.println("targetIndex = " + targetIndex);
    List<CheckBox> itemsInTodoList = this.todoList.getItems();
    CheckBox itemToChange = itemsInTodoList.get(targetIndex);
    itemToChange.setText(newName);
  }


  /**
   * Updates the item in the list view that has been edited
   *
   * @param item item being updated
   */
  public void updateListViewWithEdit(ScheduledItem item) {

    ListView<String> listViewOfDay = getListViewOfDay(item.getWeekDay());

    Day targetDay = this.week.getDayOfWeek(item.getWeekDay());

    List<ScheduledItem> itemsInDay = targetDay.getListOfScheduled();

    int targetIndex = itemsInDay.indexOf(item);

    StringBuilder itemAsString =
        new StringBuilder(item.getName() + "\n" + item.getDescription());
    if (item instanceof Event) {
      Time time = ((Event) item).getStartTime();
      int duration = ((Event) item).getDuration();
      itemAsString.append("\n" + time + "-" + time.deriveEndTime(duration));
    }

    listViewOfDay.getItems().set(targetIndex, itemAsString.toString());

  }




  /**
   * Adds the given string to the list view component for the given day
   *
   * @param day       day of the week that the string content should be displayed for
   * @param toDisplay string to display in a day's fxml list view
   */
  public void writeToGivenListView(DayOfWeek day, String toDisplay) {
    this.addedDays.add(day);
    getListViewOfDay(day).getItems().add(toDisplay);
  }

  /**
   * Creates a clickble hyperlink in the provided day and with the provided string format,
   * only in the cell with the given targetIndex to ensure lack of repetition.
   *
   * @param dayChoice dayOfWeek of the item having link content added
   * @param link relevant link in String format, beginning with https//
   * @param targetIndex index of item being modified in the display.
   */
  public void writeHyperlink(DayOfWeek dayChoice, String link, int targetIndex) {
    getListViewOfDay(dayChoice).setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          setText(item); // Set the text of the cell

          if (getIndex() == targetIndex) {
            Hyperlink hyperlink = new Hyperlink(link);

            hyperlink.setOnAction(event -> {
              // Handle hyperlink click event here
              try {
                URI uri = new URI(link);
                Desktop.getDesktop().browse(uri);
              } catch (URISyntaxException | IOException e) {
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid link!");
              }
            });
            setGraphic(hyperlink);
          } else {
            setGraphic(null);
          }
        }
      }
    });
  }

  /**
   * Given an Event or Task's description, pulls out the link within it if there is one,
   * returns empty string otherwise
   *
   * @param description the entire description of the event or task
   * @return String, textual representation of the link
   */
  public String extractLink(String description) {
    String link = "";

    // Regular expression pattern to match hyperlinks, found online
    String patternString = "https://[^\\s]+";
    Pattern pattern = Pattern.compile(patternString);
    if (description != null) {
      Matcher matcher = pattern.matcher(description);
      // Find the first match
      if (matcher.find()) {
        link = matcher.group();
      }
    }

    return link;
  }


  /**
   * Returns the list view corresponding to the given DayOfWeek
   *
   * @param day DayOfWeek to find the list view for
   * @return list view field corresponding to the given day of the week
   */
  public ListView<String> getListViewOfDay(DayOfWeek day) {
    if (day == DayOfWeek.MONDAY) {
      return mondayList;
    } else if (day == DayOfWeek.TUESDAY) {
      return tuesdayList;
    } else if (day == DayOfWeek.WEDNESDAY) {
      return wednesdayList;
    } else if (day == DayOfWeek.THURSDAY) {
      return thursdayList;
    } else if (day == DayOfWeek.FRIDAY) {
      return fridayList;
    } else if (day == DayOfWeek.SATURDAY) {
      return saturdayList;
    } else {
      return sundayList;
    }
  }


  /**
   * Writes the name of the given task to the to-do list and sets an action handler for checking the
   * box to mark this task as complete once clicked.
   *
   * @param taskToAdd model Task to have its title pulled and added as a checkbox to the to-do list
   */
  public void writeToTodoList(Task taskToAdd) {

    DayOfWeek targetDayOfWeek = taskToAdd.getWeekDay();
    Day targetDay = this.week.getDayOfWeek(targetDayOfWeek);

    CheckBox checkBox = new CheckBox(taskToAdd.getName());
    //write text to ListView
    todoList.getItems().add(checkBox);
    //keep track of checkBox
    listOfTodoTasks.add(taskToAdd);
    //keeping track of checkbox over time
    checkBox.setOnAction(event -> {
      if (checkBox.isSelected()) {
        taskToAdd.markAsComplete();
        displayCompletionStatusInDay(taskToAdd, true);
      } else {
        taskToAdd.markAsIncomplete();
        displayCompletionStatusInDay(taskToAdd, false);
      }
      updateProgressMade(listOfTodoTasks, toDoProgress);
      updateRemainingCount(taskToAdd.getWeekDay());
      updateProgressMade(targetDay.getTasks(),
          Objects.requireNonNull(getProgressIndicator(targetDayOfWeek)));
    });

  }

  /**
   * displays the corresponding task in the list view as being complete by formatting it with a
   * strikethrough
   *
   * @param taskChanged task with its visual representation to be marked as complete
   */
  private void displayCompletionStatusInDay(Task taskChanged, boolean isNowComplete) {

    DayOfWeek dayOfWeek = taskChanged.getWeekDay();
    Day scheduleDate = this.week.getDayOfWeek(dayOfWeek);
    List<ScheduledItem> itemsScheduled = scheduleDate.getListOfScheduled();
    ListView<String> dayView = getListViewOfDay(dayOfWeek);

    for (ScheduledItem item : itemsScheduled) {
      if (item.equals(taskChanged)) {
        //get item in list view corresponding to given task
        int targetIndex = itemsScheduled.indexOf(item);
        String listViewItem = dayView.getItems().get(targetIndex);
        if (isNowComplete) {
          dayView.getItems().set(
              targetIndex, "(\u2713) " + listViewItem);
        } else {
          //is already complete, so remove the completion check
          dayView.getItems().set(
              targetIndex, listViewItem.substring(4));
        }
      }
    }

  }


  /**
   * Updates the progress indicator for the given list of tasks
   */
  private void updateProgressMade(List<Task> listOfTasks, ProgressIndicator progressIndicator) {
    int totalTasks = listOfTasks.size();
    double progress = (double) getNumberOfTasksCompleted(listOfTasks) / totalTasks;
    progressIndicator.setProgress(progress);
  }

  /**
   * Returns the number of tasks completed
   *
   * @param listOfTasks list of Tasks that are found in the task queue
   * @return int the number of tasks in the list
   */
  private int getNumberOfTasksCompleted(List<Task> listOfTasks) {
    int numCompleted = 0;
    for (Task t : listOfTasks) {

      if (t.getIsComplete()) {
        numCompleted++;
      }
    }

    return numCompleted;
  }


  /**
   * Sets the stage to fullscreen
   */
  public void setFullScreen() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    this.stage.setFullScreen(!isFullScreen);
    isFullScreen = !isFullScreen;

    if (isFullScreen) {
      this.menuBat.setPrefWidth(screenSize.width);
    } else {
      this.menuBat.setPrefWidth(stage.getWidth());
    }
  }

  /**
   * Updates the model according to a given created item
   *
   * @param newScheduledItem new ScheduledItem to be added to model
   * @param chosenDay        chosen day of week to store this event in
   */
  public void scheduleItemInWeek(ScheduledItem newScheduledItem, DayOfWeek chosenDay) {
    Day targetDay = this.week.getDayOfWeek(chosenDay);

    targetDay.scheduleItem(newScheduledItem);
    checkForCommitWarnings(chosenDay);
    updateRemainingCount(chosenDay);

    updateProgressMade(targetDay.getTasks(),
        Objects.requireNonNull(getProgressIndicator(chosenDay)));

  }

  /**
   * Accesses a day's list of tasks and calls to update the remaining tasks based on how many
   * of the tasks in that list are completed.
   *
   * @param chosenDay day to update the remaining number of tasks
   */
  private void updateRemainingCount(DayOfWeek chosenDay) {
    List<Task> listOfTasksInDay = this.week.getDayOfWeek(chosenDay).getTasks();
    int numberRemaining = listOfTasksInDay.size() - getNumberOfTasksCompleted(listOfTasksInDay);
    updateRemainingOfaDay(chosenDay, numberRemaining);
  }


  /**
   * Updates the remaining task count for a day based on the given dayOfWeek and count to update
   * the display for
   *
   * @param chosenDay    day of the week for which the remaining count should be updated
   * @param updatedCount task count to update the given day's count with.
   */
  private void updateRemainingOfaDay(DayOfWeek chosenDay, int updatedCount) {
    switch (chosenDay) {
      case MONDAY -> mondayRemaining.setText(String.valueOf(updatedCount));
      case TUESDAY -> tuesdayRemaining.setText(String.valueOf(updatedCount));
      case WEDNESDAY -> wednesdayRemaining.setText(String.valueOf(updatedCount));
      case THURSDAY -> thursdayRemaining.setText(String.valueOf(updatedCount));
      case FRIDAY -> fridayRemaining.setText(String.valueOf(updatedCount));
      case SATURDAY -> saturdayRemaining.setText(String.valueOf(updatedCount));
      case SUNDAY -> sundayRemaining.setText(String.valueOf(updatedCount));
    }
  }


  /**
   * Helper method for scheduleItemInWeek that checks if the given day that a scheduled item has
   * been added to results in the user's specified ScheduledItem limit has been exceeded.
   */
  private void checkForCommitWarnings(DayOfWeek chosenDay) {
    Day dayOfInterest = this.week.getDayOfWeek(chosenDay);

    if (this.week.getMaxTasksPerDay() != null) {
      if (dayOfInterest.doTasksExceedMax()) {
        AlertUtils.showAlert(Alert.AlertType.WARNING, "You have exceeded your set maximum of tasks"
            + "for " + chosenDay.toString() + "!");
      }
    }
    if (this.week.getMaxEventsPerDay() != null) {
      if (dayOfInterest.doEventsExceedMax()) {
        AlertUtils.showAlert(Alert.AlertType.WARNING, "You have exceeded your set maximum of events"
            + "for " + chosenDay.toString() + "!");
      }
    }
  }

  /**
   * Updates the current week's title to the given title
   *
   * @param newTitle The new title for the Week to be named
   */
  public void updateWeekTitle(String newTitle) {
    this.weekTitle.setText(newTitle);
  }

  /**
   * Updates the task limit for the week to the given value
   *
   * @param taskLimit The new limit to how many tasks are allowed
   */
  public void setTasksLimit(int taskLimit) {
    this.week.setMaxTasksPerDay(taskLimit);
  }

  /**
   * Updates the event limit for the week to the given value
   *
   * @param eventLimit The new limit to how many events are allowed
   */
  public void setEventsLimit(int eventLimit) {
    this.week.setMaxEventsPerDay(eventLimit);
  }


  private ProgressIndicator getProgressIndicator(DayOfWeek dayOfWeek) {
    ListView<String> dayOfWeekView = getListViewOfDay(dayOfWeek);

    Pane parentPane = (Pane) dayOfWeekView.getParent();

    for (Node childNode : parentPane.getChildren()) {
      if (childNode instanceof ProgressIndicator) {
        return (ProgressIndicator) childNode;
      }
    }

    return null;
  }

  /**
   * sets the password of the bullet journal's current week.
   *
   * @param password password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }


  /**
   * Returns the password associated with the current bullet journal
   *
   * @return String value serving as a passkey for security purposes in opening the file.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Calls to initialize, show, and run this BulletJournalController's Enter PasswordController
   * field to ask the user for the password set for a week attempting to be opened.
   *
   * @throws IOException if unable to load relevant fxml files.
   */
  public void askForPasswordEntry() throws IOException {
    this.enterPasswordController.showPopup();
    this.enterPasswordController.run();
  }

  /**
   * Calls to initialize, show, and run this BulletJournalController's SetPasswordController
   * field to ask the user for their initial password for the week and retain the entered value
   *
   * @throws IOException if unable to load relevant fxml files.
   */
  public void askForPasswordChoice() throws IOException {
    this.setPasswordController.initPopup();
    this.setPasswordController.showPopup();
    this.setPasswordController.run();
  }

  /**
   * Getter that returns the NewTaskPopupController field
   *
   * @return NewTaskPopupController, used in MenuEventHandler
   */
  public NewTaskPopupController getNewTaskPopupController() {
    return newTaskPopupController;
  }

  /**
   * Getter that returns the NewEventPopupController field
   *
   * @return NewEventPopupController, used in MenuEventHandler
   */
  public NewEventPopupController getNewEventPopupController() {
    return newEventPopupController;
  }
}
