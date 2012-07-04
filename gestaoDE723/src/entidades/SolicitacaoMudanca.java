package entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "SM" )
public class SolicitacaoMudanca {
	private String dataInicioSM;
	public String getDataInicioSM() {
		return dataInicioSM;
	}
	public void setDataInicioSM(String dataInicioSM) {
		this.dataInicioSM = dataInicioSM;
	}
	public String getDataFimPrevistaSM() {
		return dataFimPrevistaSM;
	}
	public void setDataFimPrevistaSM(String dataFimPrevistaSM) {
		this.dataFimPrevistaSM = dataFimPrevistaSM;
	}
	public String getDataFimRealizadaSM() {
		return dataFimRealizadaSM;
	}
	public void setDataFimRealizadaSM(String dataFimRealizadaSM) {
		this.dataFimRealizadaSM = dataFimRealizadaSM;
	}
	public String getAssuntoSM() {
		return assuntoSM;
	}
	public void setAssuntoSM(String assuntoSM) {
		this.assuntoSM = assuntoSM;
	}
	public String getProjetoSM() {
		return projetoSM;
	}
	public void setProjetoSM(String projetoSM) {
		this.projetoSM = projetoSM;
	}
	public String getEstadoSM() {
		return estadoSM;
	}
	public void setEstadoSM(String estadoSM) {
		this.estadoSM = estadoSM;
	}
	public String getResponsavelSM() {
		return responsavelSM;
	}
	public void setResponsavelSM(String responsavelSM) {
		this.responsavelSM = responsavelSM;
	}
	private String dataFimPrevistaSM;
	private String dataFimRealizadaSM;
	private String assuntoSM;
	private String numeroSM;
	private String projetoSM;
	private String estadoSM;
	private String responsavelSM;
	
	@Id
	public String getNumeroSM() {
		return numeroSM;
	}
	public void setNumeroSM(String numeroSM) {
		this.numeroSM = numeroSM;
	}
	public String getDataInicio() {
		return dataInicioSM;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicioSM = dataInicio;
	}
	public String getDataFimPrevista() {
		return dataFimPrevistaSM;
	}
	public void setDataFimPrevista(String dataFimPrevista) {
		this.dataFimPrevistaSM = dataFimPrevista;
	}
	public String getDataFimRealizada() {
		return dataFimRealizadaSM;
	}
	public void setDataFimRealizada(String dataFimRealizada) {
		this.dataFimRealizadaSM = dataFimRealizada;
	}
	public String getTarefa() {
		return assuntoSM;
	}
	public void setTarefa(String tarefa) {
		this.assuntoSM = tarefa;
	}
	
	
}
