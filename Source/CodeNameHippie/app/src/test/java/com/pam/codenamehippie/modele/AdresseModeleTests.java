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

public class AdresseModeleTests {

    private AdresseModele adresse1;
    private AdresseModele adresse2;

    @Before
    public void setUp() {
        this.adresse1 = new AdresseModele().setId(1)
                                           .setTypeRue("Rue")
                                           .setNom("Gouin")
                                           .setCodePostal("G9N7P6")
                                           .setVille("Shawinigan")
                                           .setPays("Canada")
                                           .setProvince("Quebec");
        this.adresse2 = new AdresseModele().setId(2)
                                           .setTypeRue("Avenue")
                                           .setNom("de la Station")
                                           .setCodePostal("g9n 8k9")
                                           .setVille("Shawinigan")
                                           .setPays("Canada")
                                           .setProvince("Quebec");
        for (Field field : AdresseModele.class.getDeclaredFields()) {
            field.setAccessible(true);
        }
    }

    @After
    public void tearDown() {
        this.adresse1 = null;
        this.adresse2 = null;
        for (Field field : AdresseModele.class.getDeclaredFields()) {
            field.setAccessible(false);
        }
    }

    @Test
    public void setCodePostalShouldStripSpacesAndUpperCase() {
        Assert.assertFalse("Code postal shouldn't contains spaces",
                           this.adresse2.getCodePostal().contains(" ")
                          );
        Assert.assertFalse("Code postal should be uppercase",
                           this.adresse2.getCodePostal().equals("g9n8k9")
                          );
    }

    @Test
    public void formattedCodePostalShouldBeLazilyInstantiated() throws Exception {
        Field field = this.adresse1.getClass().getDeclaredField("formattedCodePostal");
        String value = ((String) field.get(this.adresse1));
        assertNull("formattedCodePostal should be null at start", value);
        String formattedCodePostal = this.adresse1.getFormattedCodePostal();
        value = ((String) field.get(this.adresse1));
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
    }

    @Test
    public void formattedStringShouldBeLazilyInstantiated() throws Exception {
        Field field = this.adresse1.getClass().getDeclaredField("formattedString");
        String value = ((String) field.get(this.adresse1));
        assertNull("formattedString should be null at start", value);
        String formattedString = this.adresse1.toFormattedString();
        value = ((String) field.get(this.adresse1));
        assertNotNull("toFormattedString should cache its value", value);
        assertEquals("toFormattedString should return cached value",
                     value,
                     formattedString
                    );
    }

    @Test
    public void setCodePostalShouldInvalidateCachedStrings() throws Exception {
        Field string = this.adresse1.getClass().getDeclaredField("formattedString");
        Field code = this.adresse1.getClass().getDeclaredField("formattedCodePostal");
        this.adresse1.toFormattedString();
        assertNotNull("toFormattedString should fill string cache", string.get(this.adresse1));
        assertNotNull("toFormattedString should fill string cache", code.get(this.adresse1));
        this.adresse1.setCodePostal("H0H 0H0");
        assertNull("setCodePostal should empty string cache", string.get(this.adresse1));
        assertNull("setCodePostal should empty string cache", code.get(this.adresse1));

    }

    @Test
    public void equalityTests() {
        AdresseModele addr1 = new AdresseModele().setId(1)
                                                 .setTypeRue("Rue")
                                                 .setNom("Gouin")
                                                 .setCodePostal("G9N7P6")
                                                 .setVille("Shawinigan")
                                                 .setPays("Canada")
                                                 .setProvince("Quebec");
        assertEquals("Same variable should be equal", this.adresse1, this.adresse1);
        assertEquals("Same values should be equal", this.adresse1, addr1);
        assertEquals("Same values hashcode should be equal",
                     this.adresse1.hashCode(),
                     addr1.hashCode()
                    );
        Assert.assertNotEquals("Different addresses shouldn't be equal",
                               this.adresse1,
                               this.adresse2
                              );
    }
}