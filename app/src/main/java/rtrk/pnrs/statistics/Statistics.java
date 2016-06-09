package rtrk.pnrs.statistics;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import rtrk.pnrs.clockgame.MainActivity;
import rtrk.pnrs.clockgame.R;

public class Statistics extends Activity {

    private ListView listView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        listView = (ListView) findViewById(R.id.listView);
        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);

        myAdapter.update(readResults());
    }

    public OneItem[] readResults() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://rtrk.pnrs.database/statistics"), null, null, null, null);
        String[] res = new String[3];
        OneItem[] results = new OneItem[cursor.getCount()];
        int i = 0;

        cursor.moveToFirst();

        if (cursor.getCount() <= 0) {
            return null;
        }

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            res[0] = cursor.getString(cursor.getColumnIndex("WhiteTime"));
            res[1] = cursor.getString(cursor.getColumnIndex("Result"));
            res[2] = cursor.getString(cursor.getColumnIndex("BlackTime"));

            results[i] = new OneItem(res[0], res[1], res[2]);

            if (res[1].equals("Draw")) {
                results[i].imageCenter = getResources().getDrawable(R.drawable.draw);
            } else if (res[1].equals("White Player Won")) {
                results[i].imageCenter = getResources().getDrawable(R.drawable.white_win);
            } else {
                results[i].imageCenter = getResources().getDrawable(R.drawable.black_win);
            }
            i++;
        }

        cursor.close();
        return results;
    }
}
