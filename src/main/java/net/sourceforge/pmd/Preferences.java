/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import java.io.File;

import bluej.extensions2.BlueJ;
import bluej.extensions2.PreferenceGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;

public class Preferences implements PreferenceGenerator {

    private GridPane pane;
    private TextField pmdPath;
    private TextField pmdOptions;

    private BlueJ bluej;
    public static final String PROPERTY_PMD_PATH = "PMD.Path";
    public static final String PROPERTY_PMD_OPTIONS = "PMD.Options";
    private static final String PMD_OPTIONS_DEFAULT = "-format text -R java-basic,java-design -version 1.7 -language java";

    public Preferences(BlueJ bluej) {
        this.bluej = bluej;
        renderPane();
        loadValues(); // Load the default/initial values
    }

    private void renderPane() {
        pane = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints(100,100,Double.MAX_VALUE);
        column2.setHgrow(Priority.ALWAYS); // second column gets any extra width
        ColumnConstraints column3 = new ColumnConstraints();
        pane.getColumnConstraints().addAll(column1, column2, column3);

        pmdPath = new TextField();
        pmdOptions = new TextField();
        Button selectPmdPathButton = new Button("Select");
        Button resetToDefaultButton = new Button("Reset to default");

        pane.add(new Label("Path to PMD installation:"), 0, 0);
        pane.add(pmdPath, 1, 0);
        pane.add(selectPmdPathButton, 2, 0);

        pane.add(new Label("PMD Options:"), 0, 1);
        pane.add(pmdOptions, 1, 1);
        pane.add(resetToDefaultButton, 2, 1);

        selectPmdPathButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File currentDir = new File(pmdPath.getText());
            if (currentDir.exists()) {
                directoryChooser.setInitialDirectory(currentDir);
            }
            File selectedDirectory = directoryChooser.showDialog(pane.getScene().getWindow());
            if (selectedDirectory != null) {
                boolean valid = verifyPMDPath(selectedDirectory);
                if (valid) {
                    pmdPath.setText(selectedDirectory.getAbsolutePath());
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "The selected path " + selectedDirectory + " doesn't seem to be"
                            + " a PMD installation. E.g. the file bin/pmd.bat or bin/run.sh is missing.");
                    alert.showAndWait();
                }
            }
        });

        resetToDefaultButton.setOnAction(e -> pmdOptions.setText(PMD_OPTIONS_DEFAULT));
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

    @Override
    public Pane getWindow() {
        return pane;
    }

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
