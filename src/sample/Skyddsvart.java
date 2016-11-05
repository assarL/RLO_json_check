package sample;

import org.json.simple.JSONObject;

/**
 * Created by Assar on 2015-04-23.
 */
public class Skyddsvart extends Geometry {
    public Skyddsvart(String name,String type,JSONObject featureRow) {


        super();

        System.out.println("Skyddsv�rt tr�d �r anropad");

        Object associatedName = name;
        Object associatedType = type;


        JSONObject associatedGeometry = (JSONObject) featureRow.get("geometry");

        JSONObject associatedData = (JSONObject) featureRow.get("properties");	// Detta plockar ut properties-objektet

        System.out.println("Keys och values i featureRow" + associatedData.entrySet());

        //--Ladda variablerna till superklassen
        setName(name);
        setType(type);


        //--------------------ta ut de fr�n alla objekttypernas gemensamma variabler ur featureRow-objektet och lagra dessa i form av typ Geometry-objekt.

        setFixedGID(associatedData.get("FixedGID").toString()); // Plockar ut UUID ur json-filen och lagrar tillg�ngligt som "Geometry". Observera att detta saknas i blockdata.
        setEmployee(associatedData.get("USER_").toString());
        setObjID(associatedData.get("OBJECTID").toString());

        //setRutaID(associatedData.get("rutaID").toString());

// Todo: G�r en try/catch som hanterar om en variabel helt saknas i datat s� att programmet kan g� vidare.


        //setCRS();


        //System.out.println("UID i java-objektet:"+getUID());
        //System.out.println("CRS i java-objektet:"+getCrs());
        //System.out.println("Type i java-objektet:"+getType());
        //String ObjectID = associatedData.get("OBJECTID").toString();
        //String GlobalID = associatedData.get("GlobalID").toString();




    }



}

