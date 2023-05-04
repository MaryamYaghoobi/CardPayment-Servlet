package ir.co.isc.helper;

import org.apache.log4j.Logger;

import java.util.Random;

public class GenerationCVV2 {
    Logger logger = Logger.getRootLogger();

    public int generateCVV2() {
        int cvv2 = 000;
        Random rand = new Random();
        for (int i = 1; i <= 100; i++) {
            int randomNum = rand.nextInt((999 - 100) + 1) + 100;

            logger.info("has been created : " + randomNum);

        }
        return cvv2;
    }
}
