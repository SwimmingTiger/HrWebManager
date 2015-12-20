package dlnu.web.hrmanager.job.entity;

public class Job {

	public int id;
	public String name;
	public double salary_min;
	public double salary_max;
	public double salary_avg;
	
	public Job(){
		
		id = 0;
		name = null;
		salary_min = 0;
		salary_max = 0;
		salary_avg = 0;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getSalarymin(){
		return this.salary_min;
	}
	
	public double getSalarymax(){
		return this.salary_max;		
	}
	
	public double getSalaryavg(){
		return this.salary_avg;
	}
	public String toString(){
		return this.getName();
	}
	
	public boolean equals(Object obj){
		return obj.getClass() == Job.class && ((Job) obj).id == this.id;
	}
}
