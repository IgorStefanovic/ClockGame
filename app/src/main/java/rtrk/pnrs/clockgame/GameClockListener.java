package rtrk.pnrs.clockgame;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by student on 1.6.2016.
 */
public class GameClockListener extends IGameTimeListener.Stub {
    @Override
    public void onTimeChange(int player, long time) throws RemoteException {
        float hours, mins;
        Game mGame = new Game();
        if(player == GameClockBinder.PLAYER_WHITE_ID) {
            time = mGame.decrementTime(time, 1);
            GameClockBinder.player1time = time;
            hours = MainActivity.player1.getHours();
            mins = MainActivity.player1.getMin();
            mins = mGame.incrementTime((long) mins, 1);
            hours += (float)1/(float)60;
            MainActivity.player1.setTime(hours, mins);
        } else if(player == GameClockBinder.PLAYER_BLACK_ID) {
            time = mGame.decrementTime(time, 1);
            GameClockBinder.player2time = time;
            hours = MainActivity.player2.getHours();
            mins = MainActivity.player2.getMin();
            mins = mGame.incrementTime((long)mins, 1);
            hours += (float)1/(float)60;
            MainActivity.player2.setTime(hours, mins);
        }
    }

    @Override
    public void onTimesUp(int player) throws RemoteException {
        if(player == GameClockBinder.PLAYER_WHITE_ID) {
            if(GameClockBinder.player1time == 0) {
                MainActivity.player1lose.performClick();
            }
        } else if(player == GameClockBinder.PLAYER_BLACK_ID) {
            if(GameClockBinder.player2time == 0) {
                MainActivity.player2lose.performClick();
            }
        }
    }
}
