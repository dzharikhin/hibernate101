package ru.hh.test.hibernate;

import java.util.Collections;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import ru.hh.test.hibernate.model.instrumentation.ComplexPrimaryKeyManyToRoot;
import ru.hh.test.hibernate.model.instrumentation.ManyToRoot;
import ru.hh.test.hibernate.model.instrumentation.OneToRoot;
import ru.hh.test.hibernate.model.instrumentation.Root;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class InstrumentationTest extends AbstractHibernateTest {


  @Override
  protected Configuration configureSpecific(Configuration initialConfig) {
    return initialConfig
      .addAnnotatedClass(Root.class)
      .addAnnotatedClass(OneToRoot.class)
      .addAnnotatedClass(ManyToRoot.class)
      .addAnnotatedClass(ComplexPrimaryKeyManyToRoot.class);
  }

  @Test
  public void testRootLazyColumn() {
    final Session currentSession = sessionFactory.getCurrentSession();
    Root initialRoot = new Root();
    initialRoot.setLazyColumn("testLazyColumn");
    currentSession.save(initialRoot);
    currentSession.flush();
    currentSession.clear();
    Root root = currentSession.get(Root.class, initialRoot.getId());
    assertFalse(Hibernate.isPropertyInitialized(root, "lazyColumn"));
    assertEquals("testLazyColumn", root.getLazyColumn());
  }

  @Test
  public void testOneToOne() {
    final Session currentSession = sessionFactory.getCurrentSession();
    Root initialRoot = new Root();
    OneToRoot oneToRoot = new OneToRoot(initialRoot);
    oneToRoot.setText("oneToRoot");
    initialRoot.setOneToRoot(oneToRoot);
    currentSession.save(initialRoot);
    currentSession.flush();
    currentSession.clear();
    Root root = currentSession.get(Root.class, initialRoot.getId());
    assertFalse(Hibernate.isPropertyInitialized(root, "oneToRoot"));
    assertEquals(oneToRoot.getId(), root.getOneToRoot().getId());
    assertEquals("oneToRoot", root.getOneToRoot().getText());
  }

  @Test
  public void testOneToMany() {
    final Session currentSession = sessionFactory.getCurrentSession();
    Root initialRoot = new Root();
    ManyToRoot manyToRoot = new ManyToRoot(initialRoot);
    initialRoot.setManyToRoots(Collections.singleton(manyToRoot));
    currentSession.save(initialRoot);
    currentSession.flush();
    currentSession.clear();
    Root root = currentSession.get(Root.class, initialRoot.getId());
    assertFalse(Hibernate.isPropertyInitialized(root, "manyToRoots"));
    assertEquals(initialRoot.getId(), root.getManyToRoots().iterator().next().getRoot().getId());
  }

  @Test
  public void testComplexPkOneToMany() {
    final Session currentSession = sessionFactory.getCurrentSession();
    Root initialRoot = new Root();
    ComplexPrimaryKeyManyToRoot complexPrimaryKeyManyToRoot = new ComplexPrimaryKeyManyToRoot();
    ComplexPrimaryKeyManyToRoot.ComplexPrimaryKey primaryKey = new ComplexPrimaryKeyManyToRoot.ComplexPrimaryKey(initialRoot, 2);
    complexPrimaryKeyManyToRoot.setPrimaryKey(primaryKey);
    initialRoot.setComplexPrimaryKeyManyToRoots(Collections.singleton(complexPrimaryKeyManyToRoot));
    currentSession.save(initialRoot);
    currentSession.flush();
    currentSession.clear();
    Root root = currentSession.get(Root.class, initialRoot.getId());
    assertFalse(Hibernate.isPropertyInitialized(root, "complexPrimaryKeyManyToRoots"));
    assertEquals(initialRoot.getId(), root.getComplexPrimaryKeyManyToRoots().iterator().next().getPrimaryKey().getRoot().getId());
  }


}
