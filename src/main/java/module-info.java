/**
 * This module represents a package for cs3500.pa05. It requires several dependencies
 * such as JavaFX, Jackson for JSON handling, controlsfx for UI components, and java.desktop.
 * The module exports and opens specific packages to certain modules.
 */
module cs3500.pa05 {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  requires org.controlsfx.controls;
  requires java.desktop;

  opens cs3500.pa05 to javafx.fxml;
  exports cs3500.pa05;
  exports cs3500.pa05.controller;
  exports cs3500.pa05.model;
  exports cs3500.pa05.view;
  opens cs3500.pa05.controller to javafx.fxml;
  opens cs3500.pa05.model to javafx.fxml;
  opens cs3500.pa05.view to javafx.fxml;
  //ADDED THE FOLLOWING LINE:
  exports cs3500.pa05.model.json to com.fasterxml.jackson.databind;
  opens cs3500.pa05.model.json to com.fasterxml.jackson.databind;
}