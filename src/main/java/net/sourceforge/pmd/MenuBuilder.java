/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import bluej.extensions.BClass;
import bluej.extensions.MenuGenerator;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;

public class MenuBuilder extends MenuGenerator {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private Frame frame;
    private String javaFileName;
    private Preferences preferences;

    public MenuBuilder(Frame frame, Preferences preferences) {
        this.frame = frame;
        this.preferences = preferences;
    }

    @Override
    public JMenuItem getClassMenuItem(BClass aClass) {
        JMenu jm = new JMenu("PMD");
        jm.add(new JMenuItem(new PMDAction("Check code")));
        return jm;
    }

    @Override
    public void notifyPostClassMenu(BClass bc, JMenuItem jmi) {
        try {
            javaFileName = bc.getJavaFile().getPath();
        } catch (ProjectNotOpenException e) {
            e.printStackTrace();
        } catch (PackageNotFoundException e) {
            e.printStackTrace();
        }
    }

    class PMDAction extends AbstractAction {
        public PMDAction(String menuName) {
            putValue(AbstractAction.NAME, menuName);
        }

        public void actionPerformed(ActionEvent anEvent) {
            if (javaFileName == null || javaFileName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pmdPath = preferences.getPMDPath();
            if (pmdPath == null || pmdPath.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The path to PMD Installation is not configured. "
                        + "Please select the path under \"Tools / Preferences / Extensions / PMD\".",
                        "No Path to PMD Installation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                JOptionPane.showMessageDialog(frame, "Running PMD on selected Class (Click OK)");
                String mycommand = preferences.getPMDPath() + "/bin/run.sh pmd " + preferences.getPMDOptions() + " -d " + javaFileName;

                if (SystemUtils.isWindows()) {
                    mycommand = preferences.getPMDPath() + "\\bin\\pmd.bat " + preferences.getPMDOptions() + " -d " + javaFileName;
                }

                String output = runPMD(mycommand);

                JOptionPane.showMessageDialog(frame, "Class Checked");

                StringBuilder msg = new StringBuilder("Any problems found are displayed below:");
                msg.append(LINE_SEPARATOR);
                msg.append(output);
                JOptionPane.showMessageDialog(frame, msg);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Couldn't run PMD: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            } catch (InterruptedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Couldn't run PMD: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(frame, "PMD run completed");
        }

        private String runPMD(String mycommand) throws IOException, InterruptedException {
            ProcessBuilder pb = new ProcessBuilder(mycommand.split(" +"));
            pb.redirectErrorStream(true);
            final Process p = pb.start();

            final StringBuilder output = new StringBuilder();
            Thread reader = new Thread(new Runnable() {
                public void run() {
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String s;
                    try {
                        while ((s = stdInput.readLine()) != null ){ 
                            output.append(s);
                            output.append(LINE_SEPARATOR);
                        }
                    } catch (IOException e) {
                        output.append(e.toString());
                        e.printStackTrace();
                    } finally {
                        try { stdInput.close(); } catch (IOException e) { /* quiet */ }
                    }
                }
            });
            reader.setDaemon(true);
            reader.start();
            p.waitFor();
            return output.toString();
        }
    }
}
