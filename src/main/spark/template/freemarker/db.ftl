<!DOCTYPE html>
<html>
<head>
   <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
   <link rel="stylesheet" type="text/css" href="css/styles.css"></link>
  <#include "header.ftl">
  
</head>
<body>
  <#include "nav.ftl">

<div id="addItemModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content">
    <div class ="modal-header">
      <h3>Add a new item</h3>
    </div>
    <div class "modal-body"> 
      <select id =  "postItemId"></select>
      <input type = "number" id = "postQuantity" size ="4" value=1></input>
      <input type = "hidden" id = "postListId"></input>
      <input type = "hidden" id = "postCategory"></input> 
    </div>
    <div class = "modal-footer">
     <div id = "itemDescription"></div> 
     <input type = "button" value = "submit" onClick = "postDatabaseItems();"></input>
      <span id= "closeModal" class="close">&times;</span>
    </div>
  </div>
</div>
<div class="container">
<ul class="pricing_table">
    <li>...</li>
    <li class="price_block" id="list_title"><!--List name-->
      <#list listsByName?keys as listName> 
        <h3>${listName}</h3>
        
        <div class="price"><!--"price" change to category-->
            <div class="price_figure">
                <span class="price_number">Produce</span>
            </div>
        </div>
        <ul class="features"><!--change to items--> 
            <#assign currentList = listsByName[listName]>
            <#list currentList.categories?keys as categoryName>
              <li id ="${categoryName}_${currentList.id}" >
                <b>${categoryName}</b>
                <a onClick = "getDatabaseItems(${currentList.id},'${categoryName}');">     Add New Item</a><br/>
                <#assign currentCategory = currentList.categories[categoryName]>
                <#list currentCategory.items as currentItem>
                  ${currentItem.name} - ${currentItem.quantity}<br/>
                </#list>
              </li>
            </#list>
         
        </ul>
        <div class="footer">
            <a href="#" class="action_button">Buy Now</a>
        </div>
    </#list>
    </li>
     
    <li>...</li>
    <li>...</li>
</ul>
  <h1>My Lists</h1>
    <ul class = "list-group" id = "list_title">
      <#list listsByName?keys as listName>
        <li id="list-group-name" class="list-group-item">
          ${listName}    
          <ul id = "nested-group">
            <#assign currentList = listsByName[listName]>
            <#list currentList.categories?keys as categoryName>
              <li id ="${categoryName}_${currentList.id}" >
                <b>${categoryName}</b>
                <a onClick = "getDatabaseItems(${currentList.id},'${categoryName}');">     Add New Item</a><br/>
                <#assign currentCategory = currentList.categories[categoryName]>
                <#list currentCategory.items as currentItem>
                  ${currentItem.name} - ${currentItem.quantity}<br/>
                </#list>
              </li>
            </#list>
          </ul>
        </li>
      </#list>
    </ul>
    

</div>
  <script src = "js/transformer.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"</script>
</body>
</html>
