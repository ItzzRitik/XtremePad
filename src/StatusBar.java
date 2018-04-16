package src;

import java.awt.*;
import javax.swing.*;import java.awt.event.*;
public class StatusBar extends JPanel 
{
	JLabel resize;
	JPanel NorthPane,SouthPane;
	JProgressBar pro;
	public StatusBar()
	{     
		setLayout(new BorderLayout());setPreferredSize(new Dimension(10, 23));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(),BorderFactory.createEmptyBorder(1,1,1,1)));
		
		NorthPane=new JPanel(new BorderLayout());NorthPane.setOpaque(false);
		resize =new JLabel(new CornerIcon());resize.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));NorthPane.add(resize,BorderLayout.EAST);
		
		SouthPane=new JPanel(new BorderLayout());SouthPane.setOpaque(false);
		pro= new JProgressBar(0,2000);pro.setValue(1000);pro.setDoubleBuffered(true);pro.setStringPainted(true);
		SouthPane.add(pro,BorderLayout.CENTER);
		
		add(NorthPane,BorderLayout.NORTH);add(SouthPane,BorderLayout.SOUTH);
	}
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
}
class CornerIcon implements Icon 
{
	private static final Color WHITE_LINE_COLOR = new Color(255, 255, 255);
	private static final Color GRAY_LINE_COLOR = new Color(172, 168, 153);
	private static final int WIDTH = 13;
	private static final int HEIGHT = 13;
	public int getIconHeight() {return WIDTH;}
	public int getIconWidth(){ return HEIGHT;}
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		g.setColor(WHITE_LINE_COLOR);
		g.drawLine(0, 12, 12, 0);
		g.drawLine(5, 12, 12, 5);
		g.drawLine(10, 12, 12, 10);
		g.setColor(GRAY_LINE_COLOR);
		g.drawLine(1, 12, 12, 1);
		g.drawLine(2, 12, 12, 2);
		g.drawLine(3, 12, 12, 3);
		g.drawLine(6, 12, 12, 6);
		g.drawLine(7, 12, 12, 7);
		g.drawLine(8, 12, 12, 8);
		g.drawLine(11, 12, 12, 11);
		g.drawLine(12, 12, 12, 12);
	}
}
