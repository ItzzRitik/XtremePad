package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;import java.awt.*;

public class font_chooser extends JPanel  implements ActionListener, ListSelectionListener
{
    //static JPanel window = new JPanel();
	notepad samp;int W=0,H=0,X=0,Y=0;

	JLabel flist_label, fsize_label, fstyle_label, fprev_label, preview;
	JList flist, fsize, fstyle;
	ScrollPane flist_sc, fstyle_sc, fsize_sc;
	JButton ok, cancel;

	JTextField fonts,size,style;

	GraphicsEnvironment ge;
	String font_names[]; 
	Font sample;

	String font_name;
	int font_size, font_style,font;
	String styles[] = {"Regular", "Bold", "Italic", "Bold Italic"};
	public void WinSize()
	{
		W=(int)samp.getSize().getWidth();H=(int)samp.getSize().getHeight();
	}
	public font_chooser(notepad ref)
	{
		samp  = ref;
		setLayout(null);setOpaque(true);
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		font_names = ge.getAvailableFontFamilyNames();
		flist = new JList(font_names);flist.setSelectedIndex(0);
		flist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		flist_label  = new JLabel("Font: ");
		add(flist_label);
		flist_label.setBounds(X+10,Y+10, 210, 20);
		fonts=new JTextField(font_names[0]);fonts.setBounds(X+10,Y+30, 210, 22);
		fonts.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));add(fonts);

		flist_sc = new ScrollPane();
		flist_sc.add(flist);
		flist_sc.setBounds(X+10,Y+52,210,203);
		add(flist_sc);
		flist.addListSelectionListener(this);

        // font style box
		fstyle = new JList(styles);fstyle.setSelectedIndex(0);
		fstyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fstyle_label = new JLabel("Style: ");
		add(fstyle_label);
		fstyle_label.setBounds(X+320,Y+128, 80, 20);

		style=new JTextField(styles[0]);style.setBounds(X+320,Y+148, 80, 22);
		style.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));add(style);

		fstyle_sc = new ScrollPane();
		fstyle_sc.add(fstyle);
		fstyle_sc.setBounds(X+320,Y+170,80,85);
		add(fstyle_sc);
		fstyle.addListSelectionListener(this);
		
		Vector a = new Vector(40, 1);
		for (int i=1;i<=500;i++)
		{
			a.addElement(String.valueOf(i));
		}
		fsize = new JList(a);fsize.setSelectedIndex(11);
		fsize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fsize_label = new JLabel("Size: ");
		fsize_label.setBounds(X+230,Y+10, 80, 20);
		add(fsize_label);

		size=new JTextField("12");size.setBounds(X+230,Y+30, 80, 22);
		size.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));add(size);

		fsize_sc = new ScrollPane();
		fsize_sc.add(fsize);
		fsize_sc.setBounds(X+230,Y+52, 80,203);
		add(fsize_sc);
		fsize.addListSelectionListener(this);

		
		ok = new JButton("OK");
		ok.setBounds(X+320,Y+30, 80, 25);
		ok.addActionListener(this);
		add(ok);

		cancel = new JButton("Cancel");
		cancel.setBounds(X+320,Y+55, 80, 25);
		cancel.addActionListener(this);
		add(cancel);

		fprev_label  = new JLabel("Preview: ");
		fprev_label.setBounds(X+10,Y+265, 300, 20);
		add(fprev_label);
        // preview
		preview = new JLabel("Sample Text");
		preview.setBounds(X+10, Y+290, 390, 120);
		preview.setHorizontalAlignment(SwingConstants.CENTER);
		preview.setOpaque(true);
		preview.setBackground(Color.white);
		preview.setBorder(new LineBorder(Color.black, 1));
		add(preview);
		setSize(W,H);
		setVisible(false);
	}
	public void paintComponent(Graphics g2d)
	{
		Graphics2D g = (Graphics2D) g2d;
		if (g instanceof Graphics2D) 
		{
			Paint p =new GradientPaint(0.0f, 0.0f, new Color(0, 0, 0, 230),0.0f, getHeight(), new Color(0,0,0,160), true);
			Graphics2D gg = (Graphics2D)g;gg.setPaint(p);gg.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==ok)
			ok();
		else if(e.getSource()==cancel)
			cancel();
	}
	public void valueChanged(ListSelectionEvent l)
	{
		if(l.getSource()==flist)
		{
			preview.setText( flist.getSelectedValue().toString() );fonts.setText(flist.getSelectedValue().toString());
			changeFontSample();
		}
		else if(l.getSource()==fsize)
		{
			changeFontSample();
		}
		else if(l.getSource()==fstyle)
		{
			changeFontSample();
		}
	}
	private void changeFontSample()
	{
		try
		{
			font_name = flist.getSelectedValue().toString();font=flist.getSelectedIndex();
		}
		catch(NullPointerException npe)
		{
			font_name = "Verdana";
		}
		try
		{
			font_style = getStyle();
		}
		catch(NullPointerException npe)
		{
			font_style = Font.PLAIN;
		}
		try
		{
			font_size = Integer.parseInt(fsize.getSelectedValue().toString());size.setText(fsize.getSelectedValue().toString());
		}
		catch(NullPointerException npe)
		{
			font_size = 12;
		}
		sample = new Font(font_name, font_style, font_size);
		preview.setFont(sample);
	}
	private int getStyle()
	{
		if( fstyle.getSelectedValue().toString().equals("Bold") )
			{    style.setText(styles[1]);return Font.BOLD;}
		if(fstyle.getSelectedValue().toString().equals("Italic") )
			{   style.setText(styles[2]);return Font.ITALIC;}
		if(fstyle.getSelectedValue().toString().equals("Bold Italic"))
			{   style.setText(styles[3]);return Font.BOLD+Font.ITALIC;}
		else{style.setText(styles[0]);}
		return Font.PLAIN;
	}
	private void ok()
	{
		try
		{
			samp.Edit.t.setFont(sample);samp.Size.setValue(font_size);samp.font.setSelectedIndex(font);
		}
		catch(NullPointerException npe){}
		this.setVisible(false);
	}
	private void cancel()
	{
		this.setVisible(false);
	}
	public static void main()
	{
		JFrame Fonts=new JFrame();
	}
}