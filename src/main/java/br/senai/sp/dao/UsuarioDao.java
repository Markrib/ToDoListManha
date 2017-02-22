package br.senai.sp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.Modelo.Usuario;

@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void criarUsuario(Usuario usuario) {

		manager.persist(usuario);
	}

	public Usuario logar(Usuario usuario) {
		TypedQuery<Usuario> query = manager
				.createQuery("select u from Usuario u where u.email= :email and u.senha = :senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;

		}

	}

}
