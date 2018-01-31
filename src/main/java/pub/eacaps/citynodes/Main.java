package pub.eacaps.citynodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        InputParser parser = new InputParser();
        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            parser.parseLine(line);
        }
        br.close();
        for (String result : parser.getResults()) {
            System.out.println(result);
        }
    }
}
