package burlap.nash;

import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.PrimalDualMethod;


public class LPToySolver {

	/**
	 * @param args
	 */
	
	
     public static void toyexample() throws Exception{
		

		// Objective function (plane)
				LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -1., -1. }, 4);

				//inequalities (polyhedral feasible set G.X<H )
				ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];
				double[][] G = new double[][] {{4./3., -1}, {-1./2., 1.}, {-2., -1.}, {1./3., 1.}};
				double[] H = new double[] {2., 1./2., 2., 1./2.};
				inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
				inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
				inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
				inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
				
				//optimization problem
				OptimizationRequest or = new OptimizationRequest();
				or.setF0(objectiveFunction);
				or.setFi(inequalities);
				//Ax = b constraing ?
				
				/*
				or.setA(new double[][] { { 1, 1} });
				or.setB(new double[] { 1 }); */
				
				//or.setInitialPoint(new double[] {0.0, 0.0});//initial feasible point, not mandatory
				or.setToleranceFeas(1.E-9);
				or.setTolerance(1.E-9);
				
				//optimization
				JOptimizer opt = new JOptimizer();
				opt.setOptimizationRequest(or);
				int returnCode = opt.optimize();
				double[] sol = opt.getOptimizationResponse().getSolution();
				System.out.println(sol[0] + "\n" + sol[1]);
		
		
		
	}
     
     //Tring to implement a diferent LP
     
     public static void toyexample2() throws Exception{
 		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -3., -2. }, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[2];
			double[][] G = new double[][] {{1., 2}, {1., -1.}};
			double[] H = new double[] {4., 1.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			/*
			or.setA(new double[][] { { 1, 1} });
			or.setB(new double[] { 1 }); */
			
			//or.setInitialPoint(new double[] {0.0, 0.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1]);

 		
 		
 	}
     
     
     public static void toyexample3() throws Exception{
  		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -4., -6. }, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[3];
			double[][] G = new double[][] {{-1., 1.}, {1., 1.} , {2., 5.}};
			double[] H = new double[] {11., 27., 90.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			/*
			or.setA(new double[][] { { 1, 1} });
			or.setB(new double[] { 1 }); */
			
			//or.setInitialPoint(new double[] {0.0, 0.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1]);

 		
 		
 	}
     
    // Added >=0 condition
     public static void toyexample4() throws Exception{
   		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { 5., 7. }, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[6];
			double[][] G = new double[][] {{-2., -3.}, {3., -1.} , {-1., 1.}, {2.,5.},{-1., 0.},{0., -1.}};
			double[] H = new double[] {-6., 15., 4., 47., 0., 0.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
			inequalities[4] = new LinearMultivariateRealFunction(G[4], -H[4]);
			inequalities[5] = new LinearMultivariateRealFunction(G[5], -H[5]);
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			/*
			or.setA(new double[][] { { 1, 1} });
			or.setB(new double[] { 1 }); */
			
			//or.setInitialPoint(new double[] {0.0, 2.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1]);

 		
 		
 	}
     
     
     // Added >=0 condition
     public static void toyexample5() throws Exception{
   		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -1., -1. }, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];
			double[][] G = new double[][] {{1., 2.}, {2., 1.},{-1., 0.},{0., -1.}};
			double[] H = new double[] {1., 1., 0., 0.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			/*
			or.setA(new double[][] { { 1, 1} });
			or.setB(new double[] { 1 }); */
			
			//or.setInitialPoint(new double[] {0.0, 2.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1]);

 		
 		
 	}
     
     
     // With three variables and >=0
     public static void toyexample6() throws Exception{
   		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { -20., -10., -15. }, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[7];
			double[][] G = new double[][] {{3., 2., 5.}, {2., 1., 1.},{1., 1.,  3.},{5., 2., 4.},{-1., 0. , 0.},{0., -1., 0.},{0., 0., -1.}};
			double[] H = new double[] {55., 26., 30., 57., 0., 0., 0.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
			inequalities[4] = new LinearMultivariateRealFunction(G[4], -H[4]);
			inequalities[5] = new LinearMultivariateRealFunction(G[5], -H[5]);
			inequalities[6] = new LinearMultivariateRealFunction(G[6], -H[6]);
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			/*
			or.setA(new double[][] { { 1, 1} });
			or.setB(new double[] { 1 }); */
			
			//or.setInitialPoint(new double[] {0.0, 2.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1] + "\n" + sol[2]);

 		
 		
 	}
 
     
     
     // Two variables with objective function missing on one variable
     public static void toyexample7() throws Exception{
   		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] {0., 1.}, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[6];
			double[][] G = new double[][] {{-1., 0.},{-3., 1.},{-1., -1.},{1., -2.},{-1., 0.},{0., -1.}};
			double[] H = new double[] {2., 0., 6., 0., 0., 0.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
			inequalities[4] = new LinearMultivariateRealFunction(G[4], -H[4]);
			inequalities[5] = new LinearMultivariateRealFunction(G[5], -H[5]);
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			/*
			or.setA(new double[][] { { 1, 1} });
			or.setB(new double[] { 1 }); */
			
			//or.setInitialPoint(new double[] {0.0, 2.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1] );

 		
 		
 	}
     
     public static void NashRowexample1() throws Exception{
    		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] {0., 0., -1.}, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];
			double[][] G = new double[][] {{-3., 2., 1.},{1., -1., 1.},{-1., 0 ,0},{0., -1., 0.}};
			double[] H = new double[] {0., 0., 0., 0.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
			
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			
			or.setA(new double[][] { { 1, 1, 0} });
			or.setB(new double[] { 1 }); 
			
			//or.setInitialPoint(new double[] {0.0, 2.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1] + "\n" + sol[2]);

 		
 		
 	}
     
     public static void NashColumnexample1() throws Exception{
 		

    	 LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] {0., 0., -1.}, 0);  
    		//inequalities (polyhedral feasible set G.X<H )
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[4];
			double[][] G = new double[][] {{3., -1., 1.},{-2., 1., 1.},{-1., 0 ,0},{0., -1., 0.}};
			double[] H = new double[] {0., 0., 0., 0.};
			inequalities[0] = new LinearMultivariateRealFunction(G[0], -H[0]);
			inequalities[1] = new LinearMultivariateRealFunction(G[1], -H[1]);
			inequalities[2] = new LinearMultivariateRealFunction(G[2], -H[2]);
			inequalities[3] = new LinearMultivariateRealFunction(G[3], -H[3]);
			
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setFi(inequalities);
			//Ax = b constraing ?
			
			
			or.setA(new double[][] { { 1, 1, 0} });
			or.setB(new double[] { 1 }); 
			
			//or.setInitialPoint(new double[] {0.0, 2.0});//initial feasible point, not mandatory
			or.setToleranceFeas(1.E-9);
			or.setTolerance(1.E-9);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			int returnCode = opt.optimize();
			double[] sol = opt.getOptimizationResponse().getSolution();
			System.out.println(sol[0] + "\n" + sol[1] + "\n" + sol[2]);

 		
 		
 	}
     
     
     
     
	public static void main(String[] args) throws Exception {
		
		toyexample();
		System.out.println("======================");
		toyexample2();
		System.out.println("======================");
		toyexample3();
		System.out.println("======================");
		toyexample4();
		System.out.println("======================");
		toyexample5();
		System.out.println("======================");
		toyexample6();
		System.out.println("======================");
		toyexample7();
		System.out.println("======Nash Solvers=========");
		NashRowexample1();
		System.out.println("======================");
		NashColumnexample1();
		
		// TODO Auto-generated method stub

	}

}

