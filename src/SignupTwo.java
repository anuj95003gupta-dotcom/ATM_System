
package bank.management.system;


import java.awt.*;
import javax.swing.*;  //Jframe is include in swing class
import java.awt.event.*;
import java.sql.PreparedStatement;



public class SignupTwo extends JFrame implements ActionListener
{
        
        JTextField pan,adhaar;
        JButton next;
        JRadioButton yes,no,eyes,eno;
        JComboBox category,income,education,occupation,religion; 
        String formno;
        
       SignupTwo(String formno)
       {
           this.formno = formno;
           setLayout(null); //By defalut./....           
         
           setTitle("NEW ACCOUNT APLLICATION FORM - PAGE 2");
           
        
           
           JLabel additionalDetails = new JLabel("Page 2 : Additional Details ");    //impoer util lib
           additionalDetails.setFont(new Font("Raleway", Font.BOLD, 22));
           additionalDetails.setBounds(290,80,400,30);      //Set layout should be null.....
           add(additionalDetails);
           
           /////////////////////Religion//////////////////////////////////////////
           JLabel name = new JLabel("Religion: ");    //impoer util lib
           name.setFont(new Font("Raleway", Font.BOLD, 22));
           name.setBounds(100,140,150,30);      //Set layout should be null.....
           add(name);
           
           String valReligion[] = {"Hindu", "Muslim", "Sikh", "Other"};
           religion = new JComboBox(valReligion);
           religion.setBounds(300,140,400,30);
           religion.setBackground(Color.WHITE);
           add(religion);
           
           
           
           //////////////////////Category NAMEE///////////////////////////////////
           JLabel fname = new JLabel("Category: ");    //import util lib
           fname.setFont(new Font("Raleway", Font.BOLD, 22));
           fname.setBounds(100,190,200,30);      //Set layout should be null.....
           add(fname);
           
           String valCategory[] = {"General", "OBC", "SC","ST","Other"};
           category = new JComboBox(valCategory);
           category.setBounds(300,190,400,30);
           category.setBackground(Color.WHITE);
           add(category);
           
         
           
           ///////////////////Income////////////////////////////////////////////////
           JLabel dob = new JLabel("Income: ");    //impoer util lib
           dob.setFont(new Font("Raleway", Font.BOLD, 22));
           dob.setBounds(100,240,200,30);      //Set layout should be null.....
           add(dob  );
            
           String valIncome[] = {"NULL", "< 1,50,000", "< 2,50,000","< 5,00,000","Upto 10,00,000"};
           income = new JComboBox(valIncome);
           income.setBounds(300,240,400,30);
           income.setBackground(Color.WHITE);
           add(income);
           
//           date1Chooser = new JDateChooser();
//           date1Chooser.setBounds(300,220,400,30);
//           date1Chooser.setForeground(new Color(105,105,105));
//           add(date1Chooser);
//           
           /////////////////////Educational/////////////////////////////////////////
           JLabel gender = new JLabel("Educational ");    //impoer util lib
           gender.setFont(new Font("Raleway", Font.BOLD, 22));
           gender.setBounds(100,290,200,30);      //Set layout should be null.....
           add(gender );
           
           ///////////////Qualification///////////////////
           JLabel email = new JLabel("Qualification: ");    //impoer util lib
           email.setFont(new Font("Raleway", Font.BOLD, 22));
           email.setBounds(100,315,200,30);      //Set layout should be null.....
           add(email);
           
           String valEducation[] = {"Non-Graduation", "Graduate", "Post-Graduate","Doctrate","Others"};
           education = new JComboBox(valEducation);
           education.setBounds(300,310,400,30);
           education.setBackground(Color.WHITE);
           add(education);
           
           ////////////////MArtital Status//////////////////
           JLabel marital = new JLabel("Occupation: ");    //impoer util lib
           marital.setFont(new Font("Raleway", Font.BOLD, 22));
           marital.setBounds(100,365,200,30);      //Set layout should be null.....
           add(marital);
           
           String valOccupation[] = {"Salaried", "Self-Employed", "Bussiness","Student","Others"};
           occupation = new JComboBox(valOccupation);
           occupation.setBounds(300,365,400,30);
           occupation.setBackground(Color.WHITE);
           add(occupation);
           
           ////////////PAN number/////////////////
           JLabel address = new JLabel("PAN number:");    //import util lib
           address.setFont(new Font("Raleway", Font.BOLD, 22));
           address.setBounds(100,415,200,30);      //Set layout should be null.....
           add(address);
           
            pan = new JTextField();
           pan.setFont(new Font("Raleway", Font.BOLD, 14));
           pan.setBounds(300,415,405,30);
           add(pan);
           
           ////////////////////////////////////Adhaar number/////////////////////
           JLabel city = new JLabel("Addhar Number: ");    //impoer util lib
           city.setFont(new Font("Raleway", Font.BOLD, 22));
           city.setBounds(100,465,200,30);      //Set layout should be null.....
           add(city);
           
           adhaar = new JTextField();
           adhaar.setFont(new Font("Raleway", Font.BOLD, 14));
           adhaar.setBounds(300,465,400,30);
           add(adhaar);
           
           //////////////////////Senior Citizen//////////////////////////////
            JLabel state = new JLabel("Senior Citizen: ");    //impoer util lib
           state.setFont(new Font("Raleway", Font.BOLD, 22));
           state.setBounds(100,515,200,30);      //Set layout should be null.....
           add(state);

           yes = new JRadioButton("YES");
           yes.setBounds(300,515,100,30);
           yes.setBackground(Color.WHITE);
           add(yes);
           
            no = new JRadioButton("NO");
           no.setBounds(450,515,100,30);
           no.setBackground(Color.WHITE);
           add(no);
           
            
           ButtonGroup maritalgroup = new ButtonGroup();
           maritalgroup.add(yes);
           maritalgroup.add(no);
          
           
           
           
           /////////////////////////////Existing acoount//////////////////////
           JLabel pincode = new JLabel("Existing Account: ");    //impoer util lib
           pincode.setFont(new Font("Raleway", Font.BOLD, 22));
           pincode.setBounds(100,565,200,30);      //Set layout should be null.....
           add(pincode);
           
           eyes = new JRadioButton("YES");
           eyes.setBounds(300,565,100,30);
           eyes.setBackground(Color.WHITE);
           add(eyes);
           
            eno = new JRadioButton("NO");
           eno.setBounds(450,565,100,30);
           eno.setBackground(Color.WHITE);
           add(eno);
           
            
           ButtonGroup accountgroup = new ButtonGroup();
           accountgroup.add(eyes);
           accountgroup.add(eno);
           
            
           
           /////////////////////NEXT BUTTON //////////////////////////////////
           
           next = new JButton("Next");
           next.setBackground(Color.BLACK);
           next.setForeground(Color.WHITE);
           next.setFont(new Font("Raleway", Font.BOLD, 14));
           next.setBounds(620,616,80,30);
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
//        String formno = "" + random;   //long->string
        String sreligion = (String)religion.getSelectedItem();  
        String scategory  = (String)category.getSelectedItem(); 
        String sincome = (String)income.getSelectedItem();   //GetUICOmp determine textfield to we have convbewrt it
        String seducation = (String)education.getSelectedItem();
        String soccupation =(String)occupation.getSelectedItem();
        String seniorcitizen = null;
        if(yes.isSelected())
        {
            seniorcitizen = "Yes";
        }
        else if(no.isSelected())
        {
            seniorcitizen = "No";
        }
       
        
        String span = pan.getText();
        String sadhaar = adhaar.getText();
        
        String exisitingaccount = null;
        if(eyes.isSelected())
        {
            exisitingaccount = "Yes";
        }
        else if(eno.isSelected())
        {
            exisitingaccount = "No";
        }
      
       if(seniorcitizen == null || exisitingaccount == null)
       {
           JOptionPane.showMessageDialog(null, "Please answer Senior Citizen and Existing Account");
           return;
       }
       if(!span.equals("") && !span.toUpperCase().matches("[A-Z]{5}\\d{4}[A-Z]"))
       {
           JOptionPane.showMessageDialog(null, "PAN must look like ABCDE1234F");
           return;
       }
       if(!sadhaar.equals("") && !sadhaar.matches("\\d{12}"))
       {
           JOptionPane.showMessageDialog(null, "Aadhaar must be exactly 12 digits");
           return;
       }
       try
       {
               conn c = new conn();
               if(!c.isConnected()) return;
               PreparedStatement ps = c.c.prepareStatement("insert into signuptwo values (?,?,?,?,?,?,?,?,?,?)");
               String[] v = {formno,sreligion,scategory,sincome,seducation,soccupation,span,sadhaar,seniorcitizen,exisitingaccount};
               for(int i = 0; i < v.length; i++) ps.setString(i+1, v[i]);
               ps.executeUpdate();
               c.close();

           //Signup3 Object
           setVisible(false);
           new SignupThree(formno).setVisible(true);
        }
       
       catch(Exception e)
       {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Could not save: " + e.getMessage());
       }
       
    }
       
    public static void main(String[] args) 
    {
     new SignupTwo(""); 
    } 
}

