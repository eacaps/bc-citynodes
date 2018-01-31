package pub.eacaps.citynodes;

import java.util.ArrayList;
import java.util.Set;

public class InputParser {
    private final String FROM_STRING = "cities from ";
    private final String TELEPORT_STRING = "can I teleport from ";
    private final String LOOP_STRING = "loop possible from ";
    private Cities cities;
    private ArrayList<String> results;

    public InputParser() {
        this.cities = new Cities();
        this.results = new ArrayList<String>();
    }

    public void parseLine(String line) {
        if (line.contains(" - ")) {
            String[] city_strings = line.split(" - ");
            this.cities.addRoute(city_strings[0], city_strings[1]);
        } else if (line.startsWith(FROM_STRING)) {
            processCityHopLine(line);
        } else if (line.contains(TELEPORT_STRING)) {
            processTeleportLine(line);
        } else if (line.contains(LOOP_STRING)) {
            processLoopLine(line);
        }
    }

    protected void processCityHopLine(String line) {
//        cities from Seattle in 1 jumps
        String remainder = line.substring(FROM_STRING.length());
        int city_end = remainder.indexOf(" in ");
        String city = remainder.substring(0, city_end);
        String int_string = remainder.substring(city_end + 4, remainder.indexOf(" jumps"));
        int hops = Integer.parseInt(int_string);
        Set<CityNode> cities = this.cities.getCitiesWithinHops(city, hops);
//            cities from Seattle in 1 jumps: New York, Baltimore
        String result = "cities from " + city + " in " + hops + " jumps: ";
        boolean comma = false;
        for (CityNode city_node : cities) {
            if (comma)
                result += ", ";
            result += city_node;
            comma = true;
        }
        this.results.add(result);
    }

    protected void processTeleportLine(String line) {
//        can I teleport from New York to Atlanta
        String remainder = line.substring(TELEPORT_STRING.length());
        String[] cities = remainder.split(" to ");
        boolean connected = this.cities.areConnected(cities[0], cities[1]);
//        can I teleport from New York to Atlanta: yes
        String result = "can I teleport from " + cities[0] + " to " + cities[1] + ":";
        if (connected) {
            result += " yes";
        } else {
            result += " no";
        }
        this.results.add(result);
    }

    protected void processLoopLine(String line) {
//        loop possible from Oakland
        String city = line.substring(LOOP_STRING.length());
        boolean loop = this.cities.canLoop(city);
//        loop possible from Oakland: yes
        String result = "loop possible from " + city + ":";
        if (loop) {
            result += " yes";
        } else {
            result += " no";
        }
        this.results.add(result);
    }

    public ArrayList<String> getResults() {
        return this.results;
    }

    protected Cities getCities() {
        return cities;
    }
}
