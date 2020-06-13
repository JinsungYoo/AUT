package sudokugui;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * 
 * This class for generate Sudoku quiz board .
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 12.06.2020
 */
public class Quiz {
     private char[][] quiz;
     
     public Quiz(char[][] board){
         quiz = new char[9][9];
         
         for(int i=0; i<9; i++){
             for(int j=0; j<9; j++){
                 quiz[i][j] = board[i][j];
             }
         }
         
         int num = 45; // Make 45 empty squares of total 81 squares in sodoku game
         do{
             int x = (int)(Math.random()*9);
             int y = (int)(Math.random()*9);// (X, Y) empty square location. 
             if(quiz[x][y]!=' '){
                 quiz[x][y] = ' ';
                 num--;
             }
        }while(num>0);
     }
     
     //Get method
     public char[][] getQuiz(){
         return quiz;
     }
}
