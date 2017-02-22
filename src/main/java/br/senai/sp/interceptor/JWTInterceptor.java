package br.senai.sp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.JWTVerifier;

import br.senai.sp.controller.UsuarioController;

public class JWTInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod method = (HandlerMethod) handler;

		if (request.getRequestURI().contains("login") || request.getRequestURI().contains("usuario")) {
			return true;
		} else {
			String token = null;
			try {
				token = request.getHeader("Authorization");
				JWTVerifier verifier = new JWTVerifier(UsuarioController.SECRET);
				Map<String, Object> claims = verifier.verify(token);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				if (token == null) {
					response.sendError(HttpStatus.UNAUTHORIZED.value());
				} else {
					response.sendError(HttpStatus.FORBIDDEN.value());
				}
				return true;
			}
		}

	}

}
