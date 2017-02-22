package br.senai.sp.controller;

import java.net.URI;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.Modelo.SubTarefa;
import br.senai.sp.dao.SubTarefaDao;

@RestController
public class SubTarefaController {

	@Autowired
	private SubTarefaDao daoSubTarefa;

	@RequestMapping(value = "/tarefa/{id}/subtarefa", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<SubTarefa> criarSubTarefa(@PathVariable("id") Long idTarefa,
			@RequestBody SubTarefa subTarefa) {
		try {
			daoSubTarefa.criarSubTarefa(idTarefa, subTarefa);
			return ResponseEntity.created(URI.create("/subtarefa/" + subTarefa.getId())).body(subTarefa);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<SubTarefa>(HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<SubTarefa>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/subtarefa/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public SubTarefa buscarSubTarefa(@PathVariable("id") Long idSubTarefa) {
		return daoSubTarefa.buscarSubTarefa(idSubTarefa);
	}
	@RequestMapping(value = "/subtarefa/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> marcarFeito(@PathVariable("id") Long idSubTarefa, @RequestBody SubTarefa subTarefa) {
		try {
			daoSubTarefa.marcarFeito(idSubTarefa, subTarefa.isFeita());
			HttpHeaders header = new HttpHeaders();
			header.setLocation(URI.create("/subtarefa/" + idSubTarefa));						
			return new ResponseEntity<Void>(header, HttpStatus.OK);
			
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/subtarefa/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirSubTarefa(@PathVariable("id") Long idSubTarefa){
		daoSubTarefa.excluir(idSubTarefa);
		return ResponseEntity.noContent().build();
		
	}
	

}
