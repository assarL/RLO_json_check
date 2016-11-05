package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Assar on 2015-06-07.
 */
public class Provyta extends Geometry implements ErrorMessage {

    private TradSkikt TrSkikt;
    private TradBuskArt TraBusk;
    private FaltSkikt FaltSkikt; // Todo: flytta all initialisering till konstruktor?
    //private StorArtSkikt StSkikt= new StorArtSkikt();
    private BottenSkikt BottenSkikt;
    private KultSkikt KultSkikt; // Variabler för kulturpåverkan av ytan.
    protected Etablering ProvyteEtablering; // Variabler för etablering av ytan
    private ArtProvYta artProvYta;
    private ArtProvYta[] ArtPry;
    private Boolean Flyttad;
    private String CentrumFlytt;
    private String orsakText;
    private Integer uniqueSpecies = 0;
    //private Set<String> UnikaArterLista = new HashSet<String>();
    private boolean hasTrees = false;  // initieras till false.
    private static String errorMsg;
    private String MarkUtFalt;
    private String MarkUtFlyg;
    private String fotoURL;
    private Set ArtHash;


    //TODO: Klassen b�r g�ras oberoende av k�llan till ing�ende f�lt. I nul�get h�mtas allt fr�n json-filen men det b�r ocks� kunna s�ttas fr�n exempelvis en databas. Olika konstruktorer beroende p� datak�lla �r kanske b�sta l�sningen.

    // TODO: Antal spy bör sättas från variabeln ANTALSPY och loopas igenom. Olika provytor har olika antal småprovytor.


    public Provyta(String name, String type, JSONObject associatedGeometry, JSONObject associatedProperties) {    // Todo: All avkodning b�r ske i GeoJsonDecoder och sedan b�r typen vara Object eller annan typ som �r mer "generisk". Detta f�r att underl�tta andra k�llor �n Json-filer.

        super(associatedGeometry, associatedProperties); // Dessa �r universella och s�tts i superklassen geometry. featurerow inneh�ller alla properties f�r geojson-filen.

        //System.out.println("[Provyta] anropad med;" + associatedProperties.toString());

        try {
            setName(name);
            setType(type);


            // Initialisering av delegerade objekt, dessa skapas för alla provytor.
            FaltSkikt = new FaltSkikt();
            BottenSkikt = new BottenSkikt();
            KultSkikt = new KultSkikt();
            ProvyteEtablering = new Etablering();
            artProvYta = new ArtProvYta();  // Initialiseras i väntan på stora provytans artlista

            //createArtProvYtor(associatedProperties); // Funktion som skapar artregistreringarna provytevis och räknar unika arter. Är ibland överlagrad av härledda objekt (exempelvis Hallmark) där artlistan skiljer sig från det normala.

            //---- Orsak till ej inventerad.

            orsakText = (String) associatedProperties.get("Orsak");
            //----
            MarkUtFalt = (String) associatedProperties.get("MarkUtFalt");
            System.out.println("[Provyta]MarkUtFalt i geojson-filen har värdet: " + MarkUtFalt);
            if (MarkUtFalt == null) {
                ProvyteEtablering.setMarkUtFalt("Ej satt");
            } else {
                ProvyteEtablering.setMarkUtFalt(MarkUtFalt);
            }
            MarkUtFlyg = String.valueOf(associatedProperties.get("MARKUTFLYG"));

            System.out.println("[Provyta] MarkUtFlyg har värdet: " + MarkUtFlyg);

            if (MarkUtFlyg == "null") {
                ProvyteEtablering.setMarkUtFlyg("Ej satt");
            } else {
                ProvyteEtablering.setMarkUtFlyg(MarkUtFlyg);
            }
            System.out.println("[Provyta] MarkUtFlyg satt till: " + this.getMarkUtFlyg());

            fotoURL = (String) associatedProperties.get("Foto");
            if (fotoURL == null) {
                ProvyteEtablering.setFotoURL("saknas");
            } else {
                ProvyteEtablering.setFotoURL(fotoURL);
            }
            System.out.println("\nFotoURL:" + this.getFoto());

            CentrumFlytt = (String) associatedProperties.get("Centrumflytt");

            if (CentrumFlytt != null) {
                Flyttad = (CentrumFlytt.equals("1")) ? true : false;
            } else Flyttad = false;

            ProvyteEtablering.setCentrumflytt(Flyttad);
            System.out.println("[Provyta] Centrumflytt har värdet: " + Flyttad);

        } catch (NullPointerException e) {
            errorMsg += "Nullpointer fångat i konstruktorn för provyta.\n";
            e.printStackTrace();
        }
        //----Variabler för flytt av provyta
        Integer FlyttX = 0;
        Integer FlyttY = 0;

        if (Flyttad) {  // Om ytan är flyttad så sätt variablerna för x och y.

            String FlyttNordSyd = (String) associatedProperties.get("FlyttNordSyd");
            String FlyttOstVast = (String) associatedProperties.get("FlyttOstVast");
            try {
                Integer flyttAvstandY = Integer.valueOf(associatedProperties.get("FlyttOstVast").toString());
                FlyttY = (FlyttNordSyd.equals("Mot söder")) ? -flyttAvstandY : +flyttAvstandY;
                Integer flyttAvstX = Integer.valueOf(associatedProperties.get("FlyttAvstOstVast").toString());
                FlyttX = (FlyttOstVast.equals("Mot väster")) ? +flyttAvstX : -flyttAvstX;
                System.out.println("[Provyta]Flyttavstand:" + flyttAvstandY);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorMsg += "[Provyta]Undantag fångat vid konvertering till Integer.";
                errorMsg += "\n Troligen ett icke numeriskt värde lagrat i någon av variablerna för centrumflytt i objektet med FixedGID=" + fixedGID;
                errorMsg += "FlyttAvstandY:" + associatedProperties.get("FlyttOstVast").toString() + "\nFlyttAvstandX:" + associatedProperties.get("FlyttAvstOstVast").toString();

            }

        }
        ProvyteEtablering.setFlyttY(FlyttY);
        ProvyteEtablering.setFlyttX(FlyttX);


        // Plocka ut samtliga nyckelv�rden ur json-properties objektet eftersom all artinformation ligger i variabelnamnet och inte som värden.


        Set keySet = associatedProperties.keySet();

        Iterator<Object> keyIterator = keySet.iterator();  // Skapa en iterator f�r att loopa igenom alla (kan vara stor skillnad i antal)

        System.out.println("\n\n keySet:" + keySet.toString());


        TraBusk = new TradBuskArt();
        while (keyIterator.hasNext())

        {     // Nycklarna l�ses en efter en.
//<...........Sm�provytor................................................................................................................................>
            // Kolla vilka nycklar som inneh�ller artinformation.
            String nextKey = keyIterator.next().toString();


//.......................................................................................................................................


// H�vdstatus och betesh�jd kan beskrivas grafisk med bild av betesdjur och h�jd p� gr�s. Egna objekt, new Betesdjur(), new Grasskikt(); new Bottenskikt("GramForna","BarrForna","LovForna"); new Faltskikt(�rter, ris, ormbunkar);


            if (nextKey.startsWith("TradBuskArt:")) {                 // Tre variabler �r taggade med [TradBuskArt:](T�ckning, h�jd och antal) Observera att kolonet i s�kstr�ngen �r viktigt f�r att skilja bort statusvariablerna i jsonfilen.

                try {
                    int firstIndex = nextKey.indexOf(":"); // Positon av första kolonet i det sammansatta variabelnamnet
                    int lastIndex = nextKey.indexOf(":", firstIndex + 1); // Position av det sista kolonet
                    //--------------------------------------------------
                    String art = nextKey.substring(firstIndex + 1, lastIndex);  // Artnamnet plockas ut h�r.
                    String variabelNamn = nextKey.substring(lastIndex + 1);
                    String GroupNamn = nextKey.substring(0, firstIndex);  // Plockar ut gruppnamnet.

                    System.out.println("[Provyta] Gruppnamn:" + GroupNamn);
                    String value = (String) associatedProperties.get(nextKey);

                    //System.out.println("variabelnamn:"+variabelNamn+"\nArt:"+art+"\nvalue:"+value);

                    if (variabelNamn.equals("ArtTackning")) { // Plockar en av de tre variablerna f�r varje tr�d. Todo:Detta kan kanske g�ras smartare �n att testa alla variabler

                        String speciesKey = "TradBuskArt:" + art + ":";
                        System.out.println("[Provyta] Hopsatt speciesKey:" + speciesKey);

                        TraBusk.setArt(art);
                        TraBusk.setTackning((String) associatedProperties.get(speciesKey + "ArtTackning")); // �verf�r v�rdet fr�n json-objektet till javaobjektet
                        TraBusk.setMedelHojd((String) associatedProperties.get(speciesKey + "ArtMedelHojd"));
                        TraBusk.setAntal((String) associatedProperties.get(speciesKey + "ArtAntal"));
                        String millisec = (String) associatedProperties.get("ts_" + speciesKey + "ArtAntal"); // Plocka en av tidsstämplarna.

                        //                      Long ts = Long.parseLong(millisec); // Gör om den till Long
                        //                      TraBusk.setTimeStamp(ts); // Skicka som Long  Todo: skapa funktion för tidsstämpel för trädbusk-objekt.

                        TraBusk.addToArtLista(art);
                        System.out.println("variabelnamn:" + variabelNamn + "\nArt:" + art + "\nvalue:" + value);


                    }
                } catch (Exception e) {
                    errorMsg += "Undantag vid inläsning av trädart. Ofta följd av att en art suddats utan att tillhörande data suddats. Dessa 'saknar då sin art'.";
                    e.printStackTrace();
                }


            }// Varje art har tre egenskaper, medelh�jd, t�ckning, antal. Skapa tomt objekt och s�tt en egenskap i taget.


        }  // ********************************Loopen f�r keys slutar h�r.**************************

        TrSkikt = new TradSkikt();
        TrSkikt.setTradArtsLista(TraBusk.getArtLista());
        List SmaProvYtor = new ArrayList<ArtProvYta>();

        System.out.println("[Provyta] TraBusk.getArtLista(): " + TraBusk.getArtLista().

                toString());
        String Trad1m = (String) associatedProperties.get("TotTradBu1m"); // H�mta tr�dt�ckningsvariablernas v�rden
        String Trad3m = (String) associatedProperties.get("TotTradBu3m");
        String Trad7m = (String) associatedProperties.get("TotTradBu7m");
        String TradVux = (String) associatedProperties.get("TotTradBuVuxna");
        String TradBlom = (String) associatedProperties.get("BlomRikTradBusk");
        TrSkikt.setAllaTradSkikt(new TradTacke(Trad1m, Trad3m, Trad7m, TradVux));  // Skickas till klassen TradSkikt
        TrSkikt.setBlommor(TradBlom);

        //---------**Kulturvariabler s�tts----------------    String HavdTyp;String HavdStatus;String MarkStorning;String DeponiTyp;String RojningsTid;----
        KultSkikt.setHavdTyp((String) associatedProperties.get("Havdtyp"));
        KultSkikt.setMarkStorning((String) associatedProperties.get("MarkStorning"));
        KultSkikt.setHavdStatus((String) associatedProperties.get("HavdStatus"));
        KultSkikt.setDeponiTyp((String) associatedProperties.get("DeponiTyp"));
        KultSkikt.setRojningsTid((String) associatedProperties.get("RojningsTid"));

        // Fältskiktsvariabler plockas ut och sätts

        FaltSkikt.setVegHojdKort((String) associatedProperties.get("VegHojdKort"));
        FaltSkikt.setVegHojdMedel((String) associatedProperties.get("VegHojdMedel"));
        FaltSkikt.setVegHojdHog((String) associatedProperties.get("VegHojdHog"));
        FaltSkikt.setVegSaknas((String) associatedProperties.get("VegSaknas"));
        FaltSkikt.setSolexpFaltsk((String) associatedProperties.get("SolexpFaltsk"));
        FaltSkikt.setBlomRikTistelv((String) associatedProperties.get("BlomRikTistelv"));
        FaltSkikt.setBlomRikArtv((String) associatedProperties.get("BlomRikArtv"));
        FaltSkikt.setBlomRikOvr((String) associatedProperties.get("BlomRikOvr"));
        FaltSkikt.setGraminiderTackning((String) associatedProperties.get("GraminiderTackning"));
        FaltSkikt.setOrterTackning((String) associatedProperties.get("OrterTackning"));
        FaltSkikt.setRisTackning((String) associatedProperties.get("RisTackning"));
        FaltSkikt.setOrmbunkarTackning((String) associatedProperties.get("OrmbunkarTackning"));

        // Bottenskiktsvariabler plockas ut och sätts

        BottenSkikt.setBlockighet((String) associatedProperties.get("Blockighet"));
        BottenSkikt.setBlottadStenTackning((String) associatedProperties.get("BlottadStenTackning"));
        BottenSkikt.setMarkStorningTackning((String) associatedProperties.get("MarkStorningTackning"));
        BottenSkikt.setDeponiTackning((String) associatedProperties.get("DeponiTackning"));
        BottenSkikt.setGramForna((String) associatedProperties.get("GramForna"));
        BottenSkikt.setLovForna((String) associatedProperties.get("LovForna"));
        BottenSkikt.setBarrForna((String) associatedProperties.get("BarrForna"));
        BottenSkikt.setOvrMossTackning((String) associatedProperties.get("OvrMossTackning"));
        BottenSkikt.setVitmossorTackning((String) associatedProperties.get("VitmossorTackning"));
        BottenSkikt.setBrunmossorTackning((String) associatedProperties.get("BrunmossorTackning"));
        BottenSkikt.setBusklavarTackning((String) associatedProperties.get("BusklavarTackning"));
        BottenSkikt.setMarkLavTackning((String) associatedProperties.get("MarkLavTackning"));
        BottenSkikt.setBladlavTackning((String) associatedProperties.get("BladlavTackning"));


        setErrorMessage(errorMsg);           // Undantag och ev. andra meddelanden för presentation i sidomenyn.

    }  // Hela detta block undantas för styrfiler


    //---------Slut-Konstruktor----------------


    public void createArtProvYtor(JSONObject associatedProperties, int antalArtPry) {

        System.out.println("[Provyta] createArtProvYtor anropad");
        JSONArray sub = (JSONArray) associatedProperties.get("sub");  // Plockar ut arrayen av subprovytor (spy)

        //Set<String> HashSet = new HashSet<>(); // Håller reda på antalet unika arter.
        ArtHash = new HashSet<>();
        //subProvytor = new HashMap<>(); // En lista över samtliga småprovytor

        if (sub != null) {

            ArtPry = new ArtProvYta[antalArtPry+1]; // Skapa småprovytor, antal skickas som argument till funktionen, hela provytan läggst ill som index 0

            for (int i = 0; i < ArtPry.length; i++) {// Fem artprovytor skapas med nuffra som id
                ArtPry[i] = new ArtProvYta("ArtPry" + Integer.toString(+i + 1));
            }

            System.out.println("[Provyta] Antalet artprovytor i denna post:" + sub.size());
            for (Object spyn : sub) {  // Loop för varje artprovyta ur JSONArrayen "sub"

                HashMap spy = (HashMap) spyn; // Gör om objektet till hashmap

                String ArtPryID = (String)spy.get("SPY"); // Spy-ID hämtas, kan vara 1,2,3,4,5,y (upp till nio vid Ä&B)
                if (ArtPryID.equals("y"))ArtPryID="0"; // Ytterligare ändras till en total artlista för hela ytan kallad "0"
                int ArtPryIndex = Integer.parseInt(ArtPryID);

                Set<String> Subkeys = spy.keySet();  // Plocka ut varje sub-grupp, inkluderar även ID.


                for (String oneKey : Subkeys) {  // Ett varv för varje nyckel i sub/spyX.

                    if (oneKey.startsWith("SmaProvArt:")) {
                        String art = spliceOutSpecies(oneKey); // Skicka variabeln till utklippning
                        Long second = Long.parseLong(spy.get("ts_" + oneKey).toString());
                        Long millisec = second * 1000;
                        System.out.println("[Provyta] Utklippt art:" + art + "  Tid för registrering:" + millisec);

                        ArtReg nyArt = new ArtReg(art, millisec);
                        ArtPry[ArtPryIndex].addArt(nyArt);
                        System.out.println("[Provyta] Artpry["+ArtPryIndex+"].addArt("+nyArt.getArt()+")");
                        ArtHash.add(nyArt.getArt()); // Arter som hör till hela provytan. Lägg till arten till hashmap för att utplåna ev. dubletter.
                        System.out.println("[Provyta] Antalet unika arter för provytan är nu:" + ArtHash.size());
                    }  // Lägg till arten till arrayen av artprovytor

                    //subProvytor.put(ArtPryID,ArtPry);// Todo; provyta noll är inte alltid med men ska sättas = alla arter.


                }

            }
            // Lägg referensen till artprovytan i en array


        }


        ArrayList ArtArray = new ArrayList();


        setSmaProvArtCounter(ArtHash.size());  // Antalet unika arter

        setProvYteArtLista("totalartlista", ArtArray);   // Listan över unika arter för hela provytan lagras som egen artprovyta

        System.out.println("[Provyta] Lista över unika arter:" +

                getProvYteArtLista());

    }

    public ArrayList<String> getSummeradeSmaProvArter() { // Hämtar artlistorna från samtliga småprovytor

        System.out.println("funktionen ej i funktion ;) ");// Todo: tänk på att inga referenser till spy-variabler kan göras här.")

        return null;  // returnera listan utan dubletter.
    }

    public String spliceOutSpecies(String nyckel) { // Funktionen returnerar mittsträngen i artvariabelns nyckel.

        int firstIndex = nyckel.indexOf(":"); // Position av första kolonet i det sammansatta variabelnamnet
        int lastIndex = nyckel.indexOf(":", firstIndex + 1); // Position av det sista kolonet
        String art = nyckel.substring(firstIndex + 1, lastIndex); // Arten plockas ut ur namnet

        return art;
    }




    public ArrayList<String>getSummeradeTradBuskArter(){

        /*
        ArrayList allTreeSpecies = new ArrayList<String>();
        System.out.println("Antal tradarter som skickats till TradBuskArt: "+TraBusk.getAntal());
        allTreeSpecies.addAll(TraBusk.getArtLista());

        System.out.println("\nSammansatt Tradartlista:"+allTreeSpecies);  // combined �r samtliga sm�provytors artlistor sammansatta inklusive dubletter.

        //ArrayList<String> dedupped = new ArrayList<String>(new LinkedHashSet<String>(allTreeSpecies));
        System.out.println("Antal tradarter som skickades till TradBuskArt: "+TraBusk.getAntal());
        //System.out.println("getSummeradeTradBuskArter.size : "+dedupped.toString());
        //return dedupped;  // Returnera listan utan dubbletter.*/
        System.out.println("Antal tradarter som skickades till TradBuskArt: "+TraBusk.getArtLista().size());
        System.out.println("\nSammansatt Tradartlista:"+TraBusk.getArtLista());

    return TraBusk.getArtLista();
    }



    public boolean getHasTrees(){
        if(getSummeradeSmaProvArter().size()>0){hasTrees=true;}
        return hasTrees;
    }
    public TradSkikt getTradskikt(){

        return TrSkikt;  //Todo:St�mmer inte med vad klassen inneh�ller. Kolla TradTacke.java
    }


    public void setSmaProvArtCounter(int uniqueSpecies){this.uniqueSpecies = uniqueSpecies;}
    public Integer getSmaProvArtCounter(){return uniqueSpecies;}
//------------------------------
    public ArrayList<ArtReg> getProvYteArtLista(){  // Hämtar hela ytans artlista, unika arter.
        return artProvYta.getArtLista();  // Delegeras till Artprovyta
    }
    public void setProvYteArtLista(String id,ArrayList Arter){
        artProvYta.setArtLista(id,Arter);
    }
//------------------------------
    public ArtProvYta[] getArtPry(){ // Returnerar samtliga sub-provytor som har registrerats. (Kan finnas "hål" i nummerserien)
        return ArtPry;
    }
    public Integer getTradartCounter(){int antal = 99; return antal;}               //getSummeradeTradBuskArter().size();return antal;

    public String getInvKod(){return ProvyteEtablering.getTillganglighet();}
    public void setInvKod(String invKod){ProvyteEtablering.setTillganglighet(invKod);}
    public String getMarkUtFalt() {return ProvyteEtablering.getMarkUtFalt();}
    public void setMarkUtFalt(String markUtFalt) {

        ProvyteEtablering.setMarkUtFalt(markUtFalt);
    }
    public String getMarkUtFlyg(){
        return ProvyteEtablering.getMarkUtFlyg();
    }
    public void setMarkUtFlyg(String markslagUndertypFlyg) {
        ProvyteEtablering.setMarkUtFlyg(markslagUndertypFlyg);
    }
    public void setFoto(String newFoto){fotoURL = newFoto;}
    public String getFoto(){return fotoURL;}
    public Boolean getFlyttad() {return ProvyteEtablering.getCentrumflytt();
    }
    public void setFlyttad(Boolean flyttad){ProvyteEtablering.setCentrumflytt(flyttad);}
    public void setFlyttX(Integer newX){ProvyteEtablering.setFlyttX(newX);}
    public Integer getFlyttX(){return ProvyteEtablering.getFlyttX();
    }
    public void setFlyttY(Integer newY){ProvyteEtablering.setFlyttY(newY);
    }
    public Integer getFlyttY(){

        return ProvyteEtablering.getFlyttY();
    }
    public void setOrsakText(String nyOrsakText){

        ProvyteEtablering.setOrsakText(nyOrsakText);
    }
    public String getOrsakText(){
        return ProvyteEtablering.getOrsakText();
    }
    public void setHavdTyp(String nyHavdTyp){

        KultSkikt.setHavdTyp(nyHavdTyp);
    }
    public String getHavdTyp(){
        return KultSkikt.getHavdTyp();
    }
    public void setMarkStorning(String nyMarkStorning){
        KultSkikt.setMarkStorning(nyMarkStorning);
    }
    public String getMarkStorning(){

        return KultSkikt.getMarkStorning();
    }
    public void setHavdStatus(String nyHavdStatus){

        KultSkikt.setHavdStatus(nyHavdStatus);
    }
    public String getHavdStatus(){

        return KultSkikt.getHavdStatus();
    }
    public void setDeponiTyp(String nyDeponiTyp){

        KultSkikt.setDeponiTyp(nyDeponiTyp);
    }
    public String getDeponiTyp(){

        return KultSkikt.getDeponiTyp();
    }
    public void setRojningsTid(String nyRojningsTid){
        KultSkikt.setRojningsTid(nyRojningsTid);
    }
    public String getRojningsTid(){
        return KultSkikt.getRojningsTid();
    }

    public void addTradArt(String nyArt){TraBusk.setArt(nyArt);}
    public ArrayList getTradArtLista(){
        return TraBusk.getArtLista();
    }
    public String getTradSkiktsSumma() {
        return TrSkikt.getTradSkiktsBeskrivning().getTotTradBuVuxna();
    }

    public String getVegHojdKort(){return FaltSkikt.getVegHojdKort();}
    public void setVegHojdKort(String newVegHojdKort){FaltSkikt.setVegHojdKort(newVegHojdKort);}
    public String getVegHojdMedel(){return FaltSkikt.getVegHojdMedel();}
    public void setVegHojdMedel(String newVegHojdMedel){FaltSkikt.setVegHojdMedel(newVegHojdMedel);}
    public String getVegHojdHog(){return FaltSkikt.getVegHojdHog();}
    public void setVegHojdHog(String newVegHojdHog){setVegHojdHog(newVegHojdHog);}
    public String getVegSaknas(){return FaltSkikt.getVegSaknas();}
    public void setVegSaknas(String newVegSaknas){setVegSaknas(newVegSaknas);}
    public String getSolexpFaltsk(){return FaltSkikt.getSolexpFaltsk();}
    public void setSolexpFaltSk(String newSolexpFaltsk){FaltSkikt.setSolexpFaltsk(newSolexpFaltsk);}
    public String getBlomRikTistelv(){return FaltSkikt.getBlomRikTistelv();}
    public void setBlomRikTistelv(String newBlomRikTistelv){FaltSkikt.setBlomRikTistelv(newBlomRikTistelv);}
    public String getBlomRikArtv(){return  FaltSkikt.getBlomRikArtv();}
    public void setBlomRikArtv(String newBlomRikArtv){FaltSkikt.setBlomRikArtv(newBlomRikArtv);}
    public String getBlomRikOvr(){return FaltSkikt.getBlomRikOvr();}
    public void setBlomRikOvr(String newBlomRikOvr){FaltSkikt.setBlomRikOvr(newBlomRikOvr);}
    public String getGraminiderTackning(){return FaltSkikt.getGraminiderTackning();}
    public void setBlomRikGraminiderTackning(String newGraminiderTackn){FaltSkikt.setGraminiderTackning(newGraminiderTackn);}
    public String getOrterTackning(){return FaltSkikt.getOrterTackning();}
    public void setOrterTackning(String newOrterTackning){FaltSkikt.setOrterTackning(newOrterTackning);}
    public String getRisTackning(){return FaltSkikt.getRisTackning();}
    public void setRisTackning(String newRisTackning){FaltSkikt.setRisTackning(newRisTackning);}
    public String getOrmbunkarTackning(){return FaltSkikt.getOrmbunkarTackning();}
    public void setOrmbunkarTacking(String newOrmbunkarTackning){FaltSkikt.setOrmbunkarTackning(newOrmbunkarTackning);}
    public String getBlockighet(){return BottenSkikt.getBlockighet();}
    public void setBlockighet(String newBlockighet){
        BottenSkikt.setBlockighet(newBlockighet);
    }
    public String getBlottadStenTacking(){return BottenSkikt.getBlottadStenTackning();}
    public void setBlottadStenTackning(String newBlottadStenTackning){BottenSkikt.setBlottadStenTackning(newBlottadStenTackning);}
    public String getMarkStorningTackning(){return BottenSkikt.getMarkStorningTackning();}
    public void setMarkStorningTackning(String newMarkStorningTackning){BottenSkikt.setMarkStorningTackning(newMarkStorningTackning);}
    public String getDeponiTackning(){return BottenSkikt.getDeponiTackning();}
    public void setDeponiTackning(String newDeponiTackning){BottenSkikt.setDeponiTackning(newDeponiTackning);}
    public String getGramForna(){return BottenSkikt.getGramForna();}
    public void setGramForna(String newGramForna){BottenSkikt.setGramForna(newGramForna);}
    public String getTraBu1m(){return TrSkikt.getTradSkiktsBeskrivning().getTotTradBu1m();}
    public void setTraBu1m(String newTrBu1m){TrSkikt.getTradSkiktsBeskrivning().setTotTradBu1m(newTrBu1m);}
    public String getTraBu3m(){return TrSkikt.getTradSkiktsBeskrivning().getTotTradBu3m();}
    public void setTraBu3m(String newTrBu3m){TrSkikt.getTradSkiktsBeskrivning().setTotTradBu3m(newTrBu3m);}
    public String getTraBu7m(){return TrSkikt.getTradSkiktsBeskrivning().getTotTradBu7m();}
    public void setTraBu7m(String newTrBu7m){TrSkikt.getTradSkiktsBeskrivning().setTotTradBu7m(newTrBu7m);}
    public String getTraBuVux(){return TrSkikt.getTradSkiktsBeskrivning().getTotTradBuVuxna();}
    public void setTraBuVux(String newTrBuVux){TrSkikt.getTradSkiktsBeskrivning().setTotTradBuVuxna(newTrBuVux);}
    public String getTradBlom(){return TrSkikt.getTradBlom();}
    public void setTradBlom(String newValue){TrSkikt.setBlommor(newValue);}
    @Override
    public String getFormattedTime(){

        String formattedTime = "ej satt";

       String TimeStamp = (String)associatedProperties.get("ts_GISTYP"); // Alla objekt ska ha en gistyp så tid hämtas från den variabeln.
        System.out.println("[Provyta] GetFormattedTime associatedProperties ser ut så här:"+TimeStamp);

        if (TimeStamp != null){
            Long millisec = Long.parseLong(TimeStamp); // Omvandla string till Long.
            millisec = millisec * 1000;  // Gör sekunder till millisekunder.

            DateFormat formatter = new SimpleDateFormat("dd/MM  kk.mm.ss"); // Formatet för presentation i tabellen

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millisec);
            formattedTime = formatter.format(calendar.getTime());
        }

     return formattedTime;
    }


}

