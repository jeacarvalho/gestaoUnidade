package br.serpro.supde.derjo.de7cc.entidades;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Apropriacao {
	private String matriculaResponsavel;
	private Date data;
	private Long minutos;
	public String getMatriculaResponsavel() {
		return matriculaResponsavel;
	}
	public void setMatriculaResponsavel(String matriculaResponsavel) {
		this.matriculaResponsavel = matriculaResponsavel;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Long getMinutos() {
		return minutos;
	}
	public void setMinutos(Long minutos) {
		this.minutos = minutos;
	}
	
	public Apropriacao(){}
}
