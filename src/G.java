import java.util.ArrayList;


public class G {
	

	public static final float BIAS = (float) 0.0;
	
	public static final int N_HIDDEN_LAYERS = 2;
	public static final int N_NEURON_PER_LAYER = 3;
	
	public static final double ERROR_TOLERANCE = 0.1;
	
	public static final double ERROR_DIFF_THRESHOLD = 1E-6;
	
	public static final float LEARNING_RATE = (float) 1.5;
	
	public static ArrayList<DataInstance> allTrainingData;
	public static ArrayList<DataInstance> allTestData;

}
