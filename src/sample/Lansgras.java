package sample;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;


/**
 * Created by Assar on 2015-06-07.
 */
public class Lansgras extends Provyta implements ErrorMessage{

    public Lansgras(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {
        super(name, type, associatedGeometry, associatedProperties);
        super.setGisTyp("lansgras");
        super.setInvKod((String)associatedProperties.get("Tillganglighet"));
        provytaFinns = true; // Aktiverar knapp i huvudfönstret

        System.out.println("[Lansgras] Tillgänglighet för länsgräsprovytan är satt till: "+super.getInvKod());

        createArtProvYtor(associatedProperties,5); //

        ArrayList allaArter = getSummeradeSmaProvArter();

        //ArtProvYta thisSubYta = thisPy.getSubProvytor().get(spykey);

        Set Geometrykeys = associatedProperties.keySet();

/*
        for(Object key : Geometrykeys) {  // Loopa igenom alla rader
            Object geometryPost = associatedProperties.get(key); // Hämta en rad
            HashMap<String,ArtProvYta> ArtProvytorna;
            if (geometryPost instanceof Provyta) {  // Utesluter alla poster som inte härleds från provytor
                Provyta thisPy = (Provyta) geometryPost; // Byt typ till Provyta eftersom det går..
                ArtProvytorna = thisPy.getSubProvytor(); // Hämta artprovytorna från provytan i form av en hashmap.
                Set keys = ArtProvytorna.keySet();  // Plocka ut de indivduella nycklarna från hashmappen

                for (Object spykey : keys) {
                    System.out.println("[ArtForekomstTabell] spykey : " + spykey);
                    ArtProvYta thisSubYta = thisPy.getSubProvytor().get(spykey);
                    System.out.println("[ArtForekomstTabell] ArtReg på thisSubYta:" + thisSubYta.getArtLista());
                    for (ArtReg artR : thisSubYta.getArtLista()) {
                        System.out.println("[ArtForekomstTabell] artR : " + artR.getArt());
                    }
                }
            }} */
    }




    public static boolean provytaFinns;

}

