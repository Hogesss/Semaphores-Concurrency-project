/*  
    c3256846A2P1.java
    
    *Author: Benjamin Hogan
    *Student No: c3256846
    *Date: 2/10/2017
    *Descripion:
    *   Main method to run the program, takes an arg from the command line for farmers
 */
import java.util.*;
import java.io.*;

public class c3256846A2P1 {
    public static void main(String[] args)
    {
        // Input of "N=x, S=y"
        String north[] = args[0].split("=");
        String south[] = args[1].split("=");

        north = north[1].split(",");

        // Input of north and bound farmers
        int northbound = Integer.parseInt(north[0]);
        int southbound = Integer.parseInt(south[1]);

        // Creates a new bridge with north and south number of farmers
        Bridge bridge = new Bridge(northbound, southbound);
    }
}