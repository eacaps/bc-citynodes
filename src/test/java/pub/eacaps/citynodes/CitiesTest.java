package pub.eacaps.citynodes;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class CitiesTest {

    @Test
    public void testSimpleCityHoping() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        Set<CityNode> cities_near_a = cities.getCitiesWithinHops("A", 1);
        assertEquals(2, cities_near_a.size());
    }

    @Test
    public void testDupCityHoping() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        cities.addRoute("B", "C");
        cities.addRoute("C", "D");
        cities.addRoute("D", "E");
        Set<CityNode> cities_near_a = cities.getCitiesWithinHops("A", 2);
        assertEquals(3, cities_near_a.size());
    }

    @Test
    public void testDeepCityHoping() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        cities.addRoute("B", "C");
        cities.addRoute("C", "D");
        cities.addRoute("D", "E");
        cities.addRoute("A", "E");
        cities.addRoute("E", "F");
        cities.addRoute("F", "G");
        Set<CityNode> cities_near_a = cities.getCitiesWithinHops("A", 3);
        assertEquals(6, cities_near_a.size());
    }

    @Test
    public void testSimpleAreConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        assertEquals(true, cities.areConnected("A", "B"));
    }

    @Test
    public void testLinearAreConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("B", "C");
        cities.addRoute("B", "D");
        cities.addRoute("C", "F");
        cities.addRoute("C", "E");
        assertEquals(true, cities.areConnected("A", "C"));
    }

    @Test
    public void testSimpleCanLoop() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("B", "C");
        cities.addRoute("C", "A");
        assertEquals(true, cities.canLoop("A"));
    }

    @Test
    public void testNoCanLoop() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("B", "C");
        cities.addRoute("C", "D");
        assertEquals(false, cities.canLoop("A"));
    }

    @Test
    public void testComplexCanLoop() {
        Cities cities = new Cities();
        cities.addRoute("W", "B");
        cities.addRoute("W", "A");
        cities.addRoute("B", "P");
        cities.addRoute("P", "N");
        cities.addRoute("N", "S");
        cities.addRoute("S", "T");
        cities.addRoute("B", "S");
        assertEquals(false, cities.canLoop("W"));
        assertEquals(true, cities.canLoop("B"));
    }
}