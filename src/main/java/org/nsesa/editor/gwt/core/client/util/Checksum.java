package org.nsesa.editor.gwt.core.client.util;

import com.googlecode.gwt.crypto.bouncycastle.digests.SHA1Digest;

/**
 * An annotations to generate a checksum given the fields that are listed.
 * Date: 05/10/12 11:48
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Checksum {
    public static String checksum(final String toHash) throws Exception {
        SHA1Digest sha1 = new SHA1Digest();
        byte[] bytes;
        byte[] result = new byte[sha1.getDigestSize()];
        bytes = toHash.getBytes();
        sha1.update(bytes, 0, bytes.length);
        sha1.doFinal(result, 0);

        return byteArrayToHexString(result);
    }

    private static String byteArrayToHexString(byte[] b) throws Exception {
        StringBuilder result = new StringBuilder();
        for (byte aB : b) {
            result.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
