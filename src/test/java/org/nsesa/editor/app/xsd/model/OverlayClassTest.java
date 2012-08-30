package org.nsesa.editor.app.xsd.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Date: 06/08/12 22:33
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayClassTest {

    @Test
    public void testGetImports() throws Exception {
        OverlayClass overlayClass = new OverlayClass();
        overlayClass.getProperties().add(new OverlayProperty("tld.domain.package1", "Foo", "foo"));
        overlayClass.getProperties().add(new OverlayProperty("tld.domain.package2", "Bar", "bar"));
        overlayClass.getProperties().add(new OverlayProperty("tld.domain.package3", "Baz", "baz"));
        overlayClass.getProperties().add(new OverlayProperty("tld.domain.package3", "Doo", "doo"));

        final String[] imports = overlayClass.getImports();
        assertNotNull(imports);
        assertEquals(3, imports.length);

        final List<String> importList = Arrays.asList(imports);
        assertTrue(importList.contains("tld.domain.package1"));
        assertTrue(importList.contains("tld.domain.package2"));
        assertTrue(importList.contains("tld.domain.package3"));
    }

    @Test
    public void testJavaLangImports() throws Exception {
        OverlayClass overlayClass = new OverlayClass();
        overlayClass.getProperties().add(new OverlayProperty("java.lang", "Foo", "foo"));
        overlayClass.getProperties().add(new OverlayProperty("java.lang", "Bar", "bar"));
        overlayClass.getProperties().add(new OverlayProperty("tld.domain.package1", "Baz", "baz"));

        final String[] imports = overlayClass.getImports();
        assertNotNull(imports);
        assertEquals(1, imports.length);

        final List<String> importList = Arrays.asList(imports);
        assertTrue(importList.contains("tld.domain.package1"));
        assertTrue(!importList.contains("java.lang"));
    }

    @Test
    public void testNoImports() throws Exception {
        OverlayClass overlayClass = new OverlayClass();
        final String[] imports = overlayClass.getImports();
        assertNotNull(imports);
        assertEquals(0, imports.length);
    }
}
