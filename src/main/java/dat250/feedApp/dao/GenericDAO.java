package dat250.feedApp.dao;
import java.util.List;
public interface GenericDAO<T, K> {

    void persist(T entity);
    void remove(T entity);
    T find(K id);
    List<T> findAll();
    T update(T entity);

}
