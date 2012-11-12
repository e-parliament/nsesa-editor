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
        PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl("");

        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "tld.domain.package1", "", "Foo", "foo", false, false));
        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "tld.domain.package2","",  "Bar", "bar", false, false));
        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "tld.domain.package3", "", "Baz", "baz", false, false));
        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "tld.domain.package3", "", "Doo", "doo", false, false));

        final String[] imports = overlayClass.getImports(packageNameGenerator);
        assertNotNull(imports);
        assertEquals(4, imports.length);

        final List<String> importList = Arrays.asList(imports);
        assertTrue(importList.contains("tld.domain.package1.Foo"));
        assertTrue(importList.contains("tld.domain.package2.Bar"));
        assertTrue(importList.contains("tld.domain.package3.Baz"));
        assertTrue(importList.contains("tld.domain.package3.Doo"));
    }

    @Test
    public void testJavaLangImports() throws Exception {
        OverlayClass overlayClass = new OverlayClass();
        PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl("");
        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "java.lang", "", "Foo", "foo", false, false));
        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "java.lang", "", "Bar", "bar", false, false));
        overlayClass.getProperties().add(new OverlayProperty(OverlayType.SimpleType, "tld.domain.package1", "", "Baz", "baz", false, false));

        final String[] imports = overlayClass.getImports(packageNameGenerator);
        assertNotNull(imports);
        assertEquals(1, imports.length);

        final List<String> importList = Arrays.asList(imports);
        assertTrue(importList.contains("tld.domain.package1.Baz"));
        assertTrue(!importList.contains("java.lang.Bar"));
    }

    @Test
    public void testNoImports() throws Exception {
        OverlayClass overlayClass = new OverlayClass();
        PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl("");
        final String[] imports = overlayClass.getImports(packageNameGenerator);
        assertNotNull(imports);
        assertEquals(0, imports.length);
    }
}
