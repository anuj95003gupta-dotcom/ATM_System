
package bank.management.system;

import javax.swing.*;       //Window
import java.awt.*;       //image
import java.awt.event.*;  //for Actionlistener
import java.sql.*; 

public class Login extends JFrame implements ActionListener
{
    
    JButton login,signup,clear;  //Declare globallyy to acess out of constructor in case of Addaction fuction
    JTextField cardTextField;
    JPasswordField pinTextField; 
    Login()
    {   
        setTitle("ATM Karo!!!");
        setLayout(null);    //for set location of logo as we use custom layout thats why wew are using null
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
        JLabel label = new JLabel(Util.icon("icons/logo.jpg",100,100));
        label.setBounds(10,10,100,100);
        add(label);
        
        ////////////////////////////////////////////////////WElcome////////////////////////////////////////////////////////////
        JLabel text = new JLabel("Welcome to ATM.....");  //using obj text we are setting the sentence in it
        text.setFont(new Font("Osward", Font.BOLD, 38));
        text.setBounds(150,40,400,40);   //postion of text
        add(text);
        
        ////////////////////////////////////////////////CARD no////////////////////////////////////////////////////////////////
        JLabel Cardno = new JLabel("Card No. ");  //using obj text we are setting the sentence in it
        Cardno.setFont(new Font("Raleway", Font.BOLD, 28));
        Cardno.setBounds(120,150,150,30);   //postion of text
        add(Cardno);
        
        //Declare golbbally
        cardTextField  = new JTextField();
        cardTextField.setBounds(300,150,250,30);
        cardTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(cardTextField);
        
        ///////////////////////////////////////PIN/////////////////////////////////////////////////////////////////////////
        
        JLabel pin = new JLabel("Pin: ");  //using obj text we are setting the sentence in it
        pin.setFont(new Font("Raleway", Font.BOLD, 28));
        pin.setBounds(120,220,250,30);   //postion of text
        add(pin);
        
        //Declare golbbally
         pinTextField  = new JPasswordField();
        pinTextField.setBounds(300,220,250,30);
         pinTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(pinTextField);
        
        //Bottun   SIGN In
         login = new JButton("Sign in");
        login.setBounds(300,300,100,30 );
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);  
        login.addActionListener(this);
        add(login);
        
        
        //Bottun   CLEAR DATA
         clear = new JButton("Clear");
        clear.setBounds(450,300,100,30 );
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE); 
        clear.addActionListener(this);
        add(clear);
        
        //Bottun SIGN UP 
         signup = new JButton("Signup");
        signup.setBounds(375,350,100,30 );
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);  
        signup.addActionListener(this);
        add(signup);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        getContentPane().setBackground(Color.WHITE);    //Window related content BG color size of window ,l location, visiblity.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,480);
        setVisible(true);
        setLocation(350,200);
        
    }

    public void actionPerformed(ActionEvent ae)   //class actionevent OBj ae
    {
        if(ae.getSource() == clear )
        {
            cardTextField.setText("");
            pinTextField.setText("");
        }
        else  if(ae.getSource() == login )
        {
            String cardnumber = cardTextField.getText();
            String pinnumber = new String(pinTextField.getPassword());
            
            try (conn conn1 = new conn())
            {
                if(!conn1.isConnected()) return;
                PreparedStatement ps = conn1.c.prepareStatement("select * from login where cardnumber = ? and pin = ?");
                ps.setString(1, cardnumber);
                ps.setString(2, pinnumber);
                ResultSet rs = ps.executeQuery();    //Value return by conn1 saved in resultset which come under the sql library
                if(rs.next())
                {
                    setVisible(false);
                    dispose();
                    new Transactions(cardnumber);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Incorrect Card Number or pin");
                }
                    
                   
            }catch (Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
            }
        }
         else  if(ae.getSource() == signup )
        {
            setVisible(false);
            new Signup().setVisible(true);
        }
        
        
    }
    
    public static void main(String[] args) 
    {
       new Login();
    }
    
}

