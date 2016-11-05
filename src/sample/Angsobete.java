package sample;

import org.json.simple.JSONObject;

/**
 * Created by Assar on 2016-05-28.
 */
public class Angsobete extends Provyta implements ErrorMessage{


    public static boolean provytaFinns;

    public Angsobete(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {

        super(name, type, associatedGeometry, associatedProperties);

        provytaFinns = true; // Aktiverar knapp i huvudfönstret

        createArtProvYtor(associatedProperties,9); // Nio småprovytor på ÄBO-ytor

        String TillganlighetABO = (String)associatedProperties.get("TillganglighetAoB"); // Ängsobete har en egen tillgänglighetsvariabel som har ett enda unikt värde, i övrigt samma.

        super.setInvKod(TillganlighetABO);

        // Abo har egna NILS-variabler för beteshöjd/mängd.
        //TackningHog,TackningMattlig,TackningLag,TackningOvrigt,TackningTuvor,TackningOvrigt

        System.out.println("[Angsobete] Angsobete anropad");

    }
}



/**
 * Created by Assar on 2015-06-07.
 */





