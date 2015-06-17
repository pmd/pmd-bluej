/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PMDExecExt extends Extension {

    public void startup (BlueJ bluej) {

        Preferences pmdPreferences = new Preferences(bluej);
        bluej.setPreferenceGenerator(pmdPreferences);
        bluej.setMenuGenerator(new MenuBuilder(bluej.getCurrentFrame(), pmdPreferences));
    }

    public boolean isCompatible () {
        return true; 
    }

    public String getVersion () {
        String version = "unknown";
        InputStream stream = null;
        try {
            stream = PMDExecExt.class.getResourceAsStream("/META-INF/maven/net.sourceforge.pmd/pmd-bluej/pom.properties");
            if (stream != null) {
                Properties properties = new Properties();
                properties.load(stream);
                version = properties.getProperty("version");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException e) { /* ignored */ }
            }
        }
        return version;
    }

    public String getName () {
        return ("PMD");
    }

    public String getDescription () {
        return "PMD extension - finds unused code, empty blocks, and more!";
    }

    public URL getURL () {
        try {
            return new URL("http://pmd.sf.net/");
        } catch ( Exception e ) {
            System.out.println ("Can't get PMD extension URL: "+e.getMessage());
            return null;
        }
    }
}
