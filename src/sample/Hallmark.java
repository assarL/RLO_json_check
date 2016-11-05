package sample;

import org.json.simple.JSONObject;

import java.util.Set;

/**
 * Created by Assar on 2015-12-27.
 */
public class Hallmark extends Provyta implements ErrorMessage {

    public static boolean provytaFinns;
    public int antalArter;

    public Hallmark(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {
        super(name, type, associatedGeometry, associatedProperties);

        provytaFinns = true; // Aktiverar knapp i huvudfönstret

        super.setInvKod((String) associatedProperties.get("TillganglighetHMProv"));

        createArtProvYtor(associatedProperties,1);

        // Överlagra art-listan Todo: gör funktion av artlistan i provytor och överlagra vid behov i respektive underklass.
/*/-------

            System.out.println("[Provyta] associatedProperties.get('sub') är =" + sub.size());
            Set keySet = associatedProperties.keySet();
            SmaProvYtor = new ArrayList<ArtProvYta>();
            //System.out.println("[Hallmark] Artlista för Hallmark läses in..");
            ArtProvYta CurrentSPY= new ArtProvYta();
            String SpyID;

            for(Object key : keySet){

                String keyString = key.toString();
                //System.out.println("[Provyta] Nuvarande keyString:"+keyString);
                System.out.println("[Provyta] keyString starts with SmaProvArt: "+keyString.startsWith("SmaProvArt:"));
                if (keyString.startsWith("SmaProvArt:")){
                    System.out.println("[Provyta] keystring = SmaProvArt, inne i loppen med :"+keyString);
                    int firstIndex = keyString.indexOf(":"); // Position av första kolonet i det sammansatta variabelnamnet
                    int lastIndex = keyString.indexOf(":", firstIndex + 1); // Position av det sista kolonet
                    String art = keyString.substring(firstIndex + 1, lastIndex);

System.out.println("[Provyta] Småprovytavariabel: "+keyString.substring(lastIndex+1));

                    switch (keyString.substring(lastIndex+1)) {

                        case "FinnsiSPY1":
                            SpyID = "1";
                            break;
                        case "FinnsiSPY2":
                            SpyID = "2";
                            break;
                        case "FinnsiSPY3":
                            SpyID = "3";
                            break;
                        case "FinnsiSPY4":
                            SpyID = "4";
                            break;
                        case "FinnsiSPY5":
                            SpyID = "5";
                            break;
                        case "FinnsiSPY_y":
                            SpyID = "y";
                            break;

                        default:
                            SpyID = "999"; // Om inget id mot förmodan hittades.


                    }

                    CurrentSPY.setSpyID(SpyID);
                    CurrentSPY.addArt(new ArtReg(art)); // Konstruktor för okänd tid används.
                    System.out.println("[Provyta] ny art "+art+" lagd till småprovyta med id:" + CurrentSPY.getSpyID());
                }
                SmaProvYtor.add(CurrentSPY); // Och småprovytan läggs till arrayen av småprovytor
            }


*/
        System.out.println("[Hällmark] Tillgänglighet är satt till: "+ProvyteEtablering.getTillganglighet());

    }

@Override
    public void createArtProvYtor(JSONObject associatedProperties, int antalArtProv){  // Funktionen ersätter den normala för artutplockning eftersom artlistan har ett annat gruppnamn i variabellistan.

        Set<String> keys = associatedProperties.keySet(); // Hämta provytans keyset

        ArtLista HallMarksArter = new ArtLista();
        Long millisec = -27080315329000L; //Initialiseras till 11/11 1111;

        for (String oneKey : keys) {

            if (oneKey.startsWith("HallMark:")) { //Separera ut arternas nycklar som innehåller artinfo

                String art = spliceOutSpecies(oneKey); // Skicka variabeln till utklippning
                System.out.println("[Hallmark] Utklippt art:"+art);

                String ts = associatedProperties.get("ts_" + oneKey).toString();
                millisec = Long.parseLong(ts); // Omvandla string till Long.

                ArtReg nyArt = new ArtReg(art, millisec);
                HallMarksArter.addArt(nyArt);
            }

        }
        antalArter = HallMarksArter.getArtLista().size();

    }

    @Override
    public Integer getSmaProvArtCounter(){

        return antalArter;
    }
}
