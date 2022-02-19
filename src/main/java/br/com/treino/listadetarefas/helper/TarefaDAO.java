package br.com.treino.listadetarefas.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.treino.listadetarefas.model.Tarefa;

//Data acess object
public class TarefaDAO implements iTarefaDao {

    private SQLiteDatabase write;
    private SQLiteDatabase reader;

    public TarefaDAO(Context contex) {
        DbHelper db = new DbHelper(contex);
        write = db.getWritableDatabase();
        reader = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            Log.i("INFO_DB", "Sucesso ao adicionar tarefa");
            write.insert(DbHelper.TABELA_TAREFAS, null, cv);
        } catch (Exception e) {
            Log.e("INFO", "ERRO AO SALVAR TAREFA" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            String[] args = {tarefa.getId().toString()};
            write.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO_DB", "Sucesso ao atualizar tarefa");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }

        return true;

    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
           String[] args = {tarefa.getId().toString()};
           write.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO_DB", "Sucesso ao deletar a tarefa");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao deletar a tarefa" + e.getMessage());
            return false;
        }

        return true;

    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = reader.rawQuery(sql, null);

        while (c.moveToNext()) {

            Tarefa tarefa = new Tarefa();
            //Roda sem esse Suppress por mais que apareça um erro de compilação
            @SuppressLint("Range") Long id = c.getLong(c.getColumnIndex("id"));
            @SuppressLint("Range") String nomeDaTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeDaTarefa);

            tarefas.add(tarefa);

        }

        return tarefas;
    }
}
