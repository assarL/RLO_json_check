package sample;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by assarl on 2016-08-01.
 */
public class ArtReg {  //Klassen skapar artobjekt bestående av art och tidsstämpel

    private String Art;
    private Long RegistreringsTid;


    public ArtReg(String art,Long regTid) {
        Art = art;
        RegistreringsTid = regTid;


        System.out.println("[ArtReg] Ny art tillagd: "+art+"\n registrerad millisekund:"+ RegistreringsTid);
    }

    public ArtReg(String art) { // Konstruktor där tiden är okänd (sattes inte i början).
        Art = art;
        RegistreringsTid = -1321006271111L;  // Tiden sätts till 11/11/11 11:11:11:111


        System.out.println("[ArtReg] Ny art tillagd: "+art+"\n registrerad millisekund:"+ RegistreringsTid);
    }

    public String getArt() {

        return Art;
    }

    public void setArt(String art) {

        Art = art;
    }


    public Long getRegistreringsTid() {

        return RegistreringsTid;
    }

    public void setRegistreringsTid(Long newTid){

        RegistreringsTid = newTid;
    }

    public void setAntal(){

    }

}
