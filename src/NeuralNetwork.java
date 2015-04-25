
public class NeuralNetwork {

	private Neuron[][] neurons;
	private Neuron[] outputNeurons;

	private int n_inputs;
	private int n_outputs;
	int n_hidden_layers; 
	int n_neuron_per_layer;

	public NeuralNetwork(int n_hidden_layers, int n_neuron_per_layer, int n_inputs, int n_outputs){

		this.n_inputs = n_inputs;
		this.n_outputs = n_outputs;
		this.n_hidden_layers = n_hidden_layers;
		this.n_neuron_per_layer = n_neuron_per_layer;

		neurons = new Neuron[n_hidden_layers][n_neuron_per_layer];
		outputNeurons = new Neuron[n_outputs];

		for(int j=0; j<n_neuron_per_layer; j++){
			neurons[0][j] = new Neuron(Neuron.TYPE_HIDDEN, n_inputs, j);
		}

		for(int i=1; i< n_hidden_layers; i++){
			for(int j=0; j<n_neuron_per_layer; j++){
				neurons[i][j] = new Neuron(Neuron.TYPE_HIDDEN, n_neuron_per_layer, j);
			}
		}

		for(int j=0; j<n_outputs; ++j){

			outputNeurons[j] = new Neuron(Neuron.TYPE_OUTPUT, n_neuron_per_layer, j);

		}



	}

	public boolean canCorrectlyClassify(DataInstance data){


		// Forward Pass first hiddden layer
		for(int i=0; i<n_neuron_per_layer; ++i){

			neurons[0][i].forwardPass(new float[]{data.attrib1, data.attrib2, data.attrib3});

		}


		// Forward pass next hidden layers
		for(int layerIndex = 1; layerIndex < n_hidden_layers; layerIndex++){


			float[] results = new float[n_neuron_per_layer];

			for(int j=0; j<n_neuron_per_layer; ++j){

				results[j] = neurons[layerIndex-1][j].output;

			}

			for(int j=0; j<n_neuron_per_layer; ++j){

				neurons[layerIndex][j].forwardPass(results);
			}

		}


		//Forward Pass output layer 
		float[] results = new float[n_neuron_per_layer];
		for(int j=0; j<n_neuron_per_layer; ++j){

			results[j] = neurons[n_hidden_layers-1][j].output;

		}

		for(int j=0; j<n_outputs; ++j){

			outputNeurons[j].forwardPass(results);

		}
		
		System.out.println("" + outputNeurons[0].output + "\t" + outputNeurons[1].output + "\t" + outputNeurons[2].output);
		
		
		int output1, output2, output3;
		
		if(Math.abs(1-outputNeurons[0].output) < G.ERROR_TOLERANCE) output1 = 1;
		else if(Math.abs(0-outputNeurons[0].output) < G.ERROR_TOLERANCE) output1 = 0;
		else output1 = 100;
		
		if(Math.abs(1-outputNeurons[1].output) < G.ERROR_TOLERANCE) output2 = 1;
		else if(Math.abs(0-outputNeurons[1].output) < G.ERROR_TOLERANCE) output2 = 0;
		else output2 = 100;
		
		if(Math.abs(1-outputNeurons[2].output) < G.ERROR_TOLERANCE) output3 = 1;
		else if(Math.abs(0-outputNeurons[2].output) < G.ERROR_TOLERANCE) output3 = 0;
		else output3 = 100;
		
		if(output1 == 1 && output2 == 0 && output3 == 0 && data.classId == 1) return true;
		if(output1 == 0 && output2 == 1 && output3 == 0 && data.classId == 2) return true;
		if(output1 == 0 && output2 == 0 && output3 == 1 && data.classId == 3) return true;
		
		return false;
		
		

	}

	public float getCurrentError(){

		float error = 0;

		for(int i=0; i<n_hidden_layers; ++i){
			for(int j=0; j<n_neuron_per_layer; ++j){
				error += Math.abs(neurons[i][j].error);
			}
		}

		for(int j=0; j<n_outputs; ++j){
			error += Math.abs(outputNeurons[j].error);
		}

		return error;


	}

	public void singlePass(DataInstance dataInstance){



		// Forward Pass first hiddden layer
		for(int i=0; i<n_neuron_per_layer; ++i){

			neurons[0][i].forwardPass(new float[]{dataInstance.attrib1, dataInstance.attrib2, dataInstance.attrib3});

		}


		// Forward pass next hidden layers
		for(int layerIndex = 1; layerIndex < n_hidden_layers; layerIndex++){


			float[] results = new float[n_neuron_per_layer];

			for(int j=0; j<n_neuron_per_layer; ++j){

				results[j] = neurons[layerIndex-1][j].output;

			}

			for(int j=0; j<n_neuron_per_layer; ++j){

				neurons[layerIndex][j].forwardPass(results);
			}

		}


		//Forward Pass output layer 
		float[] results = new float[n_neuron_per_layer];
		for(int j=0; j<n_neuron_per_layer; ++j){

			results[j] = neurons[n_hidden_layers-1][j].output;

		}

		for(int j=0; j<n_outputs; ++j){

			outputNeurons[j].forwardPass(results);

		}

		int target1, target2, target3;
		target1 = target2 = target3 = 0;

		if(dataInstance.classId == 1) target1 = 1;
		if(dataInstance.classId == 2) target2 = 1;
		if(dataInstance.classId == 3) target3 = 1;

		outputNeurons[0].calculateError(target1);
		outputNeurons[1].calculateError(target2);
		outputNeurons[2].calculateError(target3);


		outputNeurons[0].updateWeightsInHiddenAndOutput(neurons[n_hidden_layers-1]);
		outputNeurons[1].updateWeightsInHiddenAndOutput(neurons[n_hidden_layers-1]);
		outputNeurons[2].updateWeightsInHiddenAndOutput(neurons[n_hidden_layers-1]);



		for(int layerIndex = n_hidden_layers-1; layerIndex>0; --layerIndex){

			Neuron[] laterNeurons;

			if(layerIndex == n_hidden_layers-1) laterNeurons = outputNeurons;
			else laterNeurons = neurons[layerIndex+1];

			for(int j=0; j<n_neuron_per_layer; ++j){

				neurons[layerIndex][j].calculateError(laterNeurons);

			}

			for(int j=0; j<n_neuron_per_layer; ++j){

				neurons[layerIndex][j].updateWeightsInHiddenAndOutput(neurons[layerIndex-1]);

			}

		}


		Neuron[] nextNeurons;

		if(n_hidden_layers>1) nextNeurons = neurons[1];
		else nextNeurons = outputNeurons;

		for(int j=0; j<n_neuron_per_layer; ++j){



			neurons[0][j].calculateError(nextNeurons);

			neurons[0][j].updateWeightsInInput(new float[]{

					dataInstance.attrib1,
					dataInstance.attrib2,
					dataInstance.attrib3,
			});

		}



	}

}
