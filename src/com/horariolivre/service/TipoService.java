package com.horariolivre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.TipoHome;
import com.horariolivre.entity.Tipo;

@Service
public class TipoService {
	
	@Autowired
	private TipoHome tipo;
	
	public List<Tipo> listaTipos() {
		return tipo.findALL();
	}
}
