/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import org.junit.Assert;
import org.junit.Test;

public class PMDExecExtTest {

    @Test
    public void testVersion() {
        PMDExecExt ext = new PMDExecExt();
        String version = ext.getVersion();
        Assert.assertNotNull(version);
        Assert.assertTrue(version.length() > 0);
        if (!version.equals("unknown")) {
            Assert.assertTrue(Character.isDigit(version.charAt(0)));
        }
    }
}
