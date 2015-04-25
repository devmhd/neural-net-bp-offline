import java.util.ArrayList;


public class HiddenLayer {
	
	public ArrayList<Neuron> neurons;

	
	public HiddenLayer(int prevLayerSize, int size){
		

		neurons = new ArrayList<Neuron>();
		for(int i=0; i<size; ++i){
			
			neurons.add(new Neuron(Neuron.TYPE_OUTPUT, prevLayerSize, i));
			
		}
		
	}
	
	public ArrayList<Float> forwardPass(ArrayList<Float> inputs){
		
		
		ArrayList<Float> outputs = new ArrayList<Float>();
		
		for(Neuron neuron : neurons){
			
			outputs.add( neuron.forwardPass(inputs) );
			
		}
		
		return outputs;
		
		
	}
	
	public void backwardPass(float[] targetValues, HiddenLayer prevLayer){
		
		
		
	}

}
