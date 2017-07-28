package ru.hh.test.hibernate;

import javax.persistence.EntityTransaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractHibernateTest {

  protected SessionFactory sessionFactory;
  protected EntityTransaction transaction;

  @Before
  public void setUp() {
    Configuration configuration = new Configuration()
      .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread")
      .setProperty(Environment.SHOW_SQL, "true")
      .setProperty(Environment.HBM2DDL_AUTO, "create-drop")
//      .setProperty(Environment.FORMAT_SQL, "true")

      .setProperty(Environment.DRIVER, "org.h2.Driver")
      .setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
      .setProperty(Environment.URL, "jdbc:h2:mem:test")
      .setProperty(Environment.JPA_JDBC_USER, "sa")
      .setProperty(Environment.JPA_JDBC_PASSWORD, "");
    this.sessionFactory = configureSpecific(configuration).buildSessionFactory();
    this.transaction = sessionFactory.getCurrentSession().beginTransaction();
  }

  @After
  public void after() {
    transaction.rollback();
  }

  protected abstract Configuration configureSpecific(Configuration initialConfig);
}
