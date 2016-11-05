package sample;

/**
 * Created by Assar on 2015-04-14.
 */
import java.util.Set;
import org.json.simple.JSONObject;


public class BarandeTradBuskar extends Geometry {

    public BarandeTradBuskar(String name,String type,JSONObject featureRow) {


        super();

        System.out.println("BarandeTradBuskar är anropad");

        Object associatedName = name;
        Object associatedType = type;

        JSONObject associatedGeometry = (JSONObject) featureRow.get("geometry");
        JSONObject associatedData = (JSONObject) featureRow.get("properties");	// Detta plockar ut properties-objektet


        System.out.println("Keys och values i featureRow" + associatedData.entrySet());

        //--Ladda variablerna till superklassen
        setName(name);
        setType(type);


        //--------------------ta ut de från alla objekttypernas gemensamma variabler ur featureRow-objektet och lagra dessa i form av typ Geometry-objekt.

        setFixedGID(associatedProperties.get("FixedGID").toString()); // Plockar ut UUID ur json-filen och lagrar tillgängligt som "Geometry". Observera att detta saknas i blockdata.
        setEmployee(associatedData.get("USER_").toString());
        setObjID(associatedData.get("OBJECTID").toString());


// Todo: Gör en try/catch som hanterar om en variabel helt saknas i datat så att programmet kan gå vidare.


        //System.out.println("UID i java-objektet:"+getUID());
        //System.out.println("CRS i java-objektet:"+getCrs());
        //System.out.println("Type i java-objektet:"+getType());
        //String ObjectID = associatedData.get("OBJECTID").toString();
        //String GlobalID = associatedData.get("GlobalID").toString();




    }



}

