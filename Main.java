import java.io.*;
import java.util.HashMap;
// import java.util.Scanner;
// import java.util.ArrayList;
// import java.util.Collections;
import java.util.*;

public class Main{
    public static void main(String[] args) throws Exception{
        //read from a file
        Scanner scan = new Scanner(System.in);
        String fileName = scan.nextLine();

        File file = new File(fileName);
        Scanner read = new Scanner(file);

        Hashtable<String, Player> map = new Hashtable<String, Player>();
        Hashtable<String, String> keys = new Hashtable<String, String>();
        fillKeys(keys);
        while(read.hasNextLine()){
            //get the first line
            String line = read.nextLine();

            parse(line, map, keys);
        }

        read.close();
        scan.close();

        //print everything out
        PrintWriter p = new PrintWriter("leaders.txt");
        print(map, p);
        printLeaders(p, map);
    }

    public static void parse(String line, Hashtable<String, Player> map, Hashtable<String, String> keys){
        //create player object
        Player p = new Player();

        //get either home or away
        String team = line.substring(0, 1);
        p.setTeam(team.charAt(0));

        //make line smaller
        line = line.substring(2);

        //get the name of the player
        p.setName(line.substring(0, line.indexOf(' ')));

        //make line smaller
        line = line.substring(line.indexOf(' '));

        //get the key
        p.setCode(line.substring(1));

        //search through map and make sure no other player object with the same name exists
        String key = keys.get(p.getCode());

        switch (key) {
            case "outs":
                p.incrementOuts();
                break;
                
            case "strike":
                p.incrementStrike();
                break;

            case "hits":
                p.incrementHits();
                break;

            case "walks":
                p.incrementWalks();
                break;

            case "sacrifice":
                p.incrementSacrifice();
                break;

            case "hit by pitch":
                p.incrementHbp();
                break;

            case "errors":
                p.incrementPA();
                break;
        
            default: //anything else
                break;
        }

        if(map.containsKey(p.getName())){
            //if map has the name of the player update the player object in map
            Player newPlayer = map.get(p.getName()).add(p);
            map.replace(newPlayer.getName(), newPlayer);
        }
        else{
            map.put(p.getName(), p);
        }

    }
     
    public static void fillKeys(Hashtable<String, String> keys){
        //outs
        keys.put("1-3", "outs");
        keys.put("2-3", "outs");
        keys.put("3u", "outs");
        keys.put("3-1", "outs");
        keys.put("3-4", "outs");
        keys.put("4-3", "outs");
        keys.put("5-3", "outs");
        keys.put("6-3", "outs");
        keys.put("7-3", "outs");
        keys.put("8-3", "outs");
        keys.put("P1", "outs");
        keys.put("P2", "outs");
        keys.put("P3", "outs");
        keys.put("P4", "outs");
        keys.put("P5", "outs");
        keys.put("P6", "outs");
        keys.put("F7", "outs");
        keys.put("F8", "outs");
        keys.put("F9", "outs");
        keys.put("DP", "outs");
        keys.put("FC", "outs");

        //strikeouts
        keys.put("K", "strike");

        //hits
        keys.put("1B", "hits");
        keys.put("2B", "hits");
        keys.put("3B", "hits");
        keys.put("HR", "hits");

        //walks
        keys.put("BB", "walks");

        //sacrifice
        keys.put("S1", "sacrifice");
        keys.put("S2", "sacrifice");
        keys.put("S3", "sacrifice");
        keys.put("S5", "sacrifice");
        keys.put("SF7", "sacrifice");
        keys.put("SF8", "sacrifice");
        keys.put("SF9", "sacrifice");

        //hit by pitch
        keys.put("HBP", "hit by pitch");

        //errors
        keys.put("E1", "errors");
        keys.put("E2", "errors");
        keys.put("E3", "errors");
        keys.put("E4", "errors");
        keys.put("E5", "errors");
        keys.put("E6", "errors");
        keys.put("E7", "errors");
        keys.put("E8", "errors");
        keys.put("E9", "errors");
    }

    public static void sort(ArrayList<Player> list){
        int i = 1, j;
        Player temp;
        for (j = 1; j < list.size(); j++){
            temp = list.get(j);
            i = j - 1;

            while (i >= 0){
                if (temp.getName().compareTo(list.get(i).getName()) > 0 ){
                    break;
                }

                Player element = list.get(i + 1);
                list.set(i + 1, list.get(i));
                list.set(i, element);
                i--;
            }
        }
    }

    public static void print(Hashtable<String, Player> map, PrintWriter p){
        ArrayList<Player> list = new ArrayList<>();
        //only print away characters first
        p.println("AWAY");

        //get all the away characters first
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().getTeam() == 'A'){
                //theyre on the away team
                list.add(entry.getValue());
            }
        }

        sort(list);

        //print list
        for(int i = 0; i < list.size(); i++){
            p.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%.3f\t%.3f%n", list.get(i).getName(), (int)list.get(i).atBats(), list.get(i).getHits(),
            list.get(i).getWalks(), list.get(i).getStrike(), list.get(i).getHbp(), list.get(i).getSacrifice(), list.get(i).battingAverage(),
            list.get(i).onBasePercentage());
            p.flush();
        }

        list.clear();
        p.println("\nHOME");

        //get all the home characters
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().getTeam() == 'H'){
                //theyre on the home team
                list.add(entry.getValue());
            }
        }

        sort(list);

        //print list
        for(int i = 0; i < list.size(); i++){
            p.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%.3f\t%.3f%n", list.get(i).getName(), (int)list.get(i).atBats(), list.get(i).getHits(),
            list.get(i).getWalks(), list.get(i).getStrike(), list.get(i).getHbp(), list.get(i).getSacrifice(), list.get(i).battingAverage(),
            list.get(i).onBasePercentage());
            p.flush();
        }
    }

    public static void printLeaders(PrintWriter p, Hashtable<String, Player> map){
        ArrayList<Player> home = new ArrayList<>(), away = new ArrayList<>();
        double fstHighest = -1, secHighest = -1, thrdHighest = -1;
        int totalPrinted = 0;

        p.println("\nLEAGUE LEADERS");

        p.println("BATTING AVERAGE");
        //find the highest batting avg value
        //loop through hashmap and put highest player objects in arraylist
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().battingAverage() > fstHighest){
                //set fstHighest to entry.getValue
                fstHighest = entry.getValue().battingAverage();

                //clear the lists
                home.clear();
                away.clear();

                //add value to the list depending on the team
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
            //if value is the same as the highest add it to list depending on the team
            else if(entry.getValue().battingAverage() == fstHighest){
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
        }
        //sort both lists alphabetically
        sort(away);
        sort(home);

        //print away and then print home
        if(away.size() > 0)
            p.printf("%.3f\t", away.get(0).battingAverage()); // print the number
        else{
            p.printf("%.3f\t", home.get(0).battingAverage());
        }

        //loop through away and print
        if(home.size() > 0 && away.size() > 0){
            for(int i = 0; i < away.size(); i++){
                p.print(away.get(i).getName() + ", ");
            }
        }
        else if(away.size() > 0){
            for(int i = 0; i < away.size()-1; i++){
                p.print(away.get(i).getName() + ", ");
            }
            p.print(away.get(away.size()-1).getName());
        }

        //loop through home and print
        for(int i = 0; i < home.size()-1; i++){
            p.print(home.get(i).getName() + ", ");
        }
        if(home.size() > 0)
           p.print(home.get(home.size()-1).getName());
        p.println();
        p.flush();

        //get the total number of names printed
        totalPrinted = home.size() + away.size();

        //check how much is printed
        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().battingAverage() > secHighest && entry.getValue().battingAverage() < fstHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().battingAverage();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().battingAverage() == secHighest && entry.getValue().battingAverage() < fstHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%.3f\t", away.get(0).battingAverage()); // print the number
            else{
                p.printf("%.3f\t", home.get(0).battingAverage());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();

            //get the total number of names printed
            totalPrinted = totalPrinted + home.size() + away.size();
        }

        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().battingAverage() > thrdHighest && entry.getValue().battingAverage() < secHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().battingAverage();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().battingAverage() == thrdHighest && entry.getValue().battingAverage() < secHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%.3f\t", away.get(0).battingAverage()); // print the number
            else{
                p.printf("%.3f\t", home.get(0).battingAverage());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();
        }

        p.println();
        p.flush();

        /////////////////////////////////////////////////////////////////////////////////////////
        fstHighest = -1; secHighest = -1; thrdHighest = -1;
        totalPrinted = 0;

        p.println("ON-BASE PERCENTAGE");
        //find the highest obp value
        //loop through hashmap and put highest player objects in arraylist
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().onBasePercentage() > fstHighest){
                //set fstHighest to entry.getValue
                fstHighest = entry.getValue().onBasePercentage();

                //clear the lists
                home.clear();
                away.clear();

                //add value to the list depending on the team
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
            //if value is the same as the highest add it to list depending on the team
            else if(entry.getValue().onBasePercentage() == fstHighest){
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
        }
        //sort both lists alphabetically
        sort(away);
        sort(home);

        //print away and then print home
        if(away.size() > 0)
            p.printf("%.3f\t", away.get(0).onBasePercentage()); // print the number
        else{
            p.printf("%.3f\t", home.get(0).onBasePercentage());
        }

        //loop through away and print
        if(home.size() > 0 && away.size() > 0){
            for(int i = 0; i < away.size(); i++){
                p.print(away.get(i).getName() + ", ");
            }
        }
        else if(away.size() > 0){
            for(int i = 0; i < away.size()-1; i++){
                p.print(away.get(i).getName() + ", ");
            }
            p.print(away.get(away.size()-1).getName());
        }

        //loop through home and print
        for(int i = 0; i < home.size()-1; i++){
            p.print(home.get(i).getName() + ", ");
        }
        if(home.size() > 0)
           p.print(home.get(home.size()-1).getName());
        p.println();
        p.flush();

        //get the total number of names printed
        totalPrinted = home.size() + away.size();

        //check how much is printed
        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().onBasePercentage() > secHighest && entry.getValue().onBasePercentage() < fstHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().onBasePercentage();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().onBasePercentage() == secHighest && entry.getValue().onBasePercentage() < fstHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%.3f\t", away.get(0).onBasePercentage()); // print the number
            else{
                p.printf("%.3f\t", home.get(0).onBasePercentage());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();

            //get the total number of names printed
            totalPrinted = totalPrinted + home.size() + away.size();
        }

        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().onBasePercentage() > thrdHighest && entry.getValue().onBasePercentage() < secHighest){
                    //set secHighet to entry.getValue
                    thrdHighest = entry.getValue().onBasePercentage();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().onBasePercentage() == thrdHighest && entry.getValue().onBasePercentage() < secHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%.3f\t", away.get(0).onBasePercentage()); // print the number
            else{
                p.printf(".3f\t", home.get(0).onBasePercentage());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();
        }

        p.println();
        p.flush();

        //////////////////////////////////////////////////////////////////////////////////////////
        fstHighest = -1; secHighest = -1; thrdHighest = -1;
        totalPrinted = 0;

        p.println("HITS");
        //find the highest obp value
        //loop through hashmap and put highest player objects in arraylist
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().getHits() > fstHighest){
                //set fstHighest to entry.getValue
                fstHighest = entry.getValue().getHits();

                //clear the lists
                home.clear();
                away.clear();

                //add value to the list depending on the team
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
            //if value is the same as the highest add it to list depending on the team
            else if(entry.getValue().getHits() == fstHighest){
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
        }
        //sort both lists alphabetically
        sort(away);
        sort(home);

        //print away and then print home
        if(away.size() > 0)
            p.printf("%d\t", away.get(0).getHits()); // print the number
        else{
            p.printf("%d\t", home.get(0).getHits());
        }

        //loop through away and print
        if(home.size() > 0 && away.size() > 0){
            for(int i = 0; i < away.size(); i++){
                p.print(away.get(i).getName() + ", ");
            }
        }
        else if(away.size() > 0){
            for(int i = 0; i < away.size()-1; i++){
                p.print(away.get(i).getName() + ", ");
            }
            p.print(away.get(away.size()-1).getName());
        }

        //loop through home and print
        for(int i = 0; i < home.size()-1; i++){
            p.print(home.get(i).getName() + ", ");
        }
        if(home.size() > 0)
           p.print(home.get(home.size()-1).getName());
        p.println();
        p.flush();

        //get the total number of names printed
        totalPrinted = home.size() + away.size();

        //check how much is printed
        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getHits() > secHighest && entry.getValue().getHits() < fstHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().getHits();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getHits() == secHighest && entry.getValue().getHits() < fstHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getHits()); // print the number
            else{
                p.printf("%d\t", home.get(0).getHits());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();

            //get the total number of names printed
            totalPrinted = totalPrinted + home.size() + away.size();
        }

        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getHits() > thrdHighest && entry.getValue().getHits() < secHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().getHits();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getHits() == thrdHighest && entry.getValue().getHits() < secHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getHits()); // print the number
            else{
                p.printf("%d\t", home.get(0).getHits());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();
        }

        p.println();
        p.flush();

        //////////////////////////////////////////////////////////////////////////////////////////
        fstHighest = -1; secHighest = -1; thrdHighest = -1;
        totalPrinted = 0;

        p.println("WALKS");
        //find the highest obp value
        //loop through hashmap and put highest player objects in arraylist
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().getWalks() > fstHighest){
                //set fstHighest to entry.getValue
                fstHighest = entry.getValue().getWalks();

                //clear the lists
                home.clear();
                away.clear();

                //add value to the list depending on the team
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
            //if value is the same as the highest add it to list depending on the team
            else if(entry.getValue().getWalks() == fstHighest){
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
        }
        //sort both lists alphabetically
        sort(away);
        sort(home);

        //print away and then print home
        if(away.size() > 0)
            p.printf("%d\t", away.get(0).getWalks()); // print the number
        else{
            p.printf("%d\t", home.get(0).getWalks());
        }

        //loop through away and print
        if(home.size() > 0 && away.size() > 0){
            for(int i = 0; i < away.size(); i++){
                p.print(away.get(i).getName() + ", ");
            }
        }
        else if(away.size() > 0){
            for(int i = 0; i < away.size()-1; i++){
                p.print(away.get(i).getName() + ", ");
            }
            p.print(away.get(away.size()-1).getName());
        }

        //loop through home and print
        for(int i = 0; i < home.size()-1; i++){
            p.print(home.get(i).getName() + ", ");
        }
        if(home.size() > 0)
            p.print(home.get(home.size()-1).getName());
        p.println();
        p.flush();

        //get the total number of names printed
        totalPrinted = home.size() + away.size();

        //check how much is printed
        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getWalks() > secHighest && entry.getValue().getWalks() < fstHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().getWalks();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getWalks() == secHighest && entry.getValue().getWalks() < fstHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getWalks()); // print the number
            else{
                p.printf("%d\t", home.get(0).getWalks());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();

            //get the total number of names printed
            totalPrinted = totalPrinted + home.size() + away.size();
        }

        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getWalks() > thrdHighest && entry.getValue().getWalks() < secHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().getWalks();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getWalks() == thrdHighest && entry.getValue().getWalks() < secHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getWalks()); // print the number
            else{
                p.printf("%d\t", home.get(0).getWalks());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();
        }

        p.println();
        p.flush();

        //////////////////////////////////////////////////////////////////////////////////////////
        int fstLowest = 999, secLowest = 999, thrdLowest = 999;
        totalPrinted = 0;

        p.println("STRIKEOUTS");
        //find the highest obp value
        //loop through hashmap and put highest player objects in arraylist
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().getStrike() < fstLowest){
                //set fstLowest to entry.getValue
                fstLowest = entry.getValue().getStrike();

                //clear the lists
                home.clear();
                away.clear();

                //add value to the list depending on the team
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
            //if value is the same as the highest add it to list depending on the team
            else if(entry.getValue().getStrike() == fstLowest){
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
        }
        //sort both lists alphabetically
        sort(away);
        sort(home);

        //print away and then print home
        if(away.size() > 0)
            p.printf("%d\t", away.get(0).getStrike()); // print the number
        else{
            p.printf("%d\t", home.get(0).getStrike());
        }

        //loop through away and print
        if(home.size() > 0 && away.size() > 0){
            for(int i = 0; i < away.size(); i++){
                p.print(away.get(i).getName() + ", ");
            }
        }
        else if(away.size() > 0){
            for(int i = 0; i < away.size()-1; i++){
                p.print(away.get(i).getName() + ", ");
            }
            p.print(away.get(away.size()-1).getName());
        }

        //loop through home and print
        for(int i = 0; i < home.size()-1; i++){
            p.print(home.get(i).getName() + ", ");
        }
        if(home.size() > 0)
            p.print(home.get(home.size()-1).getName());
        p.println();
        p.flush();

        //get the total number of names printed
        totalPrinted = home.size() + away.size();

        //check how much is printed
        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getStrike() > secLowest && entry.getValue().getStrike() > fstLowest){
                    //set secHighet to entry.getValue
                    secLowest = entry.getValue().getStrike();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getStrike() == secLowest && entry.getValue().getStrike() > fstLowest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getStrike()); // print the number
            else{
                p.printf("%d\t", home.get(0).getStrike());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();

            //get the total number of names printed
            totalPrinted = totalPrinted + home.size() + away.size();
        }

        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getStrike() > thrdLowest && entry.getValue().getStrike() > secLowest){
                    //set secHighet to entry.getValue
                    secLowest = entry.getValue().getStrike();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getStrike() == thrdHighest && entry.getValue().getStrike() > secLowest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getStrike()); // print the number
            else{
                p.printf("%d\t", home.get(0).getStrike());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();
        }

        p.println();
        p.flush();

        //////////////////////////////////////////////////////////////////////////////////////////
        fstHighest = -1; secHighest = -1; thrdHighest = -1;
        totalPrinted = 0;

        p.println("HIT BY PITCH");
        //find the highest obp value
        //loop through hashmap and put highest player objects in arraylist
        for(HashMap.Entry<String, Player> entry : map.entrySet()){
            if(entry.getValue().getHbp() > fstHighest){
                //set fstHighest to entry.getValue
                fstHighest = entry.getValue().getHbp();

                //clear the lists
                home.clear();
                away.clear();

                //add value to the list depending on the team
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
            //if value is the same as the highest add it to list depending on the team
            else if(entry.getValue().getHbp() == fstHighest){
                if(entry.getValue().getTeam() == 'A')
                    away.add(entry.getValue());
                else{
                    home.add(entry.getValue());
                }
            }
        }
        //sort both lists alphabetically
        sort(away);
        sort(home);

        //print away and then print home
        if(away.size() > 0)
            p.printf("%d\t", away.get(0).getHbp()); // print the number
        else{
            p.printf("%d\t", home.get(0).getHbp());
        }

        //loop through away and print
        if(home.size() > 0 && away.size() > 0){
            for(int i = 0; i < away.size(); i++){
                p.print(away.get(i).getName() + ", ");
            }
        }
        else if(away.size() > 0){
            for(int i = 0; i < away.size()-1; i++){
                p.print(away.get(i).getName() + ", ");
            }
            p.print(away.get(away.size()-1).getName());
        }

        //loop through home and print
        for(int i = 0; i < home.size()-1; i++){
            p.print(home.get(i).getName() + ", ");
        }
        if(home.size() > 0)
            p.print(home.get(home.size()-1).getName());
        p.println();
        p.flush();

        //get the total number of names printed
        totalPrinted = home.size() + away.size();

        //check how much is printed
        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getHbp() > secHighest && entry.getValue().getHbp() < fstHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().getHbp();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getHbp() == secHighest && entry.getValue().getHbp() < fstHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getHbp()); // print the number
            else{
                p.printf("%d\t", home.get(0).getHbp());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();

            //get the total number of names printed
            totalPrinted = totalPrinted + home.size() + away.size();
        }

        if(totalPrinted < 3){
            //if the total printed is < 3 go ahead and print second place
            for(HashMap.Entry<String, Player> entry : map.entrySet()){
                if(entry.getValue().getHbp() > thrdHighest && entry.getValue().getHbp() < secHighest){
                    //set secHighet to entry.getValue
                    secHighest = entry.getValue().getHbp();
    
                    //clear the lists
                    home.clear();
                    away.clear();
    
                    //add value to the list depending on the team
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
                //if value is the same as the highest add it to list depending on the team
                else if(entry.getValue().getHbp() == thrdHighest && entry.getValue().getHbp() < secHighest){
                    if(entry.getValue().getTeam() == 'A')
                        away.add(entry.getValue());
                    else{
                        home.add(entry.getValue());
                    }
                }
            }

            //sort both lists alphabetically
            sort(away);
            sort(home);

            //print away and then print home
            if(away.size() > 0)
                p.printf("%d\t", away.get(0).getHbp()); // print the number
            else{
                p.printf("%d\t", home.get(0).getHbp());
            }

            //loop through away and print
            if(home.size() > 0 && away.size() > 0){
                for(int i = 0; i < away.size(); i++){
                    p.print(away.get(i).getName() + ", ");
                }
            }
            else if(away.size() > 0){
                for(int i = 0; i < away.size()-1; i++){
                    p.print(away.get(i).getName() + ", ");
                }
                p.print(away.get(away.size()-1).getName());
            }

            //loop through home and print
            for(int i = 0; i < home.size()-1; i++){
                p.print(home.get(i).getName() + ", ");
            }
            if(home.size() > 0)
                p.print(home.get(home.size()-1).getName());
            p.println();
            p.flush();
        }
        p.println();
        p.flush();
    }
}