package sample;

/**
 * Created by Assar on 2015-04-13.
 */
import java.io.File;

public class FileCheck{

    boolean fileExists;

    public FileCheck(String mappToCheck, String fileToCheck){

        File mappPath = new File(mappToCheck);
        File currentFile = new File(mappPath,fileToCheck);
        System.out.println("Filobjektet: " +currentFile);
        System.out.println(mappToCheck +" är en giltig mapp? : "+ mappPath.isDirectory()); // Om det är ett giltigt directory..
        System.out.println(currentFile.exists()?fileToCheck+" finns":fileToCheck+" finns inte");// Om filen finns i den eventuella mappen.

        fileExists= (currentFile.exists());

        FilListFilter FilTyp = new FilListFilter("json");

        File[] innehall = mappPath.listFiles(FilTyp);


        for (int i=0;i<innehall.length;i++){
            System.out.println(innehall[i]);

        }


    }

    public FileCheck(File file, String fileEnding){               // Tar ett filobjekt som argument

        FilListFilter FilTyp = new FilListFilter(fileEnding);
        File currentFile = file;
        fileExists= (currentFile.exists());
        System.out.println("CurrentFile exists:"+fileExists);
    }


}
