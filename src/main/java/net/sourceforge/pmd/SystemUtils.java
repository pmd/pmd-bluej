/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import java.util.Locale;

public class SystemUtils {
    public static boolean isWindows() {
        String osname = System.getProperty("os.name");
        return osname.toLowerCase(Locale.ROOT).contains("win");
    }
}
