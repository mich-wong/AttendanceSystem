
import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Attendance {
	
	public static Student student;
	
	
	static String month;
	static int week;
	static String weekday;
	static int day;
	public AttendanceSheet sheet;
	JFrame frame;
	private JTextField txtegFebruary;
	private JTextField txteg;
	private JTextField txtegFriday;
	private JTextField txteg_1;
	
	
	public Attendance()
	{
		initialize();
	}
	
	public void takeAttendance()
	{
		Attendance a = new Attendance();
		a.frame.setVisible(true);
	}
	
	public void generateDirectories()
	{

		createNewDirectory(month, week, weekday, day);
		generateDailyReport(month, week, weekday, day);
		generateMonthlyReport(month);
		generateWeeklyReport(month, week);
			
	}
	
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to the automated attendance system!");
		lblNewLabel.setBounds(73, 18, 307, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPleaseProvideSome = new JLabel("Please provide some information about the date to get started.");
		lblPleaseProvideSome.setBounds(39, 35, 405, 16);
		frame.getContentPane().add(lblPleaseProvideSome);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setBounds(132, 75, 61, 16);
		frame.getContentPane().add(lblMonth);
		
		txtegFebruary = new JTextField();
		txtegFebruary.setText("(e.g. February)");
		txtegFebruary.setBounds(183, 70, 130, 26);
		frame.getContentPane().add(txtegFebruary);
		txtegFebruary.setColumns(10);
		
		JLabel lblWeek = new JLabel("Week#:");
		lblWeek.setBounds(132, 108, 61, 16);
		frame.getContentPane().add(lblWeek);
		
		txteg = new JTextField();
		txteg.setText("(e.g. 1,2,3,4)");
		txteg.setBounds(183, 103, 130, 26);
		frame.getContentPane().add(txteg);
		txteg.setColumns(10);
		
		JLabel lblWeekday = new JLabel("Weekday:");
		lblWeekday.setBounds(118, 140, 61, 16);
		frame.getContentPane().add(lblWeekday);
		
		txtegFriday = new JTextField();
		txtegFriday.setText("(e.g. Friday)");
		txtegFriday.setBounds(183, 135, 130, 26);
		frame.getContentPane().add(txtegFriday);
		txtegFriday.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			if (txtegFebruary.getText().equals("") || 
						txteg.getText().equals("") || 
						txtegFriday.getText().equals("") || 
						txteg_1.getText().equals(""))
			{
					while (txtegFebruary.getText().equals("") || 
						txteg.getText().equals("") || 
						txtegFriday.getText().equals("") || 
						txteg_1.getText().equals(""))
					{
					      if (txtegFebruary.getText().equals(""))
						{
				   txtegFebruary.setText("This field is empty.");
						}
						
						if (txteg.getText().equals(""))
						{
					txteg.setText("This field is empty.");
						}
						
						if (txtegFriday.getText().equals(""))
						{
					txtegFriday.setText("This field is empty.");
						}
						
						if (txteg_1.getText().equals(""))
						{
					txteg_1.setText("This field is empty.");
						}
					}

			}
			else
			{
				
				month = txtegFebruary.getText();
				weekday = txtegFriday.getText();
				
				 boolean numeric = true;
				 boolean numeric2 = true;

			        try {
			        	week = Integer.parseInt(txteg.getText());
			        } catch (NumberFormatException e1) {
			            numeric = false;
			        }
			        
			        try {
			        	day = Integer.parseInt(txteg_1.getText());
			        } catch (NumberFormatException e2) {
			        	numeric2 = false;
			        }
			        
			        if (!numeric || !numeric2)
			        {
			        	if (!numeric)
			        	{
			        		JOptionPane.showMessageDialog(null, txteg.getText() + " is not an integer!");
			        	}
			        	
			        	if (!numeric2)
			        	{
			        		JOptionPane.showMessageDialog(null, txteg_1.getText() + " is not an integer!");
			        	}
			        }
			       
			        else
			        	
			        {
				
			     
				JOptionPane.showMessageDialog(null, "A new attendance sheet will be generated shortly.");
				
				generateDirectories();

				AttendanceSheet frame =new AttendanceSheet();
		        frame.setVisible(true);;
			        }
			}
			}
		});
		btnSubmit.setBounds(158, 229, 117, 29);
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblDay = new JLabel("Day:");
		lblDay.setBounds(144, 168, 33, 16);
		frame.getContentPane().add(lblDay);
		
		txteg_1 = new JTextField();
		txteg_1.setText("(e.g. 25)");
		txteg_1.setBounds(183, 163, 130, 26);
		frame.getContentPane().add(txteg_1);
		txteg_1.setColumns(10);
		
		
	}
	
	
	
	public static ArrayList<Student> getStudents()
	{
		ArrayList<Student> students = new ArrayList<Student>();
		
		try {
		File studentInformation = new File(System.getProperty("user.home"), "Project/Classroom/Students.txt");
		FileReader fr = new FileReader (studentInformation);
		BufferedReader textReader = new BufferedReader(fr);
		String strLine;
		while((strLine = textReader.readLine())!= null)
		{
			String[] values = strLine.split(" ");
			Student temp = new Student(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), false);
			students.add(temp);
		}
		textReader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		 return students;
	}
	

	public static String getMonth()
	{
		return month;
		
	}
	
	public static int getWeek()
	{
		return week;
	}
	
	public static String getWeekday()
	{
		return weekday;
		
	}
	
	public static int getDay()
	{
		return day;
		
	}
	
	
	//creates monday, tuesday, wednesday
	public static void generateDailyReport(String month, int week, String weekday, int day)
	{
		Path d = Paths.get(System.getProperty("user.home"), "Project/Classroom/" + month + "/week" + week + "/" , weekday + "-" + day+".txt");
		
		  if (!Files.exists(d)) {
	          try {
	              Files.createFile(d);
	          } catch (IOException e) {
	              //fail to create directory
	              e.printStackTrace();
	          }
		  }
		  
	}
	
	public static void generateMonthlyReport(String month)
	{
		
		Path monthlyReport = Paths.get(System.getProperty("user.home"), "Project/Classroom/" + month, "MONTHLYREPORT.txt");
		
				 if (!Files.exists(monthlyReport)) {
			          try {
			              Files.createFile(monthlyReport);
			          } catch (IOException e) {
			              //fail to create directory
			              e.printStackTrace();
			          }
				  }	
				
		
	}
	
	public static void generateWeeklyReport(String month, int week)
	{
		Path weeklyReport = Paths.get(System.getProperty("user.home"), "Project/Classroom/" + month + "/week" + week, "WEEKLYREPORT.txt");
		
		 if (!Files.exists(weeklyReport)) {
	          try {
	              Files.createFile(weeklyReport);
	          } catch (IOException e) {
	              //fail to create directory
	              e.printStackTrace();
	          }
		  }	
	
		
	}
	
	public static void createNewDirectory(String month, int week, String weekday, int day)
	{
		Path d = Paths.get(System.getProperty("user.home"), "Project/Classroom/" + month + "/week" + week);
		
		  if (!Files.exists(d)) {
	          try {
	              Files.createDirectories(d);
	          } catch (IOException e) {
	              //fail to create directory
	              e.printStackTrace();
	          }
		  }
		
	}
	
	
	public static void main(String[] args)
	{
	

	}
	
}
