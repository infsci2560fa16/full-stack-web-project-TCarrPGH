function getDatabaseItems(listId, categoryName){
  var Connect = new XMLHttpRequest();
    Connect.open("GET", "http://fullstack-tc.herokuapp.com/getItemDescriptions");
    Connect.setRequestHeader("Content-Type", "text/xml");
    Connect.onload = function(){
    if (Connect.readyState === 4 &&  Connect.status === 200 ){ 
	    var xmlItemDescriptions = Connect.responseXML;
	        $("#postItemId").change(function(){
			    var changedItem = $("#postItemId").val();
			    $("#itemDescription").empty();
		        var itemElements = xmlItemDescriptions.getElementsByTagName("item");
				for (var i =0; i<itemElements.length; i++){
					var item = itemElements[i];
					if (item.getAttribute("id") == changedItem){
						var description = item.getElementsByTagName("description")[0].childNodes[0].nodeValue;
						$("#itemDescription").append(description);
					}
				
				}
				
			});	
		}
			
	};
	
	
	Connect.send(null);
	
	$.getJSON( "http://fullstack-tc.herokuapp.com/getAllItems", function( items ) {
		$('#postItemId').empty();
		$.each(items, function (i, item) {
			$('#postItemId').append($('<option>', { 
				value: item.id,
				text : item.name 
			}));
		});
		$('#postListId').val(listId);
		$('#postCategory').val(categoryName);
		$("#addItemModal").css("display", "block");	
	});
}
function postDatabaseItems(){
	var requestData = {
		"itemId": $('#postItemId').val(),
		"itemQuantity" : $('#postQuantity').val(),
		"category" : $('#postCategory').val(),
		"listId" : $('#postListId').val()
	};
	$.post( "http://fullstack-tc.herokuapp.com/addItemToList",
		{"jsonData": JSON.stringify(requestData)},
		function( responseData ) {
			if(responseData == "true"){
				$("#addItemModal").css("display", "none");
				
				var itemName =  $("#postItemId option:selected").text();
				var categoryListId = "#" + requestData.category + "_" + requestData.listId;
				$(categoryListId).append( itemName + " - " + requestData.itemQuantity + "<br/>");
				
			}
			else {
				$(".modal-content").append("Your item was not added, try again");
				
			}
			console.log(responseData);
		});
	
}

// When the user clicks on <span> (x), close the modal
$("#closeModal").click(function() {
    $("#addItemModal").css("display", "none");
});
