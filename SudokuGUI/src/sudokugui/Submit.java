package sudokugui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * https://github.com/JinsungYoo/AUT/tree/master/SudokuGUI
 * 
 * When click the submit user's answer in the game, this class is executing.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 17.06.2020
 */
public class Submit extends JFrame implements ActionListener{
    private int userid, totalScore;
    private String user, userScore;
    private JPanel result;
    private JLabel resultLabel;
    private JButton playAgain, quitGame;
    
    //For connecting database.
    private static Connection conn;
    private static final String USER_NAME = "pdc";//User name for access the database
    private static final String PASSWORD = "pdc";//Password for access the database
    private static final String URL = "jdbc:derby:userDB;create=true"; //Database
    
    public Submit(int userid, String user, String userScore, int score) throws SQLException{
        try {
            setTitle("THANK YOU FOR PLAYING!");

            this.userid = userid;
            this.user = user;
            this.userScore = userScore;

            result = new JPanel();
            setContentPane(result);

            //Calculating total user score.
            totalScore = (Integer.parseInt(userScore) + score);
            resultLabel = new JLabel(user + ", Your Score is " + score + " and Your Total Score is " + totalScore);

            //After finish the game, check the game score and then update user total score on database.
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET userscore=?  WHERE userid=? ");
            pstmt.setInt(2, userid);
            pstmt.setInt(1, totalScore);
            pstmt.executeUpdate();
            
            playAgain = new JButton("Play Again");
            playAgain.addActionListener(this);
            quitGame = new JButton("Quit Game");
            quitGame.addActionListener(this);

            result.add(resultLabel);
            result.add(playAgain);
            result.add(quitGame);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(350,100);
            setResizable(false);
            setVisible(true);

            pstmt.close();
            conn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //ActionListener for Button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitGame){
            System.exit(0);
        }
        else{
            Play play = new Play(userid, user, Integer.toString(totalScore));
            setVisible(false);
        }
    }
}
