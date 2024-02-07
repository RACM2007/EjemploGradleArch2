package cl.bci.ecodig.acceptance;

import com.intuit.karate.junit5.Karate;

class KarateTests {

    @Karate.Test
    Karate testAll() {
        System.setProperty("karate.env", "dev");
        return Karate.run().relativeTo(getClass());
    }
}