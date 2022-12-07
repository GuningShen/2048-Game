package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final List<ModelObserver> observers;
  private int index;
  private Puzzle active;
  private int[][] lampLocation;
  private boolean solved;

  public ModelImpl(PuzzleLibrary library) {
    // Your constructor code here
    this.library = library;
    this.index = 0;
    this.active = library.getPuzzle(index);
    this.lampLocation = new int[active.getHeight()][active.getWidth()];
    this.solved = false;
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    // row is height
    // column is width

    int width = active.getWidth();
    int height = active.getHeight();

    if (r < 0 | r >= height | c < 0 | c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (active.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    if (lampLocation[r][c] != 1) {
      lampLocation[r][c] = 1;
    }

    notifyObserver();
  }

  @Override
  public void removeLamp(int r, int c) {
    int width = active.getWidth();
    int height = active.getHeight();

    if (r < 0 | r >= height | c < 0 | c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (active.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    if (lampLocation[r][c] == 1) {
      lampLocation[r][c] = 0;
    }

    notifyObserver();
  }

  @Override
  public boolean isLit(int r, int c) {
    int width = active.getWidth();
    int height = active.getHeight();

    if (r < 0 | r >= height | c < 0 | c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (active.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    if (isLamp(r, c)) {
      return true;
    }

    // north direction
    for (int i = 1; r - i >= 0; i++) {
      if (active.getCellType(r - i, c) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r - i, c)) {
        return true;
      }
    }

    // south direction
    for (int i = 1; r + i < active.getHeight(); i++) {
      if (active.getCellType(r + i, c) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r + i, c)) {
        return true;
      }
    }

    // west direction
    for (int i = 1; c - i >= 0; i++) {
      if (active.getCellType(r, c - i) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r, c - i)) {
        return true;
      }
    }

    // east direction
    for (int i = 1; c + i < active.getWidth(); i++) {
      if (active.getCellType(r, c + i) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r, c + i)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    int width = active.getWidth();
    int height = active.getHeight();

    if (r < 0 | r >= height | c < 0 | c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (active.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    return lampLocation[r][c] == 1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    int width = active.getWidth();
    int height = active.getHeight();

    if (r < 0 | r >= height | c < 0 | c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }

    // check if there is a lamp in the north direction
    for (int i = 1; r - i >= 0; i++) {
      if (active.getCellType(r - i, c) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r - i, c)) {
        return true;
      }
    }

    // south direction
    for (int i = 1; r + i < active.getHeight(); i++) {
      if (active.getCellType(r + i, c) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r + i, c)) {
        return true;
      }
    }

    // west direction
    for (int i = 1; c - i >= 0; i++) {
      if (active.getCellType(r, c - i) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r, c - i)) {
        return true;
      }
    }

    // east direction
    for (int i = 1; c + i < active.getWidth(); i++) {
      if (active.getCellType(r, c + i) != CellType.CORRIDOR) {
        break;
      }
      if (isLamp(r, c + i)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return active;
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 | index >= library.size()) {
      throw new IndexOutOfBoundsException();
    }
    this.index = index;
    this.active = library.getPuzzle(index);
    this.lampLocation = new int[active.getHeight()][active.getWidth()];
    notifyObserver();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return this.library.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < lampLocation.length; i++) {
      for (int j = 0; j < lampLocation[0].length; j++) {
        lampLocation[i][j] = 0;
      }
    }
    notifyObserver();
  }

  @Override
  public boolean isSolved() {
    // looping through all cells
    for (int r = 0; r < active.getHeight(); r++) {
      for (int c = 0; c < active.getWidth(); c++) {
        // if cell is clue, check whether clue is satisfied
        if (active.getCellType(r, c) == CellType.CLUE) {
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        } // if cell is corridor, check whether it is lit.
        else if (active.getCellType(r, c) == CellType.CORRIDOR) {
          // if the cell is lamp, check to see whether it is illegally placed.
          if (isLamp(r, c)) {
            if (isLampIllegal(r, c)) {
              return false;
            }
          }
          // every cell is lit
          if (!isLit(r, c)) {
            return false;
          }
        }
      }
    }

    this.solved = true;
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    int width = active.getWidth();
    int height = active.getHeight();

    if (r < 0 | r >= height | c < 0 | c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (active.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }

    int count = 0;
    int required = active.getClue(r, c);

    // north
    if (r - 1 >= 0) {
      if (active.getCellType(r - 1, c) == CellType.CORRIDOR) {
        if (isLamp(r - 1, c)) {
          count++;
        }
      }
    }

    if (r + 1 < height) {
      if (active.getCellType(r + 1, c) == CellType.CORRIDOR) {
        if (isLamp(r + 1, c)) {
          count++;
        }
      }
    }

    if (c - 1 >= 0) {
      if (active.getCellType(r, c - 1) == CellType.CORRIDOR) {
        if (isLamp(r, c - 1)) {
          count++;
        }
      }
    }

    if (c + 1 < width) {
      if (active.getCellType(r, c + 1) == CellType.CORRIDOR) {
        if (isLamp(r, c + 1)) {
          count++;
        }
      }
    }

    return required == count;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObserver() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }
}
