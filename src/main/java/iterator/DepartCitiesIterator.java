package iterator;

import java.util.List;

public class DepartCitiesIterator implements ExtendedIterator{
	
	List<String> depCities;
	int position = 0;
	
	public DepartCitiesIterator(List<String> depCities) {
		this.depCities=depCities;
	}
	
	public Object next() {
		String city =depCities.get(position);
		position += 1;
		return city;
	}
	
	public boolean hasNext(){
		return position < depCities.size();
	}
	
	//return	the	actual	element	and	go	to	the	previous
		public Object previous() {
			String city =depCities.get(position);
			position -= 1;
			return city;
		}
		//true	if there is	a	previous	element
		public boolean hasPrevious() {
			return position != 0;
		}
		//It	is	placed	in	the	first	element
		public void goFirst() {
			position = 0;
		}
		// It	is	placed	in	the	last	element
		public void goLast() {
			position = depCities.size() - 1;
		}

}
