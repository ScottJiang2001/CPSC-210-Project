package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipGridTest {
    private BattleShipGrid testBattleShipGrid;

    @BeforeEach
    void runBefore() {
        testBattleShipGrid = new BattleShipGrid();
    }

    @Test
    void testConstructor() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals("~", testBattleShipGrid.getElement(i,j+1));
            }
        }
        assertEquals(0, testBattleShipGrid.getScore());
        assertEquals(5, testBattleShipGrid.getBoardSize());
    }

    @Test
    void testPlaceShip() {
        testBattleShipGrid.placeShip(2,3);
        assertEquals("S", testBattleShipGrid.getElement(2,3));
        testBattleShipGrid.placeShip(1,2);
        assertEquals("S", testBattleShipGrid.getElement(1,2));

    }

    @Test
    void testPlaceHit() {
        testBattleShipGrid.placeHit(1,4);
        assertEquals("X", testBattleShipGrid.getElement(1,4));
    }

    @Test
    void testPlaceMiss() {
        testBattleShipGrid.placeMiss(3,5);
        assertEquals("O", testBattleShipGrid.getElement(3,5));
    }

    @Test
    void testScoreIncrement() {
        testBattleShipGrid.increaseScore();
        assertEquals(1, testBattleShipGrid.getScore());
        testBattleShipGrid.increaseScore();
        assertEquals(2, testBattleShipGrid.getScore());
    }

    @Test
    void testGetGrid() {
        String[][] testGrid = testBattleShipGrid.getGrid();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals("~", testGrid[i][j]);
            }
        }
    }

    @Test
    void testChangeGrid() {
        testBattleShipGrid.changeGrid(2,3,"X");
        assertEquals("X", testBattleShipGrid.getElement(2,3+1));
    }
}