package com.example.onlinetyari.readablereddit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.onlinetyari.readablereddit.pojo.Post;
import com.example.onlinetyari.readablereddit.pojo.PostData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Siddharth Verma on 26/4/16.
 */
public class PostsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "postsDatabase";
    private static final Integer DATABASE_VERSION = 3;

    private static final String TABLE_POSTS = "posts";

    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_TITLE = "title";
    private static final String KEY_URL = "url";
    private static final String KEY_SCORE = "score";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_SECTION = "section";

    private static PostsDatabaseHelper sInstance;

    public static synchronized PostsDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PostsDatabaseHelper(context);
        }

        return sInstance;
    }

    private PostsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_POSTS +
                "(" +
                KEY_POST_ID + " INTEGER PRIMARY KEY," +
                KEY_POST_TITLE + " TEXT," +
                KEY_URL + " TEXT," +
                KEY_SCORE + " INTEGER," +
                KEY_COMMENTS + " INTEGER, " +
                KEY_SECTION + " TEXT" +
                ")";

        Log.v("c", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.v("c","ac");
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            onCreate(db);
        }
    }

    public void addPost(PostData postData, String section) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(KEY_POST_TITLE, postData.title);
            contentValues.put(KEY_URL, postData.url);
            contentValues.put(KEY_SCORE, postData.score);
            contentValues.put(KEY_COMMENTS, postData.num_comments);
            contentValues.put(KEY_SECTION, section);

            db.insertOrThrow(TABLE_POSTS, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    public Observable<List<PostData>> getAllPosts(String section) {

        SQLiteDatabase db = getReadableDatabase();

        List<PostData> postDataList = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_POSTS, KEY_SECTION);

        Cursor cursor = db.rawQuery(selectQuery, new String[]{section});

        try {
            while (cursor.moveToNext()) {
                PostData postData = new PostData();
                postData.setTitle(cursor.getString(cursor.getColumnIndex(KEY_POST_TITLE)));
                postData.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
                postData.setScore(cursor.getInt(cursor.getColumnIndex(KEY_SCORE)));
                postData.setNum_comments(cursor.getString(cursor.getColumnIndex(KEY_COMMENTS)));
                postDataList.add(postData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return Observable.defer(() -> Observable.just(postDataList));
    }
}
