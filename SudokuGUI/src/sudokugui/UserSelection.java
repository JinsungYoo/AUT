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
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @version 14.06.2020
 */
public class UserSelection extends JFrame implements ActionListener{
    ArrayList<Integer> dbUser_id = new ArrayList<Integer>();
    ArrayList<String> dbUser_Name = new ArrayList<String>();
    ArrayList<Integer> dbUser_Score = new ArrayList<Integer>();
    private JPanel userSelect, northPanel, southPanel;
    private JLabel userLabel;
    private JComboBox userBox;
    private JButton start, newPlayer;
    private int[] scoreList;
    
    //For setting Background
    private ImageIcon icon;
    
    //For connecting database.
    private static Connection conn;
    private static final String USER_NAME = "pdc";//User name for access the database
    private static final String PASSWORD = "pdc";//Password for access the database
    private static final String URL = "jdbc:derby://localhost:1527/userDB;create=true"; //Database
    
    public UserSelection() throws SQLException{
        try {
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
            while(rs.next()){
                dbUser_id.add(rs.getInt("USERID"));
                dbUser_Name.add(rs.getString("USERNAME"));
                dbUser_Score.add(rs.getInt("USERSCORE"));
            }

            String[] userList = new String[dbUser_Name.size()]; 
            for(int i=0; i<dbUser_Name.size();i++){
                userList[i] = dbUser_Name.get(i);
            }

            userBox = new JComboBox(userList);

            start = new JButton("Game Start");
            start.addActionListener(this);

            newPlayer = new JButton("New Player");
            newPlayer.addActionListener(this);

            northPanel = new JPanel();
            northPanel.add(userLabel);
            northPanel.add(userBox);

            southPanel = new JPanel();
            southPanel.add(start);
            southPanel.add(newPlayer);

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
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //Action Listener for button
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == start){
                int temp_id = 0;
                int temp_score = 0;
                
                String selectedUserName = userBox.getSelectedItem().toString();
                for(int i=0; i<dbUser_Name.size(); i++){
                    if(selectedUserName.equals(dbUser_Name.get(i))){
                        temp_id = dbUser_id.get(i);
                        temp_score = dbUser_Score.get(i);
                    }
                }
                setVisible(false);
                Play play = new Play(temp_id, selectedUserName, Integer.toString(temp_score));

            }
            else if(e.getSource() == newPlayer){
                NewPlayer newplay = new NewPlayer();
                setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
