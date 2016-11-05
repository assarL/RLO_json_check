package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import static javax.xml.transform.OutputKeys.ENCODING;


public class Main extends Application {
    private GeoJsonDecoder InitiateGeoObject;
    Stage stage = new Stage();
    private HashMap geometryListHashMap;
    private ObservableList<Geometry> dataList;
    BorderPane borderPane = new BorderPane();
    TableView tableView = new BasTabell().getTable();
    TextField dataPresentation1;
    TextField dataPresentation2;
    TextField dataPresentation3;
    TextArea dataPresentation4;
    private File selectedFile;


    public static void main(String[] args) { launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception   // Stage �r samma sak som "window", Scene �r ung. "document".

    {
        borderPane.setTop(addHBox());
        borderPane.setLeft(addVBox());
        borderPane.setCenter(tableView);
        borderPane.setRight(RightVbox());
        // L�gger till knappar i v�nstra f�ltet.

//--------------------visa f�nster och s�tt maxstorlek
        //Group root = new Group();
        Scene scene = new Scene(borderPane, 1900, 800, Color.BEIGE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ERBI");
        primaryStage.show();
    }






    public HBox addHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 20, 10, 20));
        hbox.setSpacing(10);
        return hbox;
    }

    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Datasource");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

//     Skapa knappar och ge en inledande storlek och text

        Button FileSelectButton = new Button("Välj fil");
        FileSelectButton.setPrefSize(200, 20);

        Button GroupSelectButton = new Button("Välj mapp"); //Inaktiverad tills vidare
        GroupSelectButton.setPrefSize(200, 20);

        Button ListAllButton = new Button("ingen fil vald");
        ListAllButton.setPrefSize(200, 20);
        ListAllButton.setDisable(true); // Default är inaktiverad

        Button ListLansGrasButton = new Button("Lansgrasprovytor");
        ListLansGrasButton.setPrefSize(200, 20);
        ListLansGrasButton.setDisable(true); // Default är inaktiverad

        Button ListAkerkantButton = new Button("Akerkantprovytor");
        ListAkerkantButton.setPrefSize(200, 20);
        ListAkerkantButton.setDisable(true); // Default �r inaktiverad

        Button ListKraftledningButton = new Button("Kraftled. provytor");
        ListKraftledningButton.setPrefSize(200, 20);
        ListKraftledningButton.setDisable(true); // Default �r inaktiverad

        Button ListPatrullstigsButton = new Button("Patrulls. provytor");
        ListPatrullstigsButton.setPrefSize(200, 20);
        ListPatrullstigsButton.setDisable(true); // Default �r inaktiverad

        Button ListHallmarkstorrButton = new Button("Hallmarksprovytor");
        ListHallmarkstorrButton.setPrefSize(200,20);
        ListHallmarkstorrButton.setDisable(true);

        Button ListAngsobeteButton = new Button("Ängs- och bete");
        ListAngsobeteButton.setPrefSize(200,20);
        ListAngsobeteButton.setDisable(true);

        Button ListAngsobetekomplButton = new Button("ÄoB kompl.");
        ListAngsobetekomplButton.setPrefSize(200,20);
        ListAngsobetekomplButton.setDisable(true);

        Button ShowInputJsonButton = new Button("Visa Json-fil");//Todo: Visa en popupbild av json-filen.
        ShowInputJsonButton.setPrefSize(200,20);
        ShowInputJsonButton.setDisable(true);

        Button ShowCollectedSpeciesButton = new Button("Visa artlistor");
        ShowCollectedSpeciesButton.setPrefSize(200,20);
        ShowCollectedSpeciesButton.setDisable(true);

        final FileChooser fileChooser = new FileChooser();  // Filv�ljare skapas


        FileSelectButton.setOnAction(event -> {
            ConfigureFileChooser(fileChooser);           // Funktion nedan, g�r att bara .json-filer visas upp och att ett visst directory v�ljs automatiskt
            selectedFile = fileChooser.showOpenDialog(stage);  // Visa dialogen

                if (selectedFile!=null)
                {
                    dataPresentation1.setText(selectedFile.getName());
                    InitiateGeoObject = new GeoJsonDecoder(selectedFile);// Skicka den valda filen till avkodning i GeoJsonDecoder-klassen.
                }

            geometryListHashMap = InitiateGeoObject.getHashedGeometries();  // Denna är viktig, hashmap för alla kodade rader, refereras överallt.

            dataPresentation2.setText(String.valueOf(geometryListHashMap.size()));

            ListAllButton.setText("Samtliga objekt: (" + geometryListHashMap.size() + " st objekt)");  // Visa antalet rader (objekt) i knapptexten, aktivera sedan knappen.
            ListAllButton.setDisable(false);

            ListLansGrasButton.setText("Lansgrasprovytor: "+ InitiateGeoObject.getLansgrasNycklar().size()+" st");
            if(Lansgras.provytaFinns) {
                ListLansGrasButton.setDisable(false);       // Om det finns Länsgräsytor aktiveras knappen.
                System.out.println("[Main] InitiateGeoObject inuti Filechooser-knappen har v�rdet:" + InitiateGeoObject);
            }

            ListAkerkantButton.setText("Akerkantsprovytor: " + InitiateGeoObject.getAkerkantsNycklar().size() + " st");
            if(Akerkant.provytaFinns){
                ListAkerkantButton.setDisable(false);       // Om det finns Åkerkantsytor aktiveras knappen.
                System.out.println("Akerkantsknappen aktiverad");
            }

            ListKraftledningButton.setText("Kraftled provytor: " + InitiateGeoObject.getKraftledningsNycklar().size() + " st");
            if(Kraftledning.provytaFinns){
                ListKraftledningButton.setDisable(false);       // Om det finns >Kraftledningsytor aktiveras knappen.
                System.out.println("Kraftledningsknappen aktiverad");
            }
            ListPatrullstigsButton.setText("Patrullst provytor: " + InitiateGeoObject.getPatrullstigsNycklar().size() + " st");
            if(Patrullstig.provytaFinns){
                ListPatrullstigsButton.setDisable(false);       // Om det finns Patrullstigsytor aktiveras knappen.
                System.out.println("Patrullstigsknappen aktiverad");
            }
            ListHallmarkstorrButton.setText("Hällmarksprovytor: " + InitiateGeoObject.getHallmarksNycklar().size() + " st");
            if(Hallmark.provytaFinns) {
                ListHallmarkstorrButton.setDisable(false);       // Om det finns Hällmarksprovytor aktiveras knappen.
                System.out.println("Hällmarksknappen aktiverad");
            }
            ListAngsobeteButton.setText("Ängs- och bete: " + InitiateGeoObject.getAngsobetesNycklar().size() + " st");
            if(Angsobete.provytaFinns) {
                ListAngsobeteButton.setDisable(false);       // Om det finns Ängs- och betesytor aktiveras knappen.
                System.out.println("Ängs- och betesknappen aktiverad");
            }

            ListAngsobetekomplButton.setText("ÄoB kompl:" + InitiateGeoObject.getAngsobetekomplNycklar().size() + "st");
            if(Angsobetekompl.provytaFinns) {
                ListAngsobetekomplButton.setDisable(false);       // Om det finns Ängs- och betesytor aktiveras knappen.
                System.out.println("Ängs- och betesknappen aktiverad");
            }

            ShowInputJsonButton.setText("Json:"+selectedFile.getName()+"..");
            ShowInputJsonButton.setDisable(false);
            System.out.println("[Main] Visa JSON-knappen aktiv");

            ShowCollectedSpeciesButton.setText("Arter:"+selectedFile.getName().substring(7));
            ShowCollectedSpeciesButton.setDisable(false);
            System.out.println("[Main] Arter-knappen aktiv");



            // Skicka filen till inledande funktion




        });
/*
        GroupSelectButton.setOnAction(event -> {   //TODO skapa hantering av flerfilsval--> Skapa en konstruktor i GeoJsonDecoder.
            ConfigureFileChooser(fileChooser);
            List list = fileChooser.showOpenMultipleDialog(stage);    // Tar emomt flera filer simultant. Skapa en konstruktor f�r

        });
*/

        ListAllButton.setOnAction(event -> {  // Knappen skickar listan av objekt till tabell-f�nstret (Skapas i TableOfObjects) d�r det listas som geometry-objekt.

            //TableView newFrame = new BasTabell(geometryList).getTable(); // Listan skickas f�r presentation i tabellen, tabellen h�mtas.
            Set nycklar = geometryListHashMap.keySet();
            System.out.println("nycklar har v�rdet:"+nycklar);
            TableView newFrame = new BasTabell(geometryListHashMap,nycklar).getTable(); // Listan skickas f�r presentation i tabellen, tabellen h�mtas.

            borderPane.setCenter(newFrame);
            dataPresentation4.setText("Fixa detta."); // Addera samtliga feltexter från alla klasser. Todo: Skapa ett felrapportobjekt för samtliga klasser att rapportera fel till.

        });

        ListLansGrasButton.setOnAction(event -> {

            System.out.println("Tjoho!! L�nsgr�sknappen tryckt");

            System.out.println("[Main] InitiateGeoObject inuti lansgras-knappen har v�rdet:"+InitiateGeoObject.getLansgrasNycklar());
            System.out.println("GeometryListHashMap har v�rdet: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getLansgrasNycklar());

            System.out.println("mySet har v�rdet:"+mySet);
            //TableView LansGrasFrame = new BasTabell(geometryListHashMap,mySet).getTable();
            TableView LansGrasFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(LansGrasFrame);
            dataPresentation4.setText("Fixa detta");

        });

        ListAkerkantButton.setOnAction(event -> {

            System.out.println("Tjoho!! Akerkantsknappen tryckt");
            System.out.println("[Main] InitiateGeoObject inuti åkerkantsknappen har v�rdet:"+InitiateGeoObject.getAkerkantsNycklar());
            System.out.println("GeometryListHashMap har v�rdet: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getAkerkantsNycklar()); // Nycklar för Åkerkanterna hämtas från Åkerkantsobjektet.

            System.out.println("mySet har v�rdet:"+mySet);
            TableView AkerKantFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(AkerKantFrame);
            dataPresentation4.setText("felmeddelanden för åkerkanter ej aktiverade ännu");
        });

        ListKraftledningButton.setOnAction(event -> {

            System.out.println("Tjoho!! Kraftledningsknappen tryckt");
            System.out.println("[Main] InitiateGeoObject inuti kraftledningsknappen har v�rdet:"+InitiateGeoObject.getKraftledningsNycklar());
            System.out.println("GeometryListHashMap har v�rdet: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getKraftledningsNycklar()); // Nycklar för Åkerkanterna hämtas från Åkerkantsobjektet.

            System.out.println("mySet har v�rdet:"+mySet);
            TableView KraftLedningFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(KraftLedningFrame);
            dataPresentation4.setText("felmeddelanden för kraftledningar ej aktiverade ännu");
        });

        ListAngsobeteButton.setOnAction(event -> {

            System.out.println("Tjoho!! Ängs- och betesknappen tryckt");
            System.out.println("[Main] InitiateGeoObject inuti Ängs- och betesknappen har v�rdet:"+InitiateGeoObject.getAngsobetesNycklar());
            System.out.println("GeometryListHashMap har v�rdet: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getAngsobetesNycklar()); // Nycklar för Ängs och bete hämtas.

            System.out.println("mySet har v�rdet:"+mySet);
            TableView AngsobeteFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(AngsobeteFrame);
            dataPresentation4.setText("felmeddelanden för ängs- och bete ej aktiverade ännu");
        });

        ListAngsobetekomplButton.setOnAction(event -> {

            System.out.println("Tjoho!! Kompletterande ÄoB tryckt");
            System.out.println("[Main] InitiateGeoObject inuti Äbo-komp-knappen har v�rdet:"+InitiateGeoObject.getAngsobetesNycklar());
            System.out.println("GeometryListHashMap har v�rdet: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getAngsobetekomplNycklar()); // Nycklar för kompletterande ÄoB hämtas .

            System.out.println("mySet har v�rdet:"+mySet);
            TableView AngsobetekomplFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(AngsobetekomplFrame);
            dataPresentation4.setText("felmeddelanden för kompl ÄoB ej aktiverade ännu");
        });

        ListPatrullstigsButton.setOnAction(event -> {

            System.out.println("Tjoho!! Patrullstigsknappen tryckt");
            System.out.println("[Main] InitiateGeoObject inuti patrullstigsknappen har v�rdet:"+InitiateGeoObject.getPatrullstigsNycklar());
            System.out.println("GeometryListHashMap har v�rdet: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getPatrullstigsNycklar()); // Nycklar för Åkerkanterna hämtas från Åkerkantsobjektet.

            System.out.println("mySet har v�rdet:"+mySet);
            TableView PatrullstigFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(PatrullstigFrame);
            dataPresentation4.setText("felmeddelanden Patrullstig ej aktiverade ännu");
        });

        ListHallmarkstorrButton.setOnAction(event -> {

            System.out.println("Tjoho!! Hällmarkstorrängsknappen tryckt");
            System.out.println("[Main] hällmarksnycklar har v�rdet:"+InitiateGeoObject.getHallmarksNycklar());
            System.out.println("Alla värden i GeometryListHashMap: "+geometryListHashMap);

            Set mySet = new HashSet<>(InitiateGeoObject.getHallmarksNycklar()); // Nycklar för hällmarker hämtas från hällmarksobjektet.

            System.out.println("mySet har v�rdet:"+mySet);

            TableView HallmarkstorrFrame = new ProvyteTabell(geometryListHashMap,mySet).getTable();
            borderPane.setCenter(HallmarkstorrFrame);

            //PolygonProvTabell HallmarkFrame = new PolygonProvTabell(geometryListHashMap,mySet);
            //TableView HallmarkTableView = HallmarkFrame.getTable();

            dataPresentation4.setText("felmeddelanden Hällmark ej aktiverade ännu");
        });

        ShowInputJsonButton.setOnAction(event -> {

                    System.out.println("[Main]Visa rådatafilen");
                    System.out.println("");
                    dataPresentation2.setText(selectedFile.toString());
                    dataPresentation4.setText("Knappen för att visa JSON-filen tryckt");
                    TextArea textArea = new TextArea();

            BufferedReader inputR;

            try {
                inputR = new BufferedReader(new FileReader(selectedFile));  // Läs den valda json-filen

                String l;
                    StringBuilder fileText = new StringBuilder();
                    long t = System.currentTimeMillis(); // kolla tidsåtgång för läsningen, utgångstid

                while((l = inputR.readLine())!= null) {
                    //System.out.println("inputStream.readLine():"+l);
                    fileText.append(l+"\n");
                }
                inputR.close();

                System.out.println("time:"+(System.currentTimeMillis()-t));// Tid för while-loop
                System.out.println("Strömmen stängd");
                //System.out.println("fileText:"+fileText.toString());
                textArea.setText(fileText.toString());  // text läggs i javafx textarea

                borderPane.setCenter(textArea);   // Skicka texten till stora visningsfönstret

                System.out.println("time:"+(System.currentTimeMillis()-t)); // Tid att lägga texten i textarean.


            } catch (IOException e) {
                e.printStackTrace();
            }



            //textArea.appendText(inputStream.readLine());

                }
        );
        ShowCollectedSpeciesButton.setOnAction(event1 ->
        {
            try {
                ArtForekomstTabell artf = new ArtForekomstTabell(geometryListHashMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        vbox.getChildren().addAll(FileSelectButton, GroupSelectButton, ListAllButton, ListLansGrasButton,
                ListAkerkantButton, ListKraftledningButton, ListPatrullstigsButton,ListHallmarkstorrButton,
                ListAngsobeteButton,ListAngsobetekomplButton,ShowInputJsonButton,ShowCollectedSpeciesButton); // Knapparna l�ggs i vertikal-boxen.

        return vbox;
    }

    public void ConfigureFileChooser(final FileChooser fileChooser){    // Visar bara json-filer och v�ljer ut ett default-directory

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialDirectory(new File("C:\\Data_NILS"));  // ToDo g�r detta valbart i framtiden (kommer generera fel vid datorbyte).

    }

    public VBox RightVbox(){

        VBox Vbox = new VBox();
        Text rubrik = new Text("Info:");
        rubrik.setFont(Font.font(18));

        dataPresentation1 = new TextField("inget valt");
        dataPresentation1.setMaxSize(200,20);
        dataPresentation2 = new TextField();
        dataPresentation2.setMaxSize(200,20);
        dataPresentation3 = new TextField();
        dataPresentation3.setMaxSize(200,20);
        dataPresentation4 = new TextArea();
        dataPresentation4.setMaxSize(200,400);

        Vbox.getChildren().addAll(rubrik,dataPresentation1,dataPresentation2,dataPresentation3,dataPresentation4);
        return Vbox;
    }
}
