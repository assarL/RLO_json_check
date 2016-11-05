package sample;

/**
 * Created by Assar on 2015-10-21.
 */
public class KultSkikt {

    String HavdTyp;
    String HavdStatus;
    String MarkStorning;
    String DeponiTyp;
    String RojningsTid;

    public String getHavdTyp() {
        return HavdTyp;
    }

    public void setHavdTyp(String havdTyp) {
        HavdTyp = havdTyp; System.out.println("[KultSkikt] Hävdtyp satt till:"+HavdTyp);
    }

    public String getHavdStatus() {
        return HavdStatus;
    }

    public void setHavdStatus(String havdStatus) {
        HavdStatus = havdStatus;
    }

    public String getMarkStorning() {
        return MarkStorning;
    }

    public void setMarkStorning(String markStorning) {
        MarkStorning = markStorning;
    }

    public String getDeponiTyp() {
        return DeponiTyp;
    }

    public void setDeponiTyp(String deponiTyp) {
        DeponiTyp = deponiTyp;
    }

    public String getRojningsTid() {
        return RojningsTid;
    }

    public void setRojningsTid(String rojningsTid) {
        RojningsTid = rojningsTid;
    }
}
