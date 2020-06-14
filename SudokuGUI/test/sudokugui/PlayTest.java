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
 * It include a test of Play class.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 14.06.2020
 */
public class PlayTest {
    /**
     * Test of answerCheck method, of class Play.
     */
    @Test
    public void testAnswerCheck() {
        System.out.println("answerCheck");
        Play instance = new Play(1, "PlayerName","PlayerScore");
        int expResult = 0;
        int result = instance.answerCheck();
        assertEquals(expResult, result);
    }
}
