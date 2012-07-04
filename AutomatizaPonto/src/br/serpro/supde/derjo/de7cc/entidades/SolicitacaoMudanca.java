package br.serpro.supde.derjo.de7cc.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SolicitacaoMudanca {
	private Long numero;
	private String assunto;
	private Date dataInicio;
	private Date dataFim;
	@Id
	@GeneratedValue
	private Long id;
	private String matriculaResponsavel;
	
	public SolicitacaoMudanca(){}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatriculaResponsavel() {
		return matriculaResponsavel;
	}

	public void setMatriculaResponsavel(String matriculaResponsavel) {
		this.matriculaResponsavel = matriculaResponsavel;
	}
	
	
	
}
