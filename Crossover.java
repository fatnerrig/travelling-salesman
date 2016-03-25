import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Crossover {
	
	public String type;
	public ArrayList<Integer> parent1;
	public ArrayList<Integer> parent2;
	public ArrayList<Integer> child1;
	public ArrayList<Integer> child2;
	
	public Crossover(String type, Tour tour1, Tour tour2){
		this.type = type;
		this.parent1 = tour1.getCities();
		this.parent2 = tour2.getCities();
		
		doCrossover();
	}

	
	//selects crossover methods
	private void doCrossover() {
		if(type.equals("o")){
			orderedCrossover();
		}else if(type.equals("a")){
			ArrayList<Integer> childa = alternatingPosition(this.parent1, this.parent2);
			ArrayList<Integer> childb = alternatingPosition(this.parent2, this.parent1);
			this.child1 = childa;
			this.child2 = childb;
		}		
	}

	private ArrayList<Integer> alternatingPosition(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		int size = parent1.size();
		ArrayList<Integer> child = new ArrayList<Integer>();
		for(int i=0; i<size; i++){
			if(!child.contains(parent1.get(i))){
				child.add(parent1.get(i));
			}
			
			if(!child.contains(parent2.get(i))){
				child.add(parent2.get(i));
			}
		}
		
		return child;
	}

	private void orderedCrossover() {
		int size = parent1.size();
		Random rand = new Random();
		int cut1 =  rand.nextInt(size-1);
		int cut2 =  rand.nextInt(size-1);
		if(cut1 == cut2){
			cut2 = rand.nextInt(size-1);
		}
		
		int min = Math.min(cut1, cut2);
		int max = Math.max(cut1, cut2);
		
		ArrayList<Integer> childa = new ArrayList<Integer>();
		ArrayList<Integer> childb = new ArrayList<Integer>();
		
		childa.addAll(parent1.subList(min, max + 1));
		childb.addAll(parent2.subList(min, max + 1));
		
		int currentCityIndex = 0;
		int currentCityInParent1 = 0;
		int currentCityInParent2 = 0;
		
		for(int i = 1; i <= size; i++){
			currentCityIndex = (max + i) % size;
			
			currentCityInParent1 = parent1.get(currentCityIndex);
			currentCityInParent2 = parent2.get(currentCityIndex);
			
			if (!childa.contains(currentCityInParent2)) {
				childa.add(currentCityInParent2);
			}
			
			if (!childb.contains(currentCityInParent1)) {
				childb.add(currentCityInParent1);
			}
		}
		
		Collections.rotate(childa, min);
		Collections.rotate(childb, min);
		
		this.child1 = childa;
		this.child2 = childb;
	}
	
	public ArrayList<ArrayList<Integer>> getChildren(){
		ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
		children.add(this.child1);
		children.add(this.child2);
		
		return children;
	}

}
