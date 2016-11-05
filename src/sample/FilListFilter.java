package sample;

/**
 * Created by Assar on 2015-04-13.
 */
import java.io.File;
import java.io.FilenameFilter;


public class FilListFilter implements FilenameFilter {

    private String namn;
    private String tillagg;

    public FilListFilter(String namn,String tillagg){ // Konstruktor f�r b�rjar med "namn" och har fil�ndelse "tillagg"

        this.namn = namn;
        this.tillagg = tillagg;

    }

    public FilListFilter(String tillagg){ //Konstruktor f�r endast filtill�gg

        this.namn = "";
        this.tillagg = tillagg;

    }


    public boolean accept(File dir, String filnamn) {

        boolean filOK = true;

        if (namn!=null){
            filOK &= filnamn.startsWith(namn);
        }

        if (tillagg != null){
            filOK &= filnamn.endsWith('.'+tillagg);
        }

        return filOK;
    }

}

