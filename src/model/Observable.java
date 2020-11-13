package model;

public interface Observable {
	public void add(Observer o);
	public void remove(Observer o);
	public Object get();
	public void update();
}
