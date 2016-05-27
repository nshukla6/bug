import java.util.ArrayList;

public class BugInformation {
	
	private String bug;
	private ArrayList<String> child;
	private String desc;
	private ArrayList<String> fileCommit;
	private ArrayList<String> branch;
	
	
	public String getBug() {
		return bug;
	}
	public void setBug(String bug) {
		this.bug = bug;
	}
	public ArrayList<String> getChild() {
		return child;
	}
	public void setChild(ArrayList<String> child) {
		this.child = child;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public ArrayList<String> getFileCommit() {
		return fileCommit;
	}
	public void setFileCommit(ArrayList<String> fileCommit) {
		this.fileCommit = fileCommit;
	}
	public ArrayList<String> getBranch() {
		return branch;
	}
	public void setBranch(ArrayList<String> branch) {
		this.branch = branch;
	}
	
	
	

}
