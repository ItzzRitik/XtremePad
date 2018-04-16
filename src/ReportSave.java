package src;

import java.io.Serializable;
public class ReportSave implements Serializable
{
	private String EmailData,Name,Email,Fname;
	public ReportSave(String Name,String Fname,String Email,String EmailData)
	{
		this.EmailData=EmailData;this.Name=Name;this.Email=Email;this.Fname=Fname;
	}
	public String getEmailData(){return EmailData;}
	public String getName(){return Name;}
	public String getEmail(){return Email;}
	public String getFirstName(){return Fname;}
}