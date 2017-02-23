import duke.FileResource;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;
import java.util.List;

public class BabyBirth {

    public static void printNames(FileResource file){

        for (CSVRecord record:file.getCSVParser(false)) {
            int numBorn = Integer.parseInt(record.get(2));
            if (numBorn < 8) {
                System.out.println("Name: " + record.get(0) + ", gender: " + record.get(1) + ", number born:" + record.get(2));
            }
        }
    }

    public static List<Integer> totalBirth(FileResource fr){
        int numBorn=0;
        int boys=0;
        int girls = 0;
        for (CSVRecord record: fr.getCSVParser(false)) {
            int born = Integer.parseInt(record.get(2));
            numBorn += born;
            if (record.get(1).equals("M"))
                boys += born;
            else
                girls += born;
        }
        List<Integer> bornList = Arrays.asList(numBorn, boys, girls);
        return bornList;
        }

    public static long getRank(int year, String name, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        int nameNum = 0;
        long count=0;
        for (CSVRecord record: fr.getCSVParser(false)) {
            if (record.get(0) == name)
                nameNum = Integer.parseInt(record.get(2));
        }
        for (CSVRecord record: fr.getCSVParser(false)) {
            int currentNum = Integer.parseInt(record.get(2));
            if (record.get(1).equals(gender) && currentNum>nameNum){
                count++;
            }
        }
        return count+1;
    }



    public static void main(String[] arg){
        FileResource file = new FileResource("yob2012short.csv");
        printNames(file);
        List<Integer> born = totalBirth(file);
        System.out.println("Number born: "+born.get(0) +" number of boys born: "+born.get(1));
        System.out.println("Olivia in 2012 was the " + getRank(2012, "Olivia", "F")+ " most popular name among girls born.");
    }
}
