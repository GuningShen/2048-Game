package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public ControlView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox control = new VBox();

    StackPane index = new StackPane();
    Label puzzle = new Label(makeIndex());
    puzzle.setMinWidth(200);
    puzzle.setMinHeight(20);
    puzzle.setStyle("-fx-padding: 5px;" + "-fx-alignment: center;" + "-fx-font-size: 16px;");
    index.getChildren().add(puzzle);

    control.getChildren().add(index);

    HBox panel = new HBox();
    panel.setMinHeight(24);
    panel.setStyle("-fx-padding: 5px;" + "-fx-spacing: 8px;" + "-fx-alignment: center;");

    Button randPuzzle = new Button();
    randPuzzle.setText("Random");
    randPuzzle.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    panel.getChildren().add(randPuzzle);

    Button prevPuzzle = new Button("Previous");
    prevPuzzle.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    panel.getChildren().add(prevPuzzle);

    Button nextPuzzle = new Button("Next");
    nextPuzzle.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    panel.getChildren().add(nextPuzzle);

    Button resetPuzzle = new Button();
    resetPuzzle.setText("Start Again");
    resetPuzzle.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    panel.getChildren().add(resetPuzzle);

    control.getChildren().add(panel);

    return control;
  }

  public String makeIndex() {
    int current = model.getActivePuzzleIndex() + 1;
    return "Puzzle " + current + " of 5";
  }
}
