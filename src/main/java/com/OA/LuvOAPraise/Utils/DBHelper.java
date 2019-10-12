package com.OA.LuvOAPraise.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.OA.LuvOAPraise.Bean.SongItem;

/**
 * Created by lee on 2019-04-25.
 */
public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        db.execSQL("CREATE TABLE tblWFSongDTL (tblKey TEXT PRIMARY KEY, Title TEXT, Lyics TEXT, ModifyDate TEXT);");
    }

    public int insertSong(String tblKey, String Title, String Lyics, String ModifyDate) {
        // 읽고 쓰기가 가능하게 DB 열기

        Cursor cursor = null;
        int intRetValue = 0;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            // DB에 입력한 값으로 행 추가
            cursor = db.rawQuery("SELECT * FROM tblWFSongDTL Where Title = '" + Title + "'", null);

            if(cursor.getCount()>0)
            {
                intRetValue = -2;
            }
            else
            {
                db.execSQL("INSERT INTO tblWFSongDTL VALUES('"+tblKey+"', '" + Title + "', '" + Lyics + "', '" + ModifyDate + "');");
                intRetValue = 1;
            }

        }
        catch (Exception e)
        {
            intRetValue = -1;
        }
        finally
        {
            cursor.close();
            return intRetValue;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<SongItem> getResultLst() {
        // 읽기가 가능하게 DB 열기
        ArrayList<SongItem> lstSong = new ArrayList<SongItem>() ;
        Cursor cursor = null;
        try
        {
            SQLiteDatabase db = getReadableDatabase();

            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            cursor = db.rawQuery("SELECT * FROM tblWFSongDTL ORDER BY Title ASC", null);
            while (cursor.moveToNext()) {
                SongItem item = new SongItem();

                item.setTitle(cursor.getString(1));
                item.setLyics(cursor.getString(2));

                lstSong.add(item);
            }
        }
        catch (Exception e)
        {
            lstSong = null;
        }
        finally
        {
            cursor.close();
            return lstSong;
        }
    }

    public ArrayList searchResultLst(String searchKeyword) {

        Cursor cursor = null;
        ArrayList<SongItem> lstSong = new ArrayList<SongItem>() ;

        try
        {
            SQLiteDatabase db = getReadableDatabase();
            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            cursor = db.rawQuery("SELECT * FROM tblWFSongDTL WHERE Title LIKE '%"+ searchKeyword +"%' ORDER BY Title ASC", null);
            while (cursor.moveToNext()) {
                SongItem item = new SongItem();

                item.setTitle(cursor.getString(1));
                item.setLyics(cursor.getString(2));

                lstSong.add(item);
            }
        }
        catch (Exception e)
        {
            lstSong = null;
        }
        finally
        {
            cursor.close();
            return lstSong;
        }
    }

    public String searchResultTitle(String Title){

        Cursor cursor = null;
        String result = "";

        try
        {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM tblWFSongDTL WHERE Title LIKE '%"+ Title +"%'", null);
            while (cursor.moveToNext()) {
                result = cursor.getString(2);
            }
        }
        catch (Exception e)
        {
            result = null;
        }
        finally
        {
            cursor.close();
            return result;
        }
    }

    public int UpdateSong(String tblKey, String Title, String Lyics, String ModifyDate){

        int intRetValue = 0;
        try
        {
            SQLiteDatabase db = getReadableDatabase();

            db.execSQL("UPDATE tblWFSongDTL SET Title = '"+ Title +"', Lyics = '"+ Lyics +"', ModifyDate = '" + ModifyDate + "' WHERE tblKey = '" + tblKey + "'");

            intRetValue = 1;
        }
        catch (Exception e)
        {
            intRetValue = -1;
        }
        finally
        {
            return intRetValue;
        }
    }

    public int DeleteSong(String Title){

        int intRetValue = 0;
        try
        {
            SQLiteDatabase db = getReadableDatabase();

            db.execSQL("DELETE FROM tblWFSongDTL WHERE Title = '" + Title + "'");

            intRetValue = 1;
        }
        catch (Exception e)
        {
            intRetValue = -1;
        }
        finally
        {
            return intRetValue;
        }
    }

    public int DeleteSongs(){

        int intRetValue = 0;
        try
        {
            SQLiteDatabase db = getReadableDatabase();

            db.execSQL("DELETE FROM tblWFSongDTL");

            intRetValue = 1;
        }
        catch (Exception e)
        {
            intRetValue = -1;
        }
        finally
        {
            return intRetValue;
        }
    }
}
