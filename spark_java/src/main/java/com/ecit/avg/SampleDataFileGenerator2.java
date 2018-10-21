package com.ecit.avg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SampleDataFileGenerator2 {
    public static void main(String[] args) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(new File("d:\\sample_age_data.txt")));
        long count = 0;
        for (int i = 0; i < 10000000; i++) {
            String line = reader.readLine();
            count += Long.parseLong(line.split(" ")[1]);
        }
        System.out.println(count);
        System.out.println(count / 10000000);
    }
}
