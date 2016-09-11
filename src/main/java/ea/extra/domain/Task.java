package ea.extra.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Task {
	@Id
	@GeneratedValue
	private int id;
	
	private String detail;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<Volunteer> getVolunteers() {
		return volunteers;
	}

	public void setVolunteers(List<Volunteer> volunteers) {
		this.volunteers = volunteers;
	}

	@ManyToOne
	private Project project;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="tasks")
	private List<Resource> resources;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="tasks")
	private List<Volunteer> volunteers;

	private Task(){}
	
	public Task(String detail, Date endDate) {
		this.detail = detail;
		this.endDate = endDate;
		status = Status.PENDING;
		resources = new ArrayList<Resource>();
		volunteers = new ArrayList<Volunteer>();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public void addResource(Resource r){
		r.addTask(this);
		resources.add(r);
	}
	
	public void addVolunteer(Volunteer v){
		v.addTask(this);
		volunteers.add(v);
	}
}
