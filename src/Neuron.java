import java.util.ArrayList;


public class Neuron {
	
	public static final int TYPE_HIDDEN = 0;
	public static final int TYPE_OUTPUT = 1;
	
	public int type;
	public int n_incoming_edge;
	int serial;
	
	public float[] weights;
	public float[] oldWeights;
	
	public float output;
	
	public float error;
	
	public Neuron(int type, int n_incoming_edge, int serial){
		
		this.type = type;
		this.n_incoming_edge = n_incoming_edge;
		this.serial = serial;
		
		weights = new float[n_incoming_edge];
		oldWeights = new float[n_incoming_edge];
		
		for(int i=0; i<n_incoming_edge; ++i){
			
			// between -1 and 1
			weights[i] = (float) (1 - Math.random() * 2);
		}
		
	}
	
	public void calculateError(int target){
		
		if(type == TYPE_HIDDEN) System.out.println("ERROR: Hidden neuron called to calculate output error");
		error =  output * (1 - output) * ((float)target - output);
		
	}
	
	public void calculateError(Neuron[] laterNeurons){
		
		if(type == TYPE_OUTPUT) System.out.println("ERROR: Output neuron called to calculate hidden neuron error");
		//error =  output * (1 - output) * ((float)target - output);
		
		float errSum = 0;
		
		for(int i=0; i<laterNeurons.length; ++i){
			
			errSum += laterNeurons[i].error * laterNeurons[i].oldWeights[serial];
		}
		
		error = output * (1-output) * errSum;
		
	}
	
	public void updateWeightsInHiddenAndOutput(Neuron[] prevNeurons){
		
		for(int i=0; i<n_incoming_edge; ++i){
			
			oldWeights[i] = weights[i];
			
		}
		
		for(int i=0; i<n_incoming_edge; ++i){
			
			weights[i] = oldWeights[i] + G.LEARNING_RATE * error * prevNeurons[i].output;
		}
		
	}
	
	public void updateWeightsInInput(float[] prevOutputs){
		
		for(int i=0; i<n_incoming_edge; ++i){
			
			oldWeights[i] = weights[i];
			
		}
		
		for(int i=0; i<n_incoming_edge; ++i){
			
			weights[i] = oldWeights[i] + G.LEARNING_RATE * error * prevOutputs[i];
		}
		
	}
	
	public float forwardPass(float[] inputs){
		
		
		if(inputs.length != n_incoming_edge){
			System.out.println("ERROR: Wrong number of inputs");
			return -1;
		}
		
		float sum = 0;
		
		for(int i=0; i<n_incoming_edge; ++i){
			
			sum += inputs[i] * weights[i];
			
			
		}
		
		sum += G.BIAS;
		
		output = sigmoid(sum);
		
		return output;
		
	}
	
	public float sigmoid(float x){
		
		return (float) (1.0/(1.0 + Math.exp(-x)));
	}

}
