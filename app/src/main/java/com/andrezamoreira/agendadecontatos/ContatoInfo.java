package com.andrezamoreira.agendadecontatos;

import android.os.Parcel;
import android.os.Parcelable;

public class ContatoInfo implements Parcelable {
    private Long id = -1L;
    private String nome = "";
    private String ref = "";
    private String email = "";
    private String fone = "";
    private String endereco = "";
    private String foto = "";

    ContatoInfo(){

    }

    private ContatoInfo(Parcel in){
        String[] data = new String[7];
        in.readStringArray(data);
        setNome(data[0]);
        setEmail(data[1]);
        setEnd(data[2]);
        setRef(data[3]);
        setFone(data[4]);
        setFoto(data[5]);
        setId(Long.parseLong(data[6]));
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEnd() {
        return endereco;
    }

    public void setEnd(String end) {
        this.endereco = end;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                getNome(),
                getEmail(),
                getEnd(),
                getRef(),
                getFone(),
                getFoto(),
                String.valueOf(getId())
        });
    }

    public static final Parcelable.Creator<ContatoInfo> CREATOR = new Parcelable.Creator<ContatoInfo>(){
        @Override
        public ContatoInfo createFromParcel(Parcel parcel) {
            return new ContatoInfo(parcel);
        }

        @Override
        public ContatoInfo[] newArray(int i) {
            return new ContatoInfo[i];
        }
    };
}
