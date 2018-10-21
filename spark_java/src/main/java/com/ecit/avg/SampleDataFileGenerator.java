package com.ecit.avg;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class SampleDataFileGenerator {
    public static void main(String[] args) throws Exception{
        FileWriter writer = new FileWriter(new File("d:\\sample_age_data.txt"),false);
        Random rand = new Random();
        for (int i = 0; i < 10000000; i++) {
            writer.write( i + " " + rand.nextInt(100));
            writer.write(System.getProperty("line.separator"));
        }
        writer.flush();
        writer.close();
    }
}
