package com.andrezamoreira.agendadecontatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.lang.annotation.Target;
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
                + " fone TEXT, "
                + " foto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // TODO: banco de dados
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
            c.setEnd(cursor.getString(cursor.getColumnIndex("endereco")));
            c.setFoto(cursor.getString(cursor.getColumnIndex("foto")));

            contatos.add(c);
        }

        cursor.close();

        return contatos;
    }

    public void inserirContato(ContatoInfo c){
        // para setar os valores no banco de dados
        ContentValues values = new ContentValues();

        // dados
        values.put("nome", c.getNome());
        values.put("ref", c.getRef());
        values.put("email", c.getEmail());
        values.put("fone", c.getFone());
        values.put("endereco", c.getEnd());
        values.put("foto", c.getFoto());

        // salvando
        getWritableDatabase().insert(TABELA, null, values);
    }

    public void alteraContato(ContatoInfo c){
        // para setar os valores no banco de dados
        ContentValues values = new ContentValues();

        // dados
        values.put("id", c.getId());
        values.put("nome", c.getNome());
        values.put("ref", c.getRef());
        values.put("email", c.getEmail());
        values.put("fone", c.getFone());
        values.put("endereco", c.getEnd());
        values.put("foto", c.getFoto());

        String[] idParaSerAlterado = {c.getId().toString()};
        // alterando valores no banco de dados de acordo com o id
        getWritableDatabase().update(TABELA, values,"id=?", idParaSerAlterado);
    }

    public void apagaContato(ContatoInfo c){
        SQLiteDatabase db = getWritableDatabase();

        // id que será excluido
        String[] args = {c.getId().toString()};

        // excluindo do banco de dados
        db.delete(TABELA, "id=?", args);
    }
}
