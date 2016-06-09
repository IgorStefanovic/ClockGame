package rtrk.pnrs.clockgame;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by student on 1.6.2016.
 */
public class GameClockBinder extends IGameClock.Stub {

    public static final int PLAYER_WHITE_ID = 1;
    public static final int PLAYER_BLACK_ID = 2;

    public static long player1time;
    public static long player2time;
    private boolean player1active = false;


    private IGameTimeListener mListener;
    private GameClockCallbackCaller mGameClockCallbackCaller;

    @Override
    public void start(long time, IGameTimeListener listener) throws RemoteException {

        player1time = time;
        player2time = time;
        mListener = listener;
        mGameClockCallbackCaller = new GameClockCallbackCaller();
        mGameClockCallbackCaller.start();
    }

    @Override
    public void stop() throws RemoteException {
        mGameClockCallbackCaller.stop();
    }

    @Override
    public void turn() throws RemoteException {
        /* Maybe I don't need it, we'll see. */
        if(!player1active) {
            player1active = true;
        } else {
            player1active = false;
        }
    }

    @Override
    public long getTime(int player) throws RemoteException {
        /* Maybe I don't need it, we'll see. */
        if(player == PLAYER_WHITE_ID) {
            return player1time;
        } else if(player == PLAYER_BLACK_ID) {
            return player2time;
        }
        return -1;
    }

    private class GameClockCallbackCaller implements Runnable {

        private static final long PERIOD = 1000L;

        private Handler mHandler = null;
        private boolean mRun = true;

        public void start() {
            mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(this, PERIOD);
        }

        public void stop() {
            mRun = false;
            mHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
            if (!mRun) {
                return;
            }

            if(MainActivity.player1.isEnabled() == true) {
                try {
                    mListener.onTimesUp(PLAYER_WHITE_ID);
                    mListener.onTimeChange(PLAYER_WHITE_ID, player1time);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if(MainActivity.player2.isEnabled() == true) {
                try {
                    mListener.onTimeChange(PLAYER_BLACK_ID, player2time);
                    mListener.onTimesUp(PLAYER_BLACK_ID);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mHandler.postDelayed(this, PERIOD);
        }
    }
}
