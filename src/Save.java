package src;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.text.*;
public class Save implements Serializable
{
	private Document doc;
	public Save(Document doc)
	{
		this.doc=doc;
	}
	public Document getDocument() 
	{
		return doc;
	}
}
