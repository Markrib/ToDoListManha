package br.senai.sp.dao;

import java.util.List;

public interface InterfaceDao<T> {
	
	public void inserir(T t);
	public T busacar(Long id);
	public List<T> listar();
	public void excluir(Long id);	

}
