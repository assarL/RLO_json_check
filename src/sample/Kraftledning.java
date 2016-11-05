package sample;

import org.json.simple.JSONObject;

/**
 * Created by Assar on 2015-12-07.
 */
public class Kraftledning extends Provyta implements ErrorMessage {

    public Kraftledning(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {

        super(name, type, associatedGeometry, associatedProperties);
        provytaFinns = true; // Aktiverar knapp i huvudfönstret
        ProvyteEtablering.setTillganglighet((String)associatedProperties.get("Tillganglighet"));
        System.out.println("[Kraftledning] Tillgänglighet är satt till: "+ProvyteEtablering.getTillganglighet());
        createArtProvYtor(associatedProperties,5); // Skapa artlistor för artprovytorna
    }

    public static boolean provytaFinns;// Om klassen anropas finns provytor av den här typen. Markören används för att aktivera knappen i knappraden.


}
