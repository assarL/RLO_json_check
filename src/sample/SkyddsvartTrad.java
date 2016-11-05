package sample;

/**
 * Created by Assar on 2015-10-21.
 */

public class SkyddsvartTrad {

    private String Art;
    private String SkyddsVartTradDiam;

    public SkyddsvartTrad(String art, String skyddsVartTradDiam) {
        Art = art;
        SkyddsVartTradDiam = skyddsVartTradDiam;
    }

    public String getArt() {
        return Art;
    }

    public void setArt(String art) {
        Art = art;
    }

    public String getSkyddsVartTradDiam() {
        return SkyddsVartTradDiam;
    }

    public void setSkyddsVartTradDiam(String skyddsVartTradDiam) {
        SkyddsVartTradDiam = skyddsVartTradDiam;
    }
}
