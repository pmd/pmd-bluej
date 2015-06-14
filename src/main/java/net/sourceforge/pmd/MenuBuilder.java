/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import bluej.extensions.BClass;
import bluej.extensions.MenuGenerator;
import bluej.extensions.BlueJ;
import bluej.extensions.editor.TextLocation;
import bluej.extensions.ProjectNotOpenException;
import bluej.extensions.PackageNotFoundException;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.Frame;
import java.io.*;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.io.File;
import java.lang.Object;

public class MenuBuilder extends MenuGenerator {

    private BClass curClass;
    private Frame frame;
    private String javaFileName;

    public MenuBuilder(Frame frame) {
        this.frame = frame;
    }

    public JMenuItem getClassMenuItem(BClass aClass) {
    try {
          javaFileName = aClass.getJavaFile().getPath(); 
           } catch (ProjectNotOpenException pnoe) {
           } catch (PackageNotFoundException pnfe) {
        }
        JMenu jm = new JMenu("PMD");
        jm.add(new JMenuItem(new SimpleAction("Check code")));
        return jm;
    }

    public void notifyPostClassMenu(BClass bc, JMenuItem jmi) {
        curClass = bc;
    }
    
    private boolean isWindows() {
        String osname = System.getProperty("os.name");
        return osname.toLowerCase(Locale.ROOT).contains("win");
    }

    // The nested class that instantiates the different (simple) menus.
    class SimpleAction extends AbstractAction {

        public SimpleAction(String menuName) {
            putValue(AbstractAction.NAME, menuName);
        }

        String s;
        public void actionPerformed(ActionEvent anEvent) {
        try {
                JOptionPane.showMessageDialog(frame, "Running PMD on selected Class (Click OK)");

                String mycommand = "pmd.sh " + javaFileName;

                if (isWindows()) {
                    JOptionPane.showMessageDialog(frame, "Any errors will be displayed in command window with line numbers, press key to exit"); 
                    mycommand = "cmd /c start pmdsh.bat " + javaFileName;
                }
                Process p = Runtime.getRuntime().exec(mycommand);

                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                JOptionPane.showMessageDialog(frame, "Class Checked");

                if (!isWindows()) {
                    StringBuffer msg = new StringBuffer("");
                    msg.append("Any problems found are displayed below:");
                    msg.append(System.getProperty("line.separator"));
                    while ((s = stdInput.readLine()) != null ){ 
                           msg.append(s);
                           msg.append(System.getProperty("line.separator"));
                          }
                    JOptionPane.showMessageDialog(frame, msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Couldn't run PMD: " + e.getMessage());
            }
            JOptionPane.showMessageDialog(frame, "PMD run completed");
        }
    }
}
