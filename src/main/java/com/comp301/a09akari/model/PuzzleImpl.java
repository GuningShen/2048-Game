package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    // Your constructor code here
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 | r > getHeight() | c < 0 | c > getWidth()) {
      throw new IndexOutOfBoundsException();
    }

    int value = board[r][c];
    CellType cell = CellType.CLUE;

    switch (value) {
      case 0:
      case 4:
      case 3:
      case 2:
      case 1:
        cell = CellType.CLUE;
        break;
      case 5:
        cell = CellType.WALL;
        break;
      case 6:
        cell = CellType.CORRIDOR;
        break;
    }
    return cell;
  }

  @Override
  public int getClue(int r, int c) {
    if (r < 0 | r > getHeight() | c < 0 | c > getWidth()) {
      throw new IndexOutOfBoundsException();
    }

    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }

    int value = board[r][c];
    int clue = 0;

    switch (value) {
      case 0:
        clue = 0;
        break;
      case 1:
        clue = 1;
        break;
      case 2:
        clue = 2;
        break;
      case 3:
        clue = 3;
        break;
      case 4:
        clue = 4;
        break;
    }
    return clue;
  }
}
