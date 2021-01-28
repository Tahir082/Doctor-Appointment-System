
package doctor_appointment_system;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;


interface User{
    int sign_in(String email, String password);
    void appoint(String str1, String str2);
    int cancel_appoint(String appId);
    
}

class Doctor implements User{
    private String name;
    private String email;
    private String password;
    private String speciality;
    private String experience;
    private String available_hrs;
    

    public Doctor() {
        
    }
   
    public int sign_up(String name, String email, String password, String speciality, String experience, String available_hrs) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.speciality = speciality;
            this.experience= experience;
            this.available_hrs = available_hrs;
            
            File drFile = new File("doctors.txt");
            
            try(Writer output = new BufferedWriter(new FileWriter(drFile, true))) {
                Scanner in=new Scanner(drFile);
                while(in.hasNextLine()){
                    String row= in.nextLine();
                    String attr[]= row.split("[,]");
                    int len=attr.length;
                    if(this.email.equals(attr[1])){
                        return 0;
                    }  
                }
                output.append(this.name+","+this.email+","+this.password+","+this.speciality+","+this.experience+","+this.available_hrs+"\n");
                return 1;
            } 
            catch (IOException ex) {
                 Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                 return 0;
            }
        } 

    @Override
    public int sign_in(String email, String password) {
        File drFile = new File("doctors.txt");
            
            try (Writer output = new BufferedWriter(new FileWriter(drFile, true))) {
                Scanner in=new Scanner(drFile);
                while(in.hasNextLine()){
                    String row= in.nextLine();
                    String attr[]= row.split("[,]");
                    int len=attr.length;
                    if(email.equals(attr[1])&& password.equals(attr[2])){
                        Doctor_appointment_system.session_email=email;
                        return 1;
                    }
                    
                }
                return 0;
                
            } 
            catch (IOException ex) {
                 Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                 return 0;
            }
    }

    @Override
    public void appoint(String appointID, String dr_email) {
        
        JFrame apf= new JFrame("Make an Appointment");
            JLabel lb1= new JLabel("Appoinment Form");
            apf.setSize(720, 600);
            lb1.setBounds(50,30, 700, 50);
            lb1.setFont(new Font("Algerian", Font.BOLD, 35));
  
            JLabel lb2= new JLabel("Enter Time: ");
            lb2.setBounds(50,100, 150,50);
            JTextField tf1= new JTextField();
            tf1.setBounds(160, 100, 200, 50);
            
            JLabel lb3= new JLabel("Enter Date: ");
            lb3.setBounds(50,150, 150,50);
            JTextField tf2= new JTextField();
            tf2.setBounds(160,150, 150,50);
             
            JButton submit= new JButton("Submit");
            submit.setBounds(50, 230, 80, 50);
            apf.add(lb1);
            apf.add(lb2);
            apf.add(tf1);
            apf.add(lb3);
            apf.add(tf2);
            apf.add(submit);
            
            apf.setLayout(null);
            apf.setVisible(true);
            
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    File apFile= new File("appoints.txt");
                    String filepath=apFile.getAbsolutePath();
                    File tempFile=new File("temp.txt");
                    
                    try{
                        FileWriter fw= new FileWriter(tempFile,true);
                        BufferedWriter bw= new BufferedWriter(fw);
                        PrintWriter pw= new PrintWriter(bw);
                        Scanner ap_in= new Scanner(apFile);
                        String app_id=null;
                        String dr_name = null;
                        String dr_email = null;
                        String dr_speciality = null;
                        String pt_name = null;
                        String pt_email=null;
                        String pt_prob= null;
                        String pt_contact= null;
                        String app_time=tf1.getText();
                        String app_date= tf2.getText();
                        String app_req="Appointment Accepted by Doctor";
                        while(ap_in.hasNextLine()){
                            String line= ap_in.nextLine();
                            String att[]= line.split("[,]");
                            if(appointID.equals(att[0])){
                                app_id=att[0];
                                dr_name=att[1];
                                dr_email=att[2];
                                dr_speciality=att[3];
                                pt_name=att[4];
                                pt_email=att[5];
                                pt_prob=att[6];
                                pt_contact=att[7];
                            }
                            
                            else{
                                pw.println(line);
                            }
                        }
                        String update=appointID+","+dr_name+","+dr_email+","+dr_speciality+","+pt_name+","+pt_email+","+pt_prob+","+pt_contact+","+app_time+","+app_date+","+app_req;
                        pw.println(update);
                        PrintWriter empAp= new PrintWriter(apFile);
                        empAp.write("");
                        pw.flush();
                        pw.close();
                        empAp.flush();
                        empAp.close();
                        FileWriter fw2= new FileWriter(apFile,true);
                        BufferedWriter bw2= new BufferedWriter(fw2);
                        PrintWriter pw3= new PrintWriter(bw2);
                        Scanner in2= new Scanner(tempFile);
                        while(in2.hasNextLine()){
                            String line=in2.nextLine();
                            pw3.println(line);
                        }
                        pw3.flush();
                        pw3.close();
                        PrintWriter emptyTemp= new PrintWriter(tempFile);
                        emptyTemp.write("");
                        emptyTemp.flush();
                        emptyTemp.close();
                        tempFile.delete();
                        JFrame frame=new JFrame();
                        JOptionPane.showMessageDialog(frame, "Appointment Made!"); 
                        apf.setVisible(false);
                        
                        
                    }
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (IOException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    }

    @Override
    public int cancel_appoint(String appointID) {
        File apFile= new File("appoints.txt");
                    String filepath=apFile.getAbsolutePath();
                    File tempFile=new File("temp.txt");
                    
                    try{
                        FileWriter fw= new FileWriter(tempFile,true);
                        BufferedWriter bw= new BufferedWriter(fw);
                        PrintWriter pw= new PrintWriter(bw);
                        Scanner ap_in= new Scanner(apFile);
                        String app_req="Appointment Accepted by Doctor";
                        while(ap_in.hasNextLine()){
                            String line= ap_in.nextLine();
                            String att[]= line.split("[,]");
                            if(!appointID.equals(att[0])){
                                pw.println(line);
                            }
                        }
                        PrintWriter empAp= new PrintWriter(apFile);
                        empAp.write("");
                        pw.flush();
                        pw.close();
                        empAp.flush();
                        empAp.close();
                        FileWriter fw2= new FileWriter(apFile,true);
                        BufferedWriter bw2= new BufferedWriter(fw2);
                        PrintWriter pw3= new PrintWriter(bw2);
                        Scanner in2= new Scanner(tempFile);
                        while(in2.hasNextLine()){
                            String line=in2.nextLine();
                            pw3.println(line);
                        }
                        pw3.flush();
                        pw3.close();
                        PrintWriter emptyTemp= new PrintWriter(tempFile);
                        emptyTemp.write("");
                        emptyTemp.flush();
                        emptyTemp.close();
                        tempFile.delete();
                        return 1;
                        
                    }
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (IOException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return 0;
                
    }
    
    class profileFrame extends JFrame{
        profileFrame() throws FileNotFoundException{
            try {
                File drFile = new File("doctors.txt");
                Scanner in=new Scanner(drFile);
                String name = null;
                String email=null;
                while(in.hasNextLine()){
                    String row= in.nextLine();
                    String attr[]= row.split("[,]");
                    int len=attr.length;
                    if(Doctor_appointment_system.session_email.equals(attr[1])){
                        name=attr[0];
                        email=attr[1];
                    }   
                }
                JFrame pf= new JFrame("Doctor Profile");
                JLabel pl1= new JLabel("Welcome, "+name);
                pl1.setBounds(50 , 35 , 900, 35);
                pl1.setFont(new Font("Algerian", Font.BOLD,35));
                
                JButton signout= new JButton("Sign Out");
                signout.setBounds(1000, 38, 100 , 35);
                
                JButton bt1 =new JButton(new ImageIcon("user_bt3.jpg"));
                bt1.setBounds(170, 150, 832, 226);
                pf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("doctor_profile.jpg")))));
                
                pf.add(pl1);
                pf.add(bt1);
                pf.add(signout);
                
                bt1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            JFrame spf= new JFrame("My Appointments");
                            File apFile= new File("appoints.txt");
                            Scanner ap_in= new Scanner(apFile);
                            
                            JLabel title= new JLabel("My Appointments");
                            title.setBounds(50, 50, 700, 40);
                            title.setFont(new Font("Algerian", Font.BOLD,35));
                            JButton signout= new JButton("Sign Out");
                            signout.setBounds(1000, 40, 100 , 35);
                            spf.add(title);
                            spf.add(signout);
                            
                            JScrollPane scrollPane = new JScrollPane();
                            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPane.setBounds(50, 100, 700, 550);
                            
                            spf.add(scrollPane);
                            
                            JPanel p = new JPanel();
                            scrollPane.setViewportView(p);
                            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                            spf.setLayout(null);
                            spf.pack();
                            
                            int x=50, y=150, w=1000, h=15;
                            while(ap_in.hasNextLine()){
                                String row= ap_in.nextLine();
                                String attr[]= row.split("[,]");
                                if(Doctor_appointment_system.session_email.equals(attr[2])){
                                    JLabel l1= new JLabel("Patient Name:  "+attr[4]);
                                    l1.setSize(w,h);
                                    JLabel l2= new JLabel("Patient Contact:  "+attr[7]);
                                    l2.setSize(w,200);
                                    JLabel l3= new JLabel("Patient Problem:  "+attr[6]);
                                    l3.setSize(w,h);
                                    JLabel l4= new JLabel("Given Time:  "+attr[8]);
                                    l4.setSize(w,h);
                                    JLabel l5= new JLabel("Given Date:  "+attr[9]);
                                    l5.setSize(w,h);
                                    JLabel l6= new JLabel("Appointment:  "+attr[10]);
                                    l6.setSize(w,h);
                                    JButton accept = new JButton("Accept this appointment");
                                    int bw=300, bh=35;
                                    JButton Edit = new JButton("Edit this appointment");
                                    int bw3=300, bh3=35;
                                    JButton delete = new JButton("Delete this appointment");
                                    int bw2=300, bh2=35;
                                    
                                    accept.setSize(bw, bh);
                                    delete.setSize(bw2,bh2);
                                    
                                    
                                    p.add(l1);
                                    p.add(l2);
                                    p.add(l3);
                                    p.add(l4);
                                    p.add(l5);
                                    p.add(l6);
                                    
                                    if(attr[10].equals("Appointment Cancelled By User")){
                                        p.add(delete);
                                    }
                                    else if(attr[10].equals("Appointment Accepted by Doctor")){
                                        p.add(Edit);
                                    }
                                    else{
                                        p.add(accept);
                                    }
                                    p.add(Box.createVerticalStrut(40));
                                    
                                    accept.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent ae){
                                            String appId=attr[0];
                                            Doctor dt= new Doctor();
                                            dt.appoint(appId,Doctor_appointment_system.session_email);
                                        }
                                    });
                                    Edit.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent ae){
                                            String appId=attr[0];
                                            Doctor dt= new Doctor();
                                            dt.appoint(appId,Doctor_appointment_system.session_email);
                                        }
                                    });
                                    delete.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent ae){
                                            String appId=attr[0];
                                            Doctor dt= new Doctor();
                                            int cancel=dt.cancel_appoint(appId);
                                            if(cancel==1){
                                                JFrame frame=new JFrame();
                                                JOptionPane.showMessageDialog(frame, "Appointment Deletion Successful"); 
                                            }
                                            else{
                                                JFrame frame=new JFrame();
                                                JOptionPane.showMessageDialog(frame, "Something Went Wrong! Try Again...");
                                            }
                                        }
                                    });
                                    
                                }
                                
                                
                            }
                            signout.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent ae){
                                    try {
                                        pf.setVisible(false);
                                        spf.setVisible(false);
                                        Doctor_appointment_system.session_email=null;
                                        MainFrames mf=new MainFrames();
                                    }
                                    catch (IOException ex) {
                                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            
                            
                            spf.setSize(1280, 720);
                            spf.setVisible(true);
                            
                            
                        }
                        catch (FileNotFoundException ex) {
                            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                
                
                signout.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        try {
                            pf.setVisible(false);
                            Doctor_appointment_system.session_email=null;
                            MainFrames mf=new MainFrames();
                        } catch (IOException ex) {
                            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                
                pf.setSize(1280,720);
                pf.setLayout(null);
                pf.setVisible(true);
                pf.setDefaultCloseOperation(EXIT_ON_CLOSE);
            } catch (IOException ex) {
                Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
}

class Patient implements User{
    private String name;
    private String email;
    private String password;
    private String contact;
    public static int ret_value=0;

    public Patient() {
        
    }

    public int sign_up(String name, String email, String password, String contact) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        File ptFile = new File("patient.txt");
            
            try (Writer output = new BufferedWriter(new FileWriter(ptFile, true))) {
                Scanner in=new Scanner(ptFile);
                while(in.hasNextLine()){
                    String row= in.nextLine();
                    String attr[]= row.split("[,]");
                    int len=attr.length;
                    if(this.email.equals(attr[1])){
                        return 0;
                    }
                    
                }
                output.append(this.name+","+this.email+","+this.password+","+this.contact+"\n");
                Doctor_appointment_system.session_email=email;
                return 1;
            } 
            catch (IOException ex) {
                 Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                 return 0;
            }
    }

    @Override
    public int sign_in(String email, String password) {
       File ptFile = new File("patient.txt");
            
            try (Writer output = new BufferedWriter(new FileWriter(ptFile, true))) {
                Scanner in=new Scanner(ptFile);
                int retflag=0;
                while(in.hasNextLine()){
                    String row= in.nextLine();
                    String attr[]= row.split("[,]");
                    int len=attr.length;
                    if(email.equals(attr[1])&& password.equals(attr[2])){
                        Doctor_appointment_system.session_email=email;
                        retflag= 1;
                    }
                    
                }
                return retflag;
                
            } 
            catch (IOException ex) {
                 Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                 return 0;
            }
    }

    @Override
    public void appoint(String doc_email, String pat_email) {
        
            JFrame apf= new JFrame("Make an Appointment");
            JLabel lb1= new JLabel("Appoinment Form");
            apf.setSize(720, 600);
            lb1.setBounds(50,50, 700, 50);
            lb1.setFont(new Font("Algerian", Font.BOLD, 35));
            
            JTextArea ta;
            JLabel problem= new JLabel("Specify your problem: ");
            problem.setBounds(50,150,200,35);
            JLabel contact= new JLabel("Your Contact Number: ");
            contact.setBounds(50, 370, 200, 35);
            JTextField tf= new JTextField();
            tf.setBounds(260, 370, 200, 35);
            problem.setBounds(50,150,200,35);
            problem.setFont(new Font("Arial", Font.BOLD, 16));
            ta= new JTextArea();
            ta.setBounds(260, 150, 350, 200);
            
            JButton submit= new JButton("Submit");
            submit.setBounds(260, 410, 80, 50);
            apf.add(contact);
            apf.add(tf);
            apf.add(submit);
            apf.add(lb1);
            apf.add(problem);
            apf.add(ta);
            apf.setLayout(null);
            apf.setVisible(true);
            
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        File drFile = new File("doctors.txt");
                        File ptFile= new File("patient.txt");
                        File apFile= new File("appoints.txt");
                        Scanner dr_in= new Scanner(drFile);
                        Scanner pt_in= new Scanner(ptFile);
                        double appId=Math.random();
                        String dr_name = null;
                        String dr_email = null;
                        String dr_speciality = null;
                        String pt_name = null;
                        String pt_email=null;
                        String pt_prob= ta.getText();
                        String pt_contact= tf.getText();
                        String app_time=null;
                        String app_date= null;
                        String app_req="Waiting for Acception";
                        System.out.println(doc_email);
                        System.out.println(pat_email);
                        while(dr_in.hasNextLine()){
                            String dr_rows= dr_in.nextLine();
                            String dr_att[]= dr_rows.split("[,]");
                            if(doc_email.equals(dr_att[1])){
                                dr_name= dr_att[0];
                                dr_email = dr_att[1];
                                dr_speciality= dr_att[3];
                                Patient.ret_value=1;
                            }
                        }
                        while(pt_in.hasNextLine()){
                            String pt_rows= pt_in.nextLine();
                            String pt_att[]= pt_rows.split("[,]");
                            if(pat_email.equals(pt_att[1])){
                                pt_name= pt_att[0];
                                pt_email= pt_att[1];
                            }
                        }
                        try (Writer ap_out = new BufferedWriter(new FileWriter(apFile, true))) {
                                Scanner in = new Scanner(apFile);
                                ap_out.append((appId+1)+","+dr_name+","+dr_email+","+dr_speciality+","+pt_name+","+pt_email+","+pt_prob+","+pt_contact+","+app_time+","+app_date+","+app_req+"\n");
                                JFrame frame=new JFrame();
                                JOptionPane.showMessageDialog(frame, "Appointment Request Successful!"); 
                                
                        }                       
                        catch (IOException ex) {
                                    Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                       }
                        apf.setVisible(false);
                    }
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
      
    }

    @Override
    public int cancel_appoint(String appointID) {
        File apFile= new File("appoints.txt");
                    String filepath=apFile.getAbsolutePath();
                    File tempFile=new File("temp.txt");
                    
                    try{
                        FileWriter fw= new FileWriter(tempFile,true);
                        BufferedWriter bw= new BufferedWriter(fw);
                        PrintWriter pw= new PrintWriter(bw);
                        Scanner ap_in= new Scanner(apFile);
                        String app_id=null;
                        String dr_name = null;
                        String dr_email = null;
                        String dr_speciality = null;
                        String pt_name = null;
                        String pt_email=null;
                        String pt_prob= null;
                        String pt_contact= null;
                        String app_time=null;
                        String app_date= null;
                        String app_req="Appointment Cancelled By User";
                        while(ap_in.hasNextLine()){
                            String line= ap_in.nextLine();
                            String att[]= line.split("[,]");
                            if(appointID.equals(att[0])){
                                app_id=att[0];
                                dr_name=att[1];
                                dr_email=att[2];
                                dr_speciality=att[3];
                                pt_name=att[4];
                                pt_email=att[5];
                                pt_prob=att[6];
                                pt_contact=att[7];
                            }
                            
                            else{
                                pw.println(line);
                            }
                        }
                        String update=appointID+","+dr_name+","+dr_email+","+dr_speciality+","+pt_name+","+pt_email+","+pt_prob+","+pt_contact+","+app_time+","+app_date+","+app_req;
                        pw.println(update);
                        PrintWriter empAp= new PrintWriter(apFile);
                        empAp.write("");
                        pw.flush();
                        pw.close();
                        empAp.flush();
                        empAp.close();
                        FileWriter fw2= new FileWriter(apFile,true);
                        BufferedWriter bw2= new BufferedWriter(fw2);
                        PrintWriter pw3= new PrintWriter(bw2);
                        Scanner in2= new Scanner(tempFile);
                        while(in2.hasNextLine()){
                            String line=in2.nextLine();
                            pw3.println(line);
                        }
                        pw3.flush();
                        pw3.close();
                        PrintWriter emptyTemp= new PrintWriter(tempFile);
                        emptyTemp.write("");
                        emptyTemp.flush();
                        emptyTemp.close();
                        tempFile.delete();
                        return 1;
                        
                        
                    }
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (IOException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
        return 0;
    }
    
    class profileFrame extends JFrame{
        profileFrame() throws FileNotFoundException{
            try {
                File ptFile = new File("patient.txt");
                Scanner in=new Scanner(ptFile);
                String name = null;
                String email=null;
                while(in.hasNextLine()){
                    String row= in.nextLine();
                    String attr[]= row.split("[,]");
                    int len=attr.length;
                    email=Doctor_appointment_system.session_email;
                    if(email.equals(attr[1])){
                        name=attr[0];
                    }   
                }
                JFrame pf= new JFrame("User Profile");
                JLabel pl1= new JLabel("Welcome, "+name);
                pl1.setBounds(50 , 35 , 900, 35);
                pl1.setFont(new Font("Algerian", Font.BOLD,35));
                
                JButton signout= new JButton("Sign Out");
                signout.setBounds(1000, 38, 100 , 35);
                
                JButton bt1 =new JButton(new ImageIcon("user_bt.jpg"));
                bt1.setBounds(170, 150, 832, 226);
                JButton bt2 =new JButton(new ImageIcon("user_bt2.jpg"));
                bt2.setBounds(170, 376, 832, 226);
                
                pf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("user_profile.jpg")))));
                
                pf.add(pl1);
                pf.add(bt1);
                pf.add(bt2);
                
                bt1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            JFrame spf= new JFrame("Show Available Doctors");
                            File apFile= new File("appoints.txt");
                            Scanner ap_in= new Scanner(apFile);
                            
                            JLabel title= new JLabel("My Appointments");
                            title.setBounds(50, 50, 700, 40);
                            title.setFont(new Font("Algerian", Font.BOLD,35));
                            JButton signout= new JButton("Sign Out");
                            signout.setBounds(1000, 40, 100 , 35);
                            spf.add(title);
                            spf.add(signout);
                            
                            JScrollPane scrollPane = new JScrollPane();
                            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPane.setBounds(50, 100, 700, 550);
                            
                            spf.add(scrollPane);
                            
                            JPanel p = new JPanel();
                            scrollPane.setViewportView(p);
                            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                            spf.setLayout(null);
                            spf.pack();
                            
                            int x=50, y=150, w=1000, h=15;
                            while(ap_in.hasNextLine()){
                                String row= ap_in.nextLine();
                                String attr[]= row.split("[,]");
                                if(attr[5].equals(Doctor_appointment_system.session_email)){
                                    JLabel l1= new JLabel("Doctor Name: "+attr[1]);
                                    l1.setSize(w,h);
                                    JLabel l2= new JLabel("Doctor Email: "+attr[2]);
                                    l2.setSize(w,h);
                                    JLabel l3= new JLabel("Speciality: "+attr[3]);
                                    l3.setSize(w,h);
                                    JLabel l4= new JLabel("My Problem: "+attr[6]);
                                    l4.setSize(w,h);
                                    JLabel l5= new JLabel("Given Time: "+attr[8]);
                                    l5.setSize(w,h);
                                    JLabel l6= new JLabel("Given Date: "+attr[9]);
                                    l6.setSize(w,h);
                                    JLabel l7= new JLabel("Appointment: "+attr[10]);
                                    l7.setSize(w,h);
                                    JButton cancel = new JButton("Cancel This appointment");
                                    int bw=300, bh=35;
                                    cancel.setSize(bw, bh);
                                    p.add(l1);
                                    p.add(l2);
                                    p.add(l3);
                                    p.add(l4);
                                    p.add(l5);
                                    p.add(l6);
                                    p.add(l7);
                                    p.add(cancel);
                                    p.add(Box.createVerticalStrut(40));
                                    cancel.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent ae){
                                            String appId=attr[0];
                                            Patient pt= new Patient();
                                            int cancel=pt.cancel_appoint(appId);
                                            if(cancel==1){
                                                JFrame frame=new JFrame();
                                                JOptionPane.showMessageDialog(frame, "Appointment Cancellation Request Successful!");
                                            }
                                            else{
                                                JFrame frame=new JFrame();
                                                JOptionPane.showMessageDialog(frame, "Something Went Wrong! Try Again...");
                                            }
                                        }
                                    });
                                }
                                
                                
                                
                                
                            }
                            signout.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent ae){
                                    try {
                                        pf.setVisible(false);
                                        spf.setVisible(false);
                                        Doctor_appointment_system.session_email=null;
                                        MainFrames mf=new MainFrames();
                                    }
                                    catch (IOException ex) {
                                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            
                            
                            spf.setSize(1280, 720);
                            spf.setVisible(true);
                            
                            
                        }
                        catch (FileNotFoundException ex) {
                            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                bt2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            JFrame spf= new JFrame("Show Available Doctors");
                            File drFile= new File("doctors.txt");
                            Scanner dr_in= new Scanner(drFile);
                            
                            JLabel title= new JLabel("Available Doctors");
                            title.setBounds(50, 50, 700, 40);
                            title.setFont(new Font("Algerian", Font.BOLD,35));
                            JButton signout= new JButton("Sign Out");
                            signout.setBounds(1000, 40, 100 , 35);
                            spf.add(title);
                            spf.add(signout);
                            
                            JScrollPane scrollPane = new JScrollPane();
                            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPane.setBounds(50, 100, 700, 550);
                            
                            spf.add(scrollPane);
                            
                            JPanel p = new JPanel();
                            scrollPane.setViewportView(p);
                            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                            spf.setLayout(null);
                            spf.pack();
                            
                            int x=50, y=150, w=1000, h=15;
                            while(dr_in.hasNextLine()){
                                String row= dr_in.nextLine();
                                String attr[]= row.split("[,]");
                                JLabel l1= new JLabel("Doctor Name:  "+attr[0]);
                                l1.setSize(w,h);
                                JLabel l2= new JLabel("Doctor Email:  "+attr[1]);
                                l2.setSize(w,h);
                                JLabel l3= new JLabel("Speciality:  "+attr[3]);
                                l3.setSize(w,h);
                                JLabel l4= new JLabel("Experience:  "+attr[4]+ " Years");
                                l4.setSize(w,h);
                                JLabel l5= new JLabel("Available Hours:  "+attr[5]);
                                l5.setSize(w,h);
                                JButton choose_bt = new JButton("Request for an appointment");
                                int bw=300, bh=35;
                                
                                choose_bt.setSize(bw, bh);
                                
                                
                                p.add(l1);
                                p.add(l2);
                                p.add(l3);
                                p.add(l4);
                                p.add(l5);
                                p.add(choose_bt);
                                p.add(Box.createVerticalStrut(40));
                                choose_bt.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent ae){
                                        String dr_email=attr[1];
                                        String pt_email= Doctor_appointment_system.session_email;
                                        Patient pt= new Patient();
                                        pt.appoint(dr_email, pt_email);
                                        
                                    }
                                });
                                
                            }
                            signout.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent ae){
                                    try {
                                        pf.setVisible(false);
                                        spf.setVisible(false);
                                        Doctor_appointment_system.session_email=null;
                                        MainFrames mf=new MainFrames();
                                    }
                                    catch (IOException ex) {
                                        Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            
                            
                            spf.setSize(1280, 720);                           
                            spf.setVisible(true);
                            
                            
                        }
                        catch (FileNotFoundException ex) {
                            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                signout.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        try {
                            pf.setVisible(false);
                            Doctor_appointment_system.session_email=null;
                            MainFrames mf=new MainFrames();
                        }
                        catch (IOException ex) {
                            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                pf.add(signout);
                pf.setSize(1280,720);
                pf.setLayout(null);
                pf.setVisible(true);
                pf.setDefaultCloseOperation(EXIT_ON_CLOSE);
            } catch (IOException ex) {
                Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
    
}



class MainFrames extends JFrame{
    MainFrames() throws IOException{
        JFrame mf= new JFrame("Doctor Appointment System");     
        JLabel title = new JLabel();
        title.setText("Welcome to Green-Life Medical");
        JLabel subtitle= new JLabel();
        subtitle.setText("Your good health is our priority. Consult with professionals....");
        title.setFont(new Font("Algerian", Font.BOLD,35));
        subtitle.setFont(new Font("Arial", Font.PLAIN,17));
        title.setBounds(50,35,720,35);
        subtitle.setBounds(50,70,720,35);
        JButton bt1= new JButton("Doctor Sign Up");
        JButton bt2= new JButton("User Sign Up");
        JButton bt3= new JButton("Doctor Sign In");
        JButton bt4= new JButton("User Sign In");
        bt1.setFont(new Font("Arial", Font.BOLD,17));
        bt2.setFont(new Font("Arial", Font.BOLD,17));
        bt3.setFont(new Font("Arial", Font.BOLD,17));
        bt4.setFont(new Font("Arial", Font.BOLD,17));
        bt1.setBounds(220, 370, 160, 50);
        bt2.setBounds(220, 440, 160, 50);
        bt3.setBounds(220 ,510, 160, 50);
        bt4.setBounds(220, 580, 160, 50);
        mf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("main.jpg")))));
        mf.add(title);
        mf.add(subtitle);
        mf.add(bt1);
        mf.add(bt2);
        mf.add(bt3);
        mf.add(bt4);
        bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try {
                    JFrame sf= new JFrame(" Doctor Sign Up");
                    JTextField tf1, tf2, tf3, tf4, tf5;
                    JPasswordField pf;
                    JLabel title = new JLabel();
                    title.setFont(new Font("Algerian", Font.BOLD,35));
                    title.setText("Doctor Sign Up");
                    title.setBounds(50,35,720,35);
                    JLabel name= new JLabel("Name: ");
                    name.setBounds(50,200,120,35);
                    tf1= new JTextField();
                    tf1.setBounds(95, 200, 200, 35);
                    JLabel email= new JLabel("Email: ");
                    email.setBounds(50,250,120,35);
                    tf2= new JTextField();
                    tf2.setBounds(95, 250, 200, 35);
                    JLabel Password= new JLabel("Password: ");
                    Password.setBounds(50,300,120,35);
                    pf= new JPasswordField();
                    pf.setBounds(118, 300, 200, 35);
                    JLabel Speciality= new JLabel("Speciality: ");
                    Speciality.setBounds(50,350,120,35);
                    tf3= new JTextField();
                    tf3.setBounds(120, 350, 200, 35);
                    JLabel Experience= new JLabel("Experience (in years): ");
                    Experience.setBounds(50,400,150,35);
                    tf4= new JTextField();
                    tf4.setBounds(180, 400, 200, 35);
                    JLabel av_hrs= new JLabel("Available Hours: ");
                    av_hrs.setBounds(50,450,120,35);
                    tf5= new JTextField();
                    tf5.setBounds(150, 450, 200, 35);
                    JButton sign_up= new JButton("Sign Up Now!");
                    sign_up.setBounds(50, 500, 200, 50);
                    sf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("sign_up.jpg")))));
                    sf.add(title);
                    sf.add(name);
                    sf.add(tf1);
                    sf.add(email);
                    sf.add(tf2);
                    sf.add(Password);
                    sf.add(pf);
                    sf.add(Speciality);
                    sf.add(tf3);
                    sf.add(Experience);
                    sf.add(tf4);
                    sf.add(av_hrs);
                    sf.add(tf5);
                    sf.add(sign_up);
                    sign_up.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae1) {
                            String name = tf1.getText();
                            String email = tf2.getText();
                            String password= new String(pf.getPassword());
                            String speciality=tf3.getText();
                            String experience=tf4.getText();
                            String av_hrs = tf5.getText();
                            Doctor dr = new Doctor();
                            int checker=dr.sign_up(name, email, password, speciality, experience, av_hrs);
                            System.out.println(checker);
                            if(checker==1)
                            {
                                try {
                                    JFrame frame=new JFrame();
                                    JOptionPane.showMessageDialog(frame, "Sign-Up Successful!");
                                    mf.setVisible(false);
                                    sf.setVisible(false);
                                    Doctor_appointment_system.session_email=email;
                                    Doctor.profileFrame pf = dr.new profileFrame();
                                    
                                } 
                                catch (FileNotFoundException ex) {
                                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }
                            else
                            {
                                JFrame frame=new JFrame();
                                JOptionPane.showMessageDialog(frame, "Email Already Exists or Something Went Wrong! Try Again..."); 
                            }
                        }
                    });
                    sf.setSize(1280,720);
                    setLayout(null);
                    sf.setVisible(true);
                    
                } 
                catch (IOException ex) {
                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                }      
            }
        });
        
        bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try {
                    JFrame sf= new JFrame(" User Sign Up");
                    JTextField tf1, tf2, tf3, tf4;
                    JPasswordField pf;
                    JLabel title = new JLabel();
                    title.setFont(new Font("Algerian", Font.BOLD,35));
                    title.setText("User Sign Up");
                    title.setBounds(50,35,720,35);
                    JLabel name= new JLabel("Name: ");
                    name.setBounds(50,200,120,35);
                    tf1= new JTextField();
                    tf1.setBounds(95, 200, 200, 35);
                    JLabel email= new JLabel("Email: ");
                    email.setBounds(50,250,120,35);
                    tf2= new JTextField();
                    tf2.setBounds(95, 250, 200, 35);
                    JLabel Password= new JLabel("Password: ");
                    Password.setBounds(50,300,120,35);
                    pf= new JPasswordField();
                    pf.setBounds(118, 300, 200, 35);
                    JLabel contact= new JLabel("Contact: ");
                    contact.setBounds(50,350,120,35);
                    tf3= new JTextField();
                    tf3.setBounds(120, 350, 200, 35);
                    JButton sign_up= new JButton("Sign Up Now!");
                    sign_up.setBounds(50, 450, 200, 50);
                    sf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("user_sign_up.jpg")))));
                    sf.add(title);
                    sf.add(name);
                    sf.add(tf1);
                    sf.add(email);
                    sf.add(tf2);
                    sf.add(Password);
                    sf.add(pf);
                    sf.add(contact);
                    sf.add(tf3);
             
                    sf.add(sign_up);
                    sign_up.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae1) {
                            String name1 = tf1.getText();
                            String email = tf2.getText();
                            String password= new String(pf.getPassword());
                            String contact=tf3.getText();
                            Patient usr= new Patient();
                            int checker=usr.sign_up(name1, email, password, contact);
                            System.out.println(checker);
                            if(checker==1)
                            {
                                try {
                                    JFrame frame=new JFrame();
                                    JOptionPane.showMessageDialog(frame, "Sign-Up Successful!");
                                    mf.setVisible(false);
                                    sf.setVisible(false);
                                    Doctor_appointment_system.session_email=email;
                                    Patient.profileFrame pf = usr.new profileFrame();
                                } 
                                catch (FileNotFoundException ex) {
                                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }
                            else
                            {
                                JFrame frame=new JFrame();
                                JOptionPane.showMessageDialog(frame, "Email Already Exists or Something Went Wrong! Try Again..."); 
                            }
                        }
                    });
                    sf.setSize(1280,720);
                    setLayout(null);
                    sf.setVisible(true);
                    
                } 
                catch (IOException ex) {
                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        });
        
        bt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    JFrame sf= new JFrame(" Doctor Sign In");
                    JTextField tf;
                    JPasswordField pf;
                    JLabel title = new JLabel();
                    title.setFont(new Font("Algerian", Font.BOLD,35));
                    title.setText("Doctor Sign In");
                    title.setBounds(50,35,720,35);
                    JLabel email= new JLabel("Email: ");
                    email.setBounds(50,250,120,35);
                    tf= new JTextField();
                    tf.setBounds(95, 250, 200, 35);
                    JLabel Password= new JLabel("Password: ");
                    Password.setBounds(50,300,120,35);
                    pf= new JPasswordField();
                    pf.setBounds(118, 300, 200, 35);
                    JButton sign_in= new JButton("Sign In");
                    sign_in.setBounds(50, 360, 200, 50);
                    sf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("sign_in.jpg")))));
                    sf.add(title);
                    sf.add(email);
                    sf.add(tf);
                    sf.add(Password);
                    sf.add(pf);
                    sf.add(sign_in);
                    sign_in.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae1) {
                            String email = tf.getText();
                            String password= new String(pf.getPassword());
                            Doctor dr = new Doctor();
                            int checker=dr.sign_in(email, password);
                            System.out.println(checker);
                            if(checker==1)
                            {
                                try {
                                    mf.setVisible(false);
                                    sf.setVisible(false);
                                    Doctor.profileFrame pf = dr.new profileFrame();
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else
                            {
                                JFrame frame=new JFrame();
                                JOptionPane.showMessageDialog(frame, "Email or password Mismatch!! Please Try Again!!");
                            }
                        }
                    });
                    sf.setSize(1280,720);
                    setLayout(null);
                    sf.setVisible(true);
                    
                } 
                catch (IOException ex) {
                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        bt4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    JFrame sf= new JFrame(" User Sign In");
                    JTextField tf;
                    JPasswordField pf;
                    JLabel title = new JLabel();
                    title.setFont(new Font("Algerian", Font.BOLD,35));
                    title.setText("User Sign In");
                    title.setBounds(50,35,720,35);
                    JLabel email= new JLabel("Email: ");
                    email.setBounds(50,250,120,35);
                    tf= new JTextField();
                    tf.setBounds(95, 250, 200, 35);
                    JLabel Password= new JLabel("Password: ");
                    Password.setBounds(50,300,120,35);
                    pf= new JPasswordField();
                    pf.setBounds(118, 300, 200, 35);
                    JButton sign_in= new JButton("Sign In");
                    sign_in.setBounds(50, 360, 200, 50);
                    sf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("user_sign_in.jpg")))));
                    sf.add(title);
                    sf.add(email);
                    sf.add(tf);
                    sf.add(Password);
                    sf.add(pf);
                    sf.add(sign_in);
                    sign_in.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae1) {
                            String email = tf.getText();
                            String password= new String(pf.getPassword());
                            Patient pt = new Patient();
                            int checker=pt.sign_in(email, password);
                            System.out.println(checker);
                            if(checker==1)
                            {
                                try {
                                    mf.setVisible(false);
                                    sf.setVisible(false);
                                    Patient.profileFrame pf = pt.new profileFrame();
                                } 
                                catch (FileNotFoundException ex) {
                                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else
                            {
                                JFrame frame=new JFrame();
                                JOptionPane.showMessageDialog(frame, "Email or password Mismatch!! Please Try Again!!");
        
                            }
                        }
                    });
                    sf.setSize(1280,720);
                    setLayout(null);
                    sf.setVisible(true);
                    
                } 
                catch (IOException ex) {
                    Logger.getLogger(MainFrames.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mf.setSize(1280,720);
        setLayout(null); 
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}


public class Doctor_appointment_system {
 
    public static String session_email=null;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        MainFrames mf= new MainFrames(); 
        
    }
}
