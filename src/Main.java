
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		G.allTrainingData =  DataLoader.loadData("train.data");
		G.allTestData =  DataLoader.loadData("test.data");
		
		
		NeuralNetwork network = new NeuralNetwork(5, 5, 3, 3);
		
		
		double prevError = 0, errorDiff;
		
		do{
			
			double error = 0;
			
			
			for(DataInstance data : G.allTrainingData){
				
				network.singlePass(data);
				error += network.getCurrentError();
				
			}
			
			errorDiff = error - prevError;
			System.out.println("error is " + error + " diff " + errorDiff);
			
			prevError = error;
			
			
		}while(Math.abs(errorDiff) > G.ERROR_DIFF_THRESHOLD);
		
		// Training finished
		int nPos = 0;
		for(DataInstance data : G.allTestData){
			
			if(network.canCorrectlyClassify(data)) nPos++;
			
		}
		
		
		System.out.print("\n\n\n");
		System.out.println("" + nPos + " correctly classified out of " + G.allTestData.size() );
		System.out.println("Accuracy is " + (float)(nPos * 100.00 / G.allTestData.size()) + "%");
		
		
		
		

	}

}
