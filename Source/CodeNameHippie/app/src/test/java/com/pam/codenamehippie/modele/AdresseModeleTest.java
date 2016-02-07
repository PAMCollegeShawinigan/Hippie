package com.pam.codenamehippie.modele;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class AdresseModeleTest {

    private AdresseModele addresse1;
    private AdresseModele address2;

    @Before
    public void setUp() {
        this.addresse1 = new AdresseModele().setId(1)
                                            .setTypeRue("Rue")
                                            .setNom("Gouin")
                                            .setCodePostal("G9N7P6")
                                            .setVille("Shawinigan")
                                            .setPays("Canada")
                                            .setProvince("Quebec");
        this.address2 = new AdresseModele().setId(2)
                                           .setTypeRue("Avenue")
                                           .setNom("de la Station")
                                           .setCodePostal("g9n 8k9")
                                           .setVille("Shawinigan")
                                           .setPays("Canada")
                                           .setProvince("Quebec");
    }

    @After
    public void tearDown() {
        this.addresse1 = null;
        this.address2 = null;
    }

    @Test
    public void setCodePostalShouldStripSpacesAndUpperCase() {
        Assert.assertFalse("Code postal shouldn't contains spaces",
                           this.address2.getCodePostal().contains(" ")
                          );
        Assert.assertFalse("Code postal should be uppercase",
                           this.address2.getCodePostal().equals("g9n8k9")
                          );
    }

    @Test
    public void formattedCodePostalShouldBeLazilyInstantiated() throws Exception {
        Field field = this.addresse1.getClass().getDeclaredField("formattedCodePostal");
        field.setAccessible(true);
        String value = ((String) field.get(this.addresse1));
        assertNull("formattedCodePostal should be null at start", value);
        String formattedCodePostal = this.addresse1.getFormattedCodePostal();
        value = ((String) field.get(this.addresse1));
        assertNotNull("getFormattedCodePostal should cache its value",
                      value
                     );
        assertEquals("toFormattedString should return cached value",
                     formattedCodePostal,
                     value
                    );
        assertTrue("formattedCodePostal should be formatted",
                   formattedCodePostal.contains(" ")
                  );
        field.setAccessible(false);
    }

    @Test
    public void formattedStringShouldBeLazilyInstantiated() throws Exception {
        Field field = this.addresse1.getClass().getDeclaredField("formattedString");
        field.setAccessible(true);
        String value = ((String) field.get(this.addresse1));
        assertNull("formattedString should be null at start", value);
        String formattedString = this.addresse1.toFormattedString();
        value = ((String) field.get(this.addresse1));
        assertNotNull("toFormattedString should cache its value", value);
        assertEquals("toFormattedString should return cached value",
                     value,
                     formattedString
                    );
    }

    @Test
    @SuppressWarnings("EqualsWithItself")
    public void equalsTests() {
        AdresseModele addr1 = new AdresseModele().setId(1)
                                                 .setTypeRue("Rue")
                                                 .setNom("Gouin")
                                                 .setCodePostal("G9N7P6")
                                                 .setVille("Shawinigan")
                                                 .setPays("Canada")
                                                 .setProvince("Quebec");
        assertEquals("Same variable should be equal", this.addresse1, this.addresse1);
        Assert.assertNotEquals("Different addresses shouldn't be equal",
                               this.addresse1,
                               this.address2
                              );
    }
}