package rtrk.pnrs.clockgame;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by student on 1.6.2016.
 */
public class GameClockService extends Service {

    private GameClockBinder mGameClockBinder;

    @Override
    public IBinder onBind(Intent intent) {
        if (mGameClockBinder == null) {
            mGameClockBinder = new GameClockBinder();
        }
        return mGameClockBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        try {
            mGameClockBinder.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return super.onUnbind(intent);
    }
}
