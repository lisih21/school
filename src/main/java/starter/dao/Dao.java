package starter.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    public boolean delete(K id);
    public E save(E e);
    public void update(E e);
    public Optional<E> findById(K id);
    public Optional<E> findById(K id, Connection connection);
    public List<E> findAll();

}
