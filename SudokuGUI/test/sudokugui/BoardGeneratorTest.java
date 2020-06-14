/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokugui;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * https://github.com/JinsungYoo/AUT/tree/master/SudokuGUI
 * 
 * This is Unit testing class.
 * It includes 3 tests of BoardGenerator class.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 14.06.2020
 */
public class BoardGeneratorTest {
    
    /**
     * Test of getBoard method, of class BoardGenerator.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        BoardGenerator instance = new BoardGenerator();
        assertNotNull(instance);
    }

    /**
     * Test of testColumnAndRow method, of class BoardGenerator.
     */
    @Test
    public void testTestColumnAndRow() {
        System.out.println("testColumnAndRow");
        BoardGenerator instance = new BoardGenerator();
        char[][] temp = instance.getBoard();
        int[][] array = new int[temp.length][temp.length];
        for(int i=0; i<temp.length; i++){
             for(int j=0; j<temp.length; j++){
                 array[i][j] = (int)(temp[i][j] - 48);
             }
         }
        int row = 0;
        int column = 0;
        boolean result = BoardGenerator.testColumnAndRow(array, row, column);
        assertTrue(result);
    }

    /**
     * Test of testBlock method, of class BoardGenerator.
     */
    @Test
    public void testTestBlock() {
        System.out.println("testBlock");
        BoardGenerator instance = new BoardGenerator();
        char[][] temp = instance.getBoard();
        int[][] array = new int[temp.length][temp.length];
        for(int i=0; i<temp.length; i++){
             for(int j=0; j<temp.length; j++){
                 array[i][j] = (int)(temp[i][j] - 48);
             }
         }
        int row = 0;
        int column = 0;
        boolean result = BoardGenerator.testBlock(array, row, column);
        assertTrue(result);
    }
}
