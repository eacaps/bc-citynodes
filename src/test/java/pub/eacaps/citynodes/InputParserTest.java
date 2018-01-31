package pub.eacaps.citynodes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class InputParserTest {
    private Cities cities;
    private InputParser parser;

    @Before
    public void setUp() throws Exception {
        this.cities = new Cities();
        this.parser = new InputParser(cities);
    }

    @Test
    public void testProcessCitiesLine() {
        this.parser.parseLine("Somewhere - Nowhere");
        this.parser.parseLine("Somewhere else - Nowhere");
        Collection<CityNode> cities = this.cities.getCities();
        assertEquals(3, cities.size());
    }

    @Test
    public void testProcessCityHopLine() {
        cities.addRoute("Somewhere", "Here");
        cities.addRoute("Somewhere", "Somewhere else");
        parser.parseLine("cities from Somewhere in 1 jumps");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("cities from Somewhere in 1 jumps: Here, Somewhere else", results.get(0));
    }

    @Test
    public void testProcessTeleportLine() {
        cities.addRoute("Somewhere", "Here");
        cities.addRoute("Here", "Somewhere else");
        parser.parseLine("can I teleport from Somewhere to Somewhere else");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("can I teleport from Somewhere to Somewhere else: yes", results.get(0));
    }

    @Test
    public void testFalseProcessTeleportLine() {
        cities.addRoute("Somewhere", "Here");
        cities.addRoute("Here", "Somewhere else");
        cities.addRoute("Nowhere", "Everywhere");
        parser.parseLine("can I teleport from Somewhere to Nowhere");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("can I teleport from Somewhere to Nowhere: no", results.get(0));
    }

    @Test
    public void testProcessLoopLine() {
        cities.addRoute("Somewhere", "Here");
        cities.addRoute("Here", "Somewhere else");
        cities.addRoute("Somewhere", "Somewhere else");
        parser.parseLine("loop possible from Somewhere");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("loop possible from Somewhere: yes", results.get(0));
    }

    @Test
    public void testFalseProcessLoopLine() {
        cities.addRoute("Somewhere", "Here");
        cities.addRoute("Here", "Somewhere else");
        parser.parseLine("loop possible from Somewhere");
        ArrayList<String> results = parser.getResults();
        assertEquals(1, results.size());
        assertEquals("loop possible from Somewhere: no", results.get(0));
    }
}