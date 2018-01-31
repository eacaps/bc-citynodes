package pub.eacaps.citynodes;

import java.util.*;

public class Cities {
    HashMap<String, CityNode> cities;

    public Cities() {
        this.cities = new HashMap<String, CityNode>();
    }

    public void addRoute(String left, String right) {
        CityNode left_city = this.getNode(left);
        CityNode right_city = this.getNode(right);
        left_city.addAdjacentCity(right_city);
        right_city.addAdjacentCity(left_city);
    }

    private CityNode getNode(String city) {
        CityNode node = this.cities.get(city);
        if (node == null) {
            node = new CityNode(city);
            this.cities.put(city, node);
        }
        return node;
    }

    public boolean canLoop(String origin) {
        CityNode origin_node = this.cities.get(origin);
        return checkForLoop(origin_node, null, origin_node, new ArrayList<CityNode>());
    }

    public boolean checkForLoop(CityNode origin, CityNode prev, CityNode cur, ArrayList<CityNode> visited) {
        if (cur == origin && prev != null) {
            return true;
        }
        boolean result = false;
        if (!visited.contains(cur)) {
            visited.add(cur);
            for (CityNode city_node : cur.getAdjacentCities()) {
                if (city_node != prev)
                    result = result || checkForLoop(origin, cur, city_node, visited);
            }
        }
        return result;
    }

    public boolean areConnected(String origin, String destination) {
        CityNode origin_node = this.cities.get(origin);
        CityNode destination_node = this.cities.get(destination);
        return searchForCity(origin_node, destination_node);
    }

    private boolean searchForCity(CityNode origin, CityNode destination) {
        ArrayList<CityNode> adjacent_cities = origin.getAdjacentCities();
        if (adjacent_cities.contains(destination)) {
            return true;
        } else {
            for (CityNode next : adjacent_cities) {
                return searchForCity(next, destination);
            }
        }
        return false;
    }

    public Set<CityNode> getCitiesWithinHops(String city, int hops) {
        HashMap<CityNode, Integer> result = new HashMap<CityNode, Integer>();
        CityNode city_node = this.cities.get(city);
        this.recursiveCityHopping(city_node, hops, result);
        result.remove(city_node);
        return result.keySet();
    }

    private void recursiveCityHopping(CityNode city, int hops, HashMap<CityNode, Integer> result) {
        if (result.containsKey(city)) {
            Integer level = result.get(city);
            if (level >= hops) {
//                System.out.println("skipping: " + city + "level: " + hops);
                return;
            } else {
                result.put(city, hops);
            }
        } else {
            result.put(city, hops);
        }
        ArrayList<CityNode> neighbors = city.getAdjacentCities();
        for (CityNode neighbor : neighbors) {
            if (hops > 0) {
//                System.out.println("recurse: " + neighbor + " level: " + (hops - 1));
                this.recursiveCityHopping(neighbor, hops - 1, result);
            }
        }
    }
}
