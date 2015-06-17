/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bluej.extensions.BlueJ;
import bluej.extensions.PreferenceGenerator;

public class Preferences implements PreferenceGenerator {

    private JPanel panel;
    private JTextField pmdPath;
    private JTextField pmdOptions;
    private BlueJ bluej;
    public static final String PROPERTY_PMD_PATH = "PMD.Path";
    public static final String PROPERTY_PMD_OPTIONS = "PMD.Options";
    private static final String PMD_OPTIONS_DEFAULT = "-format text -R java-basic,java-design -version 1.7 -language java";

    public Preferences(BlueJ bluej) {
        this.bluej = bluej;
        renderPanel();
        loadValues(); // Load the default/initial values
    }

    private void renderPanel() {
        panel = new JPanel();
        pmdPath = new JTextField();
        pmdOptions = new JTextField();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Path to PMD installation:"), c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(pmdPath,c );

        JButton selectPmdPathButton = new JButton("Select");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(selectPmdPathButton, c);

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("PMD Options:"), c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(pmdOptions, c);

        JButton resetToDefaultButton = new JButton("Reset to default");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(resetToDefaultButton, c);

        selectPmdPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File(pmdPath.getText()));
                int result = fileChooser.showDialog(panel, "Select");
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    boolean valid = verifyPMDPath(selectedFile);
                    if (valid) {
                        pmdPath.setText(selectedFile.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(panel, "The selected path " + selectedFile + " doesn't seem to be"
                                + " a PMD installation. E.g. the file bin/pmd.bat or bin/run.sh is missing.");
                    }
                }
            }
        });

        resetToDefaultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pmdOptions.setText(PMD_OPTIONS_DEFAULT);
            }
        });
    }

    private boolean verifyPMDPath(File selectedFile) {
        File pathToExecutable;
        if (SystemUtils.isWindows()) {
            pathToExecutable = new File(selectedFile, "bin/pmd.bat");
        } else {
            pathToExecutable = new File(selectedFile, "bin/run.sh");
        }
        return pathToExecutable.exists();
    }

    public JPanel getPanel ()  { return panel; }

    public void saveValues () {
        bluej.setExtensionPropertyString(PROPERTY_PMD_PATH, pmdPath.getText());
        bluej.setExtensionPropertyString(PROPERTY_PMD_OPTIONS, pmdOptions.getText());
    }

    public final void loadValues () {
        pmdPath.setText(getPMDPath());
        pmdOptions.setText(getPMDOptions());
    }

    public final String getPMDOptions() {
        return bluej.getExtensionPropertyString(PROPERTY_PMD_OPTIONS, PMD_OPTIONS_DEFAULT);
    }

    public final String getPMDPath() {
        return bluej.getExtensionPropertyString(PROPERTY_PMD_PATH, "");
    }
}
