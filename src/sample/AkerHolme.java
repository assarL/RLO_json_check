package sample;

/**
 * Created by Assar on 2015-04-14.
 */
import java.util.Set;
import org.json.simple.JSONObject;


public class AkerHolme extends Geometry implements ErrorMessage {

    public AkerHolme(String name,String type,JSONObject associatedGeometry, JSONObject associatedProperties) {

        super();
        setName(name);
        System.out.println("AkerHolme �r anropad");

        Object associatedName = name;
        Object associatedType = type;


        System.out.println("Keys och values i featureRow" + associatedProperties.entrySet());

        //--Ladda variablerna till superklassen
        setName(name);
        setType(type);


        //--------------------ta ut de fr�n alla objekttypernas gemensamma variabler ur featureRow-objektet och lagra dessa i form av typ Geometry-objekt.

        setFixedGID(associatedProperties.get("FixedGID").toString()); // Plockar ut UUID ur json-filen och lagrar tillg�ngligt som "Geometry". Observera att detta saknas i blockdata.
        setEmployee(associatedProperties.get("USER_").toString());
        setObjID(associatedProperties.get("objecid").toString());
        //setRutaID(associatedData.get("rutaID").toString());

// Todo: G�r en try/catch som hanterar om en variabel helt saknas i datat s� att programmet kan g� vidare.


        //setCRS();


        //System.out.println("UID i java-objektet:"+getUID());
        //System.out.println("CRS i java-objektet:"+getCrs());
        //System.out.println("Type i java-objektet:"+getType());
        //String ObjectID = associatedData.get("OBJECTID").toString();
        //String GlobalID = associatedData.get("GlobalID").toString();




    }


    @Override
    public String getErrorMessage() {
        return null;
    }
}

