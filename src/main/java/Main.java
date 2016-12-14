import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.nio.file.Files;
import java.nio.file.Paths;
import spark.utils.ResourceUtils;
public class Main {
	
  public static void main(String[] args) {
	
	
    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");
	Gson gson = new Gson();


    get("/", (request, response) -> {
        Map<String, Object> attributes = new HashMap<>();
        response.redirect("/index.html");
        return new ModelAndView(attributes, "index.ftl");
    }, new FreeMarkerEngine());

	
			
	
    get("/db", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = DatabaseUrl.extract().getConnection();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
		   "SELECT list.list_id, list.list_title, list_item.category, list_item.item_quantity, item.item_id,item.item_name " + 
		   "FROM list "+
		   "LEFT JOIN list_item ON list_item.list_id = list.list_id " +
		   "LEFT JOIN item ON list_item.item_id = item.item_id ");
			
		Map<String, List> listsByName = new HashMap<>();
        while (rs.next()) {
			String listTitle = rs.getString("list_title");
		    int listID = rs.getInt("list_id");
			if(!listsByName.containsKey(listTitle)){
				List tempList = new List();
				tempList.id = listID;
				tempList.name = listTitle;	
				listsByName.put(listTitle, tempList ); //key and value
			}
			
			
		    List currentList = listsByName.get(listTitle); //gets the list name from the hashmap
			String category = rs.getString("category");
			
			if(!rs.wasNull()){
				if(!currentList.categoriesByName.containsKey(category)) {
					Category tempCat = new Category();
					tempCat.name = category;
					currentList.categoriesByName.put(category, tempCat); 	
				}
				Category currentCategory = currentList.categoriesByName.get(category);
				int itemId = rs.getInt("item_id");
				String itemName = rs.getString("item_name");
				int itemQuantity = rs.getInt("item_quantity"); 
				Item newItem = new Item();
				newItem.id = itemId;
				newItem.name = itemName;
				newItem.quantity = itemQuantity;
				
				currentCategory.items.add(newItem);	
			}	
        }
        attributes.put("listsByName", listsByName);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e + e.toString());
		e.printStackTrace();
        return new ModelAndView(attributes, "error.ftl");
      }

	  finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }, new FreeMarkerEngine());
	
	get("/getAllItems", (req, res) -> {
		Connection connection = null;
		try {
			connection = DatabaseUrl.extract().getConnection();

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(
			   "SELECT item_id, item_name FROM item;"
			);
			ArrayList<Item> populateItems = new ArrayList<>();
			while (rs.next()) {
				String itemName = rs.getString("item_name");
				int itemId = rs.getInt("item_id");
				Item newItem = new Item();
				newItem.name = itemName;
				newItem.id = itemId;
				populateItems.add(newItem);	
			}	

			return populateItems;
		  } catch (Exception e) {
			e.printStackTrace();
			return("There was an error: " + e + e.toString());
		  }
		  finally {
			if (connection != null) try{connection.close();} catch(SQLException e){}
		  }
	}, new JsonTransformer());
	
	post("/addItemToList", (req, res) -> {
		Connection connection = null;
		try {
			connection = DatabaseUrl.extract().getConnection();
			 
			 AddSingleItemRequest addSingleItem = gson.fromJson(req.queryParams("jsonData"), AddSingleItemRequest.class); // deserializes json into target2
			 
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO list_item(list_id, item_id, category, item_quantity) " +
			" VALUES (?, ?, ?, ?);");
			
			pstmt.setInt(1, addSingleItem.listId);
			pstmt.setInt(2, addSingleItem.itemId);
			pstmt.setString(3, addSingleItem.category);
			pstmt.setInt(4, addSingleItem.itemQuantity); 
			
			pstmt.executeUpdate();
			
			return true;
		  } catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
		  finally {
			if (connection != null) try{connection.close();} catch(SQLException e){}
		  }
	}, new JsonTransformer());
	
	get("/getItemDescriptions", (req, res) -> {
		res.type("text/xml");
		try {
			byte[] encoded = Files.readAllBytes(ResourceUtils.getFile("/app/src/main/resources/xml/describeItems.xml").toPath());
			return new String(encoded);
		
		  } catch (Exception e) {
			e.printStackTrace();
			return("There was an error: " + e + e.toString());
		  }
		 
	});

  }

}
