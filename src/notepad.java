package src;

import java.awt.*;import java.awt.event.*;import java.awt.print.*;
import java.io.*;import java.net.*;import javax.swing.plaf.metal.*;
import java.text.SimpleDateFormat;import java.util.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.*;import javax.swing.filechooser.*;
import javax.swing.event.*;import javax.swing.undo.*;
import javax.swing.text.*;import javax.swing.text.rtf.*;import javax.swing.text.html.*;
import java.text.*;import ch.randelshofer.quaqua.*;import ch.randelshofer.quaqua.colorchooser.*;
public class notepad extends JFrame implements ActionListener,Runnable,HyperlinkListener
{
	Color Col[]=
	{
		new Color(50,120,250),new Color(255,100,0),new Color(220,0,0),new Color(0,160,16),new Color(130,10,140),new Color(220,40,90),
		new Color(70,70,70),new Color(220,150,5),new Color(220,200,30)
	};
	int Lines=0;
	Editor Edit=new Editor(this);
	Document doc;int pp=0;
	JProgressBar Progress;PrintPreview view;
	RTFEditorKit rtf = new RTFEditorKit();
	HTMLEditorKit html = new HTMLEditorKit();
	DefaultEditorKit def=new DefaultEditorKit();
	Container c;Thread th=new Thread(this);
	int opn=0;
	JScrollPane sc;
	JMenuBar menubar;JMenu file,edit,format,convert,help;
	JMenuItem file_new,file_open,file_save,file_save_as,file_print,file_close,file_exit;    
	JSeparator file_sep1,file_sep2,file_sep3;
	JMenuItem edit_undo,edit_redo,edit_copy,edit_cut,edit_paste,edit_delete,edit_find,edit_find_next,edit_replace,edit_selectall,edit_timedate;
	JSeparator edit_sep1,edit_sep2,edit_sep3;
	JMenuItem format_font,str2uppr, str2lwr,help_Report,help_about;
	UndoManager undo = new UndoManager();
	UIManager.LookAndFeelInfo lnf[];
	find finder;
	font_chooser fc;
	about abt;
	String path, content,recent=".";
	notepad Note;
	StatusBar sb;
	JTabbedPane Tab;
	JPanel Tab1,Tab2,Tab3;
	JToolBar File,Home,Format;
	
	JButton New,Load,Save,SaveAs,Print,Close,JLabel,Editor;
	JButton Cut,Copy,Paste,Delete,Undo,Redo,SelectAll,Find,TimeDate;
	String Fonts[];
	JComboBox font;
	JSlider Size;
	JLabel Sizes;
	JTextField size;
	JToggleButton Bold,Italic,Underline;
	int Siz=11,styl=0;
	String fnt="Verdana";
	JButton TextColour,TextFill;
	JPopupMenu popupMenu,popupMenu2;
	int Fill=0;
	
	JMenu skin,apple,special,substance,synthetica,system;
	JRadioButtonMenuItem jaguar,panther;
	JRadioButtonMenuItem graphite,acryl,aero,aluminium,bernstein,noire,fast,hifi,luna,mcwin,mint,texture,smart,tonic,tiny,napkin;
	JRadioButtonMenuItem Standard,BlackEye,BlueIce,BlueMoon,AluOxide,Classy,SkyMetallic,WhiteVision,BlackMoon,BlackStar,BlueLight,BlueSteel,GreenDream;
	JRadioButtonMenuItem MauveMetallic,OrangeMetallic,SilverMoon,Simple2D;
	
	JPanel Case,Palete,StyleBoard;ColorIconTEXT textcolor = new ColorIconTEXT();ColorIconFILL textfill = new ColorIconFILL();int jp=0;
	JOptionPane SaveAsk;String SaveString="<html>"+
	"<head>"+
	"<style type=\"text/css\">"+
	"b { font: 13pt \"Lucida Grande\" }"+
	"p { font: 11pt \"Lucida Grande\"; margin-top: 8px }"+
	"</style>"+
	"</head>"+
	"<b>Do You Want To Save Your Xtreme Progress<br>"+
	"before closing?</b><p>"+
	"If You Don't Save, Your Progress Will Be Lost.";
	Report rep;JOptionPane pane;
	public void Resend()
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				File[] list=new File("Reports").listFiles();
				for(int i=0;i<list.length;i++)
				{
					try
					{
						ArrayList Loader = new ArrayList();FileInputStream fis = null;ObjectInputStream in = null;
						fis = new FileInputStream(list[i].toString());in = new ObjectInputStream(fis);
						Loader = (ArrayList)in.readObject();in.close();ReportSave ReportLoader = (ReportSave)Loader.get(0); 
						rep.SendReport(ReportLoader.getName(),ReportLoader.getFirstName(),ReportLoader.getEmail(),ReportLoader.getEmailData(),list[i],0);
						System.out.println(list[i]);
					}
					catch(Exception e){}
				}
			}
		});
	}
	public void Tab()
	{
		Tab1=new JPanel(new BorderLayout());Tab1.setFocusable(false);
        //Tab1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		File = new JToolBar();File.setFloatable(false);File.setRollover(true);File.add(new JLabel("  "));
        //File.putClientProperty("Quaqua.ToolBar.style","bottom");
        Tab1.add(File,BorderLayout.CENTER);//File.setPreferredSize(new Dimension((int)getSize().getWidth(),60));
        
        New = new JButton(new ImageIcon("ToolBar/new.gif"));New.setFocusable(false);New.setToolTipText("Clears The Xtreme Pad.");
        //New.putClientProperty("JButton.buttonType", "bevel");
        New.setText("New ");New.setRolloverIcon(new ImageIcon("ToolBar/new2.gif"));New.setPreferredSize(new Dimension(70,60));
        File.add(New);New.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		file_new();
        	}
        });
        
        Load = new JButton(new ImageIcon("ToolBar/open.gif"));Load.setFocusable(false);Load.setToolTipText("Loads a File To Xtreme Pad.");
        //Load.putClientProperty("JButton.buttonType", "bevel");
        Load.setText("Load ");Load.setRolloverIcon(new ImageIcon("ToolBar/open2.gif"));Load.setPreferredSize(new Dimension(70,60));
        File.add(Load);Load.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		opn=1;
        	}
        });File.addSeparator();
        
        Save = new JButton(new ImageIcon("ToolBar/Save.gif"));Save.setFocusable(false);Save.setToolTipText("Saves The Text In Xtreme Pad To The Same File.");
        //Save.putClientProperty("JButton.buttonType", "bevel");
        Save.setText("Save ");Save.setRolloverIcon(new ImageIcon("ToolBar/Save2.gif"));Save.setPreferredSize(new Dimension(70,60));
        File.add(Save);Save.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		file_save();
        	}
        });
        
        SaveAs = new JButton(new ImageIcon("ToolBar/save.gif"));SaveAs.setFocusable(false);SaveAs.setToolTipText("Saves The Text In Xtreme Pad To a New File.");
        //SaveAs.putClientProperty("JButton.buttonType", "bevel");
        SaveAs.setText("Save As ");SaveAs.setRolloverIcon(new ImageIcon("ToolBar/save2.gif"));SaveAs.setPreferredSize(new Dimension(85,60));
        File.add(SaveAs);SaveAs.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		file_save_as();
        	}
        });File.addSeparator();
        
        Print = new JButton(new ImageIcon("ToolBar/print.png"));Print.setFocusable(false);Print.setToolTipText("Prints The Text In Xtreme Pad As a Hard Copy.");
        //Print.putClientProperty("JButton.buttonType", "bevel");
        Print.setText("Print ");Print.setRolloverIcon(new ImageIcon("ToolBar/print2.png"));Print.setPreferredSize(new Dimension(70,60));
        File.add(Print);Print.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		file_print();
        	}
        });
        
        Close = new JButton(new ImageIcon("ToolBar/close.png"));Close.setFocusable(false);Close.setToolTipText("Closes The Current File in The Xtreme Pad.");
        //Close.putClientProperty("JButton.buttonType", "bevel");
        Close.setText("Close ");Close.setRolloverIcon(new ImageIcon("ToolBar/close2.png"));Close.setPreferredSize(new Dimension(80,60));
        File.add(Close);Close.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		file_close();
        	}
        });File.addSeparator();
        
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        Tab2=new JPanel(new BorderLayout());Tab2.setFocusable(false);
        //Tab2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Home =new JToolBar();Home.setFloatable(false);Home.setRollover(true);Home.add(new JLabel("  "));
        Tab2.add(Home,BorderLayout.CENTER);Home.setPreferredSize(new Dimension((int)getSize().getWidth(),60));
        
        Cut = new JButton();Cut.setFocusable(false);Cut.setToolTipText("Cuts The Selected Text From The Xtreme Pad.");
        Cut.setRolloverIcon(new ImageIcon("ToolBar/cut2.png"));Cut.setPreferredSize(new Dimension(75,60));
        Home.add(Cut);Action cutAction = new StyledEditorKit.CutAction();Cut.setAction(cutAction);
        Cut.setText("Cut ");Cut.setIcon(new ImageIcon("ToolBar/cut.png"));
        
        Copy = new JButton();Copy.setFocusable(false);Copy.setToolTipText("Copies The Selected Text From Xtreme Pad The To Clipboard.");
        Copy.setRolloverIcon(new ImageIcon("ToolBar/copy2.png"));Copy.setPreferredSize(new Dimension(80,60));
        Home.add(Copy);Action copyAction = new StyledEditorKit.CopyAction();Copy.setAction(copyAction);
        Copy.setText("Copy  ");Copy.setIcon(new ImageIcon("ToolBar/copy.png"));
        
        Paste = new JButton(new ImageIcon("ToolBar/copy.png"));Paste.setFocusable(false);Paste.setToolTipText("Paste The Text To Xtreme Pad From The Clipboard.");
        Paste.setRolloverIcon(new ImageIcon("ToolBar/Copy2.png"));Paste.setPreferredSize(new Dimension(85,60));
        Home.add(Paste);Action pasteAction = new StyledEditorKit.PasteAction();Paste.setAction(pasteAction);
        Paste.setText("Paste ");Paste.setIcon(new ImageIcon("ToolBar/copy.png"));Home.addSeparator();
        
        Undo = new JButton(new ImageIcon("ToolBar/undo.gif"));Undo.setFocusable(false);Undo.setToolTipText("Undo Your Last Action.");
        Undo.setText("Undo ");Undo.setRolloverIcon(new ImageIcon("ToolBar/undo2.gif"));Undo.setPreferredSize(new Dimension(75,60));
        Home.add(Undo);Undo.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		edit_undo();
        	}
        });
        
        Redo = new JButton(new ImageIcon("ToolBar/redo.gif"));Redo.setFocusable(false);Redo.setToolTipText("Redo Your Last Action.");
        Redo.setText("Redo ");Redo.setRolloverIcon(new ImageIcon("ToolBar/redo2.gif"));Redo.setPreferredSize(new Dimension(75,60));
        Home.add(Redo);Redo.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		edit_redo();
        	}
        });Home.addSeparator();
        
        SelectAll = new JButton(new ImageIcon("ToolBar/select.png"));SelectAll.setFocusable(false);SelectAll.setToolTipText("Selects All The Text In The Xtreme Pad.");
        SelectAll.setText("Select All  ");SelectAll.setRolloverIcon(new ImageIcon("ToolBar/select2.png"));SelectAll.setPreferredSize(new Dimension(98,60));
        Home.add(SelectAll);SelectAll.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		edit_selectall();
        	}
        });
        
        Find = new JButton(new ImageIcon("ToolBar/find.png"));Find.setFocusable(false);Find.setToolTipText("Finds The Text In The Xtreme Pad.");
        Find.setText("Find ");Find.setRolloverIcon(new ImageIcon("ToolBar/find2.png"));Find.setPreferredSize(new Dimension(78,60));
        Home.add(Find);Find.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		edit_find();
        	}
        });Home.addSeparator();
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        Tab3=new JPanel(new BorderLayout());Tab3.setFocusable(false);
        //Tab3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Format =new JToolBar();Format.setFloatable(false);Format.setRollover(true);Format.add(new JLabel("  "));
        Tab3.add(Format,BorderLayout.CENTER);Format.setPreferredSize(new Dimension(10,85));
        
        Case=new JPanel(new BorderLayout());
        
        JPanel Case1=new JPanel(new BorderLayout());Case1.setPreferredSize(new Dimension(350,35)); 
        Fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        StyleBoard=new JPanel();JPanel SB=new JPanel(new BorderLayout());SB.setPreferredSize(new Dimension(350,25)); SB.add(StyleBoard,BorderLayout.WEST);
        font=new JComboBox(Fonts);font.setFocusable(false);font.setRenderer(new ComboBoxRenderer());//font.putClientProperty("JComponent.sizeVariant", "mini");
        font.setFont(new Font(Fonts[0],0,20));font.setPreferredSize(new Dimension(350,30));
        JPanel Sizzz=new JPanel(new BorderLayout());Sizzz.setPreferredSize(new Dimension(350,25)); Sizzz.add(font,BorderLayout.WEST);
        Case1.add(SB,BorderLayout.CENTER);Case1.add(Sizzz,BorderLayout.WEST);font.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		JComboBox cb4 = (JComboBox)e.getSource();fnt =cb4.getSelectedItem().toString();fc.flist.setSelectedIndex(cb4.getSelectedIndex());
                //Edit.t.setFont(new Font(fnt,styl,Siz));
        		Action action=new StyledEditorKit.FontFamilyAction(fnt,fnt);
        		action.actionPerformed(null);font.setFont(new Font(fnt,0,20));
        	}
        });
        
        
        JPanel Style=new JPanel(new GridLayout());Style.setPreferredSize(new Dimension(130,30));
        StyleBoard.add(Style);Style.add(new JLabel(""));
        
        Bold= new JToggleButton();Bold.setFocusable(false);Bold.setToolTipText("Bold Font");Style.add(Bold);
        Action boldAction=new StyledEditorKit.BoldAction();Bold.setAction(boldAction);
        Bold.setText("");Bold.setIcon(new ImageIcon("ToolBar/Text/bold.png"));
        
        Italic= new JToggleButton();Italic.setFocusable(false);Italic.setToolTipText("Italic Font");Style.add(Italic);
        Action italicAction=new StyledEditorKit.ItalicAction();Italic.setAction(italicAction);Italic.setText("");
        Italic.setIcon(new ImageIcon(new ImageIcon("ToolBar/Text/Italic.png").getImage().getScaledInstance(16,18,java.awt.Image.SCALE_SMOOTH)));
        
        Underline= new JToggleButton();Underline.setFocusable(false);Underline.setToolTipText("Bold-Italic Font");Style.add(Underline);
        Action underlineAction=new StyledEditorKit.UnderlineAction();Underline.setAction(underlineAction);Underline.setText("");
        Underline.setIcon(new ImageIcon(new ImageIcon("ToolBar/Text/Italic.png").getImage().getScaledInstance(20,19,java.awt.Image.SCALE_SMOOTH)));
        
        JPanel Case2=new JPanel(new BorderLayout());JPanel SizeSlide=new JPanel(new BorderLayout());
        Size=new JSlider(JSlider.HORIZONTAL,1,500,11);Size.setMinorTickSpacing(10);Size.setMajorTickSpacing(499);Size.setPaintTicks(true);
        Size.setPaintLabels(true);Size.setFocusable(false);Size.setPreferredSize(new Dimension(390,40));
        Size.addChangeListener(new ChangeListener()
        {
        	public void stateChanged(ChangeEvent e)
        	{
                JSlider source = (JSlider)e.getSource();Siz=(int)source.getValue();size.setText(Siz+"");//Edit.t.setFont(new Font(fnt,styl,Siz));
                Action action=new StyledEditorKit.FontSizeAction(String.valueOf(Siz),Siz);action.actionPerformed(null);
            }
        });SizeSlide.setPreferredSize(new Dimension(390,40));SizeSlide.add(Size,BorderLayout.WEST);Case2.add(SizeSlide,BorderLayout.CENTER);
        JPanel SizeBag=new JPanel(new BorderLayout());
        Sizes=new JLabel(" Size : ");Sizes.setFont(new Font("Verdana",Font.PLAIN, 18));SizeBag.add(Sizes,BorderLayout.WEST);
        size=new JTextField("11",2);size.setFont(new java.awt.Font("Verdana",Font.PLAIN, 18));size.setPreferredSize(new Dimension(40,40));
        SizeBag.add(size,BorderLayout.CENTER);
        SizeBag.add(new JLabel("     "),BorderLayout.EAST);SizeBag.setPreferredSize(new Dimension(145,40));size.addKeyListener(new KeyListener()
        {
        	public void keyTyped(KeyEvent e){}public void keyReleased(KeyEvent e){}
        	public void keyPressed(KeyEvent e)
        	{
        		if(e.getKeyCode()==10)
        		{
        			if(size.getText().equals(""))
        			{
        				Size.setValue(-1);Size.setValue(11);
        			}
        			else
        			{
        				int Value=Integer.parseInt(size.getText());
        				if(Value<=500){Size.setValue(Value);}
        				else{Size.setValue(500);size.setText(Siz+"");Siz=Value;Edit.t.setFont(new java.awt.Font(fnt, 0,Siz));}
        			}
        		}
        	}
        });
        Case2.add(SizeBag,BorderLayout.WEST);
        JPanel LEFTLayout1=new JPanel(new BorderLayout());LEFTLayout1.add(Case2,BorderLayout.WEST);
        
        JPanel BigCase=new JPanel(new BorderLayout());
        BigCase.add(Case1,BorderLayout.NORTH);BigCase.add(LEFTLayout1,BorderLayout.SOUTH);Case.add(BigCase,BorderLayout.WEST);
        Case2.setPreferredSize(new Dimension(550,40));Case.setMaximumSize(new java.awt.Dimension(550,80));   
        Format.add(Case);Format.addSeparator();
        
        TextColour=new JButton(textcolor);TextColour.setFocusable(false);TextColour.setToolTipText("Change The Color of Text in The Xtreme Pad.");
        TextColour.setPreferredSize(new Dimension(100,55));
        JPanel ColPane=new JPanel(new BorderLayout());ColPane.add(TextColour);ColPane.setMaximumSize(new Dimension(100,55)); 
        TextColour.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		if (UIManager.getLookAndFeel().toString().startsWith("[The Quaqua Leopard")){Fill=0;popupActionPerformed(e);}
        		else{Fill=0;dialogActionPerformed(e);}
        	}
        });Format.add(ColPane);
        
        TextFill=new JButton(textfill);TextFill.setFocusable(false);TextFill.setToolTipText("Change The Color of Text in The Xtreme Pad.");
        TextFill.setPreferredSize(new Dimension(100,55));
        JPanel FillColPane=new JPanel(new BorderLayout());FillColPane.add(TextFill);FillColPane.setMaximumSize(new Dimension(100,55)); 
        TextFill.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		if (UIManager.getLookAndFeel().toString().startsWith("[The Quaqua Leopard")){Fill=1;popupActionPerformed(e);}
        		else{Fill=1;dialogActionPerformed(e);}
        	}
        });Format.add(FillColPane);
        
        
        Tab=new JTabbedPane();Tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        Tab.addTab("   File   ",new ImageIcon(""),Tab1,"Contains Tools To Maintain Xtreme Pad."); 
        Tab.addTab(" Home ",new ImageIcon(""),Tab2,"Contains Tools To Handle The Xtreme Pad.");
        Tab.addTab(" Format ",new ImageIcon(""),Tab3,"Contains Tools To Edit Texts in Xtreme Pad.");
        Tab.setSelectedIndex(1);
    }
    class ColorIconTEXT implements Icon 
    {
    	private Color color = Color.black;
    	public void setColor(Color newValue){color = newValue;}
    	public Color getColor(){return color;}
    	public int getIconHeight(){return 12;}
    	public int getIconWidth(){return 24;}
    	public void paintIcon(Component c, Graphics g, int x, int y) 
    	{
    		if (color != null) 
    		{
    			g.setColor(color);
    			g.fillRect(x-25, y+15, getIconWidth()+50, getIconHeight()-4);
    			g.setColor(color.darker());
    			g.drawRect(x-25, y+15, getIconWidth()+49, getIconHeight() -5);
    			g.setFont(new java.awt.Font("Verdana",1,12));g.setColor(Color.black);g.drawString("Text Color",x-22,y+5);
    		}
    	}
    }
    class ColorIconFILL implements Icon 
    {
    	private Color color = Color.black;
    	public void setColor(Color newValue){color = newValue;}
    	public Color getColor(){return color;}
    	public int getIconHeight(){return 12;}
    	public int getIconWidth(){return 24;}
    	public void paintIcon(Component c, Graphics g, int x, int y) 
    	{
    		if (color != null) 
    		{
    			g.setColor(color);
    			g.fillRect(x-25, y+15, getIconWidth()+50, getIconHeight()-4);
    			g.setColor(color.darker());
    			g.drawRect(x-25, y+15, getIconWidth()+49, getIconHeight() -5);
    			g.setFont(new java.awt.Font("Verdana",1,12));g.setColor(Color.black);g.drawString("Text Fill",x-15,y+5);
    		}
    	}
    }
    private void dialogActionPerformed(java.awt.event.ActionEvent evt)
    {
    	try 
    	{
    		popupMenu2 = new JPopupMenu();final JColorChooser chooser = new JColorChooser();chooser.setPreviewPanel(new JPanel());popupMenu2.add(chooser);
    		chooser.getSelectionModel().addChangeListener(new ChangeListener() 
    		{
    			public void stateChanged(ChangeEvent e) 
    			{
    				if(Fill==0)
    				{
    					textcolor.setColor(chooser.getColor());TextColour.setIcon(textcolor);TextColour.repaint();
    					Action action=new StyledEditorKit.ForegroundAction("Color",chooser.getColor());action.actionPerformed(null);
    				}
    				else
    				{
    					textfill.setColor(chooser.getColor());TextFill.setIcon(textfill);TextFill.repaint();
                        //Action action=new StyledEditorKit.BackgroundAction("Color",chooser.getColor());action.actionPerformed(null);
    				}
    			}
    		});
    		if(Fill==0){popupMenu2.show(TextColour, 0, TextColour.getHeight());}else{popupMenu2.show(TextFill, 0, TextFill.getHeight());}
    	}
    	catch(Exception e){}
    }
    private void popupActionPerformed(java.awt.event.ActionEvent evt)
    {
    	if (popupMenu == null) 
    	{
    		popupMenu = new JPopupMenu() 
    		{
    			class MouseGrabber implements AWTEventListener, Serializable 
    			{
    				public void eventDispatched(AWTEvent ev)
    				{
    					if (!(ev instanceof MouseEvent) || !(ev.getSource() instanceof Component)){return;}
    					MouseEvent me = (MouseEvent) ev;Component src = (Component) ev.getSource();
    					if (me.getID() == MouseEvent.MOUSE_PRESSED)
    					{
    						if (!SwingUtilities.isDescendingFrom(src, popupMenu)&& SwingUtilities.getWindowAncestor(src)== 
    							SwingUtilities.getWindowAncestor(popupMenu.getInvoker())) 
    						{
    							JLayeredPane srcLP = (JLayeredPane) SwingUtilities.getAncestorOfClass(JLayeredPane.class, src);Component srcLPChild = src;
    							while (srcLPChild != null && srcLPChild.getParent() != srcLP) 
    							{
    								srcLPChild = srcLPChild.getParent();
    							}
    							if (srcLPChild == null || srcLP.getLayer(srcLPChild) < JLayeredPane.POPUP_LAYER) 
    							{
    								popupMenu.setVisible(false);
    							}
    						}
    					}
    					else if (me.getID()==MouseEvent.MOUSE_CLICKED&& me.getClickCount()==2) 
    					{
    						if (SwingUtilities.isDescendingFrom(src, popupMenu)){popupMenu.setVisible(false);}
    					}
    				}
    			};
    			private MouseGrabber mouseGrabber = new MouseGrabber();
    			@Override
    			public void menuSelectionChanged(boolean isIncluded) 
    			{
    				return;
    			}
    			@Override
    			public void setVisible(boolean newValue) 
    			{
    				if (isVisible() != newValue) 
    				{
    					if (newValue){Toolkit.getDefaultToolkit().addAWTEventListener(mouseGrabber, AWTEvent.MOUSE_EVENT_MASK);} 
    					else {Toolkit.getDefaultToolkit().removeAWTEventListener(mouseGrabber);}
    					super.setVisible(newValue);
    				}
    			}
    		};
    		popupMenu.putClientProperty("Quaqua.PopupMenu.windowAlpha", 1.0f);
    		final JColorChooser c = new JColorChooser();c.setPreviewPanel(new JPanel());popupMenu.add(c);
    		c.getSelectionModel().addChangeListener(new ChangeListener() 
    		{
    			public void stateChanged(ChangeEvent e) 
    			{
    				if(Fill==0)
    				{
    					textcolor.setColor(c.getColor());TextColour.setIcon(textcolor);TextColour.repaint();
    					Action action=new StyledEditorKit.ForegroundAction("Color",c.getColor());action.actionPerformed(null);
    				}
    				else
    				{
    					textfill.setColor(c.getColor());TextFill.setIcon(textfill);TextFill.repaint();
                        //Action action=new StyledEditorKit.BackgroundAction("Color",chooser.getColor());action.actionPerformed(null);
    				}
    			}
    		});
    	}
    	if(Fill==0){popupMenu.show(TextColour, 0, TextColour.getHeight());}else{popupMenu.show(TextFill, 0, TextFill.getHeight());}
    }
    public void run()
    {
    	try
    	{
    		if(opn==1){opn=0;file_open();}
    		Thread.sleep(1000);run();
    	}
    	catch(Exception e){run();}
    }
    public notepad()
    {
    	super("Xtreme Pad");
    	try
    	{
    		File file = new File("Icons\\Icon.ico");
    		sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.getShellFolder(file);
    		this.setIconImage(sf.getIcon(true));
    	}
    	catch(Exception e){}
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);addWindowListener(new WindowAdapter()
    	{
    		@Override
    		public void windowClosing(WindowEvent w)
    		{
    			file_exit();
    		}
    	});
    	notepad.this.addMouseListener(new ComponentResizer(this));
    	Progress=new JProgressBar(0,500);Progress.setDoubleBuffered(true);
    	Progress.setPreferredSize(new Dimension((int)getSize().getWidth(),5));Progress.setVisible(false);
    	
    	Container c = getContentPane();c.setLayout(new BorderLayout());
    	Tab();c.add(Tab,BorderLayout.NORTH); 
    	Edit.t.addHyperlinkListener(this);Edit.t.setEditorKit(rtf);
        sc = new JScrollPane();sc.setAutoscrolls(true);sc.setViewportView(Edit); //adding scrollbar to text area;
        JPanel ProText=new JPanel(new BorderLayout());
        JPanel FindPro=new JPanel(new BorderLayout());FindPro.add(Progress,BorderLayout.CENTER);
        ProText.add(FindPro,BorderLayout.NORTH);ProText.add(sc,BorderLayout.CENTER);
        c.add(ProText,BorderLayout.CENTER);
        menubar = new JMenuBar();
        file = new JMenu("File");
        file_new = new JMenuItem("New");file_new.setIcon(new ImageIcon("ToolBar/new.gif"));
        file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        file_new.addActionListener(this);
        file.add(file_new);        
        file_open = new JMenuItem("Open");file_open.setIcon(new ImageIcon("ToolBar/open.gif"));
        file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        file_open.addActionListener(this);
        file.add(file_open);        
        file_sep1 = new JSeparator();
        file.add(file_sep1);        
        file_save = new JMenuItem("Save");file_save.setIcon(new ImageIcon("ToolBar/save.gif"));
        file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        file_save.addActionListener(this);
        file.add(file_save);        
        file_save_as = new JMenuItem("Save As");file_save_as.setIcon(new ImageIcon("ToolBar/save.gif"));
        file_save_as.addActionListener(this);
        file.add(file_save_as);        
        file_sep2 = new JSeparator();
        file.add(file_sep2);        
        file_print = new JMenuItem("Print");file_print.setIcon(new ImageIcon("ToolBar/print.png"));
        file_print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        file_print.addActionListener(this);        
        file.add(file_print);             
        file_sep3 = new JSeparator();
        file.add(file_sep3);        
        file_close = new JMenuItem("Close");file_close.setIcon(new ImageIcon("ToolBar/close.png"));
        file_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK));
        file_close.addActionListener(this);
        file.add(file_close);        
        file_exit = new JMenuItem("Exit");file_exit.setIcon(new ImageIcon("ToolBar/power.png"));
        file_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        file_exit.addActionListener(this);
        file.add(file_exit);
        menubar.add(file);
        
        edit = new JMenu("Edit");       
        edit_undo = new JMenuItem("Undo");edit_undo.setIcon(new ImageIcon("ToolBar/undo.gif"));
        edit_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        edit_undo.addActionListener(this);
        edit.add(edit_undo);
        edit_redo = new JMenuItem("Redo");edit_redo.setIcon(new ImageIcon("ToolBar/redo.gif"));
        edit_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        edit_redo.addActionListener(this);
        edit.add(edit_redo);        
        edit_sep1 = new JSeparator();
        edit.add(edit_sep1);        
        edit_copy = new JMenuItem("Copy");edit_copy.setIcon(new ImageIcon("ToolBar/copy.png"));
        edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        edit_copy.addActionListener(this);        
        edit.add(edit_copy);        
        edit_cut = new JMenuItem("Cut");edit_cut.setIcon(new ImageIcon("ToolBar/cut.png"));
        edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        edit_cut.addActionListener(this);
        edit.add(edit_cut);        
        edit_paste = new JMenuItem("Paste");edit_paste.setIcon(new ImageIcon("ToolBar/copy.png"));
        edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        edit_paste.addActionListener(this);
        edit.add(edit_paste);       
        edit_sep2 = new JSeparator();
        edit.add(edit_sep2);        
        edit_find = new JMenuItem("Find");edit_find.setIcon(new ImageIcon("ToolBar/find.png"));
        edit_find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        edit_find.addActionListener(this);
        edit.add(edit_find);        
        edit_find_next = new JMenuItem("Find Next");edit_find_next.setIcon(new ImageIcon("ToolBar/findNext.png"));
        edit_find_next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        edit_find_next.addActionListener(this);
        edit.add(edit_find_next);       
        edit_replace = new JMenuItem("Replace");edit_replace.setIcon(new ImageIcon("ToolBar/replace.png"));
        edit_replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        edit_replace.addActionListener(this);
        edit.add(edit_replace);        
        edit_sep3 = new JSeparator();
        edit.add(edit_sep3);        
        edit_selectall = new JMenuItem("Select All");edit_selectall.setIcon(new ImageIcon("ToolBar/select.png"));
        edit_selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        edit_selectall.addActionListener(this);
        edit.add(edit_selectall);        
        edit_timedate = new JMenuItem("Time/Date");
        edit_timedate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        edit_timedate.addActionListener(this);
        edit.add(edit_timedate);  
        
        edit.add(new JSeparator());
        Action boldAction = new StyledEditorKit.BoldAction();
        boldAction.putValue(Action.NAME, "Bold");
        boldAction.putValue(Action.ACCELERATOR_KEY, makeAccelerator("B"));
        edit.add(boldAction);
        
        Action italicAction = new StyledEditorKit.ItalicAction();
        italicAction.putValue(Action.NAME, "Italic");
        italicAction.putValue(Action.ACCELERATOR_KEY, makeAccelerator("I"));
        edit.add(italicAction);
        
        Action underlineAction = new StyledEditorKit.UnderlineAction();
        underlineAction.putValue(Action.NAME, "Underlined");
        underlineAction.putValue(Action.ACCELERATOR_KEY, makeAccelerator("U"));
        edit.add(underlineAction);
        menubar.add(edit);
        
        
        skin = new JMenu("Skin");ButtonGroup LAF=new ButtonGroup();
        
        apple= new JMenu("Apple Mac");skin.add(apple);
        jaguar = new JRadioButtonMenuItem("Jaguar");jaguar.addActionListener(this);LAF.add(jaguar);apple.add(jaguar);jaguar.setSelected(true);
        panther = new JRadioButtonMenuItem("Panther");panther.addActionListener(this);LAF.add(panther);apple.add(panther);
        
        ///////////////-----------------------------------------------------
        
        special= new JMenu("Exciting");skin.add(special);
        graphite = new JRadioButtonMenuItem("Graphite");graphite.addActionListener(this);LAF.add(graphite);special.add(graphite);
        acryl = new JRadioButtonMenuItem("Acryl");acryl.addActionListener(this);LAF.add(acryl);special.add(acryl);
        aero = new JRadioButtonMenuItem("Aero");aero.addActionListener(this);LAF.add(aero);special.add(aero);
        aluminium = new JRadioButtonMenuItem("Aluminium");aluminium.addActionListener(this);LAF.add(aluminium);special.add(aluminium);
        bernstein = new JRadioButtonMenuItem("Bernstein");bernstein.addActionListener(this);LAF.add(bernstein);special.add(bernstein);
        noire = new JRadioButtonMenuItem("Noire");noire.addActionListener(this);LAF.add(noire);special.add(noire);
        fast = new JRadioButtonMenuItem("Fast");fast.addActionListener(this);LAF.add(fast);special.add(fast);
        hifi = new JRadioButtonMenuItem("Hifi");hifi.addActionListener(this);LAF.add(hifi);special.add(hifi);
        luna = new JRadioButtonMenuItem("Luna");luna.addActionListener(this);LAF.add(luna);special.add(luna);
        mcwin = new JRadioButtonMenuItem("McWin");mcwin.addActionListener(this);LAF.add(mcwin);special.add(mcwin);
        mint = new JRadioButtonMenuItem("Mint");mint.addActionListener(this);LAF.add(mint);special.add(mint);
        texture = new JRadioButtonMenuItem("Texture");texture.addActionListener(this);LAF.add(texture);special.add(texture);
        smart = new JRadioButtonMenuItem("Smart");smart.addActionListener(this);LAF.add(smart);special.add(smart);
        tonic = new JRadioButtonMenuItem("Tonic");tonic.addActionListener(this);LAF.add(tonic);special.add(tonic);
        tiny = new JRadioButtonMenuItem("Tiny");tiny.addActionListener(this);LAF.add(tiny);special.add(tiny);
        napkin = new JRadioButtonMenuItem("Napkin");napkin.addActionListener(this);LAF.add(napkin);special.add(napkin);
        
        ///////////////-----------------------------------------------------
        substance = new JMenu("Substance");skin.add(substance);
        String [] list=SkinName(new File("org\\jvnet\\substance\\skin").listFiles());
        for(int i = 0; i < list.length; i++)
        {
        	int dot=list[i].lastIndexOf('.');if(i<=0){continue;}
        	JRadioButtonMenuItem LaF = new JRadioButtonMenuItem(list[i].substring(dot+1));
        	LaF.addActionListener(new SubstanceAction(this));LAF.add(LaF);substance.add(LaF);
        }
        
        ///////////////-----------------------------------------------------
        
        synthetica= new JMenu("Synthetica");skin.add(synthetica);
        Standard = new JRadioButtonMenuItem("Standard");Standard.addActionListener(this);LAF.add(Standard);synthetica.add(Standard);
        BlackEye = new JRadioButtonMenuItem("Black Eye");BlackEye.addActionListener(this);LAF.add(BlackEye);synthetica.add(BlackEye);
        BlueIce = new JRadioButtonMenuItem("Blue Ice");BlueIce.addActionListener(this);LAF.add(BlueIce);synthetica.add(BlueIce);
        BlueMoon = new JRadioButtonMenuItem("Blue Moon");BlueMoon.addActionListener(this);LAF.add(BlueMoon);synthetica.add(BlueMoon);
        AluOxide = new JRadioButtonMenuItem("Aluminium Oxide");AluOxide.addActionListener(this);LAF.add(AluOxide);synthetica.add(AluOxide);
        Classy = new JRadioButtonMenuItem("Classy");Classy.addActionListener(this);LAF.add(Classy);synthetica.add(Classy);
        SkyMetallic = new JRadioButtonMenuItem("Sky Metallic");SkyMetallic.addActionListener(this);LAF.add(SkyMetallic);synthetica.add(SkyMetallic);
        WhiteVision = new JRadioButtonMenuItem("White Vision");WhiteVision.addActionListener(this);LAF.add(WhiteVision);synthetica.add(WhiteVision);
        BlackMoon = new JRadioButtonMenuItem("Black Moon");BlackMoon.addActionListener(this);LAF.add(BlackMoon);synthetica.add(BlackMoon);
        BlackStar = new JRadioButtonMenuItem("Black Star");BlackStar.addActionListener(this);LAF.add(BlackStar);synthetica.add(BlackStar);
        BlueLight = new JRadioButtonMenuItem("Blue Light");BlueLight.addActionListener(this);LAF.add(BlueLight);synthetica.add(BlueLight);
        BlueSteel = new JRadioButtonMenuItem("Blue Steel");BlueSteel.addActionListener(this);LAF.add(BlueSteel);synthetica.add(BlueSteel);
        GreenDream = new JRadioButtonMenuItem("Green Dream");GreenDream.addActionListener(this);LAF.add(GreenDream);synthetica.add(GreenDream);
        MauveMetallic = new JRadioButtonMenuItem("Mauve Metallic");MauveMetallic.addActionListener(this);LAF.add(MauveMetallic);synthetica.add(MauveMetallic);
        OrangeMetallic = new JRadioButtonMenuItem("Orange Metallic");OrangeMetallic.addActionListener(this);LAF.add(OrangeMetallic);synthetica.add(OrangeMetallic);
        SilverMoon= new JRadioButtonMenuItem("Silver Moon");SilverMoon.addActionListener(this);LAF.add(SilverMoon);synthetica.add(SilverMoon);
        Simple2D= new JRadioButtonMenuItem("Simple2D");Simple2D.addActionListener(this);LAF.add(Simple2D);synthetica.add(Simple2D);
        
        system= new JMenu("System Installed");skin.add(system);
        UIManager.LookAndFeelInfo lookAndFeels[] = UIManager.getInstalledLookAndFeels();
        for(int i = 0; i < lookAndFeels.length; i++)
        {
        	JRadioButtonMenuItem LaF = new JRadioButtonMenuItem(lookAndFeels[i].getName());
        	LaF.addActionListener(new LAFAction(this));LAF.add(LaF);system.add(LaF);
        }
        menubar.add(skin);  
        
        format = new JMenu("Format");        
        format_font = new JMenuItem("Font");
        format_font.addActionListener(this);
        format.add(format_font);
        
        convert = new JMenu("Convert");
        str2uppr = new JMenuItem("To Uppercase...");
        str2uppr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_MASK));
        str2uppr.addActionListener(this);
        convert.add(str2uppr);
        str2lwr = new JMenuItem("To Lowercase...");
        str2lwr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_MASK));
        str2lwr.addActionListener(this);        
        convert.add(str2lwr);        
        format.add(convert);
        
        help = new JMenu("Help");  
        
        help_Report = new JMenuItem("Report and Suggestions");
        help_Report.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));
        help_Report.addActionListener(this);
        help.add(help_Report);  
        
        help_about = new JMenuItem("About");
        help_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        help_about.addActionListener(this);
        help.add(help_about);         
        menubar.add(help); 
        
        setJMenuBar(menubar);
        
        // undo manager
        doc= Edit.t.getDocument();
        doc.addUndoableEditListener(
        	new UndoableEditListener( )
        	{
        		public void undoableEditHappened( UndoableEditEvent event )
        		{
        			undo.addEdit(event.getEdit());
        		}
        	});
        finder = new find(this);
        fc = new font_chooser(this);
        abt = new about(this);
        rep=new Report(this);
        pane= new JOptionPane(rep);
        sb=new StatusBar();
        c.add(sb,BorderLayout.SOUTH);
        try
        {
        	Scanner in=new Scanner(new File("Icons/Size.txt"));in.useDelimiter(",");int W=800,H=850,X=0,Y=0;
        	W=Integer.parseInt(in.next());H=Integer.parseInt(in.next());X=Integer.parseInt(in.next());Y=Integer.parseInt(in.next());
        	setSize(W,H);setLocation(X,Y);recent=in.next();
        }
        catch(Exception e){}
        path = "";th.start();doc = Edit.t.getDocument();Edit.t.requestFocus(true);
    }
    public String [] SkinName(File[] list)
    {
    	int Siz=0;
    	for(int i=0;i<list.length;i++)
    	{
    		if(list[i].toString().endsWith("Skin.class")){Siz++;}
    	}
    	String[] Skin=new String[Siz];int index=0;
    	for(int i=0;i<list.length;i++)
    	{
    		if(list[i].toString().endsWith("Skin.class"))
    		{
    			String temp=list[i].toString();
    			for(int j=0;j<temp.length();j++)
    			{
    				temp=temp.replace('\\','.');
    			}
    			int dot=temp.lastIndexOf('.');
    			Skin[index]=temp.substring(0,dot);index++;
    		}
    	}
    	return Skin;
    }
    class SubstanceAction implements ActionListener
    {
    	notepad Note;
    	SubstanceAction(notepad note)
    	{
    		Note=note;
    	}
    	public void actionPerformed(ActionEvent ae)
    	{
    		Object EventSource = ae.getSource();String lookAndFeelClassName = null;
    		lookAndFeelClassName="org.jvnet.substance.skin."+ae.getActionCommand();
    		org.jvnet.substance.SubstanceLookAndFeel.setSkin(lookAndFeelClassName);SwingUtilities.updateComponentTreeUI(Note);
    		SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
            /*try
            {
                UIManager.setLookAndFeel(lookAndFeelClassName);SwingUtilities.updateComponentTreeUI(Note);
                SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
            }
            catch(Exception e)
            {
                JSheet.showMessageSheet(Note, "Can't Change The Skin");
            }*/
        }
    }
    class LAFAction implements ActionListener
    {
    	notepad Note;
    	LAFAction(notepad note)
    	{
    		Note=note;
    	}
    	public void actionPerformed(ActionEvent ae)
    	{
    		Object EventSource = ae.getSource();
    		String lookAndFeelClassName = null;
    		UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
    		for(int i = 0; i < looks.length; i++)
    		{
    			if(ae.getActionCommand().equals(looks[i].getName()))
    			{
    				lookAndFeelClassName = looks[i].getClassName();break;
    			}
    		}
    		try
    		{
    			if(lookAndFeelClassName.equals("javax.swing.plaf.nimbus.NimbusLookAndFeel"))
    			{
    				UIManager.setLookAndFeel(lookAndFeelClassName);SwingUtilities.updateComponentTreeUI(Note);
    				UIManager.getLookAndFeelDefaults().put("nimbusOrange",Col[new Random().nextInt(Col.length)]);
    				SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			}
    			if(lookAndFeelClassName.equals("javax.swing.plaf.metal.MetalLookAndFeel"))
    			{
                    MetalLookAndFeel.setCurrentTheme(new OceanTheme());//UIManager.removeAuxiliaryLookAndFeel(UIManager.getLookAndFeel());
                    UIManager.setLookAndFeel(lookAndFeelClassName);SwingUtilities.updateComponentTreeUI(Note);
                    SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
                }
                else
                {
                	UIManager.setLookAndFeel(lookAndFeelClassName);SwingUtilities.updateComponentTreeUI(Note);
                	SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
                }
            }
            catch(Exception e)
            {
            	JSheet.showMessageSheet(Note, "Can't Change The Skin");
            }
        }
    }
    private static KeyStroke makeAccelerator(String description) 
    {
    	String commandKey;if ( System.getProperty("mrj.version") == null )commandKey = "ctrl";
    	else commandKey = "meta";
    	return KeyStroke.getKeyStroke( commandKey + " " + description );
    }
    public void actionPerformed(ActionEvent e)
    {
    	Properties prop=new Properties();prop.put("logoString","Xtreme Pad");String SyntheticaPack="de.javasoft.plaf.synthetica.";
    	try
    	{
    		if(e.getSource()==file_new){file_new();}
    		else if(e.getSource()==file_open){opn=1;}
    		else if(e.getSource()==file_save){file_save();}
    		else if(e.getSource()==file_save_as){file_save_as();}
    		else if(e.getSource()==file_print){file_print();}
    		else if(e.getSource()==file_close){file_close();}
    		else if(e.getSource()==file_exit){file_exit();}
    		
    		else if(e.getSource()==edit_undo){edit_undo();}
    		else if(e.getSource()==edit_redo){edit_redo();}
    		else if(e.getSource()==edit_cut){edit_cut();}
    		else if(e.getSource()==edit_copy){edit_copy();}
    		else if(e.getSource()==edit_paste){edit_paste();}
    		else if(e.getSource()==edit_selectall){edit_selectall();}
    		else if(e.getSource()==edit_find){edit_find();}
    		else if(e.getSource()==edit_timedate){edit_timedate();}     
    		
    		else if(e.getSource()==format_font){format_font();}
    		else if(e.getSource()==str2uppr){str2uppr();}
    		else if(e.getSource()==str2lwr){str2lwr();}
    		
    		else if(e.getSource()==help_about){help_about();}
    		else if(e.getSource()==help_Report)
    		{
    			String CLOSE="<html><head><style type=\"text/css\">"+
    			"p { font: 18pt \"Courier\"; margin-top: 5px;margin-bottom: 5px;margin-left: 9px;margin-right: 9px }</style></head>"+
    			"</b><p>Close</b>";
    			Object[] options = {rep.Submit,CLOSE};pane.setOptions(options);pane.setInitialValue(options[0]);
    			pane.putClientProperty("Quaqua.OptionPane.destructiveOption", 1);
    			JSheet.showSheet(pane, Edit, new SheetListener()
    			{
    				public void optionSelected(SheetEvent evt) 
    				{
    					
    				}
    			});
    		}
    		
    		else if(e.getSource()==jaguar)
    		{
    			System.setProperty("Quaqua.design","Mac OSX");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");SwingUtilities.updateComponentTreeUI(this);
    			SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}  
    		else if(e.getSource()==panther)
    		{
    			System.setProperty("Quaqua.design","Tiger");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");SwingUtilities.updateComponentTreeUI(this);
    			SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}  
    		else if(e.getSource()==graphite)
    		{
    			com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==acryl)
    		{
    			com.jtattoo.plaf.acryl.AcrylLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==aero)
    		{
    			com.jtattoo.plaf.aero.AeroLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==aluminium)
    		{
    			com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==bernstein)
    		{
    			com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==noire)
    		{
    			com.jtattoo.plaf.noire.NoireLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==fast)
    		{
    			com.jtattoo.plaf.fast.FastLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==hifi)
    		{
    			com.jtattoo.plaf.hifi.HiFiLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==luna)
    		{
    			com.jtattoo.plaf.luna.LunaLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==mcwin)
    		{
    			com.jtattoo.plaf.mcwin.McWinLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==mint)
    		{
    			com.jtattoo.plaf.mint.MintLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==texture)
    		{
    			com.jtattoo.plaf.texture.TextureLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==smart)
    		{
    			com.jtattoo.plaf.smart.SmartLookAndFeel.setCurrentTheme(prop);MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==tonic)
    		{
    			UIManager.setLookAndFeel("com.digitprop.tonic.TonicLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==tiny)
    		{
    			UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==napkin)
    		{
    			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		/**----------------------------------------------------------------------------------------------------------------*/
    		
    		else if(e.getSource()==Standard)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaStandardLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlackEye)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlackEyeLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setForeground(new Color(0,0,0));Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlueIce)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlueIceLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlueMoon)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlueMoonLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==AluOxide)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaAluOxideLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==Classy)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaClassyLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==SkyMetallic)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaSkyMetallicLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==WhiteVision)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaWhiteVisionLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlackMoon)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlackMoonLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlackStar)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlackStarLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlueLight)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlueLightLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==BlueSteel)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaBlueSteelLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==GreenDream)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaGreenDreamLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==MauveMetallic)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaMauveMetallicLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==OrangeMetallic)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaOrangeMetallicLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==SilverMoon)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaSilverMoonLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    		else if(e.getSource()==Simple2D)
    		{
    			UIManager.setLookAndFeel(SyntheticaPack+"SyntheticaSimple2DLookAndFeel");MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    			SwingUtilities.updateComponentTreeUI(this);SwingUtilities.updateComponentTreeUI(rep);SwingUtilities.updateComponentTreeUI(pane);
    			SwingUtilities.updateComponentTreeUI(Edit);Edit.t.setBackground(new Color(225,225,230));
    		}
    	}
    	catch(Exception ex){}
    }
    public void file_new()
    {
    	try
    	{
    		doc = Edit.t.getDocument();
    		if(doc.getText(0,doc.getLength()).equals("") || doc.getText(0,doc.getLength()).equals(content))
    		{
    			Edit.t.setEditorKit(html);Edit.t.setText("");content = "";path = "";setTitle("Xtreme Pad");
    		}
    		else
    		{
    			SaveAsk= new JOptionPane(SaveString,JOptionPane.WARNING_MESSAGE);
    			Object[] options = {"Save","Don't Save","Cancel"};
    			SaveAsk.setOptions(options);
    			SaveAsk.setInitialValue(options[0]);
    			SaveAsk.putClientProperty("Quaqua.OptionPane.destructiveOption", 2);
    			JSheet.showSheet(SaveAsk, this, new SheetListener()
    			{
    				public void optionSelected(SheetEvent evt) 
    				{
    					int a=evt.getOption();
    					if(a==0){file_save();}
    					else if(a==1)
    					{
    						Edit.t.setEditorKit(rtf);path = "";Edit.t.setText("");content = "";setTitle("Xtreme Pad");
    					}
    					else if(a==2){return;}
    				}
    			});
    		}
    	}
    	catch(Exception e){}
    }
    public String getExtention(String File)
    {
    	String ext="";
    	int i=File.lastIndexOf('.');if(i>0){ext=File.substring(i+1);}
    	return ext.toLowerCase();
    }
    public void file_open()
    {
    	try
    	{
    		doc = Edit.t.getDocument();
    		if(doc.getText(0,doc.getLength()).equals("") || doc.getText(0,doc.getLength()).equals(content))
    		{
    			jp=1;Edit.t.setEditorKit(html);Edit.t.setText("");content = "";path = "";setTitle("Xtreme Pad");
    		}
    		else
    		{
    			if (UIManager.getLookAndFeel().toString().startsWith("[The Quaqua Leopard"))
    			{
    				SaveAsk= new JOptionPane(SaveString,JOptionPane.WARNING_MESSAGE);
    				Object[] options = {"Save","Don't Save","Cancel"};
    				SaveAsk.setOptions(options);
    				SaveAsk.setInitialValue(options[0]);
    				SaveAsk.putClientProperty("Quaqua.OptionPane.destructiveOption", 2);
    				JSheet.showSheet(SaveAsk, this, new SheetListener()
    				{
    					public void optionSelected(SheetEvent evt) 
    					{
    						int a=evt.getOption();
    						if(a==0){file_save();if(jp==1){file_open();}}
    						else if(a==1)
    						{
    							jp=1;Edit.t.setEditorKit(html);path = "";Edit.t.setText("");content = "";setTitle("Xtreme Pad");
    						}
    						else if(a==2){return;}
    					}
    				});
    			}
    			else
    			{
    				int a = JOptionPane.showConfirmDialog(this, "The Text In The Xtreme Pad Been Changed\nDo You Want To Save The Changes?");
    				if(a==0){file_save();jp=1;file_open();}
    				else if(a==1)
    				{
    					jp=1;Edit.t.setEditorKit(html);path = "";setTitle("Xtreme Pad");
    				}
    				else if(a==2){return;}
    			}
    		}
    	}
    	catch(Exception e){}
    	
    	final JFileChooser fc = new JFileChooser();fc.setCurrentDirectory(new File(recent));
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Rich Text Format (RTF)", "rtf");fc.setFileFilter(filter);
    	filter = new FileNameExtensionFilter("Hyper Text Document (HTML)", "html","htm");fc.setFileFilter(filter);
    	filter = new FileNameExtensionFilter("Text Document (TXT)", "txt");fc.setFileFilter(filter);
    	filter = new FileNameExtensionFilter("Xtreme Text Document (XTD)", "xtd");fc.setFileFilter(filter);
    	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	if(jp==1){jp=0;JSheet.showOpenSheet(fc, this, new SheetListener() 
    		{
    			public void optionSelected(SheetEvent evt) 
    			{
    				if(evt.getOption()==0)
    				{
    					File myfile = fc.getSelectedFile();String FilePath=fc.getSelectedFile().getPath();Edit.t.setText("");
    					if(myfile == null || myfile.getName().equals("")){return;}
    					if(getExtention(myfile.toString()).equals("rtf"))
    					{
    						try 
    						{
    							Edit.t.setEditorKit(rtf);
    							FileInputStream fi = new FileInputStream(myfile.toString());
    							rtf.read( fi,Edit.t.getDocument(),0);
    							path = myfile.toString();recent=path;Edit.t.requestFocus();
    						}
    						catch( FileNotFoundException e ){System.out.println( "File not found" );}
    						catch( IOException e ){System.out.println( "I/O error" );}
    						catch( BadLocationException e ){}catch(Exception e){System.out.println( "I/O error" );}
    					}
    					else if(getExtention(myfile.toString()).equals("html")||getExtention(myfile.toString()).equals("htm"))
    					{
    						try
    						{
    							Edit.t.setEditorKit(html);
    							FileInputStream fi = new FileInputStream(myfile.toString());path = myfile.toString();recent=path;
                            //html.read( fi,Edit.t.getDocument(),0);
    							Edit.t.setPage(new URL("File:///"+myfile.toString()));
    							Edit.t.requestFocus();Edit.t.setSize(null);
    						}
    						catch(IOException e){JSheet.showMessageSheet(Edit, "IO ERROR:"+e);}
    						catch(OutOfMemoryError e){}catch(Exception e){}
    					}
    					else if(getExtention(myfile.toString()).equals("xtd"))
    					{
    						try
    						{
    							ArrayList Loader = new ArrayList();FileInputStream fis = null;ObjectInputStream in = null;
    							
    							Edit.t.setEditorKit(rtf);
    							fis = new FileInputStream(myfile.toString());
    							in = new ObjectInputStream(fis);
    							Loader = (ArrayList)in.readObject();in.close();
    							Save sav = (Save)Loader.get(0);Edit.t.setDocument(sav.getDocument());
    							
    							path = myfile.toString();recent=path;Edit.t.requestFocus();Edit.t.setSize(null);
    						}
    						catch(IOException e){JSheet.showMessageSheet(Edit, "IO ERROR:"+e);}
    						catch(OutOfMemoryError e){}catch(Exception e){}
    					}
    					else
    					{
    						try
    						{   
    							Edit.t.setEditorKit(null);
    							BufferedReader in = new BufferedReader(new FileReader(myfile));String lines="";while((lines = in.readLine()) != null){Lines++;}
    							BufferedReader input = new BufferedReader(new FileReader(myfile));
    							StringBuffer str = new StringBuffer();String line;int ii=0;
    							while((line = input.readLine()) != null)
    							{
    								ii++;str.append(line+"\n");
    							}
    							in.close();input.close();Edit.t.setText(str.toString());content = Edit.t.getText();
    							doc = Edit.t.getDocument();Edit.t.setEditorKit(html);Edit.t.setDocument(doc);Edit.t.requestFocus();
    						}
    						catch(FileNotFoundException e){JSheet.showMessageSheet(Edit, "File not found: "+e);}
    						catch(IOException e){JSheet.showMessageSheet(Edit, "IO ERROR:"+e);}
    						catch(OutOfMemoryError e)
    						{
    							Progress.setVisible(false);
    							JSheet.showConfirmSheet(Edit, "Sorry !!! An Error Occured. Your Default Memory is Very Low To Load a Large File."+
    								"\nCannot Load The Selected File.  This File is Out of Capacity of Your System"+
    								"\nThis File Contains Text With "+Lines+" Lines. Which is Not Suported By The Default Memory."+
    								"\n\nXtreme Pad Needs a Restart, Do You Want To Close The Xtreme Pad. ", new SheetListener() 
    								{
    									public void optionSelected(SheetEvent evt) 
    									{
    										int Err=evt.getOption();
    										if(Err==0){System.exit(0);}
    										else if(Err==1){JSheet.showMessageSheet(Edit, "Xtreme Pad May Run Slower Due To Less Memory.");}
    									}
    								});
    						}
    						catch(Exception e){}
    					}
    				}
    				else{return;}
    			}            
    		});}

try
{ 
            /*Random Ra=new Random();UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.getLookAndFeelDefaults().put("nimbusOrange",Col[Ra.nextInt(Col.length-1)]);
            Progress.setIndeterminate(true);Progress.setIndeterminate(false);//Thread.sleep(10);SwingUtilities.updateComponentTreeUI(this);*/
        }catch(Exception e){}
    }
    public void hyperlinkUpdate(HyperlinkEvent event)
    {
    	if( event.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
    	{
    		Cursor cursor = Edit.t.getCursor();Cursor waitCursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );
            //Edit.t.setCursor( waitCursor );SwingUtilities.invokeLater( new PageLoader( Edit.t,event.getURL(), cursor ) );
    	}
    }
    public void open(File myfile)
    {
    	Edit.t.setText("");
    	if(myfile == null || myfile.getName().equals(""))
    	{
    		JSheet.showMessageSheet(this, "Select a file!", JOptionPane.ERROR_MESSAGE);return;
    	}
    	if(getExtention(myfile.toString()).equals("rtf"))
    	{
    		try 
    		{
    			Edit.t.setEditorKit(rtf);
    			FileInputStream fi = new FileInputStream(myfile.toString());
    			rtf.read( fi,Edit.t.getDocument(),0);
    			path = myfile.toString();recent=path;Edit.t.requestFocus();
    		}
    		catch( FileNotFoundException e )
    		{
    			JSheet.showMessageSheet(this,"File not found ", JOptionPane.ERROR_MESSAGE);
    		}
    		catch( IOException e )
    		{
    			JSheet.showMessageSheet(this,"IO ERROR: "+e, JOptionPane.ERROR_MESSAGE);
    		}
    		catch( BadLocationException e ){}catch(Exception e){JSheet.showMessageSheet(this,"IO ERROR: "+e, JOptionPane.ERROR_MESSAGE);}
    	}
    	else if(getExtention(myfile.toString()).equals("html")||getExtention(myfile.toString()).equals("htm"))
    	{
    		try
    		{
    			Edit.t.setEditorKit(html);
    			FileInputStream fi = new FileInputStream(myfile.toString());path = myfile.toString();recent=path;
                 //html.read( fi,Edit.t.getDocument(),0);
    			Edit.t.setPage(new URL("File:///"+myfile.toString()));
    			Edit.t.requestFocus();Edit.t.setSize(null);
    		}
    		catch(IOException e){JSheet.showMessageSheet(this,"IO ERROR: "+e, JOptionPane.ERROR_MESSAGE);}
    		catch(OutOfMemoryError e){}catch(Exception e){}
    	}
    	else if(getExtention(myfile.toString()).equals("xtd"))
    	{
    		try
    		{
    			ArrayList Loader = new ArrayList();FileInputStream fis = null;ObjectInputStream in = null;
    			
    			Edit.t.setEditorKit(rtf);
    			fis = new FileInputStream(myfile.toString());
    			in = new ObjectInputStream(fis);
    			Loader = (ArrayList)in.readObject();in.close();
    			Save sav = (Save)Loader.get(0);Edit.t.setDocument(sav.getDocument());
    			
    			path = myfile.toString();recent=path;Edit.t.requestFocus();Edit.t.setSize(null);
    		}
    		catch(IOException e){JSheet.showMessageSheet(this,"IO ERROR: "+e, JOptionPane.ERROR_MESSAGE);}
    		catch(OutOfMemoryError e){}catch(Exception e){}
    	}
    	else
    	{
    		try
    		{   
    			Edit.t.setEditorKit(null);
    			BufferedReader in = new BufferedReader(new FileReader(myfile));String lines="";while((lines = in.readLine()) != null){Lines++;}
    			BufferedReader input = new BufferedReader(new FileReader(myfile));
    			StringBuffer str = new StringBuffer();String line;int ii=0;
    			while((line = input.readLine()) != null)
    			{
    				ii++;str.append(line+"\n");
    			}in.close();input.close();Edit.t.setText(str.toString());content = Edit.t.getText();
    			doc = Edit.t.getDocument();Edit.t.setEditorKit(html);Edit.t.setDocument(doc);Edit.t.requestFocus();
    		}
    		catch(FileNotFoundException e){JSheet.showMessageSheet(this,"File not found:  "+e, JOptionPane.ERROR_MESSAGE);}
    		catch(IOException e){JSheet.showMessageSheet(this,"IO ERROR: "+e, JOptionPane.ERROR_MESSAGE);}
    		catch(OutOfMemoryError e)
    		{
    			Progress.setVisible(false);
    			JSheet.showConfirmSheet(this, "Sorry !!! An Error Occured. Your Default Memory is Very Low To Load a Large File."+
    				"\nCannot Load The Selected File.  This File is Out of Capacity of Your System"+
    				"\nThis File Contains Text With "+Lines+" Lines. Which is Not Suported By The Default Memory."+
    				"\n\nXtreme Pad Needs a Restart, Do You Want To Close The Xtreme Pad. ", new SheetListener() 
    				{
    					public void optionSelected(SheetEvent evt) 
    					{
    						int Err=evt.getOption();
    						if(Err==0){System.exit(0);}
    						else if(Err==1){JSheet.showMessageSheet(Edit, "Xtreme Pad May Run Slower Due To Less Memory.");}
    					}
    				});
    		}
    		catch(Exception e){}
    	}
    }
    public void file_save()
    {
    	if(path.equals(""))
    	{
    		file_save_as();
    		return;
    	}
    	try
    	{
    		FileWriter fw = new FileWriter(path);doc = Edit.t.getDocument();
    		fw.write(doc.getText(0,doc.getLength()));fw.close();
    		content = Edit.t.getText();fw.close();
    	}
    	catch(Exception i)
    	{
    		JSheet.showMessageSheet(this,"Failed to save the file", JOptionPane.ERROR_MESSAGE);
    	}
    }
    public void file_save_as()
    {
    	JFileChooser fc = new JFileChooser();fc.setCurrentDirectory(new File(recent));
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Rich Text Format (RTF)", "rtf");fc.setFileFilter(filter);
    	filter = new FileNameExtensionFilter("Hyper Text Document (HTML)", "html","htm");fc.setFileFilter(filter);
    	filter = new FileNameExtensionFilter("Text Document (TXT)", "txt");fc.setFileFilter(filter);
    	filter = new FileNameExtensionFilter("Xtreme Text Document (XTD)", "xtd");fc.setFileFilter(filter);
    	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);    
    	if (UIManager.getLookAndFeel().toString().startsWith("[The Quaqua Leopard"))
    	{
    		JSheet.showSaveSheet(fc, this, new SheetListener() 
    		{
    			public void optionSelected(SheetEvent evt) 
    			{
    				jp=1;if(evt.getOption()==1){return;}
    			}
    		});
    	}
    	else
    	{
    		int r = fc.showSaveDialog(this);        
    		if(r==fc.CANCEL_OPTION){return;}
    	}
    	File myfile = fc.getSelectedFile();if(myfile==null || myfile.getName().equals("")){return;}
    	if(myfile.exists())
    	{
    		if (UIManager.getLookAndFeel().toString().startsWith("[The Quaqua Leopard"))
    		{
    			JSheet.showConfirmSheet(this, "A File With Same Name Already Exists !!!\nAre You Sure Want To Overwrite it ?", new SheetListener()
    			{
    				public void optionSelected(SheetEvent evt) {if(evt.getOption()!=0){return;}}
    			});
    		}
    		else
    		{
    			if(JOptionPane.showConfirmDialog(this, "A File With Same Name Already Exists !!!\nAre You Sure Want To Overwrite it ?") != 0){return;}
    		}
    	}        
    	try
    	{
    		doc = Edit.t.getDocument();
    		if(fc.getFileFilter().getDescription().equals("Rich Text Format (RTF)"))
    		{
    			OutputStream out;
    			if(getExtention(myfile.toString()).equals("rtf")){out= new FileOutputStream(myfile);}
    			else {out= new FileOutputStream(myfile+".rtf");}
    			rtf.write(out,doc,0,doc.getLength());
    			content = doc.getText(0,doc.getLength());setTitle(myfile.getName()+" - Xtreme Pad");out.close();
    			Edit.t.requestFocus();
    		}
    		else if(fc.getFileFilter().getDescription().equals("Text Document (TXT)"))
    		{
    			FileWriter fw;
    			if(getExtention(myfile.toString()).equals("txt")){fw= new FileWriter(myfile);}
    			else {fw= new FileWriter(myfile+".txt");}
    			fw.write(doc.getText(0,doc.getLength()));
    			content = doc.getText(0,doc.getLength());setTitle(myfile.getName()+" - Xtreme Pad");fw.close();
    			Edit.t.requestFocus();
    		}
    		else if(fc.getFileFilter().getDescription().equals("Hyper Text Document (HTML)"))
    		{
    			OutputStream out;
    			if(getExtention(myfile.toString()).equals("html")||getExtention(myfile.toString()).equals("htm")){out= new FileOutputStream(myfile);}
    			else {out= new FileOutputStream(myfile+".html");}
    			html.write(out,doc,0,doc.getLength());
    			content = doc.getText(0,doc.getLength());setTitle(myfile.getName()+" - Xtreme Pad");out.close();
    			Edit.t.requestFocus();
    		}
    		else if(fc.getFileFilter().getDescription().equals("Xtreme Text Document (XTD)"))
    		{
    			FileOutputStream fos = null;ObjectOutputStream out = null;
    			ArrayList Saver = new ArrayList();Save sav=new Save(doc);Saver.add(sav);
    			if(getExtention(myfile.toString()).equals("xtd")){fos = new FileOutputStream(myfile);}
    			else {fos = new FileOutputStream(myfile+".xtd");}
    			out = new ObjectOutputStream(fos);out.writeObject(Saver);out.close();
    			
    			content = doc.getText(0,doc.getLength());setTitle(myfile.getName()+" - Xtreme Pad");out.close();
    			Edit.t.requestFocus();
    		}
    	}
    	catch(Exception e)
    	{
    		JSheet.showMessageSheet(this,"Failed to save the file !!!", JOptionPane.ERROR_MESSAGE);
    	}
    }
    public void file_print()
    {
        /*PrinterJob printer = PrinterJob.getPrinterJob();
        //printer.setPrintable( this);
        HashPrintRequestAttributeSet printAttr = new HashPrintRequestAttributeSet();
        if(printer.printDialog(printAttr))     // Display print dialog
        {            // If true is returned...
            try
            {
                printer.print(printAttr);    // then print
            }
            catch(PrinterException e)
            {
                JOptionPane.showMessageDialog(this,"Failed to print the file: "+e,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }*/
        
        /*J2Printer14 printer = new J2Printer14(""); 
        printer.setSeparatePrintThread(true); 
        J2TextPrinter textPrinter = new J2TextPrinter(Edit.t); 
        printer.addPageable(textPrinter);
        printer.showPrintPreviewDialog();*/
        if(pp!=0){view.dispose();}pp=1;
        PrinterJob pj = PrinterJob.getPrinterJob();
        javax.print.attribute.HashPrintRequestAttributeSet att = 
        new javax.print.attribute.HashPrintRequestAttributeSet();
        view =new PrintPreview(Edit.t.getPrintable(new MessageFormat(""),new MessageFormat("")), pj.getPageFormat(att));
    }
    public void file_close()
    {
    	try
    	{
    		doc = Edit.t.getDocument();
    		if(doc.getText(0,doc.getLength()).equals("") || doc.getText(0,doc.getLength()).equals(content))
    		{
    			Edit.t.setText("");path = "";setTitle("Xtreme Pad");
    		}
    		else
    		{
    			SaveAsk= new JOptionPane(SaveString,JOptionPane.WARNING_MESSAGE);
    			Object[] options = {"Save","Don't Save","Cancel"};
    			SaveAsk.setOptions(options);
    			SaveAsk.setInitialValue(options[0]);
    			SaveAsk.putClientProperty("Quaqua.OptionPane.destructiveOption", 2);
    			JSheet.showSheet(SaveAsk, this, new SheetListener()
    			{
    				public void optionSelected(SheetEvent evt) 
    				{
    					int a=evt.getOption();
    					if(a==0){file_save();}
    					else if(a==1){setTitle("Xtreme Pad");}
    					else if(a==2){return;}
    				}
    			});
    		}
    	}
    	catch(Exception e){}
    }
    public void file_exit()
    {
    	try
    	{
    		doc = Edit.t.getDocument();
    		try
    		{
    			FileWriter fw = new FileWriter("Icons/Size.txt");
    			int WW=Toolkit.getDefaultToolkit().getScreenSize().width,HH=Toolkit.getDefaultToolkit().getScreenSize().height;
    			int W=(int)getSize().getWidth(),H=(int)getSize().getHeight();if(W<800){W=800;}if(H<500){H=500;}
    			int X=getX(),Y=getY();if(X<0){X=0;}if((X+W)>WW){X=WW-W;};if(Y<0){Y=0;}if((Y+H)>WW){Y=HH-H;};
    			fw.write(W+","+H+","+(int)X+","+(int)Y+","+recent);fw.close();
    		}
    		catch(IOException i){}
    		if(doc.getText(0,doc.getLength()).equals("")||doc.getText(0,doc.getLength()).equals(content)){System.exit(0);}
    		else
    		{
    			SaveAsk= new JOptionPane(SaveString,JOptionPane.WARNING_MESSAGE);
    			Object[] options = {"Save","Don't Save","Cancel"};
    			SaveAsk.setOptions(options);
    			SaveAsk.setInitialValue(options[0]);
    			SaveAsk.putClientProperty("Quaqua.OptionPane.destructiveOption", 2);
    			JSheet.showSheet(SaveAsk, this, new SheetListener()
    			{
    				public void optionSelected(SheetEvent evt) 
    				{
    					int b=evt.getOption();if(b==0){file_save();}else if(b==1){System.exit(0);}else if(b==2){return;}
    				}
    			});
    		}
    	}
    	catch(Exception e){}
    }
    public void edit_undo()
    {
    	if( undo.canUndo())
    	{
    		try
    		{
    			undo.undo();
    		}
    		catch(CannotUndoException e)
    		{                
    		}
    	}           
    }
    public void edit_redo()
    {
    	if( undo.canRedo())
    	{
    		try
    		{
    			undo.redo();
    		}
    		catch(CannotRedoException e)
    		{                
    		}
    	}
    }
    public void edit_cut()
    {
    	Edit.t.cut();
    }
    public void edit_copy()
    {
    	Edit.t.copy();
    }
    public void edit_paste()
    {
    	Edit.t.paste();
    }
    public void edit_selectall()
    {
    	Edit.t.selectAll();
    }
    public void edit_find()
    {
    	finder.tf.setText(Edit.t.getSelectedText());finder.setVisible(true);
    }
    public void edit_timedate()
    {
    	try
    	{
    		int start = Edit.t.getSelectionStart();
    		int end   = Edit.t.getSelectionEnd();        
    		Calendar cal = Calendar.getInstance();
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy h:m a");
    		String now = sdf.format(cal.getTime());                
    		String temp1 = Edit.t.getText().substring(0,start);
    		String temp2 = Edit.t.getText().substring(end);        
    		Edit.t.setText(temp1+" "+now+" "+temp2);
    		Edit.t.select(start+1, start+1+now.length());
    	}
    	catch(NullPointerException e){}
    }
    public void format_font()
    {
        //fc.window.setVisible(true);
    }
    public void str2uppr()
    {
    	try
    	{
    		int start = Edit.t.getSelectionStart();
    		int end   = Edit.t.getSelectionEnd();
    		String temp1 = Edit.t.getText().substring(0,start);
    		String temp2 = Edit.t.getText().substring(end);
    		String conv  = Edit.t.getSelectedText().toUpperCase();
    		Edit.t.setText(temp1+conv+temp2);
    		Edit.t.select(start, end);
    	}
    	catch(NullPointerException e){}
    }
    public void str2lwr()
    {
    	try
    	{
    		int start = Edit.t.getSelectionStart();
    		int end   = Edit.t.getSelectionEnd();
    		String temp1 = Edit.t.getText().substring(0,start);
    		String temp2 = Edit.t.getText().substring(end);
    		String conv  = Edit.t.getSelectedText().toLowerCase();
    		Edit.t.setText(temp1+conv+temp2);
    		Edit.t.select(start, end);
    	}
    	catch(NullPointerException e){}
    }
    public void help_about()
    {
    	abt.window.setVisible(true);
    }
    class ComboBoxRenderer extends DefaultListCellRenderer
    {
    	public ComboBoxRenderer() 
    	{
    		setOpaque(false);setHorizontalAlignment(LEFT);setVerticalAlignment(CENTER);
    	}
    	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) 
    	{
    		JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    		int selectedIndex = index;Random Ra=new Random();
    		if (isSelected) 
    		{
    			l.setBackground(list.getSelectionBackground());l.setForeground(list.getSelectionForeground());
    			if(selectedIndex<0){selectedIndex=0;}
    			Action action=new StyledEditorKit.FontFamilyAction(Fonts[selectedIndex],Fonts[selectedIndex]);action.actionPerformed(null);
                //l.setBackground(Col[Ra.nextInt(Col.length)]);//l.setForeground(Color.black);
    		}
    		else
    		{
    			l.setBackground(list.getBackground());
    			l.setForeground(list.getForeground());
                //l.setBackground(Color.black);
                //l.setForeground(Color.white);
    		}
    		if(selectedIndex<0){selectedIndex=0;}l.setText(value.toString());l.setFont(new Font(Fonts[selectedIndex],0,20));
    		return l;
    	}
    }
}