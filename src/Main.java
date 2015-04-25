
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		G.allTrainingData =  DataLoader.loadData("train.data");
		G.allTestData =  DataLoader.loadData("test.data");

	}

}
