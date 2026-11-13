
package bank.management.system;


import java.awt.*;
import javax.swing.*;  //Jframe is include in swing class
import java.util.*;   ///to import random funtion
import com.toedter.calendar.JDateChooser;   //To moddify calender 
import java.awt.event.*;
import java.sql.PreparedStatement;



public class Signup extends JFrame implements ActionListener
{
        long random;
        JTextField  nameTextField,fnameTextField,emailf,addressf,cityf,statef,pincodef;
        JDateChooser date1Chooser;
        JButton next;
        JRadioButton male,female,other1, other, married,unmarried;
        
       Signup()
       {
           setLayout(null); //By defalut./....           
           Random ran = new Random();
           random = 100000L + ran.nextInt(900000); 
           
           JLabel formno = new JLabel("Applicaion Form No. " + random );    //impoer util lib
           formno.setFont(new Font("Raleway", Font.BOLD, 38));
           formno.setBounds(140,20,600,40);      //Set layout should be null.....
           add(formno);
           
           JLabel personDetails = new JLabel("Page 1 : Personal Details ");    //impoer util lib
           personDetails.setFont(new Font("Raleway", Font.BOLD, 22));
           personDetails.setBounds(290,80,400,30);      //Set layout should be null.....
           add(personDetails);
           
           /////////////////////NAME//////////////////////////////////////////
           JLabel name = new JLabel("Name: ");    //impoer util lib
           name.setFont(new Font("Raleway", Font.BOLD, 22));
           name.setBounds(100,140,100,30);      //Set layout should be null.....
           add(name);
           
           nameTextField = new JTextField();
           nameTextField.setFont(new Font("Raleway", Font.BOLD, 14));
           nameTextField.setBounds(300,140,400,30);
           add(nameTextField);
           
           //////////////////////FATHERS NAMEE///////////////////////////////////
           JLabel fname = new JLabel("Father's Name: ");    //import util lib
           fname.setFont(new Font("Raleway", Font.BOLD, 22));
           fname.setBounds(100,180,200,30);      //Set layout should be null.....
           add(fname);
           
           fnameTextField = new JTextField();
           fnameTextField.setFont(new Font("Raleway", Font.BOLD, 14));
           fnameTextField.setBounds(300,180,400,30);
           add(fnameTextField);
           
           ///////////////////DOB////////////////////////////////////////////////
           JLabel dob = new JLabel("Date Of  Birth: ");    //impoer util lib
           dob.setFont(new Font("Raleway", Font.BOLD, 22));
           dob.setBounds(100,220,200,30);      //Set layout should be null.....
           add(dob  );
           
           date1Chooser = new JDateChooser();
           date1Chooser.setBounds(300,220,400,30);
           date1Chooser.setForeground(new Color(105,105,105));
           add(date1Chooser);
           
           /////////////////////Gender/////////////////////////////////////////
           JLabel gender = new JLabel("Gender: ");    //impoer util lib
           gender.setFont(new Font("Raleway", Font.BOLD, 22));
           gender.setBounds(100,260,200,30);      //Set layout should be null.....
           add(gender );
       
            male = new JRadioButton("Male");
           male.setBounds(300,260,100,30);
           male.setBackground( Color.WHITE);
           add(male);
           
            female = new JRadioButton("Female");
           female.setBounds(450,260,100,30);
           female.setBackground(Color.WHITE);
           add(female);
           
            other1 = new JRadioButton("Other");
           other1.setBounds(600,260,100,30);
           other1.setBackground( Color.WHITE);
           add(other1);
           
           ButtonGroup gendergroup = new ButtonGroup();
           gendergroup.add(male);
           gendergroup.add(female);
           gendergroup.add(other1);
           
           
           ///////////////EMAIL///////////////////
           JLabel email = new JLabel("E-Mail Address: ");    //impoer util lib
           email.setFont(new Font("Raleway", Font.BOLD, 22));
           email.setBounds(100,300,200,30);      //Set layout should be null.....
           add(email);
           
           emailf = new JTextField();
           emailf.setFont(new Font("Raleway", Font.BOLD, 14));
           emailf.setBounds(300,300,400,30);
           add(emailf);
           
           ////////////////MArtital Status//////////////////
           JLabel marital = new JLabel("Martial Status: ");    //impoer util lib
           marital.setFont(new Font("Raleway", Font.BOLD, 22));
           marital.setBounds(100,340,200,30);      //Set layout should be null.....
           add(marital);
           
            married = new JRadioButton("Married");
           married.setBounds(300,340,100,30);
           married.setBackground(Color.WHITE);
           add(married);
           
            unmarried = new JRadioButton("Un-Married");
           unmarried.setBounds(450,340,100,30);
           unmarried.setBackground(Color.WHITE);
           add(unmarried);
           
            other = new JRadioButton("Other");
           other.setBounds(600,340,100,30);
           other.setBackground( Color.WHITE);
           add(other);
           
           ButtonGroup maritalgroup = new ButtonGroup();
           maritalgroup.add(married);
           maritalgroup.add(unmarried);
           maritalgroup.add(other);
           
           
           /////////////ADDRESSS STATUS?//////////////////
           JLabel address = new JLabel("Address: ");    //impoer util lib
           address.setFont(new Font("Raleway", Font.BOLD, 22));
           address.setBounds(100,380,200,30);      //Set layout should be null.....
           add(address);
           
            addressf = new JTextField();
           addressf.setFont(new Font("Raleway", Font.BOLD, 14));
           addressf.setBounds(300,380,400,30);
           add(addressf);
           
           ////////////////////////////////////CITY/////////////////////
           JLabel city = new JLabel("City: ");    //impoer util lib
           city.setFont(new Font("Raleway", Font.BOLD, 22));
           city.setBounds(100,420,200,30);      //Set layout should be null.....
           add(city);
           
           cityf = new JTextField();
           cityf.setFont(new Font("Raleway", Font.BOLD, 14));
           cityf.setBounds(300,420,400,30);
           add(cityf);
           
           //////////////////////STATE//////////////////////////////
            JLabel state = new JLabel("State: ");    //impoer util lib
           state.setFont(new Font("Raleway", Font.BOLD, 22));
           state.setBounds(100,460,200,30);      //Set layout should be null.....
           add(state);
           
           statef = new JTextField();
           statef.setFont(new Font("Raleway", Font.BOLD, 14));
           statef.setBounds(300,460,400,30);
           add(statef);
           
           
           
           /////////////////////////////PINCODE//////////////////////
           JLabel pincode = new JLabel("Pincode: ");    //impoer util lib
           pincode.setFont(new Font("Raleway", Font.BOLD, 22));
           pincode.setBounds(100,500,200,30);      //Set layout should be null.....
           add(pincode);
           
           pincodef = new JTextField();
           pincodef.setFont(new Font("Raleway", Font.BOLD, 14));
           pincodef.setBounds(300,500,400,30);
           add(pincodef);
           
            
           
           /////////////////////NEXT BUTTON //////////////////////////////////
           
            next = new JButton("Next");
           next.setBackground(Color.BLACK);
           next.setForeground(Color.WHITE);
           next.setFont(new Font("Raleway", Font.BOLD, 14));
           next.setBounds(620,660,80,30);
           next.addActionListener(this);
           add(next);
           
           
           ////////////////////////////////Window related data////////////////////////////////////////
           getContentPane().setBackground(Color.WHITE);
           //Under JFrame class
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setSize(850,800);
           setLocation(350,10);
           setVisible(true);
       }
       
       
       public void actionPerformed(ActionEvent ae)
       {
        String formno = "" + random;   //long->string
        String name =nameTextField.getText();  //oppposite work done by settext to set values
        String fname  = fnameTextField.getText();
        String dob = ((JTextField) date1Chooser.getDateEditor().getUiComponent()).getText();  //GetUICOmp determine textfield to we have convbewrt it
        String gender = null;
        if(male.isSelected())
        {
            gender = "Male";
        }
        else if(female.isSelected())
        {
            gender = "Female";
        }
        else if(other1.isSelected())
        {
            gender = "Other";
        }
        
        String email = emailf.getText();
        String address = addressf.getText();
        String city = cityf.getText();
        String state = statef.getText();
        String pincode = "" + pincodef.getText();
           
        String marital = null;
        if(married.isSelected())
        {
            marital = "Married";
        }
        else if(unmarried.isSelected())
        {
            marital = "Un-Married";
        }
        else if(other.isSelected())
        {
            marital = "Other";
        }
       
       
       try
       {
           if(name.trim().equals(""))
           {
               JOptionPane.showMessageDialog(null, "Name is Required");
           }
           else if(dob.trim().equals(""))
           {
               JOptionPane.showMessageDialog(null, "Date of Birth is Required");
           }
           else if(gender == null)
           {
               JOptionPane.showMessageDialog(null, "Please select a gender");
           }
           else if(!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+"))
           {
             JOptionPane.showMessageDialog(null, "A valid EMAIL is Required");
           }
           else if(marital == null)
           {
               JOptionPane.showMessageDialog(null, "Please select marital status");
           }
           else if(!pincode.trim().equals("") && !pincode.matches("\\d{4,10}"))
           {
               JOptionPane.showMessageDialog(null, "Pincode must contain digits only");
           }
           else 
           {
               conn c = new conn();
               if(!c.isConnected()) return;
               PreparedStatement ps = c.c.prepareStatement("insert into signup values (?,?,?,?,?,?,?,?,?,?,?)");
               String[] v = {formno,name,fname,dob,gender,email,address,city,state,pincode,marital};
               for(int i = 0; i < v.length; i++) ps.setString(i+1, v[i]);
               ps.executeUpdate();
               c.close();
               
             
        nameTextField.setText("");  //oppposite work done by settext to set values
        fnameTextField.setText("");
        emailf.setText("");
        addressf.setText("");
        cityf.setText("");
        statef.setText("");
        pincodef.setText("");
        
        
        setVisible(false);
        new SignupTwo(formno).setVisible(true);
           
        }
       }
       catch(Exception e)
       {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Could not save: " + e.getMessage());
       }
       
    }
       
    public static void main(String[] args) 
    {
     new Signup(); 
    } 
}

