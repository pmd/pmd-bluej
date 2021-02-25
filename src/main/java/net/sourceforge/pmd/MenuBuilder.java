/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bluej.extensions2.BClass;
import bluej.extensions2.MenuGenerator;
import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class MenuBuilder extends MenuGenerator {

    private Preferences preferences;

    public MenuBuilder(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public MenuItem getClassMenuItem(BClass aClass) {
        MenuItem menuItem = new MenuItem("PMD: Check code");
        menuItem.setOnAction(new PMDAction(aClass));
        return menuItem;
    }

    class PMDAction implements EventHandler<ActionEvent> {
        private String javaFileName;

        private void determineJavaFileName(BClass aClass) {
            try {
                javaFileName = aClass.getJavaFile().getPath();
            } catch (ProjectNotOpenException e) {
                e.printStackTrace();
            } catch (PackageNotFoundException e) {
                e.printStackTrace();
            }
        }

        public PMDAction(BClass aClass) {
            determineJavaFileName(aClass);
        }

        @Override
        public void handle(ActionEvent event) {
            if (javaFileName == null || javaFileName.trim().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR, "No file selected");
                alert.showAndWait();
                return;
            }

            String pmdPath = preferences.getPMDPath();
            if (pmdPath == null || pmdPath.trim().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR, "No Path to PMD Installation: "
                        + "The path to PMD Installation is not configured. "
                        + "Please select the path under \"Tools / Preferences / Extensions / PMD\".");
                alert.showAndWait();
                return;
            }

            try {
                String mycommand = preferences.getPMDPath() + "/bin/run.sh pmd " + preferences.getPMDOptions() + " -d " + javaFileName;

                if (SystemUtils.isWindows()) {
                    mycommand = preferences.getPMDPath() + "\\bin\\pmd.bat " + preferences.getPMDOptions() + " -d " + javaFileName;
                }

                String output = runPMD(mycommand);

                StringBuilder msg = new StringBuilder(1000);
                msg.append("PMD run finished.").append(System.lineSeparator()).append(System.lineSeparator());
                msg.append("Any problems found are displayed below:").append(System.lineSeparator());
                msg.append(output);

                showResults(msg);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "Couldn't run PMD: " + e.toString());
                alert.showAndWait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        private void showResults(StringBuilder msg) {
            Dialog<ButtonType> resultDialog = new Dialog<>();
            BorderPane dialogContent = new BorderPane();
            TextArea textArea = new TextArea(msg.toString());
            textArea.setEditable(false);
            dialogContent.setCenter(textArea);
            resultDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            resultDialog.getDialogPane().setContent(dialogContent);
            resultDialog.setTitle("PMD Results");
            resultDialog.showAndWait();
        }

        private String runPMD(String mycommand) throws IOException, InterruptedException {
            ProcessBuilder pb = new ProcessBuilder(mycommand.split(" +"));
            pb.redirectErrorStream(false);
            final Process p = pb.start();

            final StringBuilder output = new StringBuilder();
            Thread reader = new Thread(new Runnable() {
                public void run() {
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String s;
                    try {
                        while ((s = stdInput.readLine()) != null ){ 
                            output.append(s);
                            output.append(System.lineSeparator());
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
