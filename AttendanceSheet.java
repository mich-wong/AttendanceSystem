import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AttendanceSheet extends JFrame{
	 public static Attendance attendance;
	 public Student student;
	 private static ArrayList<Student> studentInformation = attendance.getStudents();
	 int rows = studentInformation.size();
	 int columns = 5;
	 String weekday = attendance.getWeekday();
	 String month = attendance.getMonth();
	 int day = attendance.getDay();
	 int week = attendance.getWeek();
	 private static String[] presence = new String[studentInformation.size()];
	 
  public static void main(String[] args)
  {
        AttendanceSheet sheet =new AttendanceSheet();
        sheet.setVisible(true);;
  }
  

  public AttendanceSheet()
  {

// to note: the following highlighted code was taken and developed from 
//https://stackoverflow.com/questions/14504055/how-to-structure-my-attendance-program
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(200,200,800,300);
    setTitle("Attendance Sheet");
    getContentPane().setLayout(null);

   JScrollPane scroll=new JScrollPane();
   scroll.setBounds(70,80,600,200);
   getContentPane().add(scroll);

    
    final JTable table=new JTable();
    scroll.setViewportView(table);

    //THE MODEL OF OUR TABLE
    DefaultTableModel model=new DefaultTableModel()
    {
      public Class<?> getColumnClass(int column)
      {
        switch(column)
        {
        case 0:
          return String.class;
        case 1:
          return String.class;
        case 2:
          return String.class;
        case 3:
          return String.class;
        case 4:
          return Boolean.class;
        default:
          return String.class;
        }
      }
    };


    table.setModel(model);

    model.addColumn("First Name");
    model.addColumn("Last Name");
    model.addColumn("Student ID");
    model.addColumn("Grade");
    model.addColumn("Absent");
    
    if (studentInformation.isEmpty())
    {
    	 JOptionPane.showMessageDialog(null, "Error! There are no students in this classroom.");
    	 System.exit(0);
    }

    for(int i=0;i< studentInformation.size();i++)
    {
      model.addRow(new Object[0]);
      model.setValueAt(studentInformation.get(i).getFirstName(), i, 0);
      model.setValueAt(studentInformation.get(i).getLastName(), i, 1);
      model.setValueAt(Integer.toString(studentInformation.get(i).getStudentID()), i, 2);
      model.setValueAt(Integer.toString(studentInformation.get(i).getGrade()), i, 3);
      model.setValueAt(false,i,4);
    }


    JButton btn = new JButton("Submit Attendance");
    

    btn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

        for(int i=0;i<table.getRowCount();i++)
        {
          Boolean checked=Boolean.valueOf(table.getValueAt(i, 4).toString());
          
          String col=table.getValueAt(i, 0).toString();
          getPresence()[i] = "P";

         
          if(checked)
          {
            JOptionPane.showMessageDialog(null, col + " was absent today!");
            getPresence()[i] = "A";
            
          }
          
        }
        JOptionPane.showMessageDialog(null, "Thank you for using the automated attendance system!");
        updateDailyReport(month, week, weekday, day);
        updateWeeklyReport(month,week);
        updateMonthlyReport(month);
      }
    });

    btn.setBounds(20,30,150,30);
    getContentPane().add(btn);
    
    
   JLabel label = new JLabel("Today's date is: " + weekday + ", " 
		   										 + month + " " 
		   										 + Integer.toString(day));
   label.setBounds(300,30,1000,30);
   getContentPane().add(label);
   
  
  }
  
  public void updateMonthlyReport(String month)
  {
	  try
	  {
		  File monthlyReport = new File(System.getProperty("user.home"), "Project/Classroom/" + month + "/MONTHLYREPORT.txt");
		  FileReader fr = new FileReader(monthlyReport);
		  BufferedReader fileReader = new BufferedReader(fr);
		  ArrayList<String> values = new ArrayList<String>();
		  String line;
		  if (!(monthlyReport.length()==0))
		  {
			  while((line = fileReader.readLine())!=null)
			  {
				  values.add(line);
				  
			  }
			  
		  monthlyReport.delete();
		  //attendance.generateMonthlyReport(month);
		  Attendance.generateMonthlyReport(month);
		  
		  //what is static and why does it have to be changed 
		  
		  File monthlyReportEdit = new File(System.getProperty("user.home"), "Project/Classroom/" + month + "/MONTHLYREPORT.txt");
		  FileWriter fw = new FileWriter(monthlyReportEdit);
		  BufferedWriter fileWriter = new BufferedWriter(fw);
		  for (int i = 0; i < values.size(); i++)
	  		{
	  			fileWriter.write(values.get(i)+getPresence()[i]);
	  			fileWriter.newLine();
	  		}
		  
		  fileWriter.close();
		  fileReader.close();
		  
		  }
		 
		  else
		  {
			  FileWriter fw = new FileWriter(monthlyReport);
			  BufferedWriter fileWriter = new BufferedWriter(fw);
		  
			  		for (int i = 0; i < attendance.getStudents().size(); i++)
			  		{
			  			fileWriter.write(getPresence()[i]);
			  			fileWriter.newLine();
			  		}
			  		
			  		 fileWriter.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	  
  }
  
  public void updateWeeklyReport(String month, int week)
  {
	  try {
		  
		  File weeklyReport = new File(System.getProperty("user.home"), "Project/Classroom/" + month 
				  													+ "/week" + week 
				  													+ "/WEEKLYREPORT.txt");
		  FileReader fr = new FileReader(weeklyReport);
		  BufferedReader fileReader = new BufferedReader(fr);
		  ArrayList<String> values = new ArrayList<String>();
		  String line;
		  
		  if (!(weeklyReport.length()==0))
		  {
			  while((line = fileReader.readLine())!=null)
			  {
				  values.add(line);
				  
			  }
			  
		  weeklyReport.delete();
		  //attendance.generateWeeklyReport(month, week);
		  Attendance.generateWeeklyReport(month, week);
		  
		  File weeklyReportEdit = new File(System.getProperty("user.home"), "Project/Classroom/" + month 
				  												+ "/week" + week + "/WEEKLYREPORT.txt");
		  FileWriter fw = new FileWriter(weeklyReportEdit);
		  BufferedWriter fileWriter = new BufferedWriter(fw);
		  for (int i = 0; i < values.size(); i++)
	  		{
	  			fileWriter.write(values.get(i)+getPresence()[i]);
	  			fileWriter.newLine();
	  		}
		  
		  fileWriter.close();
		  
		  }
		 
		  else
		  {
			  FileWriter fw = new FileWriter(weeklyReport);
			  BufferedWriter fileWriter = new BufferedWriter(fw);
		  
			  		for (int i = 0; i < attendance.getStudents().size(); i++)
			  		{
			  			fileWriter.write(getPresence()[i]);
			  			fileWriter.newLine();
			  		}
			  		
			  		 fileWriter.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
  }

	public void updateDailyReport(String month, int week, String weekday, int day) 
	  {
		try {
		  File dailyReport = new File(System.getProperty("user.home"), "Project/Classroom/" + month 
				  															+ "/week" + week + "/" 
				  															+ weekday + "-" + day+".txt");
		  FileWriter fw = new FileWriter(dailyReport);
		  BufferedWriter fileWriter = new BufferedWriter(fw);
		  
		 for (int i = 0; i < attendance.getStudents().size(); i++)
		 {
			  fileWriter.write(getPresence()[i]);
			  fileWriter.newLine();
			  
		  }
		//  System.out.println(path);
		  fileWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		  
	  }
  
  
	public String[] getPresence()
  	{
		return presence;
  	}

}
