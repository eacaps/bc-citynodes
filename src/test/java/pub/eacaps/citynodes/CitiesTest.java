package pub.eacaps.citynodes;

import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class CitiesTest {

    @Test
    public void testAddRoute() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        Collection<CityNode> city_collection = cities.getCities();
        assertEquals(city_collection.size(), 3);
    }

    @Test
    public void testAddSelfRoute() {
        Cities cities = new Cities();
        cities.addRoute("A", "A");
        Collection<CityNode> city_collection = cities.getCities();
        assertEquals(city_collection.size(), 0);
    }

    @Test
    public void testSimpleCityHoping() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        Set<CityNode> cities_near_a = cities.getCitiesWithinHops("A", 1);
        assertEquals(2, cities_near_a.size());
    }

    @Test
    public void testNoCityHoping() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        Set<CityNode> cities_near_d = cities.getCitiesWithinHops("D", 1);
        assertEquals(0, cities_near_d.size());
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
    public void testSelfConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        assertEquals(true, cities.areConnected("A", "A"));
    }

    @Test
    public void testNonExistantSelfConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        assertEquals(true, cities.areConnected("C", "C"));
    }

    @Test
    public void testAreNotConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("A", "C");
        cities.addRoute("C", "D");
        cities.addRoute("B", "E");
        cities.addRoute("F", "G");
        assertEquals(false, cities.areConnected("A", "G"));
    }

    @Test
    public void testNonExistantRightAreConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        assertEquals(false, cities.areConnected("A", "C"));
    }

    @Test
    public void testNonExistantLeftAreConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        assertEquals(false, cities.areConnected("C", "A"));
    }

    @Test
    public void testLinearAreConnected() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("B", "C");
        cities.addRoute("B", "D");
        cities.addRoute("C", "F");
        cities.addRoute("C", "E");
        assertEquals(true, cities.areConnected("A", "E"));
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
    public void testNonExistantCanLoop() {
        Cities cities = new Cities();
        cities.addRoute("A", "B");
        cities.addRoute("B", "C");
        cities.addRoute("C", "D");
        assertEquals(false, cities.canLoop("E"));
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