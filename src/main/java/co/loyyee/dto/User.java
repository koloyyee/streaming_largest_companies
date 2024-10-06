package co.loyyee.dto;

import java.util.List;

import io.jstach.jstache.JStache;

@JStache(
    // points to src/main/resources/user.mustache file
    // path = "public/mustache/user.mustache"

    // or alternatively you can inline the template
   template =
       """
{{ name }}
{{#list}}
<li>{{value}}</li>
{{/list}}


<ul>
   {{#companies}}
   <li>
           <h3>{{rank}}: {{organizationName}}</h3>
       revenueD
           <p>Revenue {{ revenueStr }}</p>
           <p> Profit {{ profitStr }}</p>
       <p> Profit margin {{ profitMargin}} </p>
   </li>
   {{/companies}}
</ul>
"""
)
public record User(String name, int age, String[] array, List<Item<String>> list, List<Company> companies) {

	public static class Item<T> {
		private final T value;
		public Item(T value) {
			this.value = value;
		}
		T value() {
			return value;
		}
	}
	
}