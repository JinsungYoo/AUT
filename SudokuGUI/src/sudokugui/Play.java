
package sudokugui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * https://github.com/JinsungYoo/AUT/tree/master/SudokuGUI
 * 
 * This class for Playing Game
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 12.06.2020
 */
public class Play extends JFrame implements ActionListener{
    private int userid;
    private char[][] answer;
    private char[][] quiz;
    private JPanel mainPanel, centerPanel, southPanel, northPanel;
    private JTextField[][] index;
    private JButton submit;
    private JLabel user, totalScore, playerid, userLabel, scoreLabel, useridLabel;

    public Play(int userid, String userName, String userScore) {
        this.userid = userid;
        answer = new char[9][9];
        quiz = new char[9][9];
        index = new JTextField[9][9];
        mainPanel = new JPanel();
        setContentPane(mainPanel);
        centerPanel = new JPanel();
        southPanel = new JPanel();
        northPanel = new JPanel();
        submit = new JButton("Submit");
        submit.addActionListener(this);
        playerid = new JLabel(Integer.toString(userid));
        user = new JLabel(userName);
        totalScore = new JLabel(userScore);
        useridLabel = new JLabel("Player ID : ");
        userLabel = new JLabel("     Player : ");
        scoreLabel = new JLabel("     Current Your Total Score : ");

        BoardGenerator answerBoard = new BoardGenerator();
        answer = answerBoard.getBoard();

        Quiz quizBoard = new Quiz(answer);
        quiz = quizBoard.getQuiz();
        
        northPanel.add(useridLabel);
        northPanel.add(playerid);
        northPanel.add(userLabel);
        northPanel.add(user);
        northPanel.add(scoreLabel);
        northPanel.add(totalScore);

        centerPanel.setLayout(new GridLayout(9, 9));
        centerPanel.setPreferredSize(new Dimension(500,500));
        centerPanel.setBackground(Color.white);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                index[i][j] = new JTextField(1);
                index[i][j].setText("" + quiz[i][j]);
                index[i][j].setHorizontalAlignment(JTextField.CENTER);
                if (quiz[i][j] != ' ') {
                    index[i][j].setEditable(false);
                }
                centerPanel.add(index[i][j]);
            }
        }
        
        southPanel.add(submit);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SUDOKU");
        setResizable(false);
        setVisible(true);
    }
    
    //Action Listener for button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            int score = answerCheck();
            try {
                Submit submitButton = new Submit(userid, user.getText(), totalScore.getText(), score);
                setVisible(false);
            } catch (SQLException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //For answer checking and counting game score.
    public int answerCheck() {
        int score = -36; //  36 squares already filled in game.
        char[][] userAnswer = new char[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                userAnswer[i][j] = index[i][j].getText().charAt(0);
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(answer[i][j] == userAnswer[i][j]){
                    score++;
                }
            }
        }
        return score;
    }
}
