package com.javafxgrid.model;
import com.javafxgrid.model.cells.Cell;
import com.javafxgrid.model.game.*;
import com.javafxgrid.model.gameBoard.*;


public class LogicsImpl implements Logics {

    private Coord current;
    private final Board<Coord, Cell> board;
    private final SweepMiner game;

    public LogicsImpl(int size, int bombNumber) {
        this.board = new BoardImpl<Coord, Cell>(size);
        this.game = new SweepMinerImpl(board);
        this.game.seedBombs(bombNumber);
        this.game.fillRemaining();
    }

    @Override
    public void hit(Coord pos) {
        this.current = pos;
        game.recursiveDiscoveryOf(current);
    }

    @Override
    public boolean isOver() {
        return game.isOver(current);
    }

    @Override
    public boolean hasWon() {
        System.out.println(game.hitCount());
        System.out.println(board.size());
        System.out.println(game.bombsSize());
        return board.size() - game.hitCount() == game.bombsSize();
    }

    @Override
    public void flag(Coord pos) {
        board.getCell(pos).changeFlag();
    }

    @Override
    public Cell getResult(Coord c) {
        return board.getCell(c);
    }

}
