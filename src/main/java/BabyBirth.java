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

        int boys=0;
        int girls = 0;
        for (CSVRecord record: fr.getCSVParser(false)) {
            if (record.get(1).equals("M"))
                boys ++;
            else
                girls ++;
        }
        int numBorn=girls+boys;
        List<Integer> bornList = Arrays.asList(numBorn, boys, girls);
        return bornList;
        }


    public static int getRank(int year, String name, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        int nameNum = 0;
        int count=0;
        for (CSVRecord record: fr.getCSVParser(false)) {
            if (name.equals(record.get(0))&& gender.equals(record.get(2)))
                nameNum = Integer.parseInt(record.get(2));
        }

        for (CSVRecord record: fr.getCSVParser(false)) {
            int currentNum = Integer.parseInt(record.get(2));
            if (currentNum>nameNum){
                count++;
            }
        }
        return count+1;
    }

    public static int getRank2(int year, String name, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        int nameNum=0;
        for (CSVRecord record: fr.getCSVParser(false)) {
                nameNum = Integer.parseInt(record.get(2));
        }

        int biggestInLoop=1000000;
        int count=0;

        while(nameNum<biggestInLoop){

            int biggestSoFar = 0;
            for (CSVRecord record: fr.getCSVParser(false)) {
                int currentNum = Integer.parseInt(record.get(2));
                if (currentNum>=biggestInLoop) break;
                else {
                    if (currentNum > biggestSoFar) {
                        biggestSoFar = currentNum;
                    }
                }}
        biggestInLoop = biggestSoFar;
        count++;
        }
            return count+1;
    }

    public static int getRank3(int year, String name, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        int nameNum=0;
        for (CSVRecord record: fr.getCSVParser(false)) {
            nameNum = Integer.parseInt(record.get(2));
        }
        int count=0;
        int biggestSoFar=0;
        while(biggestSoFar>nameNum){
            for (CSVRecord record: fr.getCSVParser(false)) {
                int currentNum = Integer.parseInt(record.get(2));
                if (currentNum==biggestSoFar) break;
                else if(currentNum>biggestSoFar &&biggestSoFar>nameNum){
                    biggestSoFar=currentNum;
                    count++;}
            }}
        return count+1;
    }


public static String getName(int year, int rank, String gender) {
    FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");

    for (CSVRecord record : fr.getCSVParser(false)) {
        if (gender.equals(record.get(1))) {
            int currentRank = getRank2(year, record.get(0), gender);
            if(currentRank-rank==0) return record.get(0);
        }}
    return "NO NAME";
}

 public static String whatIsNameInYear(String name, int year, int newYear, String gender){
     int rank = getRank(year, name, gender);
     String newName = getName(newYear, rank, gender);
     return  newName;
 }

    public static int yearOfHighestRank(String name, String gender){
     int highestSoFar=getRank(1880, name, gender);
     int year=0;
     for (int i=1881; i<=2014; i++){
         int currentRank=getRank(i, name, gender);
         if(currentRank<highestSoFar) {
             highestSoFar=currentRank;
             year=i;
         }
     }
     return year;
    }

    public static double getAverageRank(String name, String gender){

        DirectoryResource dr = new DirectoryResource();
        int sumSoFar=0;
        int count=0;
        for (File f : dr.selectedFiles()) {
            int year=Integer.parseInt(f.getName().substring(3,7));
            sumSoFar +=getRank(year, name, gender);
            count++;
        }
        double aver = sumSoFar/count;
        return aver;
    }


    public static void main(String[] arg){
//       FileResource f1 = new FileResource("us_babynames/us_babynames_by_year/yob1900.csv");
//       FileResource f2 = new FileResource("us_babynames/us_babynames_by_year/yob1905.csv");
//       List<Integer> born1900 = totalBirth(f1);
//       List<Integer> born1905 = totalBirth(f2);
//       System.out.println("Number of girls born: "+born1900.get(2));
//       System.out.println("Number of boys born: "+born1905.get(1));
//
//       System.out.println("In 1960 Emily was the: "+getRank(1960, "Emily", "F"));
//       System.out.println("Frank in 1971 was the " + getRank(1971, "Frank", "M")+ " most popular name.");

      // System.out.println("350th girl's name in 1980 was " + getName(1980, 350, "F"));
       System.out.println("450th boys's name in 1982 was " + getName(1982, 450, "M"));

//        System.out.println("Susan in 2014 was "+whatIsNameInYear("Susan", 1972, 2014, "F"));
//        System.out.println("Owen in 2014 was "+whatIsNameInYear("Owen", 1974, 2014, "M"));
////int owenRank=getRank(1974, "Owen", "M");
//System.out.print(owenRank);
//System.out.print(" , "+getName(2014, 6006, "M"));
        //Suppose Susan was born in 1972. Based on her name's rank in 1972,
// what would her name be if she were born in 2014 (that is, what name in 2014 had the same rank that "Susan" had in 1972)?
//System.out.println("Average ranking for Susan: " + getAverageRank("Susan", "F"));
//System.out.println("Average ranking for Robert: " + getAverageRank("Robert", "M"));

        // System.out.println("Emma was the most popular nam in "+yearOfHighestRank("Emma", "F"));
    }
}
