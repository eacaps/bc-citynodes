package pub.eacaps.citynodes;

import java.util.ArrayList;

public class CityNode {
    private String name;
    private ArrayList<CityNode> adjacent_cities;

    public CityNode(String name) {
        this.name = name;
        this.adjacent_cities = new ArrayList<CityNode>();
    }

    public void addAdjacentCity(CityNode city) {
        this.adjacent_cities.add(city);
    }

    public ArrayList<CityNode> getAdjacentCities() {
        return this.adjacent_cities;
    }

    @Override
    public String toString() {
        return "CityNode{" +
                "name='" + name + '\'' +
                '}';
    }
}
