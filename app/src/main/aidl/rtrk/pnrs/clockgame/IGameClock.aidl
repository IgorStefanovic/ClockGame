// IGameClock.aidl
package rtrk.pnrs.clockgame;

import rtrk.pnrs.clockgame.IGameTimeListener;
// Declare any non-default types here with import statements

interface IGameClock {
    void start(long time, IGameTimeListener listener);
    void stop();
    void turn();
    long getTime(int player);
}
