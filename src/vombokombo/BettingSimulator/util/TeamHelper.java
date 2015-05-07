package vombokombo.BettingSimulator.util;

import vombokombo.BettingSimulator.MainApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

/**
 * Created by Luca on 21.04.2015.
 */
public class TeamHelper {

    public static int lineCount = -1;
    public static final String FILENAME = "teamnames.txt";

    public static String getRandomTeamName() {
        try {
            System.out.println(MainApp.class.getResource(FILENAME));
            if (lineCount == -1) {
                BufferedReader reader = new BufferedReader(new FileReader(new File(MainApp.class.getResource(FILENAME).toURI())));
                int tempCount = 0;
                String read = reader.readLine();
                while (read != null) {
                    tempCount++;
                    reader.readLine();
                }
                lineCount = tempCount;

            }
            BufferedReader reader = new BufferedReader(new FileReader(new File(MainApp.class.getResource(FILENAME).toURI())));
            int pos = new Random().nextInt(lineCount);

            for (int i = 0; i < pos; i++) {
                reader.readLine();
            }
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

}
