package sample;

import com.sun.istack.internal.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by Assar on 2015-06-07.
 */
public class Akerkant extends Provyta implements ErrorMessage {

    public Akerkant(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {
        super(name, type, associatedGeometry, associatedProperties);
        provytaFinns = true; // Aktiverar knapp i huvudfönstret
        createArtProvYtor(associatedProperties,5); //
        super.setInvKod((String)associatedProperties.get("Tillganglighet"));
        System.out.println("[Provyta] Tillgänglighet för åkerkantsprovytan är satt till: "+super.getInvKod());

    }

    public static boolean provytaFinns;// Om klassen anropas finns provytor av den här typen. Markören används för att aktivera knappen i knappraden.


}



