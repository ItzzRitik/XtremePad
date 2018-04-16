package src;

import java.awt.*;import java.awt.dnd.*;import java.awt.datatransfer.*;import java.net.URL;
import java.util.*;import javax.swing.*;import javax.swing.event.*;import java.util.List;
import javax.swing.text.*;import java.io.*;import java.net.MalformedURLException;
public class Editor extends JPanel implements Runnable
{
    JEditorPane t;JScrollPane sc;JPanel pane=new JPanel();JPanel Design=new JPanel();notepad note;
    public Editor(notepad note)
    {
        setLayout(new BorderLayout());this.note=note;
                
        t = new JEditorPane();t.setFont(new Font("Verdana",Font.PLAIN, 11));
        t.setPreferredSize(new java.awt.Dimension(750,850));t.setForeground(new Color(0,0,0));
        t.setFocusable(true);t.setBackground(new Color(225,225,230));
        t.setDoubleBuffered(true);t.setDragEnabled(true);//t.setSize(800,1000);
        t.setMargin(new java.awt.Insets(50,50,50,50));t.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        t.setFont(new Font("Verdana",Font.PLAIN, 12));
        EditorDropTarget target = new EditorDropTarget(t);
        Design.setOpaque(false);
        pane.add(Design);pane.add(t);
        pane.setOpaque(false);add(pane);
        setSize(1000,2000);t.requestFocus(true);Thread th=new Thread(this);th.start();
    }
    public void run()
    {
        t.repaint();t.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));try{Thread.sleep(1000);run();}catch(Exception e){run();}
    }
    public void paintComponent(Graphics g)
    {
        g.setColor((note.Case.getBackground()).darker());g.fillRect(0, 0, getWidth(), getHeight());
    }
}
class EditorDropTarget implements DropTargetListener 
{
    protected JEditorPane pane;
    protected DropTarget dropTarget;
    protected boolean acceptableType;
    protected boolean draggingFile; 
    public EditorDropTarget(JEditorPane pane) 
    {
        this.pane = pane;dropTarget = new DropTarget(pane, DnDConstants.ACTION_COPY_OR_MOVE,this, true, null);
    }
    public void dragEnter(DropTargetDragEvent dtde) 
    {
        DnDUtils.debugPrintln("dragEnter, drop action = "+DnDUtils.showActions(dtde.getDropAction()));
        checkTransferType(dtde);acceptOrRejectDrag(dtde);
    }
    public void dragExit(DropTargetEvent dte) 
    {
        DnDUtils.debugPrintln("DropTarget dragExit");
    }
    public void dragOver(DropTargetDragEvent dtde) 
    {
        DnDUtils.debugPrintln("DropTarget dragOver, drop action = "+ DnDUtils.showActions(dtde.getDropAction()));
        acceptOrRejectDrag(dtde);
    }
    public void dropActionChanged(DropTargetDragEvent dtde)
    {
        DnDUtils.debugPrintln("DropTarget dropActionChanged, drop action = "+ DnDUtils.showActions(dtde.getDropAction()));
        acceptOrRejectDrag(dtde);
    }
    public void drop(DropTargetDropEvent dtde)
    {
        DnDUtils.debugPrintln("DropTarget drop, drop action = "+ DnDUtils.showActions(dtde.getDropAction()));
        if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0)
        {
            dtde.acceptDrop(dtde.getDropAction());
            Transferable transferable = dtde.getTransferable();
            try 
            {
                boolean result = false;
                if (draggingFile){result = dropFile(transferable);} 
                else{result = dropContent(transferable, dtde);}
                dtde.dropComplete(result);DnDUtils.debugPrintln("Drop completed, success: " + result);
            }
            catch (Exception e)
            {
                DnDUtils.debugPrintln("Exception while handling drop " + e);
                dtde.dropComplete(false);
            }
        }
        else
        {
            DnDUtils.debugPrintln("Drop target rejected drop");
            dtde.rejectDrop();
        }
    }
    protected boolean acceptOrRejectDrag(DropTargetDragEvent dtde) 
    {
        int dropAction = dtde.getDropAction();
        int sourceActions = dtde.getSourceActions();
        boolean acceptedDrag = false;
        DnDUtils.debugPrintln("\tSource actions are "+DnDUtils.showActions(sourceActions) + ", drop action is "+ DnDUtils.showActions(dropAction));
        if (!acceptableType||(sourceActions & DnDConstants.ACTION_COPY_OR_MOVE) == 0) 
        {
            DnDUtils.debugPrintln("Drop target rejecting drag");dtde.rejectDrag();
        } 
        else if (!draggingFile && !pane.isEditable()) 
        {
            DnDUtils.debugPrintln("Drop target rejecting drag");
            dtde.rejectDrag();
        } 
        else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0)
        {
            DnDUtils.debugPrintln("Drop target offering COPY");
            dtde.acceptDrag(DnDConstants.ACTION_COPY);acceptedDrag = true;
        } 
        else
        {
            DnDUtils.debugPrintln("Drop target accepting drag");
            dtde.acceptDrag(dropAction);acceptedDrag = true;
        }
        return acceptedDrag;
    }
    protected void checkTransferType(DropTargetDragEvent dtde) 
    {
        acceptableType = false;draggingFile = false;
        if (DnDUtils.isDebugEnabled()) 
        {
            DataFlavor[] flavors = dtde.getCurrentDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                DataFlavor flavor = flavors[i];
                DnDUtils.debugPrintln("Drop MIME type " + flavor.getMimeType()+ " is available");
            }
        }
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) 
        {
            acceptableType = true;draggingFile = true;
        } 
        else if (dtde.isDataFlavorSupported(DataFlavor.plainTextFlavor)|| dtde.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            acceptableType = true;
        }
        DnDUtils.debugPrintln("File type acceptable - " + acceptableType);
    }
    protected boolean dropFile(Transferable transferable) throws IOException,UnsupportedFlavorException, MalformedURLException 
    {
        List fileList = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
        File transferFile = (File) fileList.get(0);
        final URL transferURL = transferFile.toURL();
        DnDUtils.debugPrintln("File URL is " + transferURL);
        pane.setPage(transferURL);
        return true;
    }
    protected boolean dropContent(Transferable transferable,DropTargetDropEvent dtde)
    {
        if (!pane.isEditable()){return false;}
        try
        {
            DataFlavor[] flavors = dtde.getCurrentDataFlavors();
            DataFlavor selectedFlavor = null;
            for (int i = 0; i < flavors.length; i++)
            {
                DataFlavor flavor = flavors[i];
                if (flavor.equals(DataFlavor.plainTextFlavor)|| flavor.equals(DataFlavor.stringFlavor)) 
                {
                    selectedFlavor = flavor;break;
                }
            }
            if (selectedFlavor == null){return false;}
            DnDUtils.debugPrintln("Selected flavor is "+ selectedFlavor.getHumanPresentableName());
            Object data = transferable.getTransferData(selectedFlavor);
            DnDUtils.debugPrintln("Transfer data type is "+ data.getClass().getName());
            String insertData = null;
            if (data instanceof InputStream) 
            {
                String charSet = selectedFlavor.getParameter("charset");
                InputStream is = (InputStream) data;
                byte[] bytes = new byte[is.available()];is.read(bytes);
                try {insertData = new String(bytes, charSet);}catch (UnsupportedEncodingException e){insertData = new String(bytes);}
            } 
            else if (data instanceof String){insertData = (String) data;}
            if (insertData != null) 
            {
                int selectionStart = pane.getCaretPosition();
                pane.replaceSelection(insertData);
                pane.select(selectionStart, selectionStart+ insertData.length());
                return true;
            }
            return false;
        } 
        catch (Exception e){return false;}
    }
}
class DnDUtils 
{
    public static String showActions(int action) 
    {
        String actions = "";
        if ((action & (DnDConstants.ACTION_LINK | DnDConstants.ACTION_COPY_OR_MOVE)) == 0) 
        {
            return "None";
        }
        if ((action & DnDConstants.ACTION_COPY) != 0) 
        {
            actions += "Copy ";
        }
        if ((action & DnDConstants.ACTION_MOVE) != 0) 
        {
            actions += "Move ";
        }
        if ((action & DnDConstants.ACTION_LINK) != 0) {
            actions += "Link";
        }
        return actions;
    }
    public static boolean isDebugEnabled() 
    {
        return debugEnabled;
    }
    public static void debugPrintln(String s) 
    {
        if (debugEnabled) {System.out.println(s);}
    }
    private static boolean debugEnabled = (System.getProperty("DnDExamples.debug") != null);
}