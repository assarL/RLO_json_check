package sample;

/**
 * Created by Assar on 2015-11-08.
 */
public class Etablering {

    protected String fotoURL;
    protected String Tillganglighet;
    protected String MarkUtFalt;
    protected String MarkUtFlyg;
    protected Boolean Centrumflytt;
    protected Integer FlyttX;
    protected Integer FlyttY;
    public String OrsakText;



    public Etablering() {
    }


    public Etablering(String foto, String tillganglighet, Boolean centrumflytt, Integer flyttX, Integer flyttY,String MarkslagFlyg,String MarkslagFalt,String Orsak) {
        fotoURL = foto;
        Tillganglighet = tillganglighet;
        Centrumflytt = centrumflytt;
        FlyttX = flyttX;
        FlyttY = flyttY;
        MarkUtFlyg = MarkslagFlyg;
        MarkUtFalt = MarkslagFalt;
        OrsakText = Orsak;
    }

    public String getFoto(){

        return fotoURL;
    }

    public void setFotoURL(String newURL){

        fotoURL = newURL;
    }

    public String getTillganglighet() {

        return Tillganglighet;}

    public void setTillganglighet(String tillganglighet) {

        Tillganglighet = tillganglighet;
    }

    public Boolean getCentrumflytt() {

        return Centrumflytt;
    }

    public void setCentrumflytt(Boolean centrumflytt) {

        Centrumflytt = centrumflytt;
    }

    public Integer getFlyttX() {

        return FlyttX;
    }

    public void setFlyttX(Integer flyttX) {
        FlyttX = flyttX;
    }

    public Integer getFlyttY() {
        return FlyttY;
    }

    public void setFlyttY(Integer flyttY) {
        FlyttY = flyttY;
    }

    public String getMarkUtFalt() {

        return MarkUtFalt;
    }

    public void setMarkUtFalt(String markslagUndertypFalt) {
        System.out.println("[Etablering]SetMarkslagUndertypFalt anropad med värdet: "+markslagUndertypFalt);
        MarkUtFalt = markslagUndertypFalt;
    }

    public String getMarkUtFlyg() {

        return MarkUtFlyg;
    }

    public void setMarkUtFlyg(String markslagUndertypFlyg) {

        MarkUtFlyg = markslagUndertypFlyg;
    }

    public String getOrsakText() {
        return OrsakText;
    }

    public void setOrsakText(String orsakText) {
        OrsakText = orsakText;
    }
}
