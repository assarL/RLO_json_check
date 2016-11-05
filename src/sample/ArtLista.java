package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by Assar on 2015-10-11.
 */
public class ArtLista {

    public int artRegistreringar = 0;  // Totala antalet artregistreringar i sm�provytorna (oavsett dubletter)

    ArrayList<ArtReg> Arter;

    public ArtLista(){

        Arter = new ArrayList<ArtReg>();
    }


    protected void addArt(ArtReg newArt){

        Arter.add(newArt);
        System.out.println("[ArtLista]Art lagd till artlistan: "+newArt.getArt());
        artRegistreringar++;

    }

    public ArrayList<ArtReg> getArtLista(){

        return Arter;
    }

    public void setArtLista(ArrayList<ArtReg> ArtLista){  // Tar emot en färdig arraylista, ingen omvandling.
        Arter = ArtLista;
    }






    //TODO  Skapa en totalartlista �ver sm�provytorna. En funktion "get TotalArtLista()","getFrequency(?) som ordnar dem i vanlighetsgrad"; getTorrPoints?, getKalkPoints?

}
