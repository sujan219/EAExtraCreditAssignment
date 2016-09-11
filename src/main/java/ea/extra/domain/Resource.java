package ea.extra.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Resource {
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@ManyToMany
	private List<Task> tasks;
	
	private Resource(){}
	
	public Resource(String name){
		this.name = name;
		tasks = new ArrayList<Task>();
	}
	
	public void addTask(Task t){
		tasks.add(t);
	}
}
