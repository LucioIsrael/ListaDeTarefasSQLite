package br.com.treino.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.com.treino.listadetarefas.R;
import br.com.treino.listadetarefas.helper.TarefaDAO;
import br.com.treino.listadetarefas.model.Tarefa;

public class AdicionarTarefa extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);

        //Edit
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");
        if (tarefaAtual != null) {

            editTarefa.setText(tarefaAtual.getNomeTarefa());

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String tarefaEscrita = editTarefa.getText().toString();

        switch (item.getItemId()) {
            case R.id.bottomSalvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                if (tarefaAtual != null) {
                    if (!tarefaEscrita.isEmpty()) {

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(tarefaEscrita);
                        tarefa.setId(tarefaAtual.getId());

                        if(tarefaDAO.atualizar(tarefa)){
                            Toast.makeText(this, "Sucesso ao atualizar!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    } else {
                        if (!tarefaEscrita.isEmpty()) {
                            Tarefa tarefa = new Tarefa();
                            tarefa.setNomeTarefa(tarefaEscrita);
                            if (tarefaDAO.salvar(tarefa)) {
                                Toast.makeText(this, "Sucesso ao salvar!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                }

                return super.onOptionsItemSelected(item);
        }
    }