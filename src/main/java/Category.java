import java.util.ArrayList;

public class Category{

	String name = "<no name>";
	ArrayList<Item> items  = new ArrayList<>();
	
	public String getName(){
		return name;
	}
	public ArrayList<Item> getItems(){
		return items;
	}

}