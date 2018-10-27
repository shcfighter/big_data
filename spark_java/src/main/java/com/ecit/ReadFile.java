package com.ecit;

import java.io.IOException;

public class ReadFile {
    public static void main(String[] args) throws IOException {
        System.out.println(ReadFile.class.getClassLoader().getResource("ip2region.db").getPath());
    }
}
