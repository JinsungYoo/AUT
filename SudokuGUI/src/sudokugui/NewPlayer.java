
package sudokugui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * https://github.com/JinsungYoo/AUT/tree/master/SudokuGUI
 * 
 * This class for adding new player data on database.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 14.06.2020
 */
public class NewPlayer extends JFrame implements ActionListener{
    private int newPlayerID;
    private JPanel centerPanel, southPanel;
    private JLabel useridLabel, useridLabel2, userNameLabel;
    private JTextField userNameField;
    private JButton start, cancel;
    
    //For connecting database.
    private static Connection conn;
    private static final String USER_NAME = "pdc";//User name for access the database
    private static final String PASSWORD = "pdc";//Password for access the database
    private static final String URL = "jdbc:derby://localhost:1527/userDB;create=true"; //Database
    
    public NewPlayer() throws SQLException{
        try {
            setTitle("NEW PLAYER");

            //Connecting database and load the users table.
            conn=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            //get New user id
            ArrayList<Integer> temp = new ArrayList<Integer>();
            while(rs.next()){
                temp.add(rs.getInt("USERID"));
            }
            newPlayerID = temp.size() + 1;
            useridLabel = new JLabel("Your ID Number : ");
            useridLabel2 = new JLabel(Integer.toString(newPlayerID));
            userNameLabel = new JLabel("Name : ");
            userNameField = new JTextField(25);

            start = new JButton("Game Start");
            start.addActionListener(this);

            cancel = new JButton("Cancel");
            cancel.addActionListener(this);

            centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(2,2));
            centerPanel.add(useridLabel);
            centerPanel.add(useridLabel2);
            centerPanel.add(userNameLabel);
            centerPanel.add(userNameField);

            southPanel = new JPanel();
            southPanel.add(start);
            southPanel.add(cancel);

            add(centerPanel, BorderLayout.CENTER);
            add(southPanel, BorderLayout.SOUTH);

            setSize(300,120);
            setResizable(false);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            rs.close();
            st.close();
            conn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //Action Listener for button
    @Override
    public void actionPerformed(ActionEvent e) {
        try 
        {
            if (e.getSource() == start){
                String typedUserName = userNameField.getText();

                conn=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users VALUES(?,?,?)");
                pstmt.setInt(1,newPlayerID);
                pstmt.setString(2,typedUserName);
                pstmt.setInt(3,0);
                pstmt.executeUpdate();
                
                setVisible(false);
                Play play = new Play(newPlayerID, typedUserName, "0");
                
                pstmt.close();
                conn.close();
            }
            else if(e.getSource() == cancel){
                UserSelection play = new UserSelection();
                setVisible(false);
            }
        } catch (SQLException ex) {
                Logger.getLogger(NewPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
