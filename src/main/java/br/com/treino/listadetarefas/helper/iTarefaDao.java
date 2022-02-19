package br.com.treino.listadetarefas.helper;

import java.util.List;

import br.com.treino.listadetarefas.model.Tarefa;

public interface iTarefaDao {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
