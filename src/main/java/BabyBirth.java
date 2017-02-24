import duke.DirectoryResource;
import duke.FileResource;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
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

public static String getName(int year, long rank, String gender) {
    FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");

    for (CSVRecord record : fr.getCSVParser(false)) {
        if (record.get(1).equals(gender)) {
            long count = 0;
            long currentRecordNum = Long.parseLong(record.get(2));
            for (CSVRecord recordd : fr.getCSVParser(false)) {
                if (recordd.get(1).equals(gender)) {
                    long currentRecorddNum = Long.parseLong(recordd.get(2));
                    if (currentRecorddNum > currentRecordNum) {
                        count++;}
                    }
            }
            if (count==(rank-1l)) {return record.get(0);}
        }
        }
    return "NO NAME";
}

 public static String whatIsNameInYear(String name, int year, int newYear, String gender){
     long rank = getRank(year, name, gender);
     String newName = getName(newYear, rank, gender);
     return  newName;
 }

    public static int yearOfHighestRank(String name, String gender){
     long highestSoFar=0;
     int year=0;
     for (int i=1880; i<=2014; i++){
         long currentRank=getRank(i, name, gender);
         if(currentRank>highestSoFar) {
             highestSoFar=currentRank;
             year=i;
         }
     }
     return year;
    }

    public static double getAverageRank(String name, String gender){

        DirectoryResource dr = new DirectoryResource();
        long sumSoFar=0;
        long count=0;
        for (File f : dr.selectedFiles()) {
            int year=Integer.parseInt(f.getAbsolutePath().substring(3,7));
            sumSoFar +=getRank(year, name, gender);
            count++;
        }
        return sumSoFar/count;
    }


    public static void main(String[] arg){
//       FileResource file = new FileResource("us_babynames/us_babynames_by_year/yob1905.csv");
//       printNames(file);
//       List<Integer> born = totalBirth(file);
//        System.out.println("Number born: "+born.get(0) +" number of boys born: "+born.get(1));
        //System.out.println("Frank in 1971 was the " + getRank(1971, "Frank", "F")+ " most popular name among girls born.");
       //System.out.println("350th girl's name in 2012 was " + getName(1980, 4, "F") );
       // System.out.println("450th girl's name in 2012 was " + getName(1982, 450, "M") );
      //   System.out.println("Susan in 2014 was "+whatIsNameInYear("Susan", 1972, 2014, "F"));
        //System.out.println("Owen in 2014 was "+whatIsNameInYear("Owen", 1974, 2014, "M"));
//long owenRank=getRank(1974, "Owen", "M");
//System.out.print(owenRank);
System.out.print(" , "+getName(2014, 6006, "M"));
        //Suppose Susan was born in 1972. Based on her name's rank in 1972,
// what would her name be if she were born in 2014 (that is, what name in 2014 had the same rank that "Susan" had in 1972)?
//System.out.println("Average ranking for Susan: " + getAverageRank("Susan", "F"));
//System.out.println("Average ranking for Robert: " + getAverageRank("Robert", "M"));

        // System.out.println("Emma was the most popular nam in "+yearOfHighestRank("Emma", "F"));
    }
}
