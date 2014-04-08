package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.horariolivre.entity.Key;
import com.horariolivre.service.AtributoService;
import com.horariolivre.service.AtributoService.json_list;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="key")
public class KeyValueController {
	
	@Autowired
	private AtributoService key;

	@RequestMapping(value="cadastra_campo", method=RequestMethod.GET)
	@ResponseBody
	public String cadastra_campo(@ModelAttribute("username") String username, @RequestParam("nome") String campo) {
		json_list lista = key.getJsonList();
		
		if(key.temAutorizacao(key.getUsuarioByUsername(username).getId())) {
			if(key.cadastra(campo)) {
				lista.set(key.getCampo(campo), null);
				return lista.get();
			}
			else {
				lista.set(-1);
				return lista.get();
			}
		}
		else {
			lista.set(-2);
			return lista.get();
		}
	}
	
	@RequestMapping(value="remove_campo", method=RequestMethod.GET)
	@ResponseBody
	public String remove_campo(@ModelAttribute("username") String username, @RequestParam("nome") String campo) {
		if(key.temAutorizacao(key.getUsuarioByUsername(username).getId())) {
			if(key.remover(campo))
				return "yes";
			else
				return "not";
		}
		else {
			return "no_permit";
		}
	}
	
	@RequestMapping(value="lista_key_value", method=RequestMethod.GET)
	@ResponseBody
	public String lista_key_value(@RequestParam("id") String id_usuario) {
		json_list lista = key.getJsonList();
		String username = key.getUsuarioById(Integer.valueOf(id_usuario).intValue()).getLogin();
		lista.setLista(key.listaCampos(), key.listaValores(username));
		return lista.get();
	}
	
}
