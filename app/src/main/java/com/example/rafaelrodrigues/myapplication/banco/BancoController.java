package com.example.rafaelrodrigues.myapplication.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rafael.rodrigues on 20/05/2016.
 */
public class BancoController {

    private SQLiteDatabase db;
    private Trace9000Banco banco;

    public BancoController(Context context){
        banco = new Trace9000Banco(context);
    }

    public String insereDado(String nome){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Trace9000Banco.NOME, nome);

        resultado = db.insert(Trace9000Banco.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {Trace9000Banco.ID,Trace9000Banco.NOME};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}