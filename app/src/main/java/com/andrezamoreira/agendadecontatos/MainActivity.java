package com.andrezamoreira.agendadecontatos;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContatoDAO helper;

    private List<ContatoInfo> listaContatos;

    private RecyclerView contatosRecy;
    private ContatoAdapter adapter;

    // verificar se é novo contato ou ediçao
    private final int REQUEST_NEW = 1;
    private final int REQUEST_ALTER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // referenciando a toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        // TODO: evento do FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // trocar de tela ao clicar no botão
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                // novo contato
                i.putExtra("contato", new ContatoInfo());

                // dados de teste
                /*
                ContatoInfo teste = new ContatoInfo();
                teste.setNome("Contato Teste!");
                teste.setEmail("Contato Email Teste");
                i.putExtra("contato", teste);
                */

                // adiciona um novo contato
                startActivityForResult(i, REQUEST_NEW);
            }
        });

        helper = new ContatoDAO(this);
        listaContatos = helper.getLista("ASC");

        // lista de contatos
        contatosRecy = findViewById(R.id.contatosRecyclerView);
        contatosRecy.setHasFixedSize(true);
        LinearLayoutManager lln = new LinearLayoutManager(this);
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        contatosRecy.setLayoutManager(lln);

        // adaptador
        adapter = new ContatoAdapter(listaContatos);
        contatosRecy.setAdapter(adapter);

        // ao clicar em um item da lista
        contatosRecy.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        abrirOpcoes(listaContatos.get(position));
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NEW && resultCode == RESULT_OK){
            // cria contato
            ContatoInfo contatoInfo = data.getParcelableExtra("contato");
            helper.inserirContato(contatoInfo);
            listaContatos = helper.getLista("ASC");
            adapter = new ContatoAdapter(listaContatos);
            contatosRecy.setAdapter(adapter);
        } else if (requestCode == REQUEST_ALTER && resultCode == RESULT_OK){
            // edita contato
            ContatoInfo contatoInfo = data.getParcelableExtra("contato");
            helper.alteraContato(contatoInfo);
            listaContatos = helper.getLista("ASC");
            adapter = new ContatoAdapter(listaContatos);
            contatosRecy.setAdapter(adapter);
        }
    }

    private void abrirOpcoes(final ContatoInfo contato){
        //Log.d("Log", contato.getNome());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(contato.getNome());

        // sequencia de itens
        builder.setItems(new CharSequence[]{"Ligar", "Editar", "Excluir"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                // ligar
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + contato.getFone()));
                                startActivity(intent);
                                break;
                            case 1:
                                // editar
                                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                                intent1.putExtra("contato", contato);
                                startActivityForResult(intent1, REQUEST_ALTER);
                                break;
                            case 2:
                                // excluir
                                listaContatos.remove(contato);
                                helper.apagaContato(contato);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
