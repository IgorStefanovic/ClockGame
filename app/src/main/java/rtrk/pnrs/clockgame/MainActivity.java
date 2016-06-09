package rtrk.pnrs.clockgame;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rtrk.pnrs.clockbutton.AnalogClockView;
import rtrk.pnrs.statistics.MyAdapter;
import rtrk.pnrs.statistics.OneItem;
import rtrk.pnrs.statistics.Statistics;

public class MainActivity extends Activity implements ServiceConnection{

    public static AnalogClockView player1;
    public static AnalogClockView player2;
    private Button player1draw;
    private Button player2draw;
    public static Button player1lose;
    public static Button player2lose;
    private Button start;
    private Button setup;
    private Button statistics;
    private TextView player1text;
    private TextView player2text;
    private Boolean player1clickedDraw;
    private Boolean player2clickedDraw;
    private IGameClock mService;
    private GameClockListener mGameClockListener;
    private String whiteTime, blackTime, res;
    private float hours = 12, mins = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        disablePlayers();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initStart();
            }
        });

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPlayers();
            }
        });

        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPlayers();
            }
        });

        player1lose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerLost((Button) v);
                enableSSS();
                disablePlayers();
                setStatisticsLose((Button) v);
            }
        });

        player2lose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerLost((Button) v);
                enableSSS();
                disablePlayers();
                setStatisticsLose((Button) v);
            }
        });

        player1draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player2clickedDraw) {
                    playerDraw((Button) v);
                    enableSSS();
                    disablePlayers();
                    setStatisticsDraw();
                } else {
                    player1clickedDraw = true;
                    switchPlayers();
                }
            }
        });

        player2draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1clickedDraw) {
                    playerDraw((Button) v);
                    enableSSS();
                    disablePlayers();
                    setStatisticsDraw();
                } else {
                    player2clickedDraw = true;
                    switchPlayers();
                }
            }
        });

        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setupIntent = new Intent(MainActivity.this, Setup.class);
                startActivity(setupIntent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsIntent = new Intent(MainActivity.this, Statistics.class);
                startActivity(statisticsIntent);
            }
        });
    }

    void setStatisticsLose(Button b) {
        if(b.equals(player1lose)) {
            res = "Black player won";
        } else if(b.equals(player2lose)) {
            res = "White player won";
        }

        getTime();
        OneItem oi = new OneItem(whiteTime, res, blackTime);
        insert(oi);
    }

    void setStatisticsDraw() {
        res = "Draw";
        getTime();

        OneItem oi = new OneItem(whiteTime, res, blackTime);
        insert(oi);
    }

    void disableSSS() {
        start.setEnabled(false);
        statistics.setEnabled(false);
        setup.setEnabled(false);
    }

    void enableSSS() {
        start.setEnabled(true);
        statistics.setEnabled(true);
        setup.setEnabled(true);
    }

    void playerDraw(Button player) {
        if (player.equals(player1draw)) {
            player2text.setVisibility(View.VISIBLE);
            player2text.setText(R.string.draw);
            player2text.setTextColor(getResources().getColor(R.color.blue));

        } else if (player.equals(player2draw)) {
            player1text.setVisibility(View.VISIBLE);
            player1text.setText(R.string.draw);
            player1text.setTextColor(getResources().getColor(R.color.blue));
        }

        try {
            mService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void playerLost(Button player) {
        if (player.equals(player1lose)) {
            player1text.setVisibility(View.VISIBLE);
            player1text.setText(R.string.lose);
            player1text.setTextColor(getResources().getColor(R.color.red));

            player2text.setVisibility(View.VISIBLE);
            player2text.setText(R.string.win);
            player2text.setTextColor(getResources().getColor(R.color.green));

        } else if (player.equals(player2lose)) {
            player1text.setVisibility(View.VISIBLE);
            player1text.setText(R.string.win);
            player1text.setTextColor(getResources().getColor(R.color.green));

            player2text.setVisibility(View.VISIBLE);
            player2text.setText(R.string.lose);
            player2text.setTextColor(getResources().getColor(R.color.red));
        }

        try {
            mService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void switchPlayers() {
        if (player1.isEnabled() && player1lose.isEnabled() && player1draw.isEnabled()) {
            disablePlayer1();
            enablePlayer2();

        } else if (player2.isEnabled() && player2lose.isEnabled() && player2draw.isEnabled()) {
            enablePlayer1();
            disablePlayer2();
        }
    }

    void enablePlayer1() {
        player1.setEnabled(true);
        player1lose.setEnabled(true);
        player1draw.setEnabled(true);
    }

    void enablePlayer2() {
        player2.setEnabled(true);
        player2lose.setEnabled(true);
        player2draw.setEnabled(true);
    }

    void disablePlayer1() {
        player1.setEnabled(false);
        player1lose.setEnabled(false);
        player1draw.setEnabled(false);
    }

    void disablePlayer2() {
        player2.setEnabled(false);
        player2lose.setEnabled(false);
        player2draw.setEnabled(false);
    }

    void disablePlayers() {
        disablePlayer1();
        disablePlayer2();
    }

    void initPlayer1() {
        player1.setEnabled(true);
        player1draw.setEnabled(true);
        player1lose.setEnabled(true);
    }

    void initPlayer2() {
        player2.setEnabled(false);
        player2draw.setEnabled(false);
        player2lose.setEnabled(false);
    }

    void initStart() {
        initPlayer1();
        initPlayer2();
        disableSSS();
        player1text.setVisibility(View.INVISIBLE);
        player2text.setVisibility(View.INVISIBLE);
        player1clickedDraw = false;
        player2clickedDraw = false;

        player1.setTime(hours + mins / 60, mins);
        player2.setTime(hours + mins / 60, mins);

        try {
            mService.start(90, mGameClockListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    void initViews() {
        player1 = (AnalogClockView) findViewById(R.id.player1buttonClock);
        player2 = (AnalogClockView) findViewById(R.id.player2buttonClock);
        player1draw = (Button) findViewById(R.id.player1draw);
        player2draw = (Button) findViewById(R.id.player2draw);
        player1lose = (Button) findViewById(R.id.player1lose);
        player2lose = (Button) findViewById(R.id.player2lose);
        start = (Button) findViewById(R.id.start);
        setup = (Button) findViewById(R.id.setup);
        statistics = (Button) findViewById(R.id.statistics);
        player1text = (TextView) findViewById(R.id.player1text);
        player2text = (TextView) findViewById(R.id.player2text);

        mGameClockListener = new GameClockListener();

        player1.setTime(hours + mins / 60, mins);
        player2.setTime(hours + mins / 60, mins);

        Intent intent = new Intent(this, GameClockService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);

        clearDatabase();
    }

    public void clearDatabase() {
        ContentResolver resolver = getContentResolver();
        resolver.delete(Uri.parse("content://rtrk.pnrs.database/statistics"), null, null);
    }

    private void insert(OneItem oi) {
        ContentValues values = new ContentValues();
        values.put("WhiteTime", oi.textLeft);
        values.put("Result", oi.textCenter);
        values.put("BlackTime", oi.textRight);

        ContentResolver resolver = getContentResolver();
        resolver.insert(Uri.parse("content://rtrk.pnrs.database/statistics"), values);
    }

    void getTime() {
        if(GameClockBinder.player2time/60 < 10) {
            if(GameClockBinder.player2time%60 < 10) {
                blackTime = "00:" + "0" + GameClockBinder.player2time / 60 + ":" + "0" + GameClockBinder.player2time % 60;
            } else {
                blackTime = "00:" + "0" + GameClockBinder.player2time / 60 + ":" + GameClockBinder.player2time % 60;
            }
        } else {
            if(GameClockBinder.player2time%60 < 10) {
                blackTime = "00:" + GameClockBinder.player2time / 60 + ":" + "0" + GameClockBinder.player2time % 60;
            } else {
                blackTime = "00:" + GameClockBinder.player2time / 60 + ":" + GameClockBinder.player2time % 60;
            }
        }

        if(GameClockBinder.player1time/60 < 10) {
            if(GameClockBinder.player1time%60 < 10) {
                whiteTime = "00:" + "0" + GameClockBinder.player1time / 60 + ":" + "0" + GameClockBinder.player1time % 60;
            } else {
                whiteTime = "00:" + "0" + GameClockBinder.player1time / 60 + ":" + GameClockBinder.player1time % 60;
            }
        } else {
            if(GameClockBinder.player1time%60 < 10) {
                whiteTime = "00:" + GameClockBinder.player1time / 60 + ":" + "0" + GameClockBinder.player1time % 60;
            } else {
                whiteTime = "00:" + GameClockBinder.player1time / 60 + ":" + GameClockBinder.player1time % 60;
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mService = IGameClock.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mService = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mService != null) {
            unbindService(this);
        }
        clearDatabase();
    }
}
