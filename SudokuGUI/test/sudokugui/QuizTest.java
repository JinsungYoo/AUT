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
 * It include a test of Quiz class.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 14.06.2020
 */
public class QuizTest {
    
    /**
     * Test of getQuiz method, of class Quiz.
     */
    @Test
    public void testGetQuiz() {
        System.out.println("getQuiz");
        BoardGenerator temp = new BoardGenerator();
        Quiz instance = new Quiz(temp.getBoard());
        
        int expResult = 45;
        int result = 0;
        char[][] temp2 = instance.getQuiz();
        for(int i=0; i<temp2.length; i++){
             for(int j=0; j<temp2.length; j++){
                 if(temp2[i][j] == ' '){
                     result++;
                 }
             }
         }
        assertSame(expResult, result);
    }
}
