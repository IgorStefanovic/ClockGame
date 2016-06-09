package rtrk.pnrs.clockgame;

/**
 * Created by Igor1 on 09/06/2016.
 */
public class Game {
    public static native long incrementTime(long time, long add);
    public static native long decrementTime(long time, long add);

    static {
        System.loadLibrary("game");
    }
}
