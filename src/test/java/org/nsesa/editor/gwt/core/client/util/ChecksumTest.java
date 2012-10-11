package org.nsesa.editor.gwt.core.client.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Date: 11/10/12 16:49
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ChecksumTest {
    @Test
    public void testChecksumNull() throws Exception {
        Assert.assertNull("Expecting a null return value.", Checksum.checksum(null));
    }

    @Test
    public void testChecksumBlank() throws Exception {
        final String checksum = Checksum.checksum("");
        Assert.assertEquals("Validating the checksum for a blank string.", "da39a3ee5e6b4b0d3255bfef95601890afd80709", checksum);
    }

    @Test
    public void testChecksumWhitespaceAware() throws Exception {
        final String checksum1 = Checksum.checksum(" ");
        final String checksum2 = Checksum.checksum("  ");
        Assert.assertNotSame("Making sure the checksum is whitespace aware.", checksum1, checksum2);
    }

    /**
     * @throws Exception
     * @see <a href="http://stackoverflow.com/questions/7071902/small-differences-in-sha1-hashes">Stackoverflow discussion</a>
     */
    @Test
    public void testChecksum() throws Exception {
        final String checksum = Checksum.checksum("happa");
        Assert.assertEquals("Validating the checksum (see Javadoc).", "fb3c3a741b4e07a87d9cb68f3db020d6fbfed00a", checksum);
    }
}
