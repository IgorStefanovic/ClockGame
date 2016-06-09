package rtrk.pnrs.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class GameClockProvider extends ContentProvider {
    GameClockDatabase gameClockDatabase;

    @Override
    public boolean onCreate() {
        gameClockDatabase = new GameClockDatabase(this.getContext(), "results", null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = gameClockDatabase.getReadableDatabase();
        return db.query("statistics", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = gameClockDatabase.getWritableDatabase();
        db.insert("statistics", null, values);
        gameClockDatabase.close();

        ContentResolver resolver = getContext().getContentResolver();
        resolver.notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = gameClockDatabase.getWritableDatabase();
        int id = db.delete("statistics", null, null);

        gameClockDatabase.close();

        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = gameClockDatabase.getWritableDatabase();
        int updated = db.update("statistics", values, selection, selectionArgs);

        gameClockDatabase.close();

        return updated;

    }
}
