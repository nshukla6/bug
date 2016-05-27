import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetData {
	private static Connection con = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	
	private static List<String> bugList=new ArrayList<>();
	private static Map<String,List<String>>baseChildMap=new HashMap<>();
	private static Set<String> totalBugSet=new HashSet<>();
	
	
	
	static String dbUrl="jdbc:mysql://bz3-m-db3.eng.vmware.com:3306/bugzilla";
	static String user="mts";
	static String pwd="mts";
	

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String queryAll = new StringBuilder()
		           .append("SELECT b.bug_id FROM bugs b ")
		           .append("JOIN profiles p ON p.userid=b.assigned_to ")
		           .append("JOIN products pr ON pr.id=b.found_in_product_id ")
		           .append("JOIN versions v ON v.id=b.found_in_version_id ")
		           .append("WHERE pr.name=? and p.login_name=? and v.name=? ")
		           .append("and b.resolution ='fixed' and b.bug_status in ('closed','resolved') ")
		           .toString();
		          
		
		 con=getConnection(dbUrl, user, pwd);
		 preparedStatement = con.prepareStatement(queryAll);

		  preparedStatement.setString(1, "Cloud");
	      preparedStatement.setString(2, "sena");
	      preparedStatement.setString(3, "Pinnacle");
	      
	      resultSet = preparedStatement.executeQuery();
	      bugList=getBugList(resultSet);
	      //System.out.println(bugList);
	      
	      baseChildMap=getBaseChildMap(bugList);
	      
	   /* for (Map.Entry<String, List<String>> entry : baseChildMap.entrySet()) {
	  		
	  		
	  			System.out.println("Parent : " + entry.getKey() + " child : " + entry.getValue());
	  			
	  	
	  	}*/
	    System.out.println(totalBugSet);
	    Map<String,String> map=getTextMap(totalBugSet);
	    System.out.println("files commited="+getAffectedFilesCount(map.get("1550406")));
	   
	  	
	  		
	}
	
	
	
	
	
	
	private static Connection getConnection(String connectionUrl,String user,String pwd){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}

		//System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager
			.getConnection(connectionUrl,user, pwd);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (connection != null) {
			//System.out.println("You made it, take control your database now!");
			return connection;
		} else {
			System.out.println("Failed to make connection!");
			return null;
		}
	}
	
	
	
	private static List<String> getBugList(ResultSet resultSet) throws SQLException {
		List<String> bugList=new ArrayList<>();
	    while (resultSet.next()) {
	      
	      String bugID = resultSet.getString("bug_id");
	      bugList.add(bugID);
	      
	    }
	    return bugList;
	  }
	
	private static Map<String,List<String>> getBaseChildMap(List<String> bugList) throws SQLException{
	    Map<String,List<String>>baseChildMap=new HashMap<>();
	   
		String childQuery=new StringBuilder().append("SELECT child from related where base=?").toString();
		
        preparedStatement = con.prepareStatement(childQuery);
        
		for (String bug:bugList){
			List<String> childBugList=new ArrayList<>();
			preparedStatement.setString(1,bug);
	        resultSet = preparedStatement.executeQuery();
	        totalBugSet.add(bug);
	        while (resultSet.next()) {
	  	      
	  	      String bugID = resultSet.getString("child");
	  	      childBugList.add(bugID);
	  	      totalBugSet.add(bugID);
	  	      
	  	    }
	        baseChildMap.put(bug, childBugList);
	        
	        
			
		}
		preparedStatement=null;
		resultSet=null;
		return baseChildMap;
		
		
	}
	
	private static Map<String,String> getTextMap(Set<String> totalBugSet) throws SQLException{
		 Map<String,String>theTextMap=new HashMap<>();
		 
		 String theTextQuery=new StringBuilder().append("SELECT thetext from longdescs where bug_id=? and thetext like ?").toString();
		 preparedStatement = con.prepareStatement(theTextQuery);
		 for (String bug : totalBugSet) {
			 StringBuffer sb=new StringBuffer();
			 preparedStatement.setString(1,bug);
			 preparedStatement.setString(2,"%Affected files (%");
			 resultSet = preparedStatement.executeQuery();
			 while (resultSet.next()) {
		  	      
		  	      String text = resultSet.getString("thetext");
		  	      //theText.add(text);
		  	      sb.append(text);
		  	     
		  	      
		  	    }
		        theTextMap.put(bug, sb.toString());
		        
			}
		 preparedStatement=null;
		 resultSet=null;
		return theTextMap;
		
	}
	
	private static int getAffectedFilesCount(String text){
		int count=0;
	
		Pattern pattern = Pattern.compile("\\((\\d|\\d\\d|\\d\\d\\d)\\)");
		 Matcher matcher = pattern.matcher(text);
		 while (matcher.find()) {
	            
	         count+=Integer.parseInt(matcher.group(1));
	         
	         
	          }
		return count;
	}
	
	

}
