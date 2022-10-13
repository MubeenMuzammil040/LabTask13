import Conn.Conn;
//import JButtonGroup.JButtonGroup;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class Myframe extends JFrame implements ActionListener {

    JLabel label1, label2, label3, label4, label5, label6, label7, label8;
    JTextField t1, t2, t3, t4, t5, t6, t7;
    JRadioButton male, female;
    JRadioButton Matriculation, intermediate, Graduate, PostGraduate;
    JComboBox Country;
    JTextArea ta1;
    JCheckBox terms;
    // JButtonGroup gen;
    JButton jButton1, Print,database,clear;
    JLabel msg;
    private String gender;

    public Myframe() {
        super();

        setTitle("Registration form");
        setBounds(300, 100, 800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();   //RETURN THE CONTENT PANE
        c.setLayout(null);
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        c.setBackground(Color.yellow);
        label1=new JLabel("Name"); //label create
        label1.setBounds(20, 50, 150, 20);
        c.add(label1);

        t1 = new JTextField();  // label text box
        t1.setBounds(130, 50, 150, 20);
        c.add(t1);

        label2= new JLabel("Batch"); //label create
        label2.setBounds(20, 100, 100, 20);
        c.add(label2);

        t2 = new JTextField();  // label text box
        t2.setBounds(130, 100, 100, 20);
        c.add(t2);

        label3= new JLabel("Section"); //label create
        label3 .setBounds(20, 150, 100, 20);
        c.add(label3);

        t3 = new JTextField();
        t3.setBounds(130, 150, 100, 20);
        c.add(t3);

        label4=new JLabel("RollNo"); //label create
        label4.setBounds(20, 200, 100, 20);
        c.add(label4);

        t4 = new JTextField();  // label text box
        t4.setBounds(130, 200, 100, 20);
        c.add(t4);

        label5 =new JLabel("Gender");
        label5. setBounds(20, 250, 100, 20);
        c.add(label5);

        male = new JRadioButton("Male"); // create an unselected button
        female = new JRadioButton("Female");
        male.setBounds(150, 250, 80, 20);
        female.setBounds(300, 250, 80, 20);

        male.setSelected(true);
        c.add(male);
        c.add(female);

        ButtonGroup gen = new ButtonGroup();
        gen.add(male);
        gen.add(female);

        label6= new JLabel("Qualification"); //label create
        label6.setBounds(20, 300, 100, 20);
        c.add(label6);

        Matriculation = new JRadioButton("Matric");
        intermediate = new JRadioButton("intermediate");
        Graduate = new JRadioButton("Graduate");
        PostGraduate = new JRadioButton("post Graduate");
        Matriculation.setBounds(150, 300, 130, 20);
        intermediate.setBounds(300, 300, 130, 20);
        Graduate.setBounds(150, 350, 130, 20);
        PostGraduate.setBounds(300, 350, 130, 20);

        Matriculation.setSelected(true);
        intermediate.setSelected(true);
        c.add(Matriculation);
        c.add(intermediate);
        c.add(Graduate);
        c.add(PostGraduate);

        ButtonGroup qul = new ButtonGroup();
        qul.add(Matriculation);
        qul.add(intermediate);
        qul.add(Graduate);
        qul.add(PostGraduate);

        label7 =new JLabel("country");
        label7. setBounds(20, 400, 100, 20);
        c.add(label7);
        String Countries[] = {"pakistan", "china", "India", "England", "Afghanistan"};
        Country = new JComboBox(Countries);
        Country.setBounds(130, 400, 150, 20);
        c.add(Country);

        label8= new JLabel("Address");
        label8.setBounds(20, 450, 100, 20);
        c.add(label8);

        ta1 = new JTextArea();
        ta1.setBounds(130, 450, 200, 50);
        c.add(ta1);

        jButton1= new JButton("Save");
        jButton1 . setBounds(100, 550, 100, 20);
        c.add(jButton1);

        Print= new JButton("Print");
        Print.setBounds(250, 550, 100, 20);
        c.add(Print);

        database=new JButton("Database");
        database.setBounds(400, 550, 120, 20);
        database.addActionListener(this);
        c.add(database);

        clear= new JButton("Clear");
        clear. setBounds(550, 550, 100, 20);
        clear.addActionListener(this);
        c.add(clear);
        msg = new JLabel();
        msg.setBounds(20, 600, 250, 20);

        setVisible(true);

        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                saveData();
            }
        });

        Print.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                displayData();
            }
        });
    }
    public void saveData() {

        String gender;
        String qualification;
        if (Matriculation.isSelected()) qualification = "Matric";
        else if (intermediate.isSelected()) qualification = "Intermediate";
        else if (Graduate.isSelected()) qualification = "Graduate";
        else qualification = "Post Graduate";
        if (male.isSelected()) gender = "Male";
        else gender = "Female";
        String name = t1.getText();
        String rollNumber = t2.getText();
        String batch = t3.getText();
        String section = t4.getText();
        String country = (String) Country.getSelectedItem();
        String address = ta1.getText();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", name);
        jsonObject.put("RollNumber", rollNumber);
        jsonObject.put("Batch", batch);
        jsonObject.put("Section", section);
        jsonObject.put("Gender", gender);
        jsonObject.put("Qualification", qualification);
        jsonObject.put("Country", country);
        jsonObject.put("Address", address);
        try {
            FileWriter file = new FileWriter("D:/java.json");
            file.write(jsonObject.toJSONString());
            file.close();
            JOptionPane.showMessageDialog(null, "Data stored successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void displayData(){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("D:/java.json"));
            JSONObject object = (JSONObject) obj;
            String name = (String) object.get("Name");
            String rollNumber = (String) object.get("RollNumber");
            String batch = (String) object.get("Batch");
            String section = (String) object.get("Section");
            String gender = (String) object.get("Gender");
            String qualification = (String) object.get("Qualification");
            String country = (String) object.get("Country");
            String address = (String) object.get("Address");
            NewFrame ne= new NewFrame();
            JPanel panel =new JPanel();
            ne.remove(panel);
            Panel panel1 = new Panel();
            panel1.setLayout(null);
            panel1.setBackground(Color.RED);
            panel1.setSize(700, 700);
            JLabel l1, l2, l3, l4, l5, l6, l7, l8;
            l1 = new JLabel("Name : " + name);
            l2 = new JLabel("Roll #: " + rollNumber);
            l3 = new JLabel("Batch: " + batch);
            l4 = new JLabel("Section: " + section);
            l5 = new JLabel("Gender: " + gender);
            l6 = new JLabel("Qualification: " + qualification);
            l7 = new JLabel("Country: " + country);
            l8 = new JLabel("Address: " + address);
            l1.setBounds(10, 20, 200, 50);
            l2.setBounds(10, 70, 200, 50);
            l3.setBounds(10, 120, 200, 50);
            l4.setBounds(10, 170, 200, 50);
            l5.setBounds(10, 220, 200, 50);
            l6.setBounds(10, 270, 200, 50);
            l7.setBounds(10, 320, 200, 50);
            l8.setBounds(10, 370, 200, 50);
            panel1.add(l1);
            panel1.add(l2);
            panel1.add(l3);
            panel1.add(l4);
            panel1.add(l5);
            panel1.add(l6);
            panel1.add(l7);
            panel1.add(l8);
            ne.add(panel1);
            ne.setVisible(true);
            ne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==database){
            String Name = t1.getText();
            String Batch = t2.getText();
            String section = t3.getText();
            String RollNo=t4.getText();
            String Gender=null;
            if (male.isSelected()) {
                Gender = "Male";
            } else if (female.isSelected()) {
                Gender = "Female";
            }
            String qualification = null;
            if (Matriculation.isSelected()) qualification = "Matric";
            else if (intermediate.isSelected()) qualification = "Intermediate";
            else if (Graduate.isSelected()) qualification = "Graduate";
            String country = (String) Country.getSelectedItem();
            String address = ta1.getText();

            try {
                if (Name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Name is Required");
                } else {
                    Conn c = new Conn();
                    String query1 = "insert into store values('" + Name + "','" + RollNo + "','" + Batch + "','" + section + "','" + Gender + "','" + qualification + "','" + country + "', ' " +address+ "' )";
                    c.s.executeUpdate(query1);

                }
            } catch (Exception t) {
                System.out.println(t);
            }
        }else if(e.getSource()==clear) {
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from store");  //create obj of resultset for get the query
                while (rs.next()) {
                    System.out.println(rs.getString(1)+" "+rs.getString(2)+ " "+rs.getString(3)+ " "+rs.getString(4)+ " "+rs.getString(5)+ " "+rs.getString(6)+ " "+rs.getString(7)+ " "+rs.getString(8));  //console
                }
                //c.close();

            }catch(Exception a){
                System.out.println(a);
            }
        }
    }

}






class Registration {
    public static void main(String[] args) {
        Myframe frame = new Myframe();
    }
}

