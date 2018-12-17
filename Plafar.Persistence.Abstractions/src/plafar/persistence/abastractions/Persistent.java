package plafar.persistence.abastractions;

import java.util.List;

public interface Persistent<T> {
	boolean addObject(T object);
	T getObject(int objectId);
	boolean editObject(T object);
	boolean deleteObject(int objectId);
	List<T> getAllObjects();
}
