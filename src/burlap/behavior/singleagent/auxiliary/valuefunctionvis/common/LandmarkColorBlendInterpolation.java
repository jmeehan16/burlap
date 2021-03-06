package burlap.behavior.singleagent.auxiliary.valuefunctionvis.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * A {@link ColorBlend} instance that takes as input a set of "landmark" {@link java.awt.Color} objects and corresponding values between them.
 * For a given value, it interpolates between the two landmarks values than lower bound and upper bound the input. The interpolation is by default
 * linear. However, it can also be specified to first find the normalized distance between the bounding landmark values and raise it to a power, which
 * may produce more obvious perceptual changes to small changes in value.
 * @author James MacGlashan
 *
 */
public class LandmarkColorBlendInterpolation implements ColorBlend {

	/**
	 * The value position of each landmark
	 */
	protected List <Double>			landmarkValues;
	
	/**
	 * The landmark colors
	 */
	protected List <Color>			landmarkColors;
	
	/**
	 * The power to raise the normalized distance
	 */
	protected double				polyDegree = 1.;
	
	
	public LandmarkColorBlendInterpolation() {
		this.landmarkValues = new ArrayList<Double>();
		this.landmarkColors = new ArrayList<Color>();
	}
	
	/**
	 * Initializes the color blend with a power to raise the normalized distance of values.
	 * @param polyDegree
	 */
	public LandmarkColorBlendInterpolation(double polyDegree) {
		this.landmarkValues = new ArrayList<Double>();
		this.landmarkColors = new ArrayList<Color>();
		this.polyDegree = polyDegree;
	}
	
	/**
	 * Sets the color blend to raise the normalized distance of values to the given degree.
	 * @param polyDegree the power to raise the normalized distance
	 */
	public void setPolynomialDegree(double polyDegree){
		this.polyDegree = polyDegree;
	}
	
	/**
	 * Returns the power to raise the normalized distance
	 * @return
	 */
	public double getPolynomialDegree(){
		return this.polyDegree;
	}
	
	
	/**
	 * Adds the next landmark between which interpolation should occur. Assumes that the value val is greater than the last landmark value added.
	 * @param val the value of the landmark
	 * @param c the color for the landmark
	 */
	public void addNextLandMark(double val, Color c){
		this.landmarkValues.add(val);
		this.landmarkColors.add(c);
	}
	
	
	@Override
	public void rescale(double minV, double maxV) {
		double curMin = this.landmarkValues.get(0);
		double curMax = this.landmarkValues.get(this.landmarkValues.size()-1);
		
		if(curMin == minV && curMax == maxV){
			return ; //already correctly scaled
		}
		
		double curRange = curMax - curMin;
		double newRange = maxV - minV;
		
		for(int i = 0; i < this.landmarkValues.size(); i++){
			double v = this.landmarkValues.get(i);
			double t = (v - curMin) / curRange;
			double nv = t*newRange + minV;
			this.landmarkValues.set(i, nv);
		}
		
		
	}

	@Override
	public Color color(double v) {
		
		//end point?
		if(v <= landmarkValues.get(0)){
			return landmarkColors.get(0);
		}
		if(v >= landmarkValues.get(landmarkValues.size()-1)){
			return landmarkColors.get(landmarkColors.size()-1);
		}
		
		//which is the interpolation end point?
		int ePoint = 1;
		for(int i = 1; i < landmarkValues.size(); i++){
			ePoint = i;
			if(landmarkValues.get(i) > v){
				break ;
			}
			
		}
		
		double sv = landmarkValues.get(ePoint-1);
		double ev = landmarkValues.get(ePoint);
		double vRange = ev - sv;
		
		double t = (v - sv) / vRange;
		t = Math.pow(t, this.polyDegree);
		
		Color sC = landmarkColors.get(ePoint-1);
		Color eC = landmarkColors.get(ePoint);
		
		float [] sColorComp = sC.getColorComponents(null);
		float [] eColorComp = eC.getColorComponents(null);
		
		float red = this.interpolate(sColorComp[0], eColorComp[0], (float)t);
		float green = this.interpolate(sColorComp[1], eColorComp[1], (float)t);
		float blue = this.interpolate(sColorComp[2], eColorComp[2], (float)t);
		
		Color finalColor = new Color(red, green, blue);
		
		return finalColor;
	}
	
	
	/**
	 * Returns the point that is a normalized distance t between s and e
	 * @param s the start value
	 * @param e the end value
	 * @param t the normalized distance between s and e
	 * @return the point that is a normalized distance t between s and e
	 */
	protected float interpolate(float s, float e, float t){
		float range = e - s;
		return s + t*range;
	}

	

}
