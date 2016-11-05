package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by Assar on 2015-07-27.
 */
public class BasTabell {


    protected TableView<TableRow> table;


    protected ObservableList<TableRow> data = FXCollections.observableArrayList(); // OBS! att det �r en observableArrayList...



    public BasTabell(){

        createColumn();
        }

    public BasTabell(HashMap geometries, Set nycklar){          // Konstruktor för en Hash-map. Används vid tolkade GeoJson-filer


        TableRow[] toObservableList = new TableRow[nycklar.size()];

        int i=0;

        for (Object obj : nycklar){

            Geometry oneRow = (Geometry)geometries.get(obj);


        //System.out.println("[BasTabell] oneRow:"+oneRow.toString());
        String fixedGID = oneRow.getFixedGID();
        String coordinates = oneRow.getCoordinates().toString();
        String ruta = oneRow.getRutaID();
        String rutaNils = oneRow.getRutaNilsID();
        String objectID = oneRow.getObjID();
        String type = oneRow.getType();
        String inventerare = oneRow.getEmployee();
        String gisTyp = oneRow.getGisTyp();
        String invAr = oneRow.getInvAr();
        String geometryClass = oneRow.getType();
        String datum = oneRow.getFormattedTime();//oneRow.getDatum;
        String viewMe = "999";

            toObservableList[i] = new TableRow(ruta,rutaNils,fixedGID,objectID,gisTyp,coordinates,type,inventerare,invAr,geometryClass,datum,viewMe);
            i++;
        }
        data.clear();

        data.addAll(toObservableList);

        createColumn();

        table.setItems(data);
    }



    public BasTabell(Geometry[] geometries) {             // Konstruktor för en Geometry-lista.

         TableRow[] toObservableList = new TableRow[geometries.length];  // En array f�rbereds f�r att lagra objekten i datamodellen.



        for(int i=0;i<geometries.length;i++){

            Geometry oneRow = geometries[i]; // Plockar ut en rad med data

            System.out.println("[BasTabell] oneRow:"+oneRow.toString());
            String ruta = oneRow.getRutaID();
            String rutaNils = oneRow.getRutaNilsID();
            String fixedGID = oneRow.getFixedGID();
            String coordinates = oneRow.getCoordinates().toString();

            String objectID = oneRow.getObjID();
            String type = oneRow.getType();
            //String foto = oneRow.getFoto();
            String inventerare = oneRow.getEmployee();
            String gisTyp = oneRow.getGisTyp();
            String invAr = oneRow.getInvAr();
            String geometryClass = oneRow.getType();
            String datum = oneRow.getFormattedTime();
            String viewMe = "999";

        toObservableList[i] = new TableRow(ruta,rutaNils,fixedGID,objectID,gisTyp,coordinates,type,inventerare,invAr,geometryClass,datum,viewMe);

            System.out.println("[BasTabell]"+fixedGID+":"+coordinates+":"+ruta+":"+objectID+":"+type+":"+inventerare+":"+gisTyp+":"+invAr+":"+geometryClass+":"+viewMe+"\n\n");
        }
        data.clear();

        data.addAll(toObservableList);
        createColumn();

        table.setItems(data);
                                                              // Skapa kolumnerna
    }

    private TableView createColumn(){

        System.out.println("createColumn aropad");

        table = new TableView(); // Sj�lva tabellen

        table.setEditable(true);
        TableColumn rutaKolumn = new TableColumn("RUTA");rutaKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("ruta"));
        TableColumn rutaNilsKolumn = new TableColumn("RUTANILS");rutaNilsKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("rutaNils"));
        TableColumn FixedGIDKolumn = new TableColumn("FIXEDGID");FixedGIDKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("FixedGID"));
        FixedGIDKolumn.setPrefWidth(320);
        TableColumn objectIDKolumn = new TableColumn("OBJECTID");objectIDKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("objID"));
        TableColumn gisTypKolumn = new TableColumn("GISTYP");gisTypKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("gisTyp"));  //lansgr�s, akerholme etc..
        TableColumn coordinatesKolumn = new TableColumn("Coordinates");coordinatesKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("Coordinates"));
        coordinatesKolumn.setPrefWidth(100);

        TableColumn typeKolumn = new TableColumn("Typ");typeKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("type"));
        TableColumn inventerareKolumn = new TableColumn("Inventerare");inventerareKolumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("inventerare"));
        TableColumn invArKolumn = new TableColumn("Inventerings�r");invArKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("invAr"));
        TableColumn geometryShapeKolumn = new TableColumn("Geometriklass");geometryShapeKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("geometryClass"));
        TableColumn datumKolumn = new TableColumn("Datum/Tid");datumKolumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("datum"));
        TableColumn viewMeColumn = new TableColumn("Link");viewMeColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("viewMe"));


        table.getColumns().addAll(rutaKolumn,rutaNilsKolumn,FixedGIDKolumn,objectIDKolumn,gisTypKolumn,coordinatesKolumn,typeKolumn,inventerareKolumn,invArKolumn,geometryShapeKolumn,datumKolumn,viewMeColumn);  // L�gg till kolumner

        return table;

    }

    public TableView getTable() {
        return table;
    }   // Returnerar hela tabellen f�r utritning p� stage.

//-----------------------------------------------------------
    /*private class ButtonCell extends TableCell<, Boolean> {

        final Button cellButton = new Button("View");

        ButtonCell(){

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // do something when button clicked
                    //...
                }
            });
        }}*/
//---------------------------------------------------------------------
    public static class TableRow{  // Datamodellen f�r tabellen skapas h�r

        private final SimpleStringProperty ruta;
        private final SimpleStringProperty rutaNils;
        private final SimpleStringProperty fixedGID;
        private final SimpleStringProperty objectID;
        private final SimpleStringProperty gisTyp;
        private final SimpleStringProperty coordinates;
        private final SimpleStringProperty type;
    //private final SimpleStringProperty foto;
        private final SimpleStringProperty inventerare;
        private final SimpleStringProperty invAr;
        private final SimpleStringProperty geometryShape;
        private final SimpleStringProperty viewMe;
        private final SimpleStringProperty datum; // Jag utgår tills vidare från att vi inte vill ändra datum i detta program. Annars kan ett Date-objekt vara lämpligare.


        public TableRow(String ruta, String rutaNils, String fixedGID, String objectID, String gisTyp, String coordinates, String type, String inventerare, String invAr, String geometryClass, String datum, String viewMe) {       // Kapslar in variablerna som properties f�r visning i tabellen.

                // F�rst g�r vi om f�lten/variablerna till properties
            this.ruta = new SimpleStringProperty(ruta);
            this.rutaNils = new SimpleStringProperty(rutaNils);
            this.fixedGID = new SimpleStringProperty(fixedGID);
            this.coordinates = new SimpleStringProperty(coordinates);
            this.type = new SimpleStringProperty(type);
            this.objectID = new SimpleStringProperty(objectID);
            this.inventerare = new SimpleStringProperty(inventerare);
            this.gisTyp = new SimpleStringProperty(gisTyp);
            this.invAr = new SimpleStringProperty(invAr);  // Obs Integer
            this.geometryShape = new SimpleStringProperty(geometryClass);
            this.datum = new SimpleStringProperty(datum);
            this.viewMe = new SimpleStringProperty(viewMe);
        }

        // Och d� m�ste vi ocks� skapa setters, getters och properties enligt konventionen..

        public String getFixedGID(){

            return  fixedGID.get();
        }

        public void setFixedGID(String newValue){

            fixedGID.set(newValue);
        }

        public final StringProperty fixedGIDProperty(){

            return fixedGID;
        }

        public String getCoordinates(){

            return  coordinates.get();
        }

        public void setCoordinates(String newValue){

            coordinates.set(newValue);
        }

        public final StringProperty coordinatesProperty(){

            return coordinates;
        }

        public String getRuta(){

            return  ruta.get();
        }

        public void setRuta(String newValue){

            ruta.set(newValue);
        }

        public final StringProperty rutaProperty(){

            return ruta;
        }

        public String getRutaNils(){

        return  rutaNils.get();
        }

        //------
        public void setRutaNils(String newValue){

            rutaNils.set(newValue);
        }

        public final StringProperty rutaNilsProperty(){

        return rutaNils;
        }

        public String getType(){

            return type.get();
        }

    //-----
        public void setType(String newValue){

            type.set(newValue);
        }

        public final StringProperty typeProperty(){

            return type;
        }

        public String getObjID(){

            return objectID.get();
        }

        public void setObjID(String newValue){

            objectID.set(newValue);
        }

        public final StringProperty objIDProperty(){

            return objectID;
        }

        public String getInventerare(){

            return inventerare.get();
        }

        public void setInventerare(String newValue){

            inventerare.set(newValue);
        }

        public final StringProperty inventerarProperty(){

            return inventerare;
        }

        public String getGisTyp(){

            return gisTyp.get();
        }

        public void setGisTyp(String newValue){

            gisTyp.set(newValue);
        }

        public final StringProperty gisTypProperty(){

            return gisTyp;
        }

        public String getInvAr(){

            return invAr.get();
        }

        public void setInvAr(String newValue){

            invAr.set(newValue);
        }

        public final StringProperty invArProperty(){

            return invAr;
        }

        public String getGeometryClass(){

            return geometryShape.get();
        }

        public void setGeometryClass(String newValue){

            geometryShape.set(newValue);
        }

        public final StringProperty geometryClassProperty(){

            return geometryShape;
        }
//

        public String getDatum(){

            return datum.get();
        }

        public void setDatum(String newDatum){

            datum.set(newDatum);
        }

        public final StringProperty datumProperty(){

            return datum;
        }

//
        public String getViewMe(){

            return viewMe.get();
        }

        public void setViewMe(String newValue){

            viewMe.set(newValue);
        }

        public final StringProperty viewMeProperty(){

            return viewMe;
        }


    }


}
