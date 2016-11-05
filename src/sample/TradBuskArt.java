package sample;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Assar on 2015-10-19.
 */
public class TradBuskArt {

    String art,antal,medelHojd,tackning;
    Long timeStamp;
    ArrayList ArtLista = new ArrayList<String>();  //Todo: Gör om till en hashmap för att lättare kunna radera arter.

    public TradBuskArt(){
        System.out.println("[TradBuskArt]Tom konstruktor anropad i tr�dbuskart");
        // Tom konstruktor
    }
    public TradBuskArt(String art,String medelHojd, String antal_klassgrupp, String tackning, Long timestamp) { // H�jd, t�ckning,antal
        System.out.println("[TradBuskArt] Konstruktor anropad i tr�dbuskart med:"+art+":"+medelHojd+":"+antal+":"+tackning);
        this.art = art;
        this.medelHojd = medelHojd;  // L�ngd,antal,h�jd,t�ckning
        this.antal = antal_klassgrupp;
        this.tackning = tackning;
        this.timeStamp = timestamp;
        System.out.println("[Provyta] timestamp satt till:"+timeStamp.toString());
    }



    protected void addToArtLista(String newArt){

        ArtLista.add(newArt);
        System.out.println("[TradBusk]Ny träd- buskart lagd till objekt:"+ newArt);
    }

    public ArrayList getArtLista() {
        return ArtLista;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {this.art = art;}

    public String getMedelHojd() {
        return medelHojd;
    }

    public void setMedelHojd(String medelHojd) {
        this.medelHojd = medelHojd;
    }

    public String getAntal() {
        return antal;
    }

    public void setAntal(String antal) {
        this.antal = antal;
    }

    public String getTackning() {
        return tackning;
    }

    public void setTackning(String tackning) {
        this.tackning = tackning;
    }

    public void setTimeStamp(Long ts){this.timeStamp = ts;}

    public Long getTimeStamp(){return timeStamp;}
}
