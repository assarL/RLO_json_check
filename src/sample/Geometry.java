package sample;

/**
 * Created by Assar on 2015-04-14.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

// Klassen ?r basklass f?r alla typer av placerade objekt.

public class Geometry implements ErrorMessage {

    protected String type = "Feature"; // S?tts alltid till "Feature" men kan �ndras av set-funktionen, anv?nds i json-filen
    protected String name;
    protected String timeStamp; //Datum vid inventeringstillf�llet
    protected String objID;
    protected String invAr;
    protected JSONObject crs;
    protected String rutaID; // S?tts till noll initialt
    protected String rutaNilsID;
    protected String globalID;
    protected String author;  // Vem som har inventerat
    protected ImageIcon Icon;
    protected JSONObject associatedProperties;
    protected JSONObject associatedGeometry;
    protected JSONArray coordinates;
    protected String MarkUtFlyg;
    protected String gisTyp;
    protected String fixedGID;


    public Geometry() {  // Tom konstruktor, v�rdena s�tts enbart med set och get.

    }

    //private JSONObject integratedVariables;

    public Geometry(JSONObject associatedGeometry, JSONObject assProperties) {
        associatedProperties = assProperties;
        System.out.println("[i Geometry.class]Detta objekt �r en: " + associatedGeometry.get("type"));
        System.out.println("[i Geometry.class] Konstruktor i Geometry-klassen anropad med associatedGeometry:" + associatedGeometry.toString() + "\noch associatedProperties:" + associatedProperties.toString());

        System.out.println("[i Geometry.class] Properties (JSONObject) inneh�ller:" + associatedProperties.toString());
        System.out.println("[i Geometry.class] Variabeln gisTyp har v�rdet:" + associatedProperties.get("GISTYP"));
        System.out.println("[i Geometry.class] Variabeln invAr har v�rdet:" + associatedProperties.get("INVENTAR"));
        System.out.println("[i Geometry.class] Variabeln rutaID har v�rdet:" + associatedProperties.get("RUTA"));
        System.out.println("[i Geometry.class] Variabeln rutaNilsID har v�rdet:" + associatedProperties.get("RUTANILS"));
        System.out.println("[i Geometry.class] Variabeln fixedGID har v�rdet:" + associatedProperties.get("FIXEDGID"));
        System.out.println("[i Geometry.class] Variabeln fotoURL har v�rdet:" + associatedProperties.get("Foto"));
        System.out.println("[i Geometry.class] Variabeln author har v�rdet:" + associatedProperties.get("AUTHOR"));


        try {
            gisTyp = (String) associatedProperties.get("GISTYP");// TODO Denna borde plockas ut en gång i början för alla typer och inte om igen.
            //if (gisTyp==null){gisTyp=(String)associatedProperties.get("gistyp");}
            invAr = String.valueOf(associatedProperties.get("INVENTAR"));
            rutaID = String.valueOf(associatedProperties.get("RUTA"));  // ValueOf används för att detta är ett numeriskt värde i JSON.
            rutaNilsID = String.valueOf(associatedProperties.get("RUTANILS"));
            if (rutaNilsID=="null") {
                System.out.println("[Geometry] Ajdå, inte en NILS-ruta (rutaNilsID= " + rutaNilsID);
            } else {System.out.println("Detta är också en NILS-ruta med id= "+ rutaNilsID);}
            objID = String.valueOf(associatedProperties.get("OBJECTID"));
            fixedGID = (String) associatedProperties.get("FIXEDGID");
            author = (String) associatedProperties.get("author");
            coordinates = (JSONArray) associatedGeometry.get("coordinates"); // Observera att koordinaterna tas fr�n ett annat objekt (associatedGeometry) �n �vriga (associatedProperties).
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage("Konstruktor i Geometry]. Undantag fångat vid hämtande av variabelvärden i Geometry. Aktuellt objekt är: " + this.fixedGID);
            System.out.println("[Geometry,Konstruktor]. Undantag vid hämtning av variabelvärden. Aktuellt objekt är: " + this.fixedGID);
            setErrorMessage("[Geometry,Konstruktor]. Undantag vid hämtning av variabelvärden");
            e.printStackTrace();
        }
        System.out.println("------------------------Geometrykonstruktor påbörjas-------------------------------");
/*
        if (gisTyp==null) {  // Om varken GISTYP eller gistyp finns.
            setGisTyp("saknas");
        }
        else {
            setGisTyp(gisTyp);
        }*/
        System.out.println("\n[Geometry] Gistyp har värdet: " + this.getGisTyp());

        if (invAr == null) {
            setInvAr("saknas");
        } else {
            setInvAr(invAr);
        }
        System.out.println("[Geometry] InvAr:" + invAr);

        if (rutaID == null) {
            setRutaID("saknas");

        } else {
            setRutaID(rutaID);
        }
        System.out.println("[Geometry] rutaID: " + getRutaID());

        if (rutaNilsID == null) {
            setRutaNilsID("saknas");

        } else {
            setRutaNilsID(rutaNilsID);
        }
        System.out.println("[Geometry] rutaNilsID:" + getRutaNilsID());

        if (objID == null) {
            setObjID("saknas");
        } else {
            setObjID(objID);
        }
        System.out.println("[Geometry] objID:" + objID);

        if (fixedGID == null) {
            setFixedGID("saknas");
            System.out.println("FIXEDGID saknas, korrekt om objektet inte har formulär kopplat");
        } else {
            setFixedGID(fixedGID);
        }
        System.out.println("[Geometry] fixedGID:" + fixedGID);

        if (author == null) {
            setEmployee("saknas");

        } else {
            setEmployee(author);

        }
        System.out.println("[Geometry] author: " + author);

        if (coordinates == null) {
            setCoordinates(new JSONArray());
        } else {
            setCoordinates(coordinates);
        }
        System.out.println("[Geometry] coordinates:" + coordinates.toString());
        System.out.println("-------------------------Geometry konstruktorn lämnas--------------------------");

    }


    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public void setErrorMessage(String newMessage) {

    }

    public void setObjID(String newObjID) {

        objID = newObjID;
    }

    public String getObjID() {

        return objID;
    }

    public void setName(String newName) {

        name = newName;
    }

    public String getName() {

        return name;
    }

    public void setType(String newType) {

        type = newType;
    }

    public String getType() {

        return type;
    }

    public void setCRS(JSONObject newCRS) {

        crs = newCRS;
    }

    public JSONObject getCrs() {

        return crs;
    }

    public void setRutaID(String newRutaID) {
        System.out.println("setRutaID anropad med:" + newRutaID);
        rutaID = newRutaID;
    }

    public String getRutaID() {

        return rutaID;
    }

    public String getRutaNilsID() {
        return rutaNilsID;
    }

    public void setRutaNilsID(String rutaNilsID) {
        this.rutaNilsID = rutaNilsID;
    }

    public void setFixedGID(String newUID) {

        this.fixedGID = newUID;
    }

    public String getFixedGID() {

        return fixedGID;
    }

    public void setEmployee(String newEmployee) {

        this.author = newEmployee;
    }

    public String getEmployee() {

        return author;
    }


    public void setInvAr(String newInvAr) {

        invAr = newInvAr;
    }

    public String getInvAr() {

        return invAr;
    }

    public void setGisTyp(String newGisTyp) {

        this.gisTyp = newGisTyp;
    }

    public String getGisTyp() {

        return gisTyp;
    }

    public void setCoordinates(JSONArray newCoordinates) {
        coordinates = newCoordinates;
    }

    public JSONArray getCoordinates() {

        return coordinates;
    }

    public String getFormattedTime() {
        String StartTid = "tid från Geometry";

        return StartTid;
    }

}



