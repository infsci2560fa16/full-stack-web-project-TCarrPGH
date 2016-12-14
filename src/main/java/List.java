import java.util.HashMap;
import java.util.Map;

public class List{

	int id;
	String name  = "<no name>";
	Map<String, Category> categoriesByName = new HashMap<>();
	
	public String getName(){
		return name;
	}
	public Map<String, Category> getCategories(){
		return categoriesByName;
	}
	
	public int getId(){
		return id;
	}
}