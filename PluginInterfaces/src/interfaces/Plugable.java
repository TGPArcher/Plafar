package interfaces;

public interface Plugable {
	String getName();
	String getKey();
	String getVersion();
	void intitialize(String args[]);
}