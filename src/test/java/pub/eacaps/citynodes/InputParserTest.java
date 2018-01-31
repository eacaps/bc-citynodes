package pub.eacaps.citynodes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class InputParserTest {
    private InputParser parser;

    @Before
    public void setUp() throws Exception {
        this.parser = new InputParser();
    }

    @Test
    public void testProcessCitiesLine() {
        this.parser.parseLine("Somewhere - Nowhere");
        this.parser.parseLine("Somewhere else - Nowhere");
        Collection<CityNode> cities = this.parser.getCities().getAllCities();
        assertEquals(3, cities.size());
    }

    @Test
    public void testProcessCityHopLine() {
        this.parser.getCities().addRoute("Somewhere", "Here");
        this.parser.getCities().addRoute("Somewhere", "Somewhere else");
        parser.parseLine("cities from Somewhere in 1 jumps");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals(true, results.get(0).contains("Somewhere else"));
        assertEquals(true, results.get(0).contains("Here"));
    }

    @Test
    public void testProcessTeleportLine() {
        this.parser.getCities().addRoute("Somewhere", "Here");
        this.parser.getCities().addRoute("Here", "Somewhere else");
        parser.parseLine("can I teleport from Somewhere to Somewhere else");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("can I teleport from Somewhere to Somewhere else: yes", results.get(0));
    }

    @Test
    public void testFalseProcessTeleportLine() {
        this.parser.getCities().addRoute("Somewhere", "Here");
        this.parser.getCities().addRoute("Here", "Somewhere else");
        this.parser.getCities().addRoute("Nowhere", "Everywhere");
        parser.parseLine("can I teleport from Somewhere to Nowhere");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("can I teleport from Somewhere to Nowhere: no", results.get(0));
    }

    @Test
    public void testProcessLoopLine() {
        this.parser.getCities().addRoute("Somewhere", "Here");
        this.parser.getCities().addRoute("Here", "Somewhere else");
        this.parser.getCities().addRoute("Somewhere", "Somewhere else");
        parser.parseLine("loop possible from Somewhere");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("loop possible from Somewhere: yes", results.get(0));
    }

    @Test
    public void testFalseProcessLoopLine() {
        this.parser.getCities().addRoute("Somewhere", "Here");
        this.parser.getCities().addRoute("Here", "Somewhere else");
        parser.parseLine("loop possible from Somewhere");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("loop possible from Somewhere: no", results.get(0));
    }
}