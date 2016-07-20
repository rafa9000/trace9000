package com.example.rafaelrodrigues.myapplication.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rafael.rodrigues on 20/05/2016.
 */
public class Trace9000Banco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "trace9000.db";
    public static final String TABELA = " localizacao";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    private static final int VERSAO = 1;

    public Trace9000Banco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE"+TABELA+"("
                + ID + " integer primary key autoincrement,"
                + NOME + " text"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }
}