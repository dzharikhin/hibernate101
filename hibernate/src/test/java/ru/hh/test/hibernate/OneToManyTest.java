package ru.hh.test.hibernate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import ru.hh.test.hibernate.model.onetomany.Child;
import ru.hh.test.hibernate.model.onetomany.Parent;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OneToManyTest extends AbstractHibernateTest {
  @Override
  protected Configuration configureSpecific(Configuration initialConfig) {
    return initialConfig
      .addAnnotatedClass(Parent.class)
      .addAnnotatedClass(Child.class);
  }

  private Parent originalParent;
  private Child child1;
  private Child child2;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    Session currentSession = sessionFactory.getCurrentSession();
    originalParent = new Parent();
    child1 = new Child(originalParent, "child1").withValue("childValue1");
    child2 = new Child(originalParent, "child2").withValue("childValue2");

    originalParent.setChildren(Arrays.asList(child1, child2));
    currentSession.saveOrUpdate(originalParent);
    currentSession.flush();
    currentSession.clear();
  }

  @Test
  public void testChildrenSave() {
    Session currentSession = sessionFactory.getCurrentSession();
    Parent parent = currentSession.get(Parent.class, originalParent.getId());
    assertThat(parent.getChildren(), containsInAnyOrder(child1, child2));
  }

  @Test
  public void testChildAdd() {
    Session currentSession = sessionFactory.getCurrentSession();
    Child newChild = new Child(originalParent, "child3").withValue("childValue3");
    originalParent.getChildren().add(newChild);
    currentSession.saveOrUpdate(originalParent);
    currentSession.flush();
    currentSession.clear();
    Parent service = currentSession.get(Parent.class, originalParent.getId());
    assertThat(service.getChildren(), containsInAnyOrder(child1, child2, newChild));
  }

  @Test
  public void testChildRemove() {
    Session currentSession = sessionFactory.getCurrentSession();
    Collection<Child> children = Collections.singletonList(child1);
    originalParent.setChildren(children);
    currentSession.saveOrUpdate(originalParent);
    currentSession.flush();
    currentSession.clear();
    Parent service = currentSession.get(Parent.class, originalParent.getId());
    assertThat(service.getChildren(), contains(child1));
  }

  @Test
  public void testChildUpdate() {
    Session currentSession = sessionFactory.getCurrentSession();
    Child expectedChild = originalParent.getChildren().stream().filter(child -> child1.getName().equals(child.getName()))
      .findAny().orElseThrow(RuntimeException::new);
    String newValue = "newChildValue1";
    expectedChild.withValue(newValue);
    currentSession.saveOrUpdate(originalParent);
    currentSession.flush();
    currentSession.clear();
    Parent service = currentSession.get(Parent.class, originalParent.getId());
    Child actualChild = service.getChildren().stream().filter(child -> expectedChild.getName().equals(child.getName())).findAny()
      .orElseThrow(RuntimeException::new);
    assertEquals(expectedChild.getValue(), actualChild.getValue());
  }
}
