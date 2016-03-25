import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Population {
	
	private int maxPopulationSize;
	private int generation;
	private ArrayList<Tour> population = new ArrayList<Tour>();
	private ArrayList<Tour> prevMatingPool = new ArrayList<Tour>();
	private ArrayList<Tour> matingPool = new ArrayList<Tour>();
	
	
	//Generation 0 Population constructor
	public Population(int maxPopulationSize){
		this.maxPopulationSize = maxPopulationSize;
		generatePopulation();		
		Random random = new Random();
		int k = random.nextInt(19) + 1;
		tournamentSelect(20);
	}
	
	//Generation 1 + Population constructor
	public Population(ArrayList<Tour> prevMatingPool, String crossoverType, double Pc, String mutationType, double Pm, int maxPopulationSize){
		this.prevMatingPool = prevMatingPool;
		this.maxPopulationSize = maxPopulationSize;
		Random rand = new Random();
		double prob = rand.nextDouble();
		double Pr = 1 - Pc - Pm;
		int i = 0;
		while(!this.prevMatingPool.isEmpty()){
			if(this.prevMatingPool.size() < 2){
				Tour tour = this.prevMatingPool.get(0);
				this.prevMatingPool.remove(0);
				addToPop(tour);
			}else if(prob < Pc){
				//crossover
				int one = rand.nextInt(this.prevMatingPool.size() - 1);
				int two = rand.nextInt(this.prevMatingPool.size() - 1);
				Tour parent1 = this.prevMatingPool.get(one);
				Tour parent2 = this.prevMatingPool.get(two);
				this.prevMatingPool.remove(one);
				this.prevMatingPool.remove(two);
				
				Crossover crossover = new Crossover(crossoverType, parent1, parent2);
				ArrayList<ArrayList<Integer>> children = crossover.getChildren();
				for(int j=0; j<children.size(); j++){
					Tour tour = new Tour(children.get(j));
					addToPop(tour);
				}
			}else if(prob >= Pc && prob < (Pc + Pm)){
				//mutation
				int one = rand.nextInt(this.prevMatingPool.size() - 1);
				Tour tour = this.prevMatingPool.get(one);
				this.prevMatingPool.remove(one);
				Mutation mutation = new Mutation(mutationType, tour);
				ArrayList<Integer> mutatedTour = mutation.getTour();
				Tour newTour = new Tour(mutatedTour);
				addToPop(newTour);		
			}else{
				//reproduction
				int one = rand.nextInt(this.prevMatingPool.size() - 1);
				Tour tour = this.prevMatingPool.get(one);
				this.prevMatingPool.remove(one);
				addToPop(tour);
			}
			i++;
		}
		Random random = new Random();
		int k = random.nextInt(19)+1;
		tournamentSelect(20);
	}
	
	public ArrayList<Tour> getMatingPool(){
		return this.matingPool;
	}
	
	//Adds tour to population
	public void addToPop(Tour tour){
		if(this.population.size() < this.maxPopulationSize){
		this.population.add(tour);
		}
	}
	
	//Tournament Selection
	public void tournamentSelect(int k){
		for(int i=0; i<population.size();i++){
			ArrayList<Tour> holder = new ArrayList<Tour>();
			for(int j=0; j<k; j++){
				Random random = new Random();
				int x = random.nextInt(population.size()-1);
				holder.add(population.get(x));
			}
			
			Tour tour =  getFittestTour(holder);
			matingPool.add(tour);
		}
	}
	
	//Generates initial population
	public void generatePopulation() {
		for (int i = 0; i < maxPopulationSize; i++) {
			Tour tour = new Tour(100);
			population.add(tour);
		}
	}
	
	public double getAvgFitness(){
		int sum=0;
		for(int i=0; i<population.size(); i++){
			sum += population.get(i).getFitness();
		}
		
		double avg = sum/population.size();
		return avg;
	}
	
	public ArrayList<Tour> getAllToursInPopulation(){
		return population;
	}
	
	//Gets fittest tour of list of tours
	public static Tour getFittestTour(ArrayList<Tour> cities){
		Tour fittestTour = cities.get(0);
		for(int i=0; i< cities.size(); i++){
			Tour tour = cities.get(i);
			if(tour.getFitness() < fittestTour.getFitness()){
				fittestTour = tour;
			}			
		}
	
		return fittestTour;
	}

}
