package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class PuzzleView implements FXComponent {
  private final Model model;
  ClassicMvcController controller;

  public PuzzleView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane board = new GridPane();
    board.setAlignment(Pos.CENTER);
    board.setHgap(10);
    board.setVgap(10);

    Puzzle active = model.getActivePuzzle();

    for (int r = 0; r < active.getHeight(); r++) {
      for (int c = 0; c < active.getWidth(); c++) {
        board.add(makeTile(active, r, c), r, c);
      }
    }

    return board;
  }

  private Label makeTile(Puzzle active, int r, int c) {
    Label tile = new Label();
    if (active.getCellType(r, c) == CellType.WALL) {
      tile.setStyle("-fx-background-color: #000000;");
    } else if (active.getCellType(r, c) == CellType.CLUE) {
      tile.setText(String.valueOf(active.getClue(r, c)));
      tile.setTextFill(Color.WHITE);
      tile.setTextAlignment(TextAlignment.CENTER);
      if (model.isClueSatisfied(r, c)) {
        tile.setStyle(
            "-fx-font-size: 28px;" + "-fx-alignment: center;" + "-fx-background-color: #3cb371;");
      } else {
        tile.setStyle(
            "-fx-font-size: 28px;" + "-fx-alignment: center;" + "-fx-background-color: #000000;");
      }
    } else {
      if (model.isLamp(r, c)) {
        Image lamp = new Image("light-bulb.png");
        ImageView view = new ImageView(lamp);
        view.setFitHeight(40);
        view.setPreserveRatio(true);
        tile.setGraphic(view);
        tile.setAlignment(Pos.CENTER);
        if (model.isLampIllegal(r, c)) {
          tile.setStyle("-fx-background-color: #CC0000;");
        } else {
          tile.setStyle("-fx-background-color: #ffff99;");
        }
      } else {
        if (model.isLit(r, c)) {
          tile.setStyle("-fx-background-color: #ffff99;");
        } else {
          tile.setStyle("-fx-background-color: #ffffff;");
        }
      }
    }

    tile.setOnMouseClicked(
        (Event event) -> {
          controller.clickCell(r, c);
        });

    return tile;
  }
}
