package sample;

import org.json.simple.JSONObject;

/**
 * Created by Assar on 2015-12-08.
 */
public class Patrullstig extends Provyta implements ErrorMessage {


    public Patrullstig(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {

        super(name, type, associatedGeometry, associatedProperties);

        ProvyteEtablering.setTillganglighet((String)associatedProperties.get("Tillganglighet"));
        System.out.println("[Patrullstig] Tillgänglighet för patrullstigen är satt till: "+ProvyteEtablering.getTillganglighet());
        System.out.println("Patrullstig anropad");

        provytaFinns = true; // Aktiverar knapp i huvudfönstret

        createArtProvYtor(associatedProperties,5); //
    }
    public static boolean provytaFinns;// Om klassen anropas finns provytor av den här typen. Markören används för att aktivera knappen i knappraden.
}
