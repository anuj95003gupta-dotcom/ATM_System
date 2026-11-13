
package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.util.*;

public class SignupThree extends JFrame implements ActionListener
{
        
    JRadioButton r1,r2,r3,r4;    
    JCheckBox c1,c2,c3,c4,c5,c6,c7;
    JButton submit,cancel;
    String formno;
    
       SignupThree(String formno)
       {
               this.formno = formno;
               setLayout(null);
           
               JLabel l1  = new JLabel("Page 3: Account Details");
               l1.setFont(new Font("Raleway", Font.BOLD, 22));
               l1.setBounds(280,40,400,40);
               add(l1);
               
               
               JLabel type  = new JLabel("Account Type: ");
               type.setFont(new Font("Raleway", Font.BOLD, 22));
               type.setBounds(100,130,200,40);
               add(type);
               
               r1 = new JRadioButton("Saving Account");
               r1.setFont(new Font("Raleway", Font.BOLD, 16));
               r1.setBackground(Color.WHITE);
               r1.setBounds(100,180,150,20);
               add(r1); 
               
               r2 = new JRadioButton("Fixed Deposit  Account");
               r2.setFont(new Font("Raleway", Font.BOLD, 16));
               r2.setBackground(Color.WHITE);
               r2.setBounds(350,180,300,20);
               add(r2); 
               
               r3 = new JRadioButton("Current Account");
               r3.setFont(new Font("Raleway", Font.BOLD, 16));
               r3.setBackground(Color.WHITE);
               r3.setBounds(100,220,150,20);
               add(r3); 
               
               r4 = new JRadioButton("Recurring Deposit Account");
               r4.setFont(new Font("Raleway", Font.BOLD, 16));
               r4.setBackground(Color.WHITE);
               r4.setBounds(350,220,300,20);
               add(r4); 
               
              
                ButtonGroup accountgroup = new ButtonGroup();
                accountgroup.add(r1);
                accountgroup.add(r2);
                accountgroup.add(r3);
                accountgroup.add(r4);
                
                /////////////////////////////Card Number///////////////////////
               JLabel card  = new JLabel("Card Number: ");
               card.setFont(new Font("Raleway", Font.BOLD, 22));
               card.setBounds(100,300,200,40);
               add(card);
               
               JLabel carddetail  = new JLabel("(Your 16 Digit Card Number)");
               carddetail.setFont(new Font("Raleway", Font.BOLD, 12));
               carddetail.setBounds(100,330,300,20);
               add(carddetail);
               
               JLabel number  = new JLabel(" XXXX-XXXX-XXXX-4184 ");
               number.setFont(new Font("Raleway", Font.BOLD, 22));
               number.setBounds(330,300,400,40);
               add(number);
               
               //////////////////////Pin Number///////////////////////////
               JLabel pin  = new JLabel(" Pin Number: ");
               pin.setFont(new Font("Raleway", Font.BOLD, 22));
               pin.setBounds(100,370,200,40);
               add(pin);
               
               JLabel pindetail  = new JLabel("(Your 4 Digit Pin Number)");
               pindetail.setFont(new Font("Raleway", Font.BOLD, 12));
               pindetail.setBounds(100,400,300,20);
               add(pindetail);
               
               JLabel pnumber  = new JLabel(" XXXX");
               pnumber.setFont(new Font("Raleway", Font.BOLD, 22));
               pnumber.setBounds(330,370,400,40);
               add(pnumber);
               
               ////////////////Serivecs Required////////////////////////////
               JLabel services  = new JLabel(" Services  Required: ");
               services.setFont(new Font("Raleway", Font.BOLD, 22));
               services.setBounds(100,450,300,40);
               add(services);
               
               c1= new JCheckBox("ATM Card");
               c1.setBackground(Color.WHITE);
               c1.setFont(new Font("Raleway", Font.BOLD,16));
               c1.setBounds(100,500,200,30);
               add(c1);
               
               
               c2= new JCheckBox("Internet Banking");
               c2.setBackground(Color.WHITE);
               c2.setFont(new Font("Raleway", Font.BOLD,16));
               c2.setBounds(350,500,200,30);
               add(c2);
               
               c3= new JCheckBox("Moblie Banking");
               c3.setBackground(Color.WHITE);
               c3.setFont(new Font("Raleway", Font.BOLD,16));
               c3.setBounds(100,550,200,30);
               add(c3);
               
               c4= new JCheckBox("EMAIL & SMS Alert");
               c4.setBackground(Color.WHITE);
               c4.setFont(new Font("Raleway", Font.BOLD,16));
               c4.setBounds(350,550,200,30);
               add(c4);
               
               c5= new JCheckBox("Cheque Book");
               c5.setBackground(Color.WHITE);
               c5.setFont(new Font("Raleway", Font.BOLD,16));
               c5.setBounds(100,600,200,30);
               add(c5);
               
               c6= new JCheckBox("E-Statement");
               c6.setBackground(Color.WHITE);
               c6.setFont(new Font("Raleway", Font.BOLD,16));
               c6.setBounds(350,600,200,30);
               add(c6);
               
               c7= new JCheckBox("I hereby declares that the above entered details are correct to the best of my Knowledge ");
               c7.setBackground(Color.WHITE);
               c7.setFont(new Font("Raleway", Font.BOLD,12));
               c7.setBounds(100,680,600,30);
               add(c7);
               
               ////////////////////////Button///////////////////////////////////////////
               submit = new JButton("Submit");
               submit.setBackground(Color.BLACK);
               submit.setForeground(Color.WHITE);
               submit.setFont(new Font("Raleway", Font.BOLD,14));
               submit.setBounds(250,720,100,30);
               submit.addActionListener(this); 
               add(submit);
               
       
                cancel = new JButton("Cancel");
               cancel.setBackground(Color.BLACK);
               cancel.setForeground(Color.WHITE);
               cancel.setFont(new Font("Raleway", Font.BOLD,14));
               cancel.setBounds(420,720,100,30);
               cancel.addActionListener(this); 
               add(cancel);
                       
                       
               //////////////////////Frame Formation////////////////////
               setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               setSize(850, 820);
               setLocation(350,0);
               getContentPane().setBackground(Color.WHITE);
               setVisible(true);
               
       }
       
       
       public void actionPerformed(ActionEvent ae)
       {
           if(ae.getSource() == submit)
           {
               
               ////Radio Buttons
               String accountType = null;
               if(r1.isSelected())
               {
                   accountType = "Saving Account";
               }
               else if(r2.isSelected())
               {
                    accountType = "Fixed Deposit Account";
               }
               else if(r3.isSelected())
               {
                    accountType = "Current Account";
               }
               else if(r4.isSelected())
               {
                    accountType = "Recurring Deposit Account";
               }     
               
               
               ////Card and pin number
               Random random = new Random();
               String cardnumber = "" + (5040936000000000L + (long)(random.nextDouble() * 90000000L));
               
               String pinnumber  = "" + (1000 + random.nextInt(9000));
               
               //Check box for facities
                
               java.util.List<String> fl = new java.util.ArrayList<>();
               if(c1.isSelected()) fl.add("ATM Card");
               if(c2.isSelected()) fl.add("Internet Banking");
               if(c3.isSelected()) fl.add("Mobile Banking");
               if(c4.isSelected()) fl.add("Email & SMS Alert");
               if(c5.isSelected()) fl.add("Cheque Book");
               if(c6.isSelected()) fl.add("E-Statement");
               String facility = String.join(", ", fl);
               
               try
               {
                   if(accountType == null)
                   {
                       JOptionPane.showMessageDialog(null,"Account type is Required");
                   }
                   else
                   {
                       conn conn1 = new conn();
                       if(!conn1.isConnected()) return;
                       PreparedStatement p1 = conn1.c.prepareStatement("insert into signupthree values (?,?,?,?,?)");
                       String[] v1 = {formno,accountType,cardnumber,pinnumber,facility};
                       for(int i = 0; i < v1.length; i++) p1.setString(i+1, v1[i]);
                       p1.executeUpdate();
                       PreparedStatement p2 = conn1.c.prepareStatement("insert into login values (?,?,?)");
                       p2.setString(1, formno); p2.setString(2, cardnumber); p2.setString(3, pinnumber);
                       p2.executeUpdate();
                       conn1.close();
                       JOptionPane.showMessageDialog(null, "Card Number: " + cardnumber + "\n Pin: " + pinnumber);
                        setVisible(false);
                        dispose();
                        new Login();   // back to the login screen so the new card can be used
                       
                   }
                   
               }
               catch(Exception e)
               {
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "Could not save: " + e.getMessage());
               }
               
               
           }
           
           else if(ae.getSource() == cancel)
           {
               setVisible(false);
               dispose();
               new Login();
           }
          
//          
        }
       
       
    public static void main(String[] args) 
    {
     new SignupThree(""); 
    } 

}

