package com.horariolivre.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AtributoHome;
import com.horariolivre.dao.KeyHome;
import com.horariolivre.dao.ValueHome;
import com.horariolivre.entity.Atributo;
import com.horariolivre.entity.Key;
import com.horariolivre.entity.Usuario;
import com.horariolivre.entity.Value;

@Service
public class AtributoService {
	
	@Autowired
	private AtributoHome atributo;
	
	@Autowired
	private KeyHome key;
	
	@Autowired
	private ValueHome value;
	
	public List<Key> listaCampos() {
		return key.findALL();
	}
	
	public String[] listaKey() {
		List<Key> lista_campos = key.findALL();
		
		String[] lista = new String[lista_campos.size()];
		for(int i=0; i<lista.length; i++) {
			lista[i] = lista_campos.get(i).getNome();
		}
		
		return lista;
	}
	
	public String[] listaValue(Usuario user) {
		List<Atributo> lista_atributo = user.getAtributo();
		List<Value> lista_valores = new ArrayList<Value>();
		for(int i=0; i<lista_atributo.size(); i++) {
			lista_valores.add(lista_atributo.get(i).getValue());
		}
		
		String[] lista = new String[lista_valores.size()];
		for(int i=0; i<lista.length; i++) {
			lista[i] = lista_valores.get(i).getConteudo();
		}
		
		return lista;
	}
}
