package repository;



public interface GenericRepo {

    public void sort();
    public void add(Object o);
    public void remove(Object o);
    public void update(int ID, Object newobj);
    public void print();
}
