package pub.eacaps.citynodes;


import java.util.*;

/**
 * This class represents our network of cities.
 */
public class Cities {
    HashMap<String, CityNode> cities;

    public Cities() {
        this.cities = new HashMap<String, CityNode>();
    }

    /**
     *
     * @return collection of all the connected cities
     */
    public Collection<CityNode> getAllCities() {
        return cities.values();
    }

    /**
     * take the processed strings and create a connection
     * @param left
     * @param right
     */
    public void addRoute(String left, String right) {
        if (left != right) {
            CityNode left_city = this.getNode(left);
            CityNode right_city = this.getNode(right);
            left_city.addAdjacentCity(right_city);
            right_city.addAdjacentCity(left_city);
        }
    }

    /**
     * get or create a citynode for a city
     * @param city
     * @return citynode for the requested city
     */
    private CityNode getNode(String city) {
        CityNode node = this.cities.get(city);
        if (node == null) {
            node = new CityNode(city);
            this.cities.put(city, node);
        }
        return node;
    }

    /**
     * public accessor for checking a loop
     * @param origin
     * @return true if there is a loop for this city, false otherwise
     */
    public boolean canLoop(String origin) {
        CityNode origin_node = this.cities.get(origin);
        return checkForLoop(origin_node, null, origin_node, new HashSet<CityNode>());
    }

    /**
     * protected recursive function to determine looping
     * @param origin the city we are looking for
     * @param previous the city we just came from in the graph
     * @param current the city we are on now
     * @param visited the list of cities that have been visited
     * @return true if there is a loop for this city, false otherwise
     */
    protected boolean checkForLoop(CityNode origin, CityNode previous, CityNode current, HashSet<CityNode> visited) {
        if (current == origin && previous != null) {
            return true;
        }
        boolean result = false;
        if (current != null && !visited.contains(current)) {
            visited.add(current);
            for (CityNode city_node : current.getAdjacentCities()) {
                if (city_node != previous)
                    result = result || checkForLoop(origin, current, city_node, visited);
            }
        }
        return result;
    }

    /**
     * public accessor for checking if two cities are connected
     * @param origin
     * @param destination
     * @return true if there is a path connecting the cities, false otherwise
     */
    public boolean areConnected(String origin, String destination) {
        if (origin == destination)
            return true;
        CityNode origin_node = this.cities.get(origin);
        CityNode destination_node = this.cities.get(destination);
        return searchForCity(origin_node, destination_node, new HashSet<CityNode>());
    }

    /**
     * recursive method to find a path between cities
     * @param current
     * @param destination
     * @param visited list of cities traversed so we don't backtrack
     * @return true if there is a path connecting the cities, false otherwise
     */
    private boolean searchForCity(CityNode current, CityNode destination, HashSet<CityNode> visited) {
        if (current == null || destination == null)
            return false;
        ArrayList<CityNode> adjacent_cities = current.getAdjacentCities();
        if (adjacent_cities.contains(destination)) {
            return true;
        } else {
            visited.add(current);
            for (CityNode next : adjacent_cities) {
                if (!visited.contains(next))
                    return searchForCity(next, destination, visited);
            }
        }
        return false;
    }

    /**
     * public accessor for finding cities within a certain number of teleports
     * @param city origin
     * @param hops max distance
     * @return set of cities
     */
    public Set<CityNode> getCitiesWithinHops(String city, int hops) {
        HashMap<CityNode, Integer> result = new HashMap<CityNode, Integer>();
        CityNode city_node = this.cities.get(city);
        if (city_node != null) {
            this.recursiveCityHopping(city_node, hops, result);
        }
        result.remove(city_node);
        return result.keySet();
    }

    /**
     * advanced search for connected nodes, a hashmap is used with the level so we can don't recompute results we
     * already know
     * @param city origin
     * @param hops max disatnce
     * @param result hashmap of citynode and distance, used to skip paths that have already been traversed
     */
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
