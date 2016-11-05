package sample;

/**
 * Created by Assar on 2015-10-19.
 */
public class TradTacke {

    //TotTradBu3m,TotTradBu7m,TotTradBuVuxna,TotTradBu1m:

    private String TotTradBu1m;// T�ckningsgraderna indelade i klasser fr�n 1-4%,5-10%,11-30%,30-60%,>60% [1,2,3,4,5]
    private String TotTradBu3m;
    private String TotTradBu7m;
    private String TotTradBuVuxna;
    private String TraBuFlyg;


    public TradTacke() {

        TotTradBu1m="";
        TotTradBu3m="";
        TotTradBu7m="";
        TotTradBuVuxna="";
        TraBuFlyg = "";
    }

    public TradTacke(String Trad1m, String Trad3m, String Trad7m, String TradVux) {  // S�tt alla i ett svep

        if (Trad1m == null) {
            setTotTradBu1m("-");
        } else {
            setTotTradBu1m(Trad1m);
        }

        if (Trad3m == null) {
            setTotTradBu3m("-");
        } else {
            setTotTradBu3m(Trad1m);
        }

        if (Trad7m == null) {
            setTotTradBu7m("-");
        } else {
            setTotTradBu7m(Trad7m);
        }

        if (TradVux == null) {
            setTotTradBuVuxna("-");
        } else {
            setTotTradBuVuxna(Trad1m);
        }
        if (TraBuFlyg == null) {
            setTraBuFlyg("-");
        } else {
            setTotTradBuVuxna(Trad1m);
        }
    }


    public void setTotTradBu1m(String newTackning1m)
    {
        TotTradBu1m = newTackning1m;
    }
    public void setTotTradBu3m(String newTackning3m)
    {
        TotTradBu3m = newTackning3m;
    }
    public void setTotTradBu7m(String newTackning7m)
    {
        TotTradBu7m = newTackning7m;
    }
    public void setTotTradBuVuxna(String newTackningVuxna)
    {
        TotTradBuVuxna = newTackningVuxna;
    }
    public void setTraBuFlyg(String newTraBuFlyg){
        TraBuFlyg = newTraBuFlyg;
    }

    public String getTotTradBu1m() {
        return TotTradBu1m;
    }

    public String getTotTradBu3m() {
        return TotTradBu3m;
    }

    public String getTotTradBu7m() {
        return TotTradBu7m;
    }

    public String getTotTradBuVuxna() {
        return TotTradBuVuxna;
    }

    public String getTraBuFlyg(){
        return TraBuFlyg;
    }
}