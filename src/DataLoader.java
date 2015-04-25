import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DataLoader {
	
	public static ArrayList<DataInstance> loadData(String filename){
		
		FileInputStream fstream;
		BufferedReader br;
		
		ArrayList<DataInstance> data = new ArrayList<DataInstance>();
		try {


			fstream = new FileInputStream(filename);
			br = new BufferedReader(new InputStreamReader(fstream));

			String line;

	
			
			
			

			while ((line = br.readLine()) != null){

				
				
					String[] words = line.split("[ \\t]");
					
					int i=0;
					
					while(words[i].isEmpty()) i++;
					
		//			System.out.println("#" + words[i] + "#");
					
					float attrib1 = Float.parseFloat(words[i++]);
					
					while(words[i].isEmpty()) i++;
					
		//			System.out.println("#" + words[i] + "#");
					
					float attrib2 = Float.parseFloat(words[i++]);
					
					while(words[i].isEmpty()) i++;
					
		//			System.out.println("#" + words[i] + "#");
					
					float attrib3 = Float.parseFloat(words[i++]);
					
					while(words[i].isEmpty()) i++;
					
		//			System.out.println("#" + words[i] + "#");
					
					int classId = Integer.parseInt(words[i++]);
					
		//			System.out.println("#" + attrib1 + "#" + attrib2 + "#" + attrib3 + "#" + classId);
					
					
					data.add(new DataInstance(attrib1, attrib2, attrib3, classId));
	

					




			}

			br.close();



		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		System.out.println("" + data.size() + " instances loaded from " + filename);
		
		return data;

	}

}
