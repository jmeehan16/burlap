package burlap.nash;

import java.io.PrintStream;
import java.lang.*;

import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.PrimalDualMethod;


public class TwoPersonZeroSumGameNash {
	
	 public static double[] RowSolver(double[][] P) throws Exception{
		 
		 //Setting up the objective function
        double[] Objectivefunction = new double[P.length + 1];
        Objectivefunction[P.length] = -1.;
        
        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(Objectivefunction, 0);
        
        /*System.out.println("\n Printing ObjectiveFunction:");
        for (int i = 0; i <= P.length ; i++){
			 System.out.print(Objectivefunction[i] + "\t");
		 }*/
        
		 
		 
		 //Setting up G and H
        // # of inequalities= number of columns + number of rows (for the >=0 constraint)
        int inequalitycount = P[0].length + P.length;
        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[inequalitycount];
        double[][] G = new double[inequalitycount][P.length + 1];
		 
        //Setting up the >=0 constratint
        
        for(int i = inequalitycount - P.length,j = 0; i < inequalitycount; i++, j++ ){
       	  
       	 G[i][j] = -1.;
       	 
        }
        
        //Setting up the the last coordinate as 1 for the payoff constraints (corresponding to z)
		 
		  for(int i = 0; i < inequalitycount - P.length; i++){
      	  
	        	 G[i][P.length] = 1.;
	        	 
	         }
		  
		  /*System.out.println("\n Printing G before filling in the payoff constraint:");
			 for (int i = 0; i < inequalitycount ; i++){
				 for(int j = 0; j < P.length +1; j++){
					 System.out.print(G[i][j] + "\t"); 
				 }
				 System.out.print("\n"); 
				 
			 }*/
			 
		  
		  //Setting up the rest of the payoff constraints: Needs careful look
		  
	      double[][] PTransposed = transpose(P);		 
			 
		  for(int i =0; i< PTransposed.length; i++){
			  for(int j = 0; j<PTransposed[i].length ; j++){
				  G[i][j]  =  -PTransposed[i][j];
			  }
		  }
		  
		  //Setting up H
		 
		  double[] H = new double[inequalitycount] ;
		  
		 /* System.out.println("\n Printing H:");
	         for (int i = 0; i < inequalitycount ; i++){
				 System.out.print(H[i] + "\t");
			 }*/
        
        /* System.out.println("\n Printing G:");
		 for (int i = 0; i < inequalitycount ; i++){
			 for(int j = 0; j < P.length +1; j++){
				 System.out.print(G[i][j] + "\t"); 
			 }
			 System.out.print("\n"); 
			 
		 }*/
		 
		 
		 //setting up inequalitues
		 
		 for(int i=0; i < inequalitycount; i++){
			 inequalities[i] = new LinearMultivariateRealFunction(G[i], -H[i]);
		 }
		 
		//optimization problem
		OptimizationRequest or = new OptimizationRequest();
		or.setF0(objectiveFunction);
		or.setFi(inequalities);
		 
		 
		 //Equality constraint; setting up A and b
		 
		 double[][] A = new double[1][P.length+1];
		 
		 for (int i = 0; i < P.length; i++){
			 A[0][i] = 1;
		 }
		 
		 or.setA(A);
		 or.setB(new double[] { 1 }); 
		 
		 
		 
		/* System.out.println("\n Printing A:");
		 for (int i = 0; i < P.length+1 ; i++){
			 System.out.print(A[0][i] + "\t");
		 }*/
		 
		 
		
		or.setToleranceFeas(1.E-9);
		or.setTolerance(1.E-9);
			
		//optimization
		JOptimizer opt = new JOptimizer();
		opt.setOptimizationRequest(or);
		int returnCode = opt.optimize();
		double[] sol = opt.getOptimizationResponse().getSolution();
		//System.out.println(sol[0] + "\n" + sol[1] + "\n" + sol[2]);
		return(sol);
		 
		 
	 
	 }
	 
	 
	 public static double[][] transpose(double[][] original){
		 
		 double[][] transposed = new double[original[0].length][original.length];

		 
		 
	     for (int i = 0; i < original[0].length; i++) {
	    	 
	    	 for (int j = 0; j < original.length; j++) {
	    		 
	    		  transposed[i][j] = original[j][i];
	    		  
	              }
	     }
	                
	            
	     /*for (int i = 0; i < transposed.length; i++) {
	            for (int j = 0; j < transposed[i].length; j++) {
	                System.out.print(transposed[i][j] + " ");
	            }
	            System.out.print("\n");
	        }*/
	     
	     return(transposed);
	       
		 
		 
	 }
	 
	public static void ComputeNash(double[][] A) throws Exception{
		
		 double[] rowresult = RowSolver(A);
		 System.out.println("Row Player:");
		 for(int i=0; i<rowresult.length;i++){
			 System.out.println("x"+ i + ":" + rowresult[i]);
		 }
		 
		 double[] columnresult = RowSolver(transpose(A));
		 System.out.println("Column Player:");
		 for(int i=0; i<columnresult.length;i++){
			 System.out.println("y"+ i + ":" + columnresult[i]);
		 }
		 
		
	}
	
	public static double[] getNash(double[][] A) throws Exception{
		
		 double[] rowresult = RowSolver(A);
		 double[] columnresult = RowSolver(transpose(A));
		 double[] result = new double[2];
		 result[0] = rowresult[rowresult.length];
		 result[1] = columnresult[columnresult.length];
		 return result;
	}
	 
	 
	
	 
	 public static void main(String []args) throws Exception{
		 
		 
		 
		 final PrintStream err = new PrintStream(System.err);
		 System.setErr(new PrintStream("/dev/null"));

		// Presidential Election 
			double A1[][] = {{3.,-1.},{-2.,1.}};
			System.out.println("Presedential Election:");
			ComputeNash(A1); 
			System.out.println("\n"); 
			//Rock paper Scissor
			double A2[][] = {{0.,-1., 1.},{1., 0., -1.},{-1., 1., 0.}};
			System.out.println("RPS");
			ComputeNash(A2); 
			System.out.println("\n");
			// A 3rd game
			
			double A3[][] ={{0., 10.},{5., 0.}};
			System.out.println("3rd game");
			ComputeNash(A3);
			System.out.println("\n");
		 System.setErr(err);
		
		 
		 
		 //transpose(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } });
		 	 
		 //Asynmmetric
      /* double A3[][] = {{0.,-1., 1.},{1., 0., -1.},{-1., 1., 0.},{1.,-1.,0}};
		 
		 double[] result3 = RowSolver(A3);
		 
		 for(int i=0; i<result3.length;i++){
			 System.out.println("x"+ i + ":" + result3[i]);
		 }*/
		 
		 
		 
		 
		 
	 }
			
	
	
	
	
	
	
	

}

