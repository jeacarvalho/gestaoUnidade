package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "APROPRIACOES" )
public class ApropriacaoDia {
	private String matricula;
	private String nomeEmpregado;
	
	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}
	private String data;
	private int horas;
	private String sm;
	private String percentualConclusao;
	
	private Long id;


	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }
	
	public ApropriacaoDia() {
	}

	@Override
	public boolean equals(Object arg0) {
		ApropriacaoDia outraApropriacao = (ApropriacaoDia)arg0;
		
		if((outraApropriacao.getMatricula().equalsIgnoreCase(this.getMatricula())) && 
		  (outraApropriacao.getSm().equalsIgnoreCase(this.getSm())) &&
		  (outraApropriacao.getData().compareTo(this.getData())) == 0){
			return true;
		}else{
			return false;
		}
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getHoras() {
		return horas;
	}
	public void setHoras(int horas) {
		this.horas = horas;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getPercentualConclusao() {
		return percentualConclusao;
	}
	public void setPercentualConclusao(String percentualConclusao) {
		this.percentualConclusao = percentualConclusao;
	}
	
}
