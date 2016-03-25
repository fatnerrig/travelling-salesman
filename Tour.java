import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Tour {
	
	private int size;
	private ArrayList<Integer> cities;
	private double fitness;
	
	public Tour(int size){
		this.size = size;
		ArrayList<Integer> cities = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			int city = i + 1;
			cities.add(city);
		}
        shuffleTour(cities);
		generateFitness();
	}
	
	public Tour(ArrayList<Integer> cities){
		this.cities = cities;
		this.size = cities.size();
		generateFitness();
	}
	
	//shuffles tour based on seed
	public void shuffleTour(ArrayList<Integer> cities){
		Collections.shuffle(cities, is10124845.random);
		this.cities = cities;
	}
	
	public void generateFitness(){
		double fitness = 0;
		for(int i =0; i < size; i++){
			int town1 = cities.get(i);
			int town2;
			if(i == 99){
				town2 = cities.get(0);
			}else{
				town2 = cities.get(i + 1);
			}

			double distance = TSP100ROI.distance(town1, town2);
			fitness += distance;
		}
		
		this.fitness = fitness;
	}
	
	public ArrayList<Integer> getCities(){
		return cities;		
	}
	
	public double getFitness(){
		return fitness;		
	}
	
	public int getSize(){
		return size;		
	}
	
	public String outPutTour(){
		String output = "";
		for(int i = 0; i<cities.size(); i++){
			output += String.valueOf(cities.get(i));
		}
		
		//output += " " + this.getFitness();
		return output;
	}

}
