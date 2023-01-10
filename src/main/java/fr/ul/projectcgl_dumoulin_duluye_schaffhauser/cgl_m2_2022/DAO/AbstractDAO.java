package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.IEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractDAO<E extends IEntity<ID>, ID> {

    private final Class<E> entityClass;

    protected AbstractDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session getSession() {
        return HibernateUtils.getInstance().getSession();
    }

    public E getById(ID id) {
        try (Session session = getSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isPresent(ID id) {
        return Optional.ofNullable(getById(id)).isPresent();
    }

    public Stream<E> getAll() {
        String query = "from " + getSession().getMetamodel().entity(entityClass).getName();
        return getSession()
                .createQuery(query, entityClass)
                .getResultStream();
    }

    protected E persistEntity(E entity) {
        try (Session session = HibernateUtils.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();

            try {
                session.persist(entity);

                tx.commit();
            }
            catch (Exception e) {
                tx.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return entity;
    }

    public E insert(E entity) {
        return persistEntity(entity);
    }

    public E update(E entity) {
        try (Session session = getSession()) {
            return persistEntity(session.merge(entity));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean delete(E entity) {
        try (Session session = getSession()) {
            Transaction tx = session.beginTransaction();

            try {
                session.remove(entity);

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return isPresent(entity.getId());
    }
}
