package sample;

/**
 * Created by Assar on 2015-04-14.
 */
import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoJsonDecoder implements Comparable,ErrorMessage{


    protected Geometry[] geometries;  //Array att l�gga alla objekt i.
    protected HashMap hashedGeometries;
    protected Object UIDkey;
    protected ArrayList<Object> lansgrasNycklar = new ArrayList<>(200);  // Plockar ut nycklar för respektive typ för separat uppvisning senare.
    private ArrayList<Object> akerkantsNycklar = new ArrayList<>(200);
    private ArrayList<Object> kraftledningsNycklar = new ArrayList<>(200);
    private ArrayList<Object> patrullstigsNycklar = new ArrayList<>(200);
    private ArrayList<Object> hallmarksNycklar = new ArrayList<>(200);
    private ArrayList<Object> angsobetesNycklar= new ArrayList<>(200);
    private ArrayList<Object> angsobetekomplNycklar= new ArrayList<>(200);
    private String name;
    private String type;
    static String errorMsg;


    public GeoJsonDecoder(File file)   {

        FileReader reader;
        try
        {
            reader = new FileReader(file);        // JSON-filen som ska l�sas in

            JSONParser jsonParser = new JSONParser();

                JSONObject jsonObject= (JSONObject) jsonParser.parse(reader);

                JSONArray features = (JSONArray)jsonObject.get("features");
                name = jsonObject.get("name").toString();
                type = jsonObject.get("type").toString();


            //JSONObject crs = (JSONObject)jsonObject.get("crs");



            System.out.println("name:"+name+"\ntype:"+type+"\n");


            geometries = new Geometry[features.size()]; // En array skapas och tilldelas l�ngd utifr�n antalet enskilda poster i filen.
            hashedGeometries = new HashMap<Geometry,Object>();   // En hashmap skapas som lagrar objekten under sina egna unika id:n

            Iterator<?> featuresIterator = features.iterator(); // En iterator skapas ur arrrayen "features"[2]som plockar ut features ur Geo-json filen.

            System.out.println("Geometry arrayen har i det h�r l�get l�ngden"+ geometries.length);


            int i = 0;



            while (featuresIterator.hasNext()){
                System.out.println("---> Nytt objekt påbörjat Loopen har nu g�tt "+i+" varv inne i jsonfilen\n");

                JSONObject featureRow = (JSONObject)featuresIterator.next();  // H�r plockas en "rad" ut ur json-filen vilket motsvarar ett registrerat geometriskt objekt inneh�llande objekten [type,geometry,properties] denna rad skickas ut till respektive klass f�r vidare datautplock.

                    JSONObject associatedGeometry = (JSONObject) featureRow.get("geometry");  // H�r finns punkt, polygon, etc..
                    JSONObject associatedProperties = (JSONObject) featureRow.get("properties");  // H�r finns alla insamlade data f�r respektive objekt.

                        System.out.println("{GeoJsonDecoder]Antal variabler i Properties för ett properties-objekt:"+associatedProperties.size()+"st");

                      UIDkey = associatedProperties.get("FIXEDGID");  // Plockar ut UID ur json-str�ngen.
                System.out.println("FIXEDGID HAR VÄRDET:"+UIDkey);
                       if (UIDkey==null){UIDkey = (i);errorMsg+="Variabeln FixedGID finns inte, redigera .json-filen!";System.out.println("[GeoJsonDecoder]  UID är null och har nu tilldelats värdet: "+UIDkey);}  // Om unikt id saknas tilldelas värdet med räknaren för "i".



                     try {
                         String gisTyp=(String)associatedProperties.get("GISTYP"); // Gistypen har det gamla namnet
                         if (gisTyp=="") gisTyp=(String)associatedProperties.get("Gistyp"); // GISTYP


                         // Plocka ut vilken gis-typ det �r och skapa objekt efter det. Objekten lagras i en vanlig matris och i en HashMap med UID som nyckel.
                         // Genom att j�mf�ra arrayernas l�ngd och inneh�ll kan man avg�ra om det saknas UID eller om samma UID anv�nts till flera objekt.
                         //Todo: Felkontroller av Unikt id.
                         // Kanske finns b�ttre s�tt att g�ra dessa felkontroller och d� kan objekten lagras direkt i kartan.

                         if (gisTyp == null) {
                             geometries[i] = new Geometry(associatedGeometry, associatedProperties); System.out.println("{GeoJsonDecoder]gistyp = saknas, ett tomt Geometry-objekt skapas");// Om variabeln gistyp saknas i filen.
                             hashedGeometries.put(UIDkey, geometries[i]);
                         } else {
                             System.out.println("[GeoJsonDecoder]AssociatedProperties.get(gistyp) returnerar v�rdet:" + gisTyp);

                             switch (gisTyp.toLowerCase()) {

                                 case "lansgras":
                                     //System.out.println("[GeoJsonDecoder] geometri[i] har f�re konstruktoranrop v�rdet:"+ geometries[i]);
                                     System.out.println("[GeoJsonDecoder] skickar data till Lansgras, strax tillbaka om det är OK.");
                                     geometries[i] = new Lansgras(name, type, associatedGeometry, associatedProperties);
                                     System.out.println("[GeoJsonDecoder] Tillbaka i dekodern efter lansgras-anrop, allt gick bra.");
                                     hashedGeometries.put(UIDkey,geometries[i]); // Lagra samma objekt under en nyckel best�ende av den unika koden. Koden h�mtas ur variabeln FixedID.
                                     lansgrasNycklar.add(UIDkey); // Plocka ut nycklar för lansgras
                                     System.out.println("[GeoJsonDecoder]gistypen = --------------lansgras............, lansgras anropas med:" + name + type + associatedGeometry + associatedProperties);

                                        break;

                                 case "akerkant":
                                     geometries[i] = new Akerkant(name, type,associatedGeometry, associatedProperties);

                                     hashedGeometries.put(UIDkey,geometries[i]);
                                     akerkantsNycklar.add(UIDkey);  // Plocka ut nycklar för åkerkanter

                                     System.out.println("[GeoJsonDecoder]gistypen = --------------akerkant............, akerkant anropas med:" + name + type + associatedGeometry + associatedProperties);

                                     break;

                                 case "kraftledning":
                                     geometries[i] = new Kraftledning(name, type,associatedGeometry, associatedProperties);

                                     hashedGeometries.put(UIDkey,geometries[i]);
                                     kraftledningsNycklar.add(UIDkey);  // Plocka ut nycklar för kraftledningar

                                     System.out.println("[GeoJsonDecoder]gistypen = --------------kraftledning............, kraftledning anropas med:" + name + type + associatedGeometry + associatedProperties);

                                     break;

                                 case "patrullstig":
                                     geometries[i] = new Patrullstig(name, type,associatedGeometry, associatedProperties);

                                     hashedGeometries.put(UIDkey,geometries[i]);
                                     patrullstigsNycklar.add(UIDkey);  // Plocka ut nycklar för patrullstigar

                                     System.out.println("[GeoJsonDecoder]gistypen = --------------patrullstig............, patrullstig anropas med:" + name + type + associatedGeometry + associatedProperties);

                                     break;

                                 case "hallmarkstorr":
                                     geometries[i] = new Hallmark(name,type,associatedGeometry,associatedProperties);

                                     hashedGeometries.put(UIDkey,geometries[i]);
                                     hallmarksNycklar.add(UIDkey);// Plocka ut nycklar för hällmarkstorrängar (provytorna ej polygonen)

                                     System.out.println("[GeoJsonDecoder]gistypen = --------------Hallmarkstorr............, Hallmark anropas med:" + name + type + associatedGeometry + associatedProperties);

                                      break;

                                 case "angsobete":

                                     geometries[i] = new Angsobete(name,type,associatedGeometry,associatedProperties);

                                     System.out.println("[GeoJsonDecoder]gistypen = -----------------Angsobete............, Angsobete anropas med:" + name +":"+ type + associatedGeometry + associatedProperties);

                                     hashedGeometries.put(UIDkey,geometries[i]);
                                     angsobetesNycklar.add(UIDkey);// Plocka ut nycklar för angsobete

                                      break;

                                 case "angsobetekompl":

                                     geometries[i] = new Angsobetekompl(name,type,associatedGeometry,associatedProperties);
                                     hashedGeometries.put(UIDkey,geometries[i]);
                                     angsobetekomplNycklar.add(UIDkey);// Plocka ut nycklar för angsobete

                                     break; // Glöm inte break om du adderar fler poster, ger väldigt besvärliga felmeddelanden..

                                 default:
                                     geometries[i] = new Geometry(associatedGeometry, associatedProperties);  //--------------default, �vrig typ ej uppf�ngad............, // Inget av ovanst�ende passar och ett tomt objekt skapas ist�llet.
                                     hashedGeometries.put(UIDkey, geometries[i]);

                             }

                         }

                     }

                     catch (Exception e){
                         e.printStackTrace();
                         System.out.println("Exception fångad");

                     }
                    i++;

            }

        }
        catch (ParseException e) {
            errorMsg += "[GeoJsonDecoder]Parseexception uppt�cktes i geometries. Detta kan bero p� att vissa v�rden saknar citationstecken.";
            System.out.println("[GeoJsonDecoder]Parseexception uppt�cktes i geometries. Detta kan bero p� att vissa v�rden saknar citationstecken.");
            e.printStackTrace();
        }

            catch (IOException e) {
                // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
@Override
    public int hashCode(){  // Skapar en hash-kod som g�r att reproducera
        return 7*UIDkey.hashCode()*31;
    } // Genererar unik haschcode på ett förutsägbart sätt för att kunna identifiera objekt mha namnet.

    public boolean equals(Object UIDkey){           // Jämför hashkoder..

        return compareTo(UIDkey) == 0;
    }

    public Geometry[] getGeometries(){

        return geometries;
    }

    public ArrayList<Object> getLansgrasNycklar(){return lansgrasNycklar;}
    public ArrayList<Object> getAkerkantsNycklar(){return akerkantsNycklar;}
    public ArrayList<Object> getKraftledningsNycklar() {return kraftledningsNycklar;}
    public ArrayList<Object> getPatrullstigsNycklar() {return patrullstigsNycklar;}
    public ArrayList<Object> getHallmarksNycklar(){ return hallmarksNycklar;}
    public ArrayList<Object> getAngsobetesNycklar(){ return angsobetesNycklar;}
    public ArrayList<Object> getAngsobetekomplNycklar(){ return angsobetekomplNycklar;}

    public HashMap getHashedGeometries(){

        return hashedGeometries;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


    @Override
    public String getErrorMessage() {
        return errorMsg;
    }

    public void setErrorMessage(String newMessage) {

    }
}














