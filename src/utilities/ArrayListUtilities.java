package utilities;

import java.util.ArrayList;

/**
 * A useful method that can work with an ArrayList of any Type
 * @author mattroberts
 *
 * @param <T> the type of the ArrayList
 */
public class ArrayListUtilities<T> {

	/**
	 * Removes the exact object, by checking its address
	 * @param arrayListOfObjects the array to remove the object from
	 * @param object the object to be removed
	 */
	public static <T extends Object> void removeObjectFromArrayList(ArrayList<T> arrayListOfObjects, T object) {
		for (int i = 0; i < arrayListOfObjects.size(); i++) {
			if (arrayListOfObjects.get(i) == object) {
				arrayListOfObjects.remove(i);
			}
		}
	}
}
