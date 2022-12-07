package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MessageView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public MessageView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {

    StackPane layout = new StackPane();
    layout.setStyle("-fx-padding: 5px;");
    Label result = new Label(makeLabelString());
    result.setMinWidth(400);
    result.setMinHeight(25);
    result.setAlignment(Pos.CENTER);
    result.setStyle(
        "-fx-padding: 5px;"
            + "-fx-font-family: Luminari, fantasy;"
            + "-fx-font-weight: normal;"
            + "-fx-font-size: 24px;");
    layout.getChildren().add(result);

    return layout;
  }

  private String makeLabelString() {
    if (model.isSolved()) {
      return "Congratulations!";
    } else {
      return "Keep trying";
    }
  }
}
