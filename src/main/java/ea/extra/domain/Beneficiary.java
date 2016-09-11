package ea.extra.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Beneficiary {
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@ManyToMany
	private List<Project> projects;
	
	private Beneficiary(){}
	
	public Beneficiary(String name){
		this.name = name;
		projects = new ArrayList<Project>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addProject(Project p){
		projects.add(p);
	}
}
