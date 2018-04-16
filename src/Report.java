package src;

import java.awt.*;import javax.swing.event.*;import java.awt.event.*;
import javax.swing.*;import java.io.*;import java.util.*;
import javax.mail.*;import javax.mail.internet.*;import javax.activation.*;
import java.io.Serializable;import ch.randelshofer.quaqua.*;
public class Report extends JPanel
{
	notepad Note;
	JScrollPane TopicScroll,ReportScroll;
	JLabel logo,fname,lname,email,topic,report;
	JComboBox surname;
	JTextField Fname,Lname,Email;
	JTextArea Topic,Report; 
	JPanel MainPanel,MainPane,NameMainPane,logoPane,SurPane,NamePane,ReportPane,TopicPane,DiscPane;
	JButton Submit;File ReportFile;
	String EmailData="";
	public Report(notepad note)
	{
		Note=note;
		setLayout(new FlowLayout());
		
		MainPanel=new JPanel(new BorderLayout());MainPanel.setPreferredSize(new Dimension(650,700));add(MainPanel);
		
		logoPane =new JPanel(new FlowLayout());MainPanel.add(logoPane,BorderLayout.NORTH);
		logo=new JLabel("Reports and Suggestions");logo.setFont(new Font("Calibri",0,40));logoPane.add(logo);
		logoPane.setPreferredSize(new Dimension(650,50));
		MainPane=new JPanel(new BorderLayout());MainPanel.add(MainPane,BorderLayout.CENTER);
		
		NameMainPane=new JPanel(new GridLayout(6, 0));MainPane.add(NameMainPane,BorderLayout.NORTH);NameMainPane.add(new JLabel(" "));
		
        //-----------------------------------------------------------------------------------------------------------------------
		SurPane=new JPanel(new FlowLayout(FlowLayout.LEADING));SurPane.add(new JLabel("   "));
		SurPane.setPreferredSize(new Dimension(550,25));SurPane.setMaximumSize(new Dimension(550,25));
		surname= new JComboBox();surname.setModel(new DefaultComboBoxModel(new String[]{"Mr.", "Mrs.", "Dr." }));
		surname.setPreferredSize(new Dimension(60,25));
		surname.setEditable(true);surname.setDoubleBuffered(true);SurPane.add(surname);NameMainPane.add(SurPane);
        //-----------------------------------------------------------------------------------------------------------------------
		NamePane =new JPanel(new FlowLayout(FlowLayout.LEADING));NameMainPane.add(NamePane);
		
		fname=new JLabel("      First Name :  ");NamePane.add(fname);
		Fname=new JTextField(12);
		Fname.putClientProperty("Quaqua.TextField.style","search");
		Fname.putClientProperty("JTextField.variant","search");NamePane.add(Fname);
		
		lname=new JLabel("      Last Name :  ");NamePane.add(lname);
		Lname=new JTextField(12);
		Lname.putClientProperty("Quaqua.TextField.style","search");
		Lname.putClientProperty("JTextField.variant","search");NamePane.add(Lname);
		
		JPanel pane1=new JPanel(new FlowLayout(FlowLayout.LEADING));pane1.setOpaque(false);NameMainPane.add(pane1);
		email=new JLabel("      Email          :  ");pane1.add(email);
		Email=new JTextField(35);
		Email.putClientProperty("Quaqua.TextField.style","search");
		Email.putClientProperty("JTextField.variant","search");pane1.add(Email);
		
		NameMainPane.add(new JLabel());NameMainPane.add(new JSeparator());
        //-----------------------------------------------------------------------------------------------------------------------
		
		ReportPane=new JPanel(new BorderLayout());MainPane.add(ReportPane,BorderLayout.CENTER);
        //-----------------------------------------------------------------------------------------------------------------------
		
		TopicPane=new JPanel(new BorderLayout());TopicPane.setPreferredSize(new Dimension(600,150));ReportPane.add(TopicPane,BorderLayout.NORTH);
		
		topic=new JLabel("       Brief Discription of Report :  ");TopicPane.add(topic,BorderLayout.NORTH);
		JPanel pane2=new JPanel(new BorderLayout());pane2.setOpaque(false);pane2.setLayout(null);TopicPane.add(pane2,BorderLayout.CENTER);
		
		TopicScroll= new JScrollPane();TopicScroll.setBounds(28,15,580,80);pane2.add(TopicScroll);
		Topic=new JTextArea();Topic.setMargin(new Insets(25,25,25,25));
		TopicScroll.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
		TopicScroll.setViewportView(Topic);
		
        //-----------------------------------------------------------------------------------------------------------------------
		
		DiscPane=new JPanel(new BorderLayout());ReportPane.add(DiscPane,BorderLayout.CENTER);
		
		report=new JLabel("       Discription of The Report in Detail :  ");DiscPane.add(report,BorderLayout.NORTH);
		JPanel pane0=new JPanel(new BorderLayout());pane0.setOpaque(false);pane0.setLayout(null);DiscPane.add(pane0,BorderLayout.CENTER);
		
		ReportScroll= new JScrollPane();ReportScroll.setBounds(28,15,580,200);pane0.add(ReportScroll);
		Report=new JTextArea();Report.setMargin(new Insets(25,25,25,25));
		ReportScroll.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
		ReportScroll.setViewportView(Report);
        //-----------------------------------------------------------------------------------------------------------------------
		
		String SUBMIT="<html><head><style type=\"text/css\">"+
		"p { font: 18pt \"Courier\"; margin-top: 5px;margin-bottom: 5px;margin-left: 5px;margin-right: 5px }</style></head>"+
		"</b><p>Submit</b>";Submit=new JButton(SUBMIT);
		Submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Submit();
			}
		});
		Transparent();
	}
	public String Title(String Name)
	{
		String First=(String.valueOf(Name.charAt(0))).toUpperCase();
		String Other="";Other=Name.substring(1);return (First+Other);
	}
	public void Submit()
	{
		try
		{
			if(Fname.getText()==null||Fname.getText().equals("")){Fname.requestFocus();return;}
			else if(Lname.getText()==null||Lname.getText().equals("")){Lname.requestFocus();return;}
			else if(Email.getText()==null||Email.getText().equals("")){Email.requestFocus();return;}
			else if(Topic.getText()==null||Topic.getText().equals("")){Topic.requestFocus();return;}
			else if(Report.getText()==null||Report.getText().equals("")){Report.requestFocus();return;}
			EmailData="";
			EmailData=EmailData+"+------------------------------------------------------------------------------------------------------------------------------\n";
			EmailData=EmailData+"|    About The Reporter\n";
			EmailData=EmailData+"+------------------------------------------------------------------------------------------------------------------------------\n|\n";
			EmailData=EmailData+"|    Name:  "+Title(surname.getSelectedItem().toString())+" "+Title(Fname.getText())+" "+Title(Lname.getText())+"\n|    Email: "+Email.getText();
			EmailData=EmailData+"\n|\n|_______________________________________________________________________________\n|\n";
			EmailData=EmailData+"+------------------------------------------------------------------------------------------------------------------------------\n";
			EmailData=EmailData+"|    Topic of Report:  \n";
			EmailData=EmailData+"+------------------------------------------------------------------------------------------------------------------------------\n|\n";
			EmailData=EmailData+"|    ";
			for(int io=0;io<Topic.getText().length();io++)
			{
				if(Topic.getText().charAt(io)=='\n'){EmailData=EmailData+Topic.getText().charAt(io)+"|    ";}
				else{EmailData=EmailData+Topic.getText().charAt(io);}
			}
			EmailData=EmailData+"\n|\n|_______________________________________________________________________________\n|\n";
			EmailData=EmailData+"+------------------------------------------------------------------------------------------------------------------------------\n";
			EmailData=EmailData+"|    Discription of Report:  \n";
			EmailData=EmailData+"+------------------------------------------------------------------------------------------------------------------------------\n|\n";
			EmailData=EmailData+"|    ";
			for(int io=0;io<Report.getText().length();io++)
			{
				if(Report.getText().charAt(io)=='\n'){EmailData=EmailData+Report.getText().charAt(io)+"|    ";}
				else{EmailData=EmailData+Report.getText().charAt(io);}
			}
			EmailData=EmailData+"\n|\n|_______________________________________________________________________________\n";
			
			ReportFile = new File("Reports/"+Fname.getText()+".Xtreme");int i=1;
			while(ReportFile.exists()){ReportFile = new File("Reports/"+Fname.getText()+"("+i+")");i++;}
			
			FileOutputStream fos = null;ObjectOutputStream out = null;ArrayList ReportSaver = new ArrayList();
			ReportSave sav=new ReportSave(Title(surname.getSelectedItem().toString())+" "+Title(Fname.getText())+" "+Title(Lname.getText()),
				Title(Fname.getText()),Email.getText(),EmailData);
			ReportSaver.add(sav);fos = new FileOutputStream(ReportFile);
			out = new ObjectOutputStream(fos);out.writeObject(ReportSaver);out.close();
			
			SendReport(Title(surname.getSelectedItem().toString())+" "+Title(Fname.getText())+" "+Title(Lname.getText()),
				Title(Fname.getText()),Email.getText(),EmailData,ReportFile,1);
		}
		catch(Exception e){}
	}
	public void SendReport(String Name,String Firstname,String Email,String EmailData,File ReportFile,int Thanks)
	{
		final String ID="xtremecorporationlimited@gmail.com",Password="121121";
		try 
		{
			Properties props = new Properties();
			props.put("mail.smtp.host","smtp.gmail.com");props.put("mail.smtp.socketFactory.port","465");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth","true");props.put("mail.smtp.port","465");
			Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
			{
				protected PasswordAuthentication getPasswordAuthentication() 
				{
					return new PasswordAuthentication(ID,Password);
				}
			});
			Message message = new MimeMessage(session);
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(ID));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(ID));
			message.setSubject("Xtreme Pad Suggestion From "+Name+" .");
			message.setText(EmailData);Transport.send(message);ReportFile.delete();
			
			if(Thanks==1)
			{
				message = new MimeMessage(session);
				message.setSentDate(new Date());
				message.setFrom(new InternetAddress(ID));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Email));
				message.setSubject("Xtreme © Corporation Limited.");
				String Data="Hello "+Firstname +",\n\nThanks, For Your Suggestions To Xtreme Pad.\n"+
				"We Will Investigate The Problems And Will Try To Make Use of Your Suggestions.\n"+
				"We Will Remind You For The Next Release of New Xtreme Pad.";
				message.setText(Data);Transport.send(message);
			}
		}
		catch (MessagingException e) 
		{
			if(Thanks==1)
			{
				String SaveString="<html>"+
				"<head><style type=\"text/css\">"+
				"b { font: 14pt \"Lucida Grande\" }p { font: 11pt \"Lucida Grande\"; margin-top: 8px }</style></head>"+
				"<b>An Error Occured While Processing Your<br>Report / Suggestion.</b><p>"+
				"Please Try Sending Again, Your Report / Suggestion is Usefull to Us.";
				JOptionPane SaveAsk= new JOptionPane(SaveString,JOptionPane.WARNING_MESSAGE);
				Object[] options = {"Cancle","Send Again"};
				SaveAsk.setOptions(options);
				SaveAsk.setInitialValue(options[1]);
				JSheet.showSheet(SaveAsk,Report.this, new SheetListener()
				{
					public void optionSelected(SheetEvent evt) 
					{
						int b=evt.getOption();
						if(b==1)
						{
							SendReport(Title(surname.getSelectedItem().toString())+" "+Title(Report.this.Fname.getText())+" "+Title(Lname.getText()),
								Title(Report.this.Fname.getText()),Report.this.Email.getText(),Report.this.EmailData,Report.this.ReportFile,1);
						}
					}
				});
			}
		}
	}
	public void Transparent()
	{
		MainPane.setOpaque(false);NameMainPane.setOpaque(false);logoPane.setOpaque(false);SurPane.setOpaque(false);
		NamePane.setOpaque(false);ReportPane.setOpaque(false);DiscPane.setOpaque(false);TopicPane.setOpaque(false);
	}
}
/*
            {
                ArrayList Loader = new ArrayList();FileInputStream fis = null;ObjectInputStream in = null;
                fis = new FileInputStream(ReportFile.toString());in = new ObjectInputStream(fis);
                Loader = (ArrayList)in.readObject();in.close();
                ReportSave ReportLoader = (ReportSave)Loader.get(0);
                System.out.println("Name => "+ReportLoader.getName());
                System.out.println("First Name => "+ReportLoader.getFirstName());
                System.out.println("Email => "+ReportLoader.getEmail());
                System.out.println("\n\n\nEmail Data => "+ReportLoader.getEmailData());
            }           
*/