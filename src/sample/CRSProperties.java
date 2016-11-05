package sample;

/**
 * Created by Assar on 2015-04-14.
 */

public class CRSProperties {
    private String name;

    CRSProperties(){  // defaultkonstruktor "name". I normalfallet för oss "EPSG:3006"

        name = "";
    }

    public void setName(String newName){

        name = newName;

    }

    public String getName(){

        return name;

    }

}
