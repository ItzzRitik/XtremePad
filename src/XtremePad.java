package src;

import javax.swing.*;import java.awt.*;import java.util.*;
public class XtremePad
{
	public static void main(final String args[])
	{
		try
		{
			if (!System.getProperty("os.name").toLowerCase().startsWith("mac")&& !System.getProperty("os.name").toLowerCase().startsWith("darwin")) 
			{
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
			}
			SwingUtilities.invokeLater(new Runnable() 
			{
				public void run() 
				{
                    //Tiger,Panther,Jaguar,Puma,Cheetah
                    //System.setProperty("Quaqua.design","Panther");
					try
					{
						UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
						notepad mynote = new notepad();SwingUtilities.updateComponentTreeUI(mynote);mynote.Resend();mynote.setVisible(true);
						if(args.length>0){mynote.open(new java.io.File(args[0]));}
					}
					catch(Exception e){}
				}
			});
		}
		catch(Exception e){}
	}
}