package sample;

import java.util.ArrayList;

/**
 * Created by Assar on 2015-10-21.
 */
public class TradSkikt {


    private TradTacke TradSkiktsBeskrivning;
    private ArrayList<TradBuskArt> TradArtsLista;
    private int speciesCount=0;
    private String TradBlom;
    private TradTacke TraBuFlyg;

    public TradSkikt(){

        TradSkiktsBeskrivning = new TradTacke(); // Skapar initialt tomma poster märkta som "saknas".
    }


    public void setTradArtsLista(ArrayList<TradBuskArt> tradArtsLista) {
        TradArtsLista = tradArtsLista;
    }


    public void setSpeciesCount(int speciesCount){

        this.speciesCount=speciesCount;

    }
    public void setBlommor(String TradBlom){

        this.TradBlom = TradBlom;

    }

    public void setAllaTradSkikt(TradTacke skiktBeskrivning){

        TradSkiktsBeskrivning = skiktBeskrivning;
    }

    public TradTacke getTradSkiktsBeskrivning() {
        return TradSkiktsBeskrivning;
    } // Returnerar ett TradTacke-Objekt

    public ArrayList<TradBuskArt> getTradArtsLista() {
        return TradArtsLista;
    }

    public int getSpeciesCount() {
        return speciesCount;
    }

    public String getTradBlom() {
        return TradBlom;
    }


}
