package br.senai.sp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.Modelo.SubTarefa;
import br.senai.sp.Modelo.Tarefa;

@Repository
public class SubTarefaDao {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void criarSubTarefa(Long idTarefa, SubTarefa subTarefa) {
		subTarefa.setTarefa(manager.find(Tarefa.class, idTarefa));
		manager.persist(subTarefa);

	}

	public SubTarefa buscarSubTarefa(Long idsubTarefa) {
		return manager.find(SubTarefa.class, idsubTarefa);
	}

	@Transactional
	public void marcarFeito(Long idsubTarefa, boolean valor) {
		SubTarefa subTarefa = buscarSubTarefa(idsubTarefa);
		subTarefa.setFeita(valor);
		manager.merge(subTarefa);
	}
	
	@Transactional
	public void excluir(Long idSubTarefa){
		SubTarefa subTarefa = buscarSubTarefa(idSubTarefa);
		Tarefa tarefa= subTarefa.getTarefa();
		tarefa.getSubtarefas().remove(subTarefa);
		manager.merge(tarefa);
	}

}
