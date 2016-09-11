package ea.extra.credit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ea.extra.domain.Beneficiary;
import ea.extra.domain.Project;
import ea.extra.domain.Resource;
import ea.extra.domain.Status;
import ea.extra.domain.Task;
import ea.extra.domain.Volunteer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	
	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testApp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("extraCredit");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
			insertRecords(em);
			displayProjectAndBeneficiaries(em, "bridge");
			updateProjectStatus(em, "bridge", Status.COMPLETED);
			displayProjectAndTasks(em, "pavement");
			displayProjectByStatus(em, Status.PENDING);
			displayProjectWithResource(em, "Resource2");
			searchProject(em, "Nepal");
			displayProjectByVolunteer(em, "Sujan");
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(tx != null){
				tx.rollback();
			}
		}finally{
			em.close();
		}
	}

	//can also be used for searching by keyword
	private void displayProjectAndBeneficiaries(EntityManager em, String string) {
		Query query = em.createQuery("FROM Project p WHERE p.name LIKE '%"+string+"%'");
		List<Project> projects = query.getResultList();
		System.out.println("-------Displaying projects and their beneficiaries--------");
		for(Project project:projects){
			System.out.println("Project name: " + project.getName());
			System.out.println("Project description: " + project.getDescription());
			System.out.println("Project location: " + project.getLocation());
			System.out.println("Project Status: " + project.getStatus());
			System.out.println("Project start date: " + project.getStartDate());
			System.out.println("Project end date: " + project.getEndDate());
			System.out.println("Project beneficiaries:");
			for(Beneficiary b:project.getBeneficiaries()){
				System.out.println("\tName: " + b.getName());
			}
		}
		System.out.println();
		System.out.println();
	}
	
	//can also be used for searching by keyword
	private void displayProjectAndTasks(EntityManager em, String string){
		Query query = em.createQuery("FROM Project p WHERE p.name LIKE '%"+string+"%'");
		List<Project> projects = query.getResultList();
		System.out.println("-------Displaying projects and their tasks--------");
		for(Project project:projects){
			System.out.println("Project name: " + project.getName());
			System.out.println("Project description: " + project.getDescription());
			System.out.println("Project location: " + project.getLocation());
			System.out.println("Project Status: " + project.getStatus());
			System.out.println("Project start date: " + project.getStartDate());
			System.out.println("Project end date: " + project.getEndDate());
			System.out.println("Project tasks:");
			for(Task b:project.getTasks()){
				System.out.println("\tDetail: " + b.getDetail());
				System.out.println("\tStatus: " + b.getStatus());
				System.out.println("\tEnd date: " + b.getEndDate());
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();
	}

	private void displayProjectByStatus(EntityManager em, Status s){
		Query query = em.createQuery("FROM Project p WHERE p.status = :status");
		query.setParameter("status", s);
		List<Project> projects = query.getResultList();
		System.out.println("-------Filtering projects with status--------");
		for(Project project:projects){
			System.out.println("Project name: " + project.getName());
			System.out.println("Project description: " + project.getDescription());
			System.out.println("Project location: " + project.getLocation());
			System.out.println("Project Status: " + project.getStatus());
			System.out.println("Project start date: " + project.getStartDate());
			System.out.println("Project end date: " + project.getEndDate());
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	private void displayProjectWithResource(EntityManager em, String resourceName){
		Query query = em.createQuery("SELECT DISTINCT p FROM Project p JOIN p.tasks t JOIN t.resources r WHERE r.name=:resourceName");
		query.setParameter("resourceName", resourceName);
		List<Project> projects = query.getResultList();
		System.out.println("-------Filtering projects with resource: " + resourceName + "--------");
		for(Project project:projects){
			System.out.println("Project name: " + project.getName());
			System.out.println("Project description: " + project.getDescription());
			System.out.println("Project location: " + project.getLocation());
			System.out.println("Project Status: " + project.getStatus());
			System.out.println("Project start date: " + project.getStartDate());
			System.out.println("Project end date: " + project.getEndDate());
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	private void searchProject(EntityManager em, String keyword){
		Query query = em.createQuery("FROM Project p WHERE p.location LIKE '%" + keyword + "%' OR p.name LIKE '%" + keyword + "%'");
		List<Project> projects = query.getResultList();
		System.out.println("-------Filtering projects with keyword: " + keyword + "--------");
		for(Project project:projects){
			System.out.println("Project name: " + project.getName());
			System.out.println("Project description: " + project.getDescription());
			System.out.println("Project location: " + project.getLocation());
			System.out.println("Project Status: " + project.getStatus());
			System.out.println("Project start date: " + project.getStartDate());
			System.out.println("Project end date: " + project.getEndDate());
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	private void displayProjectByVolunteer(EntityManager em, String vName){
		Query query = em.createQuery("SELECT DISTINCT p FROM Project p JOIN p.tasks t JOIN t.volunteers v WHERE v.name=:vName ORDER BY t.endDate");
		query.setParameter("vName", vName);
		List<Project> projects = query.getResultList();
		System.out.println("-------Filtering projects with volunteer: " + vName + "--------");
		for(Project project:projects){
			System.out.println("Project name: " + project.getName());
			System.out.println("Project description: " + project.getDescription());
			System.out.println("Project location: " + project.getLocation());
			System.out.println("Project Status: " + project.getStatus());
			System.out.println("Project start date: " + project.getStartDate());
			System.out.println("Project end date: " + project.getEndDate());
			System.out.println("Project tasks:");
			for(Task b:project.getTasks()){
				System.out.println("\tDetail: " + b.getDetail());
				System.out.println("\tStatus: " + b.getStatus());
				System.out.println("\tEnd date: " + b.getEndDate());
				System.out.println();
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	private void updateProjectStatus(EntityManager em, String projectName, Status status){
		Query query = em.createQuery("FROM Project p WHERE p.name LIKE '%" + projectName + "%'");
		List<Project> projects = query.getResultList();
		for(Project p:projects){
			p.setStatus(status);
			em.merge(p);
		}
	}
	
	private void insertRecords(EntityManager em) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		Beneficiary b1 = new Beneficiary("Government");
		Beneficiary b2 = new Beneficiary("XYZ.co");
		
		Resource r1 = new Resource("Resource1");
		Resource r2 = new Resource("Resource2");
		
		Volunteer v1 = new Volunteer("Sujan");
		Volunteer v2 = new Volunteer("Frank");
		
		Project project1 = new Project("Long bridge", "Building long bridge over a wide river", "Fairfield IA", dateFormat.parse("01-05-2016"), dateFormat.parse("01-12-2016"));
		List<Task> taskList = getSampleTasks(dateFormat);
		for(Task task:taskList){
			project1.addTask(task);
			task.addResource(r1);
			task.addResource(r2);
			task.addVolunteer(v1);
			task.addVolunteer(v2);
		}
		project1.addBeneficiary(b1);
		project1.addBeneficiary(b2);
		em.persist(project1);
		
		Project project2 = new Project("Street pavement", "Construction of pavement on the road", "Kathmandu Nepal", dateFormat.parse("01-05-2016"), dateFormat.parse("01-12-2016"));
		taskList = getSampleTasks(dateFormat);
		for(Task task:taskList){
			project2.addTask(task);
			task.addResource(r1);
			task.addVolunteer(v1);
		}
		project2.addBeneficiary(b1);
		em.persist(project2);
	}
	
	private List<Task> getSampleTasks(SimpleDateFormat dateFormat) throws ParseException{
		return Arrays.asList(new Task("Task1", dateFormat.parse("02-07-2016")),
				new Task("Task2", dateFormat.parse("02-08-2016")),
				new Task("Task3", dateFormat.parse("02-09-2016")));
	}
}