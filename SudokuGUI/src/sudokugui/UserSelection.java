package sudokugui;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * COMP603 Program Design and Construction & Software Construction 2020 S1
 * https://github.com/JinsungYoo/AUT/tree/master/SudokuGUI
 * 
 * This class for Selection Player. 
 * Player Date come from users table on database.
 * 
 * @author Jinsung Yoo
 * @StudentID 18037792
 * @version 12.06.2020
 */
public class UserSelection extends JFrame implements ActionListener{
    private JPanel userSelect, northPanel, southPanel;
    private JLabel userLabel;
    private JComboBox userBox;
    private JButton start;
    private int[] scoreList;
    
    //For setting Background
    private ImageIcon icon;
    
    //For connecting database.
    private static Connection conn;
    private static final String USER_NAME = "pdc";//User name for access the database
    private static final String PASSWORD = "pdc";//Password for access the database
    private static final String URL = "jdbc:derby://localhost:1527/userDB;create=true"; //Database
    
    public UserSelection() throws SQLException{
        icon = new ImageIcon("AUTLogo.png"); //Load background file.
        setTitle("SUDOKU");
        
        //setting background
        userSelect = new JPanel(){
        public void paintComponent(Graphics g){
            g.drawImage(icon.getImage(), 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
            }
        };
        
        
        setContentPane(userSelect);
        
        userLabel = new JLabel("PLAYER : ");
        
        //Connecting database and load the users table.
        conn=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users");
        
        //get the table data user name and user score from database.
        ArrayList<String> temp_user = new ArrayList<String>();
        ArrayList<Integer> temp_score = new ArrayList<Integer>();
        while(rs.next()){
            temp_user.add(rs.getString("USERNAME"));
            temp_score.add(rs.getInt("USERSCORE"));
        }
        
        String[] userList = new String[temp_user.size()]; 
        scoreList = new int[temp_user.size()];
        for(int i=0; i<temp_user.size();i++){
            userList[i] = temp_user.get(i);
            scoreList[i] = temp_score.get(i);
        }
        
        userBox = new JComboBox(userList);
        
        start = new JButton("Game Start");
        start.addActionListener(this);
        
        northPanel = new JPanel();
        northPanel.add(userLabel);
        northPanel.add(userBox);
        
        southPanel = new JPanel();
        southPanel.add(start);
        
        userSelect.setLayout(new BorderLayout());
        userSelect.add(northPanel, BorderLayout.NORTH);
        userSelect.add(southPanel, BorderLayout.SOUTH);
        setSize(240,280);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        rs.close();
        st.close();
        conn.close();
    }
    
    //Action Listener for button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start){
            String selectedUserName = userBox.getSelectedItem().toString();
            int temp = userBox.getSelectedIndex();
            String selectedUserScore = Integer.toString(scoreList[temp]);
            int userid = (temp + 1);
            setVisible(false);
            Play play = new Play(userid, selectedUserName, selectedUserScore);
            
        }
    }
}
