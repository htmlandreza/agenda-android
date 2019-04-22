package com.andrezamoreira.agendadecontatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private final String TABELA = "Contatos";
    private static final String DATABASE = "DadosAgenda";


    public ContatoDAO(@Nullable Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql;
        sql = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nome TEXT NOT NULL, "
                + " ref TEXT, "
                + " email TEXT, "
                + " endereco TEXT, "
                + " foto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<ContatoInfo> getLista(String order){
        List<ContatoInfo> contatos = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + " ORDER BY nome " + order + ";", null);

        while(cursor.moveToNext()){
            ContatoInfo c = new ContatoInfo();

            c.setId(cursor.getLong(cursor.getColumnIndex("id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setRef(cursor.getString(cursor.getColumnIndex("ref")));
            c.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            c.setFone(cursor.getString(cursor.getColumnIndex("fone")));
            c.setEnd(cursor.getString(cursor.getColumnIndex("end")));
            c.setFoto(cursor.getString(cursor.getColumnIndex("foto")));

            contatos.add(c);
        }

        cursor.close();

        return contatos;
    }
}
