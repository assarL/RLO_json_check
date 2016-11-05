package sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by assarl on 2016-08-01.
 */
public class ArtProvYta {


    private String pyID;
    private ArtLista pyArter = new ArtLista();  // Delegerar småprovytans artlista till klassen ArtLista

public ArtProvYta(){ // Tom konstruktor

}

    public ArtProvYta(String pyID) {

        this.pyID = pyID;

    }
    public ArtProvYta(String pyID,ArtLista pyArter) {

        this.pyID = pyID;
        this.pyArter= pyArter;

    }


    public ArrayList<ArtReg> getArtLista() {

        return pyArter.getArtLista(); // Delegerat till Artlista, hämtar arterna som en lista av typen ArtReg.

    }

    public void setArtLista(String pyID,ArrayList<ArtReg> ArtLista){
        this.pyID = pyID;
        pyArter.setArtLista(ArtLista);
    }

    protected void addArt(ArtReg nyArt) {

        pyArter.addArt(nyArt);

    }

    protected void setPyID(String pyID) {

        this.pyID = pyID;

    }

    public String getPyID() {

        return pyID;
    }





}
