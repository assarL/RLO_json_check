package sample;

/**
 * Created by Assar on 2015-10-21.
 */
public class StorArtSkikt {
    String art;
    String storArtTackning;

    public StorArtSkikt(String art, String storArtTackning) {
        this.art = art;
        this.storArtTackning = storArtTackning;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getStorArtTackning() {
        return storArtTackning;
    }

    public void setStorArtTackning(String storArtTackning) {
        storArtTackning = storArtTackning;
    }
}
