package sample;


        import org.json.simple.JSONObject;


/**
 * Created by assarl on 2016-08-22.
 */
public class Angsobetekompl extends Provyta implements ErrorMessage {


    public Angsobetekompl(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {

        super(name, type, associatedGeometry, associatedProperties);
        super.setGisTyp("angsobetekompl");
        super.setInvKod((String) associatedProperties.get("Tillganglighet"));
        provytaFinns = true; // Aktiverar knapp i huvudfönstret
        createArtProvYtor(associatedProperties,5); //
        System.out.println("[Angsobetekompl] Tillgänglighet för ÄBO-komplettering är satt till: " + super.getInvKod());
    }


    public static boolean provytaFinns;
}

