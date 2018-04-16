package src;

import javax.swing.text.*;
import java.awt.Checkbox;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class find extends JFrame implements ActionListener
{
	int startIndex=0;
	Label l1, l2;
	TextField tf, tr;
	JButton find_btn, find_next, replace, replace_all, cancel;
	notepad samp;
	public find(notepad mynote)
	{
		super("Find / Replace");
		samp = mynote;
		
		l1 = new Label("Find What: ");
		l2 = new Label("Replace With: ");
		tf = new TextField(30);
		tr = new TextField(30);
		find_btn = new JButton("Find");
		find_next = new JButton("Find Next");
		replace = new JButton("Replace");
		replace_all = new JButton("Replace All");
		cancel = new JButton("Cancel");
		
		setLayout(null);
		int label_w = 80;
		int label_h = 20;
		int tf_w    = 120;
		
		l1.setBounds(10,10, label_w, label_h);
		add(l1);
		tf.setBounds(10+label_w, 10, tf_w, 20);
		tf.addKeyListener(new java.awt.event.KeyAdapter()
		{
			public void keyPressed(java.awt.event.KeyEvent evt) 
			{
				find_btn.doClick();
			}
		});
		add(tf);
		l2.setBounds(10, 10+label_h+10, label_w, label_h);
		add(l2);
		tr.setBounds(10+label_w, 10+label_h+10, tf_w, 20);
		add(tr);
		
		find_btn.setBounds(220, 10, 100, 20);
		add(find_btn);
		find_btn.addActionListener(this);
		find_next.setBounds(220, 30, 100, 20);
		add(find_next);
		find_next.addActionListener(this);
		replace.setBounds(220, 50, 100, 20);
		add(replace);
		replace.addActionListener(this);
		replace_all.setBounds(220, 70, 100, 20);
		add(replace_all);
		replace_all.addActionListener(this);
		cancel.setBounds(220, 90, 100, 20);
		add(cancel);
		cancel.addActionListener(this);
		
		int w = 340;
		int h = 150;
		
		setSize(w,h);
		
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(center.x-w/2, center.y-h/2);
		setVisible(false);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==find_btn)
		{
			find(0);
		}
		else if(e.getSource() == find_next)
		{
			find_next();
		}
		else if(e.getSource() == replace)
		{
			replace();
		}
		else if(e.getSource() == replace_all)
		{
			replace_all();
		}
		else if(e.getSource() == cancel)
		{
			this.setVisible(false);
		}
	}
	
	public boolean find(int wht)
	{
		try
		{
			Document doc=samp.Edit.t.getDocument();
			int select_start = doc.getText(0,doc.getLength()).indexOf(tf.getText());
			if(select_start == -1)
			{
				startIndex = 0;return false;
			}
			if(select_start == doc.getText(0,doc.getLength()).lastIndexOf(tf.getText()))
			{
				startIndex = 0;
			}
			int select_end = select_start+(tf.getText().length());            
			samp.Edit.t.select(select_start, select_end);
		}
		catch(Exception e){}
		return true;
	}
	
	public void find_next()
	{
		Document doc=samp.Edit.t.getDocument();
		String selection = samp.Edit.t.getSelectedText();System.out.println(selection);
		try
		{
			selection.equals("");
		}
		catch(NullPointerException e)
		{
			selection = tf.getText();
			try
			{
				selection.equals("");
			}
			catch(NullPointerException e2)
			{
				selection = JOptionPane.showInputDialog("Find:");tf.setText(selection);
			}
		}
		try
		{
			int select_start = doc.getText(0,doc.getLength()).indexOf(selection, startIndex);
			int select_end = select_start+selection.length();
			samp.Edit.t.select(select_start, select_end);
			startIndex = select_end+1;
			if(select_start == doc.getText(0,doc.getLength()).lastIndexOf(selection))
			{
				startIndex = 0;
			}
		}
		catch(Exception e){}
	}
	public void replace()
	{
		Document doc=samp.Edit.t.getDocument();
		try
		{
			find(0);
			samp.Edit.t.replaceSelection(tr.getText());
		}
		catch(Exception e)
		{}
	}
	public void replace_all()
	{
		try
		{
			Document doc=samp.Edit.t.getDocument();
			while(find(1)){samp.Edit.t.replaceSelection(tr.getText());}
		}
		catch(Exception e){}
	}
}