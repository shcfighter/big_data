package com.ecit.high;

import java.io.FileWriter;
import java.io.File;
import java.util.Random;


class PeopleInfoFileGenerator {
    public static void main(String[] args) throws Exception{
        FileWriter writer = new FileWriter(new File("d:\\sample_people_info.txt"), false);
        Random rand = new Random();
        for (int i = 0; i < 100000000; i++){
            int height = rand.nextInt(220);
            if (height < 50) {
                height = height + 50;
            }
            String gender = getRandomGender();
            if (height < 100 && gender == "M")
                height = height + 100;
            if (height < 100 && gender == "F")
                height = height + 50;
            writer.write(i + " " + getRandomGender() + " " + height);
            writer.write(System.getProperty("line.separator"));
        }
        writer.flush();
        writer.close();
        System.out.println("People Information File generated successfully.");
    }

    public static String getRandomGender()
    {
        Random rand = new Random();
        int randNum = rand.nextInt(2) + 1;
        if (randNum % 2 == 0) {
            return "M";
        } else {
            return "F";
        }
    }
}