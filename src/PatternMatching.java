import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class PatternMatching {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text    =
				"Affected files (12) ..."+

				"... //depot/cloud/cloud801/cell/fabric/fabric-storage/src/main/java/com/vmware/vcloud/fabric/storage/placement/sdrs/impl/SdrsPlacementManagerImpl.java#2 edit"+
				"hello... //depot/cloud/cloud801/cell/fabric/fabric-storage/src/main/java/com/vmware/vcloud/fabric/storage/placement/sdrs/impl/StoragePlacementSpecFactoryImpl.java#2 edit";

		
	       // Pattern pattern = Pattern.compile("\\((\\d|\\d\\d|\\d\\d\\d)\\)");
		//String patternString = ".*... //depot/.*";
		//Pattern pattern1 = Pattern.compile(patternString);
	       // Matcher matcher = pattern.matcher(text);
	        
			String[]arr=text.split("... //depot/");
			for(int i=1;i<arr.length;i++){
				System.out.println(arr[i].substring(arr[i].lastIndexOf("/")+1).split("#")[0]);
				System.out.println(arr[i].split("/")[1]);
			}
	       // Matcher matcher1 = pattern1.matcher(text);
	       // boolean matches = matcher1.matches();
	        //System.out.println("lookingAt = " + matcher1.lookingAt());
	        //System.out.println("matches   = " + matcher1.matches());
	       /* while (matcher.find()) {
	            
	            System.out.print("files commited: " + matcher.group(1));
	            
	          }
	        System.out.println("\n");
	        
	       // System.out.println(text.substring(text.indexOf("Affected files")));
	     */

	}

}
