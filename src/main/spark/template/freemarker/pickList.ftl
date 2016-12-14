<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>
<body>
  <#include "nav.ftl">
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
          <li> bla bla</li>
          <li> bla bla</li>
          </ul>
      </li>
        </#list>
    </ul>
</div>
  <script src = "js/transformer.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"</script>
</body>
</html>
