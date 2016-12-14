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
    
