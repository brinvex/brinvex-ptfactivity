package com.brinvex.brokercon.testsupport;

import com.brinvex.fintypes.enu.Country;
import com.brinvex.fintypes.enu.Currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionUtil {

    public static void assertPtfSnapshotEqual(SimplePtf expectedPtf, SimplePtf actualPtf) {
        assertEquals(expectedPtf.getCurrencies(), actualPtf.getCurrencies());
        for (Currency ccy : expectedPtf.getCurrencies()) {
            BigDecimal expectedCash = expectedPtf.getCash(ccy);
            BigDecimal actualCash = actualPtf.getCash(ccy);
            assertEquals(0, expectedCash.compareTo(actualCash),
                    () -> "expectedCash=%s, actualCash=%s, ccy=%s".formatted(expectedCash, actualCash, ccy));
        }

        Map<Country, Set<String>> expectedCountrySymbols = expectedPtf.getCountrySymbols(false);
        assertEquals(expectedCountrySymbols, actualPtf.getCountrySymbols(false));
        for (Map.Entry<Country, Set<String>> e : expectedCountrySymbols.entrySet()) {
            Country country = e.getKey();
            Set<String> symbols = e.getValue();
            for (String symbol : symbols) {
                BigDecimal expectedQty = expectedPtf.getHoldingQty(country, symbol);
                BigDecimal qty = actualPtf.getHoldingQty(country, symbol);
                assertEquals(expectedQty, qty, () -> "%s:%s".formatted(country, symbol));
            }
        }
    }

}
