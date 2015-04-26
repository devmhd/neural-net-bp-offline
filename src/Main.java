
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		G.allTrainingData =  DataLoader.loadData("train.data");
		G.allTestData =  DataLoader.loadData("test.data");
		
		
		NeuralNetwork network = new NeuralNetwork(2, 5, 3, 3);
		
		
		double prevError = 0, errorDiff=0;
		
		
		double error = 0;
		
		int n_iteration = 0;
		
		do{
			
			error = 0;
			
			
			for(DataInstance data : G.allTrainingData){
				
				network.singlePass(data);
				error += network.getCurrentError();
				
			}
			
		//	errorDiff = error - prevError;
			System.out.println("error is " + error + " diff " + errorDiff);
			
			n_iteration++;
			
		//	prevError = error;
			
			
		}while(error > G.ERROR_THRESHOLD && n_iteration<10000);
		
		// Training finished
		int nPos = 0;
		for(DataInstance data : G.allTestData){
			
			if(network.canCorrectlyClassify(data)) nPos++;
			
		}
		
		
		System.out.print("\n\n\n");
		System.out.println("Iterations: " + n_iteration);
		System.out.println("" + nPos + " correctly classified out of " + G.allTestData.size() );
		System.out.println("Accuracy is " + (float)(nPos * 100.00 / G.allTestData.size()) + "%");
		
		
		
		

	}

}
