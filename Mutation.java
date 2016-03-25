import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Mutation {
	
	public String type;
	public ArrayList<Integer> tour;
	
	public Mutation(String type, Tour tour){
		this.type = type;
		this.tour = tour.getCities();
		
		doMutation();
	}

	
	//selects mutation method
	private void doMutation() {
		if(type.equals("e")){
			exchangeMutation();
		}else if(type.equals("i")){
			simpleInversionMutation();
		}else if(type.equals("d")){
			displacementMutation();
		}		
	}

		
	private void simpleInversionMutation() {
		int size = tour.size();
		Random rand = new Random();
		int num1 = rand.nextInt(size-1);
		int num2 = rand.nextInt(size-1);
		int min = Math.min(num1, num2);
		int max = Math.max(num1, num2);
		
		ArrayList<Integer> mutatedTour = new ArrayList<Integer>();
		ArrayList<Integer> inverse = new ArrayList<Integer>();
		mutatedTour.addAll(tour.subList(0, min));
		inverse.addAll(tour.subList(min, max));
		Collections.reverse(inverse);
		mutatedTour.addAll(inverse);
		mutatedTour.addAll(tour.subList(max, size));
		
		this.tour = mutatedTour;
	}

	private void exchangeMutation() {
		int size = tour.size();
		Random rand = new Random();
		int num1 = rand.nextInt(size-1);
		int num2 = rand.nextInt(size-1);
		
		Collections.swap(tour,num1,num2);
	}
	
	private void displacementMutation(){
		int size = tour.size();
		Random rand = new Random();
		int random = rand.nextInt(size-1);
		int num1 = rand.nextInt(size-1);
		int num2 = rand.nextInt(size-1);
		int min = Math.min(num1, num2);
		int max = Math.max(num1, num2);
		
		ArrayList<Integer> mutatedTour = new ArrayList<Integer>();
		mutatedTour.addAll(tour.subList(min, max));
		mutatedTour.addAll(tour.subList(0, min));
		mutatedTour.addAll(tour.subList(max, size));
		Collections.rotate(mutatedTour,random);
		
		this.tour = mutatedTour;		
	}
	
	public ArrayList<Integer> getTour(){
		return tour;
	}

}
