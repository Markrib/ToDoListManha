package br.senai.sp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.Modelo.Tarefa;

@Repository
public class TarefaDao implements InterfaceDao<Tarefa> {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void inserir(Tarefa t) {
		manager.persist(t);

	}

	public Tarefa busacar(Long id) {
		return manager.find(Tarefa.class, id);
	}

	public List<Tarefa> listar() {
		TypedQuery<Tarefa> query = manager.createQuery("select t from Tarefa t", Tarefa.class);
		return query.getResultList();

	}

	@Transactional
	public void excluir(Long id) {
		Tarefa tarefa = busacar(id);
		manager.remove(tarefa);

	}

}
