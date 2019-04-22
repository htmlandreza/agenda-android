package com.andrezamoreira.agendadecontatos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContactViewHolder>{

    private List<ContatoInfo> listaContatos;

    // construtor do adaptador
    ContatoAdapter(List<ContatoInfo> lista){
        listaContatos = lista;
    }
    
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.celula_contato, parent, false);

        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContatoInfo c = listaContatos.get(position);
        holder.nome.setText(c.getNome());
        holder.referencia.setText(c.getRef());
        holder.telefone.setText(c.getFone());

        File imgFile = new File(c.getFoto());
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.foto.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return listaContatos.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder{
        // declarando atributos da celula
        ImageView foto;
        TextView nome;
        TextView referencia;
        TextView telefone;

        // construtor
        ContactViewHolder(View v){
            super(v);

            foto = v.findViewById(R.id.fotoImageView);
            nome = v.findViewById(R.id.nomeTextView);
            referencia = v.findViewById(R.id.nomeTextView);
            telefone = v.findViewById(R.id.telefoneTextView);
        }
    }
}
