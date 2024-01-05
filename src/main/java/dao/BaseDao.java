package dao;

import org.hibernate.SessionFactory;
import utils.DatabaseUtils;

public class BaseDao {
    public SessionFactory sessionFactory = null;

    public BaseDao() {
        if (sessionFactory == null) {
            sessionFactory = DatabaseUtils.getDbSession();
        }
    }
}
