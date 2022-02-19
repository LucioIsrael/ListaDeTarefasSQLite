package br.com.treino.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.treino.listadetarefas.R;
import br.com.treino.listadetarefas.adapter.TarefaAdapter;
import br.com.treino.listadetarefas.helper.DbHelper;
import br.com.treino.listadetarefas.helper.RecyclerItemClickListener;
import br.com.treino.listadetarefas.helper.TarefaDAO;
import br.com.treino.listadetarefas.model.Tarefa;

import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Tarefa> tarefaList = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefa.class);
                startActivity(intent);

            }
        });

        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                tarefaSelecionada = tarefaList.get(position);

                                Intent intent = new Intent(MainActivity.this, AdicionarTarefa.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                tarefaSelecionada = tarefaList.get(position);

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder (MainActivity.this);

                                alertDialog.setTitle("Confirmar exclusão");
                                alertDialog.setMessage("Deseja excluir a tarefa: "+ tarefaSelecionada.getNomeTarefa() + " ?");

                                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TarefaDAO tarefaDAO = new TarefaDAO(MainActivity.this);
                                        if(tarefaDAO.deletar(tarefaSelecionada)){
                                            Toast.makeText(getApplicationContext(), "Sucesso ao deletar!", Toast.LENGTH_SHORT).show();
                                            criarlistadetarefas();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Erro ao deletar!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                alertDialog.setNegativeButton("Não", null);

                                alertDialog.create();
                                alertDialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

    }

    public void onStart() {
        criarlistadetarefas();
        super.onStart();
    }

    public void criarlistadetarefas() {

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefaList = tarefaDAO.listar();


        TarefaAdapter tarefaAdapter = new TarefaAdapter(tarefaList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);

    }

}