package sample;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by assarl on 2016-09-24.
 * Info om CSVReader hittas här: http://opencsv.sourceforge.net/
 * "You may also skip the first few lines of the file if you know that the content doesn't start till later in the file. So, for example, you can skip the first two lines by doing:

 CSVReader reader = new CSVReader(new FileReader("yourfile.csv"), '\t', '\'', 2);"
 *
 */
public class ArtForekomstTabell {

    public ArtForekomstTabell(HashMap GeometryListHashmap) throws IOException { //Todo: skicka endast nycklar från de objekt som ska visas i tabellen (Provytor).

        Set Geometrykeys = GeometryListHashmap.keySet();

        System.out.println("[ArtForekomstTabell] Geometrykeys:\n" + Geometrykeys);

        CSVReader reader = new CSVReader(
                new FileReader("C:\\Users\\assarl\\IdeaProjects\\ERBI_datakoll\\src\\sample\\Groups - ERBI 2016.txt"), ',', '\"', 2);
        List<String[]> myEntries = reader.readAll();
// Den aktuella artlistan läses in här i form av en csv-fil. Biblioteket "opencsv" används för detta.

        String[] SpeciesNames = new String[myEntries.size()];
        int i = 0;
        for (String[] entry : myEntries) {
            //System.out.println("[ArtForekomstTabell] entry:  " + entry[4]);  // Skriver ut art-variabelnamnet.
            SpeciesNames[i] = entry[4]; // Artens namnvariabel finns i denna kolumn.
            i++;
        }
        //Arrays.sort(SpeciesNames);

        Set<String> mySet = new HashSet<>(Arrays.asList(SpeciesNames)); // Gör om artlistan till ett keyset.

        //
        //System.out.println("[ArtForekomstTabell] GeometryKeys :"+Geometrykeys.toString());
        System.out.println("[ArtForekomstTabell] mySet innehåller :"+mySet.toString());

        System.out.println("[ArtForekomstTabell] SpeciesNames.length = "+SpeciesNames.length);

        for (Object key : Geometrykeys) {  // Loopa igenom alla rader
            Object geometryPost = GeometryListHashmap.get(key); // Hämta en rad från exportfilen
            ArtProvYta[] ArtPry;
            //HashMap<String,ArtProvYta[]> ArtProvytorna;
            if (geometryPost instanceof Provyta) {  // Utesluter alla poster som inte härleds från provytor
                Provyta thisPy = (Provyta)geometryPost; // Byt typ till Provyta eftersom det går..
                ArtPry = thisPy.getArtPry();
                //ArtProvytorna = thisPy.getSubProvytor(); // Hämta artprovytorna från provytan i form av en hashmap.
if (ArtPry!=null) {
    //Set keys = ArtPry.toString();  // Plocka ut de indivduella nycklarna från hashmappen
    System.out.println("[ArtForekomstTabell] ArtPry.length :"+ArtPry.length);

    for (int j=0; j<ArtPry.length; j++){

        ArrayList templist = ArtPry[j].getArtLista();
        for (int k=0;k<=ArtPry.length;k++)
        System.out.println("templist"+templist.get(k));
        Set<String> tempHash = new HashSet<String>();
    }
    }

}
                System.out.println("---------------[ArtForekomstTabell]---nästa provyta-------------");


            }// Slut på loop

        }

    }



