/* The original wordpad app frame was written by Arup Guha  */ 

//package as5spellcheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

class JavapadMainFrame extends JFrame implements ActionListener, KeyListener
{
    private Container content;
    private JTextArea text;
    private JLabel statusMenu;
    private boolean changesSaved, everSaved;
    private String saveName, saveDir;
       
    public JavapadMainFrame()
    {
        /* frame settings */         
        super();        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) {}
        setSize(600, 400);
        setFocusable(false);
        content = getContentPane();

        /* menu bar */
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem menuItem = new JMenuItem("New",KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Open...",KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Save",KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Save As...",KeyEvent.VK_A);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Exit",KeyEvent.VK_X);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        menu = new JMenu("Tools");
        menu.setMnemonic(KeyEvent.VK_T);
        menuItem = new JMenuItem("Spell Check",KeyEvent.VK_S);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Word Count",KeyEvent.VK_W);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);

        /* text area */
        text = new JTextArea();
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.addKeyListener(this);
        JScrollPane jsp = new JScrollPane(text);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        /* status bar */
        statusMenu = new JLabel();
        statusMenu.setBorder(new BevelBorder(BevelBorder.LOWERED));

        /* add all to frame */
        setJMenuBar(menuBar);
        add(jsp,BorderLayout.CENTER);
        add(statusMenu,BorderLayout.SOUTH);

        /* inits */
        changesSaved = true; // set to true to trick New() into running properly for very first run
        New();       
    }
       
    private void setTitle()
    {
        setTitle((saveName == null ? "Untitled" : saveName) + (!changesSaved ? "*" : "") + " - JavaWordPad");       
    }

    public void actionPerformed(ActionEvent e)
    {               
        String cmd = e.getActionCommand();
        if (cmd.equals("New"))
            New();
        else if (cmd.equals("Open..."))
            open();
        else if (cmd.equals("Save"))
            save();
        else if (cmd.equals("Save As..."))
            saveAs();
        else if (cmd.equals("Exit"))
            System.exit(0);
        else if (cmd.equals("Spell Check"))
            spellCheck();
        else if (cmd.equals("Word Count"))
            wordCount();
        else
            System.out.println("UNHANDLED ACTION!!!");       
    }

    public void keyPressed(KeyEvent e) 
    { err("fired keypressed"); }
       
    public void keyReleased(KeyEvent e) 
    { err("fired keyreleased"); }
       
    public void keyTyped(KeyEvent e)
    {
        err("fired keytyped");
        err(e.isControlDown() + " " + e.getKeyChar());
        
        // if key typed was ctrl, meaning it was part of shortcut, do not consider it important to the text field changing
        if (e.isControlDown())
               return;
       
        changesSaved = false;
        text.removeKeyListener(this);
        statusMenu.setText("");
        setTitle();       
    }

    private void New()
    {
        err("fired new");
        if (!wantToSaveUnsavedChanges())
            return;
        // this is so that we can use set title and have it display correctly
        changesSaved = true;
        setTitle();
        
        changesSaved = false;
        everSaved = false;
        saveName = null;
        saveDir = null;
        text.setText("");
    }

    /* open file action */
    private void open()
    {
        err("fired open");
        if (!wantToSaveUnsavedChanges())
            return;
        FileDialog fd = new FileDialog(this, "Open", FileDialog.LOAD);            
        fd.setVisible(true);
        if (fd.getFile() != null)
        {
            saveDir = fd.getDirectory();
            saveName = fd.getFile();
            try
            {
                FileReader fr = new FileReader(new File(saveDir + saveName));
                BufferedReader br = new BufferedReader(fr);
                String s = "", t;
                while ((t = br.readLine()) != null)
                    s += t + "\n";
                
                if (!s.equals(""))
                    s = s.substring(0,s.length()-1);
                       
                text.setText(s);                
                everSaved = true;                
                changesSaved = true;
                setTitle();               
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }      
        }     
    }

    /* Confirm Dialog Box if trying to navigate away from an unsaved document */
    private boolean wantToSaveUnsavedChanges()
    {
        if (!changesSaved & !text.getText().equals(""))
        {
            int option = JOptionPane.showConfirmDialog(this,"The text in the " + (saveName == null ? "Untitled" : saveDir + saveName) + " file has changed.\n\nDo you want to save the changes?","Javapad",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
            if (option == JOptionPane.YES_OPTION)
            {
                if (!save())
                    return false;                   
            }                   
            else if (option == JOptionPane.CANCEL_OPTION)
                return false;           
        }           
        return true;   
    }
       
    private boolean save()
    {
        err("fired save");
        /* if file never saved, run saveAs logic */
        if (!everSaved)
            return saveAs();
        
        /* save file to saveName */
        try
        {
            FileWriter fw = new FileWriter(new File(saveDir + saveName),false);
            fw.write(text.getText());
            fw.close();
            changesSaved = true;
            text.addKeyListener(this);
            setTitle();
            return true;
        }
        catch (Exception e)
        {
               System.out.println(e.getMessage());
        }
        return false;       
    }
    
    private boolean saveAs()
    {
        err("fired saveAs");
        FileDialog fd = new FileDialog(this,"Save As",FileDialog.SAVE);
        fd.setFile(".txt");
        fd.setVisible(true);
        if (fd.getFile() != null)
        {
            /* save the file to the spot chosen */
            try
            {
                saveName = fd.getFile();
                if (!saveName.contains("."))
                    saveName += ".txt";
                saveDir = fd.getDirectory();
                FileWriter fw = new FileWriter(new File(saveDir + saveName),false);
                fw.write(text.getText());
                fw.close();
                changesSaved = true;
                everSaved = true;
                text.addKeyListener(this);
                setTitle();
                return true;                       
            }
            catch (IOException e)            
            {
                System.out.println(e.getMessage());
            }            
        }               
        return false;       
    }

    private void spellCheck()
    {
        try
        {
            String wrongWordList = Tools.spellCheck(text.getText());
                  
            if (wrongWordList.length() == 0)
            {
                JOptionPane.showMessageDialog(this, "No incorrectly spelled words!", "Good job",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
         
            statusMenu.setText("Missspelled words: " +  wrongWordList);
            text.addKeyListener(this);
        }               
        catch (Exception e)
        {
            System.out.println("Error in spell checking...");
        }       
    }
       
    private void wordCount()
    {
        int count = Tools.wordCount(text.getText());
        statusMenu.setText("Word Count: " + count);
        text.addKeyListener(this);
    }

    private void err(String s)
    {
        System.out.println(s);
    }

}

