package sudokugui;

import java.util.Random;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * https://github.com/JinsungYoo/AUT/tree/master/SudokuGUI
 * 
 * This class for generating sudoku board.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 12.06.2020
 */
public class BoardGenerator {
    private static Random rand = new Random();
    private static int[][] board;
    
    //Construct
    public BoardGenerator() {    
        do{
            board = new int[9][9];
            board = BoardGenerator.generate(board, 0, 0);
        }while (board == null);
    }
    
    //Get method
    //For changing integer array sudoku board to char array
    public char[][] getBoard(){
        char[][] temp = new char[9][9];
        for(int i=0; i<9; i++){
             for(int j=0; j<9; j++){
                 temp[i][j] = (char)(board[i][j] + 48);
             }
         }
        return temp;
    }
    
    //Logic of Sudoku generating
    static int[][] generate(int[][] array, int row, int column){
        try{
            int count = 0;
            do {
                if(count == 10) {
                    array = resetRow(array,row);
                    column = 0;
                }
                array[row][column] = rand.nextInt(9) + 1;
                count++;
            }while(!(testColumnAndRow(array, row, column) && testBlock(array, row, column)));
            count = 0;
            
            if(row == 8 && column == 8){
                return array;
            }
            
            if(column == 8){
                return generate(array, row + 1, 0);
            }
            
            return generate(array, row, column + 1);
        } catch (NullPointerException | StackOverflowError e){
            return null;
        }
    }
    
    //Reset the row when the sudoku is stuck
    static int[][] resetRow(int[][] array, int row) {
        int[][] temp = new int[9][9];
        for(int c = 0; c < 9; c++) {
            for(int r = 0; r < row; r++) {
                temp[r][c] = array[r][c];
            }
        }
        return temp;
    }
    
    //Test the value that was generated correctly or not in row or column
    static boolean testColumnAndRow(int[][] array, int row, int column) {
        //Test Column
        for (int r = 0; r < row; r++){
            if(array[row][column] == array[r][column]){
                return false;
            }
        }
        
        //Test Row
        for (int c = 0; c < column; c++) {
            if(array[row][column] == array[row][c]) {
                return false;
            }
        }
        return true;
    }
    
    //Test the value that was generated correctly or not in 3X3 block. 
    static boolean testBlock(int[][] array, int row, int column) {
        int rNum = 0;
        int cNum = 0;
        
        if (row / 3 == 0 && column / 3 == 0) {
            rNum = 0;
            cNum = 0;
        }
        else if (row / 3 == 0 && column / 3 == 1) {
            rNum = 0;
            cNum = 3;
        } 
        else if (row / 3 == 0 && column / 3 == 2) {
            rNum = 0;
            cNum = 6;
        } 
        else if (row / 3 == 1 && column / 3 == 0) {
            rNum = 3;
            cNum = 0;
        } 
        else if (row / 3 == 1 && column / 3 == 1) {
            rNum = 3;
            cNum = 3;
        } 
        else if (row / 3 == 1 && column / 3 == 2) {
            rNum = 3;
            cNum = 6;
        } 
        else if (row / 3 == 2 && column / 3 == 0) {
            rNum = 6;
            cNum = 0;
        } 
        else if (row / 3 == 2 && column / 3 == 1) {
            rNum = 6;
            cNum = 3;
        } 
        else {
            rNum = 6;
            cNum = 6;
        }
        
        for (int r = rNum; r < (rNum + 3); r++) {
            for (int c = cNum; c < (cNum + 3); c++) {
                if (!(row == r && column == c) && array[row][column] == array[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }
}
