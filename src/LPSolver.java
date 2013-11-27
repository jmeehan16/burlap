import com.joptimizer.functions.*;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.PrimalDualMethod;


public class LPSolver{
	
	
	public static double[] toyexample() throws Exception{
		

		LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { 2, 1 }, 0);
		
		ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[2];
		inequalities[0] = new LinearMultivariateRealFunction(new double[] { -1,  0 }, 0);
		inequalities[1] = new LinearMultivariateRealFunction(new double[] {  0, -1 }, 0);
		
		OptimizationRequest or = new OptimizationRequest();
		or.setF0(objectiveFunction);
		or.setInitialPoint(new double[] { 0.9, 0.1 });
		or.setFi(inequalities);
		// Equality constraints
		or.setA(new double[][] { { 1, 1} });
		or.setB(new double[] { 1 });
		or.setTolerance(1.E-9);
		
		// optimization
		PrimalDualMethod opt = new PrimalDualMethod();
		opt.setOptimizationRequest(or);
		int returnCode = opt.optimize();
		
		double[] sol = opt.getOptimizationResponse().getSolution();
		
		return sol;
		
	}
	
	public static void main(String []args) throws Exception{
	
		toyexample();
		
		System.out.println("Hello");
	}
}

