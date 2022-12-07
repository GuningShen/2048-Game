package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    // Constructor code goes here
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {

    int size = model.getPuzzleLibrarySize();
    int index = model.getActivePuzzleIndex();
    if (index + 1 < size) {
      model.setActivePuzzleIndex(index + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {

    int index = model.getActivePuzzleIndex();
    if (index - 1 >= 0) {
      model.setActivePuzzleIndex(index - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int current = model.getActivePuzzleIndex();
    Random random = new Random();
    int rand = random.nextInt(model.getPuzzleLibrarySize());

    while (current == rand) {
      rand = random.nextInt(model.getPuzzleLibrarySize());
    }

    model.setActivePuzzleIndex(rand);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    Puzzle puzzle = model.getActivePuzzle();
    if (puzzle.getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
