/*  
    c3256846A2P3.java
    
    *Author: Benjamin Hogan
    *Student No: c3256846
    *Date: 2/10/2017
    *Descripion:
    *   Main method to run the program, takes an arg from the command line for file input
 */
import java.util.*;
import java.io.*;

public class c3256846A2P3 {
    
    private Printer printer = new Printer();
    private Mode colour = new Mode(printer, 1), mono = new Mode(printer, 0);

    public static void main(String[] args)
    {
        c3256846A2P3 control = new c3256846A2P3();
        String file = "";

        try
            {//takes argument from the command line 
                file = args[0];
            }
        catch (ArrayIndexOutOfBoundsException e)
            {// checks for argument, if not quits.
                System.out.println ("Please add a file to the command line.");
                System.exit ( 0 );
            }

        control.jobFactory(file);
        control.startPrinting();
    }

    // Role:    starts the threads of the Modes
    // Args:    none
    // Return:  none
    public void startPrinting()
    {
        mono.start();
        colour.start();
    }

    // Role:    reads the input given and creates jobs placing them in their respective queues in Printer/Mode.
    // Args:    String input file
    // Return:  none
    public void jobFactory(String input)
    {
        Scanner console = new Scanner(System.in);
        Scanner scanner = null;

        //try-catch for scanner.
        try
        {
            scanner = new Scanner (new File (input)); 
            String add, line = "";
            String[] data;
            int track = 0, total = 0, type;

            //while loop builds string from input file
            while (scanner.hasNextLine()) 
            {
                line += scanner.nextLine();
            }

            //splits the string and works out where each process is and collects data about the object.
            line = line.replace(" ", "P"); // replaces spaces with P to distinguish the space between type and pages
            data = line.split("");
            add = "";
            
            // finds how many jobs are in the file. Count
            while(!(data[track].equals("C") || data[track].equals("M")))
            {
                add += data[track];
                track++;
            }
            int count = Integer.parseInt(add);    

            while(total < count)
            {
                Job temp = new Job();
                add = "";

                //Monochrome Job or Colour Job
                if(data[track].equals("M"))
                    temp.setType("M");
                else 
                    temp.setType("C");

                // The ID of the job
                while(!data[track].equals("P"))
                {
                    add += data[track];
                    track++;
                }
                track++;
                temp.setID(add);
                add = "";

                // Pages 
                try{
                    while(!(data[track].equals("M") || data[track].equals("C")))
                    {
                        add += data[track];
                        track++;
                    }
                }catch(ArrayIndexOutOfBoundsException e){}
                temp.setPages(Integer.parseInt(add));

                //Then add to the queues
                if(temp.getType().equals("C"))
                    {colour.add(temp);
                    printer.add(temp, 1);}
                else   
                    {mono.add(temp);
                    printer.add(temp, 0);}
                total++;
            }
        }catch (FileNotFoundException e){System.exit(0);}
    }
}