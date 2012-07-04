package geradorDados;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GerenciadorPersistencia {
	
	public static GerenciadorPersistencia _instance = null;

	public static GerenciadorPersistencia getInstance() {
		if (_instance == null) {
			_instance = new GerenciadorPersistencia();
		}
		return _instance;
	}
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private void setup(){
		entityManagerFactory = Persistence.createEntityManagerFactory("gestaoDE723.entidades");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public void persisteClasse (Object classe){
		if(entityManagerFactory==null){
			setup();
		}
		
		entityManager.getTransaction().begin();
		entityManager.persist(classe);
		entityManager.getTransaction().commit();
		//entityManager.close();

	}
	
	public GerenciadorPersistencia() {
		super();
		// TODO Auto-generated constructor stub
	}




}
