// IGameTimeListener.aidl
package rtrk.pnrs.clockgame;

// Declare any non-default types here with import statements

interface IGameTimeListener {

    void onTimeChange(int player, long time);
    void onTimesUp(int player);
}
