package sample;

import org.json.simple.JSONObject;

/**
 * Created by Assar on 2015-07-24.
 */
public class ImportData {

   public ImportData(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {

      //super(associatedGeometry, associatedProperties);

       String gisTyp = (String) associatedProperties.get("gistyp"); // Plocka ut vilken gis-typ det �r och skapa objekt efter det.

       if (gisTyp == null) {
           System.out.println("Gis-typ saknas, objekt kan inte skapas");
       }

        if (gisTyp=="lansgras"){

        }




      System.out.println("ImportData �r anropad");



       System.out.println("Properties (JSONObject) inneh�ller:" + associatedProperties.toString());

      }
   }
