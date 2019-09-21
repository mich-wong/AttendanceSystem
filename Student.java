
public class Student extends Person
{
	private int studentID;
	private int grade;
	private Boolean isAbsent;
	
	
	public Student (String firstName, String lastName, int studentID, int grade, Boolean isAbsent)
	{
		super(firstName, lastName);
		this.studentID = studentID;
		this.grade = grade;
		this.isAbsent = isAbsent;
		
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public int getStudentID()
	{
		return studentID;
	}
	
	public int getGrade()
	{
		return grade;
	}
	
	public boolean getPresence()
	{
		return isAbsent;
	}
	
	public static void main(String[] args)
	{	
		
	}

}
