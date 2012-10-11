package org.nsesa.editor.gwt.core.client.util;

import com.googlecode.gwt.crypto.bouncycastle.digests.SHA1Digest;

/**
 * Checksum utility class.
 * Date: 05/10/12 11:48
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Checksum {
    public static String checksum(final String toHash) throws Exception {
        if (toHash == null) return null;
        final SHA1Digest sha1 = new SHA1Digest();
        final byte[] result = new byte[sha1.getDigestSize()];
        final byte[] bytes = toHash.getBytes();
        sha1.update(bytes, 0, bytes.length);
        sha1.doFinal(result, 0);

        return byteArrayToHexString(result);
    }

    private static String byteArrayToHexString(final byte[] b) throws Exception {
        final StringBuilder result = new StringBuilder();
        for (final byte aB : b) {
            result.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
