package sample;

/**
 * Created by Assar on 2015-04-14.
 */

public class CoordinateReferensSystem {

    private String type;
    public CRSProperties properties;


    CoordinateReferensSystem(){

        type = "name";
        properties = new CRSProperties();
    }

    public void setType(String newType){

        type = newType;
    }

    public String getType(){

        return type;

    }

}

