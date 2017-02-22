package br.senai.sp.controller;

import java.net.URI;
import java.util.HashMap;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import br.senai.sp.Modelo.TokenJWT;
import br.senai.sp.Modelo.Usuario;
import br.senai.sp.dao.UsuarioDao;

@RestController
public class UsuarioController {
	
	public static final String EMISSOR ="senai";
	public static final String SECRET ="ToDoListSENAIInformatica";
	

	@Autowired
	private UsuarioDao usuarioDao;

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
		try {
			usuarioDao.criarUsuario(usuario);
			return ResponseEntity.created(URI.create("/usuario/" + usuario.getId())).body(usuario);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TokenJWT> logar(@RequestBody Usuario usuario) {
		try {
			usuario = usuarioDao.logar(usuario);
			if (usuario != null) {
				HashMap<String, Object> claims = new HashMap<String, Object>();
				claims.put("iss", EMISSOR);
				claims.put("id_user",usuario.getId());
				claims.put("nome_user",usuario.getNome());
				
				//hora atual
				long horaAutal = System.currentTimeMillis()/1000;

				// tempo para  
				long horaExpiracao = horaAutal + 3600;
				
				claims.put("iat",horaAutal);
				claims.put("exp",horaExpiracao);
				
				JWTSigner signer = new JWTSigner(SECRET);
				
				TokenJWT tokenJWT = new TokenJWT();
				
				tokenJWT.setToken(signer.sign(claims));
				
				return ResponseEntity.ok(tokenJWT);

			} else {
				return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
			}

			// return ResponseEntity.created(URI.create("/usuario/" +
			// usuario.getId())).body(usuario);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<TokenJWT>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
