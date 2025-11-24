package com.uninter.vidaplus.healthcarefacility.unit;

import com.uninter.vidaplus.healthcarefacility.core.domain.Cnpj;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CnpjUnitTest {

    @Test
    void shouldKeepDigitsOnlyWhenConstructed() {
        Cnpj cnpj = new Cnpj("26.793.920/0001-06");
        assertEquals("26793920000106", cnpj.getValue());
    }

    @Test
    void shouldAcceptCnpjWithoutFormatting() {
        Cnpj cnpj = new Cnpj("26793920000106");
        assertEquals("26793920000106", cnpj.getValue());
    }

    @Test
    void shouldRemoveAllNonNumericCharacters() {
        Cnpj cnpj = new Cnpj("ABC26*793#920-0001!!06XYZ");
        assertEquals("26793920000106", cnpj.getValue());
    }

    @Test
    void shouldAllowEmptyString() {
        Cnpj cnpj = new Cnpj("");
        assertEquals("", cnpj.getValue());
    }

    @Test
    void shouldAllowStringWithOnlySpecialCharacters() {
        Cnpj cnpj = new Cnpj("..-!!///");
        assertEquals("", cnpj.getValue());
    }
}

