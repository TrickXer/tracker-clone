package com.gamestats.gamestats;

import java.util.*;

public class test {
    public static void main(String[] args) {
        String nameAndTag = "NesaMani Pray";
    
        String name[] = nameAndTag.split(" ");
        String username = name[0];
        String tagline = name[1];
    
        System.out.println(username + " " + tagline);
    }
}
