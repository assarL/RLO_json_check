package sample;

/**
 * Created by Assar on 2015-04-15.
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;
import java.util.Arrays;


public class TableOfObjects {



    private TableView<GeometryInstance> table = new TableView();
    private final ObservableList<GeometryInstance> dataList = FXCollections.observableArrayList();


    Integer objID;
    String gTyp;
    String invent;
    String gUUID;
    Integer rutaID;

      public TableOfObjects() {  // Tom konstruktor för att initialisera tabellen med "Geometry"-rubriker innan datakälla har valts.

          System.out.println("Observable-listan efter loop har längden:" + dataList.size());

          table.setEditable(true);

        TableColumn firstColumn = new TableColumn("Object ID");
        firstColumn.setMinWidth(100);
        firstColumn.setCellValueFactory(new PropertyValueFactory<GeometryInstance, Integer>("objectID")); // ("objectID") är (i detta fall) nyckeln till att fylla tabellen mha funktionerna i datamodellen "GeometryInstance". get- och set- ska ha identiska namn i datamodellen.

        TableColumn secondColumn = new TableColumn("Geometrisk typ");
        secondColumn.setMinWidth(100);
        secondColumn.setCellValueFactory(new PropertyValueFactory<GeometryInstance, String>("objectTyp"));

        TableColumn thirdColumn = new TableColumn("Inventerare");
          thirdColumn.setMinWidth(100);
        thirdColumn.setCellValueFactory(new PropertyValueFactory<GeometryInstance, String>("inventerare"));
          thirdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
          thirdColumn.setOnEditCommit(e -> inventerare_OnEditCommit(e));


        TableColumn fourthColumn = new TableColumn("UUID");
        fourthColumn.setMinWidth(270);
        fourthColumn.setCellValueFactory(new PropertyValueFactory<GeometryInstance, String>("UUID"));

        TableColumn fifthColumn = new TableColumn("rutaID");
                    fifthColumn.setMinWidth(100);
        fifthColumn.setCellValueFactory(new PropertyValueFactory<GeometryInstance, Integer>("rutaID"));



        table.getColumns().addAll(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn);
        table.setPlaceholder(new Label("No Content In List yet"));         // Innan något data har valts, genererar bara text.
    }



    public TableView newTable(Geometry[] geometries) {

        GeometryInstance[] toObservableList = new GeometryInstance[geometries.length]; // Initialisera listan med geometrilistans längd. Arrayen innehåller instanser av datamodellen.

        dataList.removeAll(dataList);  // Rensar Observable-listan (tror jag).

        System.out.println("Observable-listan före loop har längden:" + dataList.size());

//-----------------------loopar igenom data

       for(int i=0;i<geometries.length;i++){

           Geometry oneRow = geometries[i];
           System.out.println("oneRow.getObjID():  " + oneRow.getObjID());
           System.out.println("oneRow.getRutaID:  " + oneRow.getRutaID());

            objID = Integer.valueOf(oneRow.getObjID()); // Omvandla till siffra för logisk sortering i tabelllen.
            gTyp = oneRow.getType();
            invent = oneRow.getEmployee();
            gUUID = oneRow.getFixedGID();
            rutaID = Integer.valueOf(oneRow.getRutaID()); // Omvandla ruta-ID tillfälligt för sortering på ruta

            toObservableList[i] = new GeometryInstance(objID,gTyp,invent,gUUID,rutaID);  // Lista av datamodells-objekt skapas, här skapas instanser för tabell-visandet.
    }
// Slut på loop
        System.out.println("inne i tabell-loopen har rutaID värdet:  " + rutaID.toString());

        dataList.addAll(toObservableList); // Den tillfälliga listan toObservableList är nu "laddad" från datamodellen GeometryInstance och skickas nu för att packas in i "Observable-listan" dataList, Denna ger tillgång till lyssnare för förändringar i tabellen..

        System.out.println("Observable-listan efter loop har längden:" + dataList.size());


        table.setItems(dataList);  // Skicka listan till tabellen som pytsar ut det i sina kolumner.

        return table;
}

        public TableView getTable(){
          // Returnerar hela rasket till Main som placerar det i mitten (borderPane.setCenter)
        table.setMinHeight(800);
        return table;
    }

    public void inventerare_OnEditCommit(Event e){
        TableColumn.CellEditEvent<GeometryInstance, String> ce;
        ce = (TableColumn.CellEditEvent<GeometryInstance, String>) e;   // Casta event till CellEditEvent,ce
        GeometryInstance m = ce.getRowValue();
        m.setInventerare(ce.getNewValue());
        System.out.println("Inventerare ändrad till:"+ce.getNewValue());
    }

    public static class GeometryInstance {
// Properties skapas för att användas i tabellen
        private final SimpleIntegerProperty objectID; // Obs! integer
        private final SimpleStringProperty objectTyp;
        private final SimpleStringProperty inventerare;
        private final SimpleStringProperty UUID;
        private final SimpleIntegerProperty rutaID; // Obs! integer

        private GeometryInstance(Integer oID, String oTyp, String invent, String uUID,Integer rID) {
           System.out.println("GeometryInstance är anropad med: "+oID+":"+oTyp+":"+invent+":"+uUID+":"+rID);
            this.objectID = new SimpleIntegerProperty(oID);
            this.objectTyp = new SimpleStringProperty(oTyp);
            this.inventerare = new SimpleStringProperty(invent);
            this.UUID = new SimpleStringProperty(uUID);
            this.rutaID = new SimpleIntegerProperty(rID);
        }

        public Integer getObjectID(){

            return objectID.get();
        }

        public void setObjectID(Integer objID){

            objectID.set(objID);
        }
        public String getObjectTyp(){

            return objectTyp.get();
        }
        public void setObjectTyp(String objTyp){

            objectTyp.set(objTyp);
        }
        public String getInventerare(){

            return  inventerare.get();
        }
        public  void setInventerare(String invent){

            inventerare.set(invent);
        }
        public String getUUID(){

            return UUID.get();
        }
        public  void setUUID(String uUID){

            UUID.set(uUID);
        }
        public Integer getRutaID(){

            return rutaID.get();
        }
        public void setRutaID(Integer rID){

            rutaID.set(rID);
        }

    }

}




