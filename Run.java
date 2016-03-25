import java.util.*;
import java.io.*;

public class Run {
	private static int maxPopulationSize;
	private static int maxNumOfGen;
	private static String crossoverType;
	private static double crossoverRate;
	private static String mutationType;
	private static double mutationRate;
	private static String filename;
	private static long seed = 16796789;
	private static int generationNumber = 0;
	private static ArrayList<Population> populationHolder = new ArrayList<Population>();
	static Random random = new Random();

	public static void main(String[] args) throws IOException {
		random.setSeed(16796789);
		if (args.length != 7) {
			System.out
					.println("Proper Usage is: java filename {pop size} {generations} {crossover type} {crossover rate} {mutation type} {mutation rate} {output file}");
			System.exit(1);
		}

		try {
			maxPopulationSize = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Error: Population size must be an integer");
			System.exit(1);
		}

		try {
			maxNumOfGen = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err
					.println("Error: Number of generations must be an integer");
			System.exit(1);
		}

		crossoverType = args[2];

		try {
			crossoverRate = Double.parseDouble(args[3]);
		} catch (NumberFormatException e) {
			System.err.println("Error: Crossover rate must be a double");
			System.exit(1);
		}

		mutationType = args[4];

		try {
			mutationRate = Double.parseDouble(args[5]);
		} catch (NumberFormatException e) {
			System.err.println("Error: Mutation rate must be a double");
			System.exit(1);
		}

		filename = args[6];
		
		//Goes through generations, creates populations.
		generationNumber = 0;
		while(generationNumber < maxNumOfGen){
			Population pop;
			if(generationNumber == 0){
				pop = new Population(maxPopulationSize);
				populationHolder.add(pop);
			}else{
				Population previousPop = populationHolder.get(generationNumber-1);
				ArrayList<Tour> matingPool = previousPop.getMatingPool();
				pop = new Population(matingPool, crossoverType, crossoverRate, mutationType, mutationRate, maxPopulationSize); //start over
				populationHolder.add(pop);
			}
			//System.out.println(pop.
			//System.out.println(generationNumber + ": " + Population.getFittestTour(pop.getAllToursInPopulation()).getFitness());
			//System.out.println(generationNumber + ": " + Population.getFittestTour(pop.getAllToursInPopulation()).getSize());
			//System.out.println(generationNumber + " " + pop.getAvgFitness() + " " + Population.getFittestTour(pop.getAllToursInPopulation()).getFitness() + " " + Population.getFittestTour(pop.getAllToursInPopulation()).outPutTour());
			generationNumber++;
		}
		
		outputPopulation();
		
	}
	
	public static long getSeed(){
		return seed;
	}
	
	public static void outputPopulation() throws IOException{
		FileWriter fstream = new FileWriter(filename);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(maxPopulationSize + " " + maxNumOfGen + " " + crossoverType
				+ " " + crossoverRate + " " + mutationType + " " + mutationRate
				+ " " + filename + " " + seed);
		out.newLine();
		for (int j = 0; j < populationHolder.size(); j++) {
			Population pop = populationHolder.get(j);
			out.write(String.valueOf(j + " " + pop.getAvgFitness() + " " + Population.getFittestTour(pop.getAllToursInPopulation()).getFitness() + " " + Population.getFittestTour(pop.getAllToursInPopulation()).outPutTour()));
			out.newLine();
		}
		out.close();
	}
	
//	public static void outputPopulation() throws IOException {
//		FileWriter fstream = new FileWriter(filename);
//		BufferedWriter out = new BufferedWriter(fstream);
//		out.write(maxPopulationSize + " " + maxNumOfGen + " " + crossoverType
//				+ " " + crossoverRate + " " + mutationType + " " + mutationRate
//				+ " " + filename + " " + seed);
//		out.newLine();
//		for (int j = 0; j < populationHolder.size(); j++) {
//			Population currentPopulation = populationHolder.get(j);
//			out.write("Generation: " + j);
//			out.newLine();
//			for (int m = 0; m < 100; m++) {
//				out.write(m + " " + currentPopulation.getAllToursInPopulation().get(m).outPutTour());
//				out.newLine();
//			}
//			out.newLine();
//			out.newLine();
//			out.newLine();
//			out.newLine();
//		}
//		out.close();
//	}

}