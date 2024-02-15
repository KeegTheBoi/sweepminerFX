package com.javafxgrid.model.game;

import com.javafxgrid.model.Coord;
import com.javafxgrid.model.cells.Cell;
import com.javafxgrid.model.cells.CellFactory;
import com.javafxgrid.model.cells.CellFactoryImpl;
import com.javafxgrid.model.cells.CellsUtils;
import com.javafxgrid.model.gameBoard.Board;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;

import java.util.*;
import java.util.stream.*;
import static java.util.function.Predicate.not;

public class SweepMinerImpl implements SweepMiner{
    
    private static final long ONE_SECOND = 1000L;
    private static final int SCALAR_INCREASE = 50;
    private static final double ARC_SIN_INF_BOUND = -1.0;
    private static final double ARC_SIN_SUP_BOUND = 1.0;
    private Board<Coord, Cell> board;
    private final CellFactory cf = new CellFactoryImpl();
    private Double limit;
    private int hitted;
    private SimpleBooleanProperty timerElapsedProprety = new SimpleBooleanProperty();
    private SimpleIntegerProperty timerCount = new SimpleIntegerProperty(Integer.MAX_VALUE);


    private Thread timThread;
    private Random rand = new Random();
    private boolean interrupt;

    public SweepMinerImpl(Board<Coord, Cell> board) {
        this.board = board;
        timThread = new Thread(timerTask());
        this.timerElapsedProprety.bind(Bindings
                .when(timerCount.isEqualTo(0))
                .then(true)
                .otherwise(false)
            );
    }


    @Override
    public void seedBombs(int difficulty) {
        double ratio = (difficulty / 10.0);
        double filler = board.bound() / 2.0;
        ratio = ratio < 0.5 ? -ratio : ratio;
        double scount = ratio * board.bound();
        limit = 1.5 * board.bound() - (filler - scount);
        while(board.mapSize() < (int)Math.ceil(limit)) {
            board.setValue(this.randomCoord(), cf.mine());
        }
        System.out.println((int)Math.ceil(limit));
    }

    @Override
    public void fillRemaining() {
        // alternative using iterable
        this.all().filter(c -> Objects.isNull(board.getCell(c)))
            .forEach(pos -> board.setValue(pos, cf.ground(countAdjaxBombs(pos))));
    }

    @Override
    public int getBombSize() {
        return (int)Math.ceil(limit);
    }

    private int countAdjaxBombs(Coord pos) {
        return (int)this.adjaxOf(pos).stream().map(board::getCell)
            .filter(Objects::nonNull)
            .filter(CellsUtils::isBomb).count();
    }
    
    @Override
    public void unveil(Coord pos) {
        this.hitted++;
        board.getCell(pos).reveal();
    }
    
    @Override
    public void recursiveDiscoveryOf(Coord c) {
        
        System.out.println(c);
        unveil(c);
        Optional.of(c).map(board::getCell).filter(CellsUtils::isEmpty)
            .filter(e -> !CellsUtils.isBomb(e))
            .ifPresent(h -> this.adjaxOf(c).stream()
                .map(board::getCell)
                .filter(CellsUtils::isVeiled)
                .filter(CellsUtils::isValuable)
                .map(board::getCoord)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::recursiveDiscoveryOf)
            );
    }

    private Set<Coord> adjaxOf(Coord pos) {
        return IntStream.rangeClosed(pos.x() - 1, pos.x() + 1).boxed().flatMap(
            i -> IntStream.rangeClosed(pos.y() -1, pos.y() +1 ).mapToObj(j -> new Coord(i, j))
        ).filter(not(pos::equals))
        .filter(this::inBound)
        .collect(Collectors.toSet());
    }

    private Stream<Coord> all() {
        return IntStream.range(0, board.bound())
            .boxed().flatMap(i -> IntStream.range(0, board.bound())
                .mapToObj(j -> new Coord(i, j)));
    }

    @Override
    public int hitCount() {
        return this.hitted;
    }

    private Coord randomCoord() {
        Random rand = new Random();
        return new Coord(rand.nextInt(board.bound()), rand.nextInt(board.bound()));
    }


    private boolean inBound(Coord c) {
        return c.x() >= 0 && c.y() < this.board.bound() && c.x() < this.board.bound() && c.y() >= 0; 
    }

    @Override
    public ObservableBooleanValue timerElapsedObservable() {
        return this.timerElapsedProprety;
    }

    @Override
    public void startTimer() {
        timThread.start();
    }

    //Not public cause updtaes may require a different approach
    private Runnable timerTask() {
        return () -> {
            int seconds = getDurationFromDifficulty();
            Stream.iterate(seconds, i -> i >= 0, i -> i - 1)
                .peek(timerCount::set)
                .takeWhile(i -> !this.interrupt)
                .forEach(i -> sleepThread()); //should do a reduce 
            //MUST DO IN INITIALIZATION
            
            System.out.println("OVER PROP -> " + timerElapsedProprety.get());
        };
        
    }

    private void sleepThread() {
        try {
            Thread.sleep(ONE_SECOND);
        } catch (InterruptedException e) {
            System.out.println("INTERRUPT");
        }
    }

    @Override
    public ObservableIntegerValue getTimerCountObservable() {
        return this.timerCount;
    }


    private int getDurationFromDifficulty() {
        return this.getBombSize() * SCALAR_INCREASE + 
            (int)Math.toDegrees(Math.asin(rand.nextDouble(ARC_SIN_INF_BOUND, ARC_SIN_SUP_BOUND)));
    }


    @Override
    public void stopTimer() {
        this.interrupt = true;
    }

    
}
