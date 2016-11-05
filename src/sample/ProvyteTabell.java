package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.*;

/**
 * Created by Assar on 2015-10-25.
 */
public class ProvyteTabell {


    TableView tableView;  // Tabellen alla kolumner ska l�ggas till;//

    HashMap<Object, Provyta> ProvytaHashMap;


    public ProvyteTabell(HashMap ProvytaHM, Set<HashSet> keys) {

        ProvytaHashMap = ProvytaHM;


        ObservableList datalist = FXCollections.observableArrayList();  // Listan som ska l�ggas ut i tabellen.


        System.out.println("[ProvyteTabell] keys.size=" + keys.size());

        for (Object key : keys) { //

            Object enProvyta = ProvytaHashMap.get(key);


            datalist.add(new TableRow((Provyta) enProvyta));  // rader av data läggs till observable-list.

        }

        createProvyteColumns();
        tableView.setItems(datalist);


    }

    private TableView createProvyteColumns() {

        tableView = new TableView(); // Sj�lva tabellen
        tableView.setEditable(true);


//---------------------------Funktioner för kopiering av tabellvärden------------------------

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        MenuItem item = new MenuItem("Copy");
        item.setOnAction(event -> {

            ObservableList rowList = tableView.getSelectionModel().getSelectedItems();

            StringBuilder clipboardString = new StringBuilder();

            for (Iterator it = rowList.iterator(); it.hasNext(); ) {
                ObservableList<String> row = (ObservableList<String>) it.next();

                for (String cell : row) {
                    if (cell == null) {
                        cell = "";
                    }
                    clipboardString.append(cell);
                    clipboardString.append('\t');
                }
                clipboardString.append('\n');

            }
            final ClipboardContent content = new ClipboardContent();

            content.putString(clipboardString.toString());
            Clipboard.getSystemClipboard().setContent(content);
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(item);
        tableView.setContextMenu(menu);

//-------------------------------------------------------------------------------
        TableColumn RutaKolumn = new TableColumn("Ruta"); RutaKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("Ruta"));
        TableColumn<TableRow, String> FixedGIDKolumn = new TableColumn<TableRow, String>("Provytor FIXEDGID");FixedGIDKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("FixedGID"));
        FixedGIDKolumn.setPrefWidth(320);
        TableColumn<TableRow, String> InventeringsKodsKolumn = new TableColumn<TableRow, String>("Tgh:");InventeringsKodsKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("Tillganglighet"));
        TableColumn<TableRow, String> InventerarKolumn = new TableColumn<>("Inventerare");InventerarKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("Inventerare"));
        TableColumn<TableRow, Integer> FlyttadKolumn = new TableColumn<TableRow, Integer>("Flyttad?"); FlyttadKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,Integer>("flyttad"));
        TableColumn<TableRow, String> FotoKolumn = new TableColumn<>("FotoID"); FotoKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("Foto"));
        FotoKolumn.setPrefWidth(40);
        TableColumn MarkslagFaltKolummn = new TableColumn("MslFä"); MarkslagFaltKolummn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("MarkUtFalt"));
        MarkslagFaltKolummn.setCellFactory(TextFieldTableCell.forTableColumn());
        MarkslagFaltKolummn.setOnEditCommit( (e) -> MarkslagFaltKolummn_onEditCommit(e));

        TableColumn<TableRow, String> MarkslagFlygKolumn = new TableColumn<TableRow, String>("MslFly"); MarkslagFlygKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("MarkslagFlyg"));
        TableColumn<TableRow, String> HavdTypKolumn = new TableColumn<TableRow, String>("Hävdtyp"); HavdTypKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("HavdTyp"));
        TableColumn<TableRow, String> HavdStatusKolumn = new TableColumn<>("Hvdstat."); HavdStatusKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("HavdStatus"));
        TableColumn<TableRow, String> VegHojdKortKolumn = new TableColumn<TableRow, String>("VegHK"); VegHojdKortKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("VegHojdKort"));
        TableColumn<TableRow, String> VegHojdMedelKolumn = new TableColumn<TableRow, String>("VegHM"); VegHojdMedelKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("VegHojdMedel"));
        TableColumn<TableRow, String> VegHojdHogKolumn = new TableColumn<>("VegHH"); VegHojdHogKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("VegHojdHog"));
        TableColumn<TableRow, String> VegHojdSaknasKolumn = new TableColumn<>("VegSakn"); VegHojdSaknasKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("VegSaknas"));
        TableColumn<TableRow, String> SolExpKolumn = new TableColumn<>("SolExp"); SolExpKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("SolexpFaltsk"));
        TableColumn<TableRow, String> TistelBlomKolumn = new TableColumn<>("TistelBl."); TistelBlomKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("BlomRikTistelv"));
        TableColumn<TableRow, String> ArtBlomKolumn = new TableColumn<TableRow, String>("Ärtbl.");ArtBlomKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("BlomRikArtv"));
        TableColumn<TableRow, String> OvrBlomKolumn = new TableColumn<>("Övrbl.");OvrBlomKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("BlomRikOvr"));
        TableColumn<TableRow, String> GraminiderKolumn = new TableColumn<TableRow, String>("Gramin.");GraminiderKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("GraminiderTackning"));
        TableColumn<TableRow, String> OrterKolumn = new TableColumn<TableRow, String>("Örter");OrterKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("OrterTackning"));
        TableColumn<TableRow, String> RisKolumn = new TableColumn<TableRow, String>("Ris");RisKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("RisTackning"));
        TableColumn<TableRow, String> OrmbunkarKolumn = new TableColumn<TableRow, String>("Ormbv.");OrmbunkarKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("OrmbunkarTackning"));
        TableColumn<TableRow, String> AntalSpyArterKolumn = new TableColumn<>("SPYarter");AntalSpyArterKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("SpyTotArtAntal"));
        TableColumn<TableRow, String> SolexpKolumn = new TableColumn<TableRow, String>("Solexp."); SolexpKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("SolexpFaltsk"));
        TableColumn<TableRow, String> BlockighetKolumn = new TableColumn<TableRow, String>("Block"); BlockighetKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("Blockighet"));
        TableColumn<TableRow, String> BlottadStenKolumn = new TableColumn<TableRow, String>("Stenyta"); BlottadStenKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("BlottadStenTackning"));
        TableColumn<TableRow, String> MarkStorningKolumn = new TableColumn<TableRow, String>("Störning"); MarkStorningKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("MarkStorning"));
        TableColumn<TableRow, String> MarkStorningTackning = new TableColumn<>("St. Täckn"); MarkStorningTackning.setCellValueFactory(new PropertyValueFactory<TableRow,String>("MarkStorningTackning"));
        TableColumn<TableRow, String> DeponitypKolumn = new TableColumn<>("Deponityp"); DeponitypKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("DeponiTyp"));
        TableColumn<TableRow, String> DeponiTackningKolumn = new TableColumn<>("Dep.Täckn."); DeponiTackningKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("DeponiTackning"));
        TableColumn<TableRow, String> GramFornaKolumn = new TableColumn<>("Gramförna");GramFornaKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("GramForna"));
        TableColumn<TableRow, String> TotTradBu1mKolumn = new TableColumn<TableRow, String>("TrBu1"); TotTradBu1mKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("TotTradBu1m"));
        TableColumn<TableRow, String> TotTradBu3mKolumn = new TableColumn<TableRow, String>("TrBu3"); TotTradBu3mKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("TotTradBu3m"));
        TableColumn<TableRow, String> TotTradBu7mKolumn = new TableColumn<TableRow, String>("TrBu7"); TotTradBu7mKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("TotTradBu7m"));
        TableColumn<TableRow, String> TotTradBuVmKolumn = new TableColumn<>("TrBuV"); TotTradBuVmKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("TotTradBuVuxna"));
        TableColumn<TableRow, Integer> TradBuskArt_ArtAntalKolumn = new TableColumn<>("TraBu-arter");TradBuskArt_ArtAntalKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,Integer>("TradBuskArt_ArtAntal"));


        tableView.getColumns().addAll(RutaKolumn,FixedGIDKolumn,InventeringsKodsKolumn,InventerarKolumn,FlyttadKolumn,FotoKolumn,MarkslagFaltKolummn,MarkslagFlygKolumn,HavdTypKolumn,HavdStatusKolumn,
                VegHojdKortKolumn,VegHojdMedelKolumn,VegHojdHogKolumn,VegHojdSaknasKolumn,SolExpKolumn,TistelBlomKolumn,ArtBlomKolumn,OvrBlomKolumn,GraminiderKolumn,OrterKolumn,RisKolumn,OrmbunkarKolumn,AntalSpyArterKolumn,BlockighetKolumn,
                BlottadStenKolumn,MarkStorningKolumn,MarkStorningTackning,DeponitypKolumn,DeponiTackningKolumn,GramFornaKolumn,TotTradBu1mKolumn,TotTradBu3mKolumn,TotTradBu7mKolumn,TotTradBuVmKolumn
                ,TradBuskArt_ArtAntalKolumn
        );

        return tableView;

    }

    public void MarkslagFaltKolummn_onEditCommit(Event e) // Om markslagskolumnen ändras.
    {
TableColumn.CellEditEvent<TableRow,String> ce;
        ce = (TableColumn.CellEditEvent<TableRow,String>) e; // Gör om event-objekt till ett cell-edit-event-objekt.
        Object SelectedKey = ce.getRowValue().getFixedGID();  // Ta fram nyckeln
        Provyta ValdProvyta = ProvytaHashMap.get(SelectedKey);// Hämta Objektet ur Hashmap
        //Provyta ValdProvyta = (Provyta)obsMapData.get(SelectedKey);

        // Ändra key:value
        System.out.println("[ProvyteTabell]Event source: "+ce.getSource()+"  : "+((TableColumn.CellEditEvent<TableRow, String>) e).getRowValue().getFixedGID());
        System.out.println("[ProvyteTabell]ce.getRowValue().getFixedGID(): "+ce.getRowValue().getFixedGID());

        TableRow TR = ce.getRowValue(); // Uppdatera värdet i tabellen direkt genom att anropa funktionerna i TableRow-klassen direkt.
        String newValue = ce.getNewValue();
        String oldValue = ce.getOldValue();

        try {
            if (Integer.valueOf(newValue)>0){
                TR.setMarkUtFalt(newValue);
                ValdProvyta.setMarkUtFalt(newValue); // Uppdatera hashmap med det nya värdet
        }
        } catch (NumberFormatException e1) {
            //e1.printStackTrace();
            TR.setMarkUtFalt(oldValue);// Input är inte en siffra, sätt tillbaks gammalt värde i cellen.
        }


        System.out.println("[ProvyteTabell]Hashmap uppdaterad ->. Oldvalue:"+oldValue+"  NewValue:"+newValue);
        System.out.println("[ProvyteTabell]ProvytaHashMap.get("+SelectedKey+".getMarkUtFalt)="+ValdProvyta.getMarkUtFalt());
        tableView.refresh();

    }

    public static class TableRow {

        final SimpleStringProperty Ruta;
        final SimpleStringProperty FixedGID;
        final SimpleStringProperty Tillganglighet;  // InvKod är koden för tillgänglighet.
        final SimpleStringProperty Inventerare;
        final SimpleStringProperty OrsakText;
        final SimpleBooleanProperty flyttad;  // Obs! BooleanProperty
        final SimpleIntegerProperty FlyttAvstOstVast;
        final SimpleIntegerProperty FlyttAvstNordSyd;
        final SimpleStringProperty Foto;
        final SimpleStringProperty MarkUtFalt;
        final SimpleStringProperty MarkslagFlyg;
        final SimpleIntegerProperty SpyTotArtAntal;
        final SimpleStringProperty HavdTyp;
        final SimpleStringProperty HavdStatus;
        final SimpleStringProperty VegHojdKort;
        final SimpleStringProperty VegHojdMedel;
        final SimpleStringProperty VegHojdHog;
        final SimpleStringProperty VegSaknas;
        final SimpleStringProperty BlomRikTistelv;
        final SimpleStringProperty BlomRikArtv;
        final SimpleStringProperty BlomRikOvr;
        final SimpleStringProperty GraminiderTackning;
        final SimpleStringProperty OrterTackning;
        final SimpleStringProperty RisTackning;
        final SimpleStringProperty OrmbunkarTackning;
        final SimpleStringProperty SolexpFaltsk;
        final SimpleStringProperty Blockighet;
        final SimpleStringProperty BlottadStenTackning;
        final SimpleStringProperty MarkStorning;
        final SimpleStringProperty MarkStorningTackning;
        final SimpleStringProperty DeponiTyp;
        final SimpleStringProperty DeponiTackning;
        final SimpleStringProperty GramForna;
        final SimpleStringProperty TotTradBu1m;
        final SimpleStringProperty TotTradBu3m;
        final SimpleStringProperty TotTradBu7m;
        final SimpleStringProperty TotTradBuVuxna;
        final SimpleIntegerProperty TradBuskArt_ArtAntal;
        final SimpleStringProperty RojningsTid;


        public TableRow(Provyta enProvyta) {
            Ruta = new SimpleStringProperty(enProvyta.getRutaID());  // Hämta värdet för visning ur provyte-objektet
            FixedGID = new SimpleStringProperty(enProvyta.getFixedGID());
            Tillganglighet = new SimpleStringProperty(enProvyta.getInvKod());  // InvKod är koden för tillgänglighet.
            OrsakText = new SimpleStringProperty(enProvyta.getOrsakText());
            Inventerare = new SimpleStringProperty(enProvyta.getEmployee());
            flyttad = new SimpleBooleanProperty(enProvyta.getFlyttad());  // Obs! BooleanProperty
            FlyttAvstOstVast = new SimpleIntegerProperty(enProvyta.getFlyttY());
            FlyttAvstNordSyd = new SimpleIntegerProperty(enProvyta.getFlyttX());
            String shortFoto = (enProvyta.getFoto() == "saknas")? "Nej":"Ja";
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^!"+enProvyta.getFoto()+"--------------------------------------------------------");
            Foto =  new SimpleStringProperty(shortFoto);
            // Visar om foto finns men sparar plats i tabellen.
            MarkUtFalt = new SimpleStringProperty(this, "MarkUtFalt", enProvyta.getMarkUtFalt());
            MarkslagFlyg = new SimpleStringProperty(enProvyta.getMarkUtFlyg());
            SpyTotArtAntal = new SimpleIntegerProperty(enProvyta.getSmaProvArtCounter()); // Hämtar unika artantalet, integer
            HavdTyp = new SimpleStringProperty(enProvyta.getHavdTyp());
            HavdStatus = new SimpleStringProperty(enProvyta.getHavdStatus());
            VegHojdKort = new SimpleStringProperty(enProvyta.getVegHojdKort());
            VegHojdMedel = new SimpleStringProperty(enProvyta.getVegHojdMedel());
            VegHojdHog = new SimpleStringProperty(enProvyta.getVegHojdHog());
            VegSaknas = new SimpleStringProperty(enProvyta.getVegSaknas());
            SolexpFaltsk = new SimpleStringProperty(enProvyta.getSolexpFaltsk());
            BlomRikTistelv = new SimpleStringProperty(enProvyta.getBlomRikTistelv());
            BlomRikArtv = new SimpleStringProperty(enProvyta.getBlomRikArtv());
            BlomRikOvr = new SimpleStringProperty(enProvyta.getBlomRikOvr());
            GraminiderTackning = new SimpleStringProperty(enProvyta.getGraminiderTackning());
            OrterTackning = new SimpleStringProperty(enProvyta.getOrterTackning());
            RisTackning = new SimpleStringProperty(enProvyta.getRisTackning());
            OrmbunkarTackning = new SimpleStringProperty(enProvyta.getOrmbunkarTackning());
            Blockighet = new SimpleStringProperty(enProvyta.getBlockighet());
            BlottadStenTackning = new SimpleStringProperty(enProvyta.getBlottadStenTacking());
            MarkStorning = new SimpleStringProperty(enProvyta.getMarkStorning());
            MarkStorningTackning = new SimpleStringProperty(enProvyta.getMarkStorningTackning());
            DeponiTyp = new SimpleStringProperty(enProvyta.getDeponiTyp());
            DeponiTackning = new SimpleStringProperty(enProvyta.getDeponiTackning());
            GramForna = new SimpleStringProperty(enProvyta.getGramForna());
            TotTradBu1m = new SimpleStringProperty(enProvyta.getTraBu1m());
            TotTradBu3m = new SimpleStringProperty(enProvyta.getTraBu3m());
            TotTradBu7m = new SimpleStringProperty(enProvyta.getTraBu7m());
            TotTradBuVuxna = new SimpleStringProperty(enProvyta.getTraBuVux());
            TradBuskArt_ArtAntal = new SimpleIntegerProperty(enProvyta.getTradartCounter());
            RojningsTid = new SimpleStringProperty(enProvyta.getRojningsTid());

        }

        public String getRuta(){return Ruta.get();}

        public SimpleStringProperty RutaProperty(){return Ruta;}

        public void setRuta(String rutaID){this.Ruta.set(rutaID);}

        public String getFixedGID() {
            return FixedGID.get();
        }

        public SimpleStringProperty FixedGIDProperty() {
            return FixedGID;
        }

        public void setFixedGID(String uniktID) {
            this.FixedGID.set(uniktID);
        }

        public String getTillganglighet() {
            return Tillganglighet.get();
        }

        public SimpleStringProperty tillganglighetProperty() {
            return Tillganglighet;
        }

        public void setTillganglighet(String tillganglighet) {
            this.Tillganglighet.set(tillganglighet);
        }

        public Boolean getFlyttad() {
            return flyttad.get();
        }

        public SimpleBooleanProperty flyttadProperty() {
            return flyttad;
        }

        public void setFlyttad(Boolean flyttad) {
            this.flyttad.set(flyttad);
        }

        public String getOrsakText() {
            return OrsakText.get();
        }

        public SimpleStringProperty orsakTextProperty() {
            return OrsakText;
        }

        public void setOrsakText(String orsakText) {
            this.OrsakText.set(orsakText);
        }

        public void setInventerare(String nyInventerare){

            Inventerare.set(nyInventerare);
        }
        public String getInventerare(){

            return Inventerare.get();
        }

        public SimpleStringProperty InventerareProperty(){
            return Inventerare;
        }

        public Integer getFlyttAvstNordSyd() {
            return FlyttAvstNordSyd.get();
        }

        public SimpleIntegerProperty flyttAvstNordSydProperty() {
            return FlyttAvstNordSyd;
        }

        public void setFlyttAvstNordSyd(Integer flyttAvstNordSyd) {
            this.FlyttAvstNordSyd.set(flyttAvstNordSyd);
        }
        /*
                public String getFlyttOstVast() {
                    return FlyttOstVast.get();
                }

                public SimpleStringProperty flyttOstVastProperty() {
                    return FlyttOstVast;
                }

                public void setFlyttOstVast(String flyttOstVast) {
                    this.FlyttOstVast.set(flyttOstVast);
                }
               */

        public Integer getFlyttAvstOstVast() {
                    return FlyttAvstOstVast.get();
                }

        public SimpleIntegerProperty flyttAvstOstVastProperty() {
            return FlyttAvstOstVast;
        }

        public void setFlyttAvstOstVast(Integer flyttAvstOstVast) {
            this.FlyttAvstOstVast.set(flyttAvstOstVast);
        }

        public String getFoto() {
            return Foto.get();
        }

        public SimpleStringProperty fotoProperty() {
            return Foto;
        }

        public void setFoto(String foto) {
            this.Foto.set(foto);
        }

        public String getMarkUtFalt() {
            return MarkUtFalt.get();
        }

        public SimpleStringProperty markUtFaltProperty() {
            return MarkUtFalt;
        }

        public void setMarkUtFalt(String markUtFalt) {this.MarkUtFalt.set(markUtFalt); }

        public String getMarkUtFlyg() {
            return MarkslagFlyg.get();
        }

        public SimpleStringProperty markUtFlygProperty() {
            return MarkslagFlyg;
        }

        public void setMarkUtFlyg(String markslagFlyg) {
            this.MarkslagFlyg.set(markslagFlyg);
        }

        public void setSpyTotArtAntal(Integer spytotAntal){this.SpyTotArtAntal.set(spytotAntal);}

        public SimpleIntegerProperty SpyTotArtAntalProperty() { return SpyTotArtAntal; }

        public Integer getSpyTotArtAntal() {return SpyTotArtAntal.get();}

        public String getHavdTyp() {
            return HavdTyp.get();
        }

        public SimpleStringProperty havdTypProperty() {
            return HavdTyp;
        }

        public void setHavdTyp(String havdTyp) {
            this.HavdTyp.set(havdTyp);
        }

        public String getHavdStatus() {
            return HavdStatus.get();
        }

        public SimpleStringProperty havdStatusProperty() {
            return HavdStatus;
        }

        public void setHavdStatus(String havdStatus) {
            this.HavdStatus.set(havdStatus);
        }

        public String getVegHojdKort() {
            return VegHojdKort.get();
        }

        public SimpleStringProperty vegHojdKortProperty() {
            return VegHojdKort;
        }

        public void setVegHojdKort(String vegHojdKort) {
            this.VegHojdKort.set(vegHojdKort);
        }

        public String getVegHojdMedel() {
            return VegHojdMedel.get();
        }

        public SimpleStringProperty vegHojdMedelProperty() {
            return VegHojdMedel;
        }

        public void setVegHojdMedel(String vegHojdMedel) {
            this.VegHojdMedel.set(vegHojdMedel);
        }

        public String getVegHojdHog() {
            return VegHojdHog.get();
        }

        public SimpleStringProperty vegHojdHogProperty() {
            return VegHojdHog;
        }

        public void setVegHojdHog(String vegHojdHog) {
            this.VegHojdHog.set(vegHojdHog);
        }

        public String getVegSaknas() {
            return VegSaknas.get();
        }

        public SimpleStringProperty vegSaknasProperty(){
            return VegSaknas;
        }

        public void setVegSaknas(String vegSaknas) {
            this.VegSaknas.set(vegSaknas);
        }

        public String getSolexpFaltsk() {
            return SolexpFaltsk.get();
        }

        public SimpleStringProperty solexpFaltskProperty() {
            return SolexpFaltsk;
        }

        public void setSolexpFaltsk(String solexpFaltsk) {
            this.SolexpFaltsk.set(solexpFaltsk);
        }

        public String getBlockighet() {
            return Blockighet.get();
        }

        public SimpleStringProperty blockighetProperty() {
            return Blockighet;
        }

        public void setBlockighet(String blockighet) {
            this.Blockighet.set(blockighet);
        }

        public String getBlottadStenTackning() {
            return BlottadStenTackning.get();
        }

        public SimpleStringProperty blottadStenTackningProperty() {
            return BlottadStenTackning;
        }

        public void setBlottadStenTackning(String blottadStenTackning) {
            this.BlottadStenTackning.set(blottadStenTackning);
        }

        public String getMarkStorning() {
            return MarkStorning.get();
        }

        public SimpleStringProperty markStorningProperty() {
            return MarkStorning;
        }

        public void setMarkStorning(String markStorning) {
            this.MarkStorning.set(markStorning);
        }

        public String getMarkStorningTackning() {
            return MarkStorningTackning.get();
        }

        public SimpleStringProperty markStorningTackningProperty() {
            return MarkStorningTackning;
        }

        public void setMarkStorningTackning(String markStorningTackning) {
            this.MarkStorningTackning.set(markStorningTackning);
        }

        public String getDeponiTyp() {
            return DeponiTyp.get();
        }

        public SimpleStringProperty deponiTypProperty() {
            return DeponiTyp;
        }

        public void setDeponiTyp(String deponiTyp) {
            this.DeponiTyp.set(deponiTyp);
        }

        public String getDeponiTackning() {
            return DeponiTackning.get();
        }

        public SimpleStringProperty deponiTackningProperty() {
            return DeponiTackning;
        }

        public void setDeponiTackning(String deponiTackning) {
            this.DeponiTackning.set(deponiTackning);
        }

        public String getTotTradBu1m() {
            return TotTradBu1m.get();
        }

        public SimpleStringProperty totTradBu1mProperty() {
            return TotTradBu1m;
        }

        public void setTotTradBu1m(String totTradBu1m) {
            this.TotTradBu1m.set(totTradBu1m);
        }

        public String getTotTradBu3m() {
            return TotTradBu3m.get();
        }

        public SimpleStringProperty totTradBu3mProperty() {
            return TotTradBu3m;
        }

        public void setTotTradBu3m(String totTradBu3m) {
            this.TotTradBu3m.set(totTradBu3m);
        }

        public String getTotTradBu7m() {
            return TotTradBu7m.get();
        }

        public SimpleStringProperty totTradBu7mProperty() {
            return TotTradBu7m;
        }

        public void setTotTradBu7m(String totTradBu7m) {
            this.TotTradBu7m.set(totTradBu7m);
        }

        public String getTotTradBuVuxna() {
            return TotTradBuVuxna.get();
        }

        public SimpleStringProperty totTradBuVuxnaProperty() {
            return TotTradBuVuxna;
        }

        public void setTotTradBuVuxna(String totTradBuVuxna) {
            this.TotTradBuVuxna.set(totTradBuVuxna);
        }


        public Integer getTradBuskArt_ArtAntal() { return TradBuskArt_ArtAntal.get();}

        public SimpleIntegerProperty TradBuskArt_ArtAntalProperty(){return TradBuskArt_ArtAntal;}

        public void setTradBuskArt_ArtAntal(Integer newValue){this.TradBuskArt_ArtAntal.set(newValue);}


        public String getBlomRikTistelv() {
            return BlomRikTistelv.get();
        }

        public SimpleStringProperty blomRikTistelvProperty() {
            return BlomRikTistelv;
        }

        public void setBlomRikTistelv(String blomRikTistelv) {
            this.BlomRikTistelv.set(blomRikTistelv);
        }

        public String getBlomRikArtv() {
            return BlomRikArtv.get();
        }

        public SimpleStringProperty blomRikArtvProperty() {
            return BlomRikArtv;
        }

        public void setBlomRikArtv(String blomRikArtv) {
            this.BlomRikArtv.set(blomRikArtv);
        }

        public String getBlomRikOvr() {
            return BlomRikOvr.get();
        }

        public SimpleStringProperty blomRikOvrProperty() {
            return BlomRikOvr;
        }

        public void setBlomRikOvr(String blomRikOvr) {
            this.BlomRikOvr.set(blomRikOvr);
        }

        public String getGraminiderTackning() {
            return GraminiderTackning.get();
        }

        public SimpleStringProperty graminiderTackningProperty() {
            return GraminiderTackning;
        }

        public void setGraminiderTackning(String graminiderTackning) {
            this.GraminiderTackning.set(graminiderTackning);
        }

        public String getOrterTackning() {
            return OrterTackning.get();
        }

        public SimpleStringProperty orterTackningProperty() {
            return OrterTackning;
        }

        public void setOrterTackning(String orterTackning) {
            this.OrterTackning.set(orterTackning);
        }

        public String getRisTackning() {
            return RisTackning.get();
        }

        public SimpleStringProperty risTackningProperty() {
            return RisTackning;
        }

        public void setRisTackning(String risTackning) {
            this.RisTackning.set(risTackning);
        }

        public String getOrmbunkarTackning() {
            return OrmbunkarTackning.get();
        }

        public SimpleStringProperty ormbunkarTackningProperty() {
            return OrmbunkarTackning;
        }

        public void setOrmbunkarTackning(String ormbunkarTackning) {
            this.OrmbunkarTackning.set(ormbunkarTackning);
        }

        public String getGramForna() {
            return GramForna.get();
        }

        public SimpleStringProperty gramFornaProperty() {
            return GramForna;
        }

        public void setGramForna(String gramForna) {
            this.GramForna.set(gramForna);
        }

    }
    public TableView getTable() {
        return tableView;
    }   // Returnerar hela tabellen f�r utritning p� stage.
}


