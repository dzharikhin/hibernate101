package ru.hh.test.hibernate;

import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import ru.hh.test.hibernate.model.complexcollection.CollectionRoot;
import ru.hh.test.hibernate.model.complexcollection.Dto;
import static org.junit.Assert.assertEquals;

public class CollectionTest extends AbstractHibernateTest {

  @Override
  protected Configuration configureSpecific(Configuration initialConfig) {
    return initialConfig
      .addAnnotatedClass(CollectionRoot.class);
  }

  @Test
  public void testCollectionWorks() {
    final Session currentSession = sessionFactory.getCurrentSession();
    Dto dto1 = new Dto(1, "1");
    Dto dto2 = new Dto(2, "2");
    List<Dto> dtos = Arrays.asList(dto1, dto2);
    CollectionRoot root = new CollectionRoot();
    root.setDtos(dtos);
    currentSession.save(root);
    currentSession.flush();
    currentSession.clear();
    CollectionRoot actual = currentSession.get(CollectionRoot.class, root.getId());
    assertEquals(dtos, actual.getDtos());
  }

  @Test
  public void testCollectionCanSelectOnlyCollection() {
    final Session currentSession = sessionFactory.getCurrentSession();
    Dto dto1 = new Dto(1, "1");
    Dto dto2 = new Dto(2, "2");
    List<Dto> expectedDtos = Arrays.asList(dto1, dto2);
    CollectionRoot root = new CollectionRoot();
    root.setDtos(expectedDtos);
    currentSession.save(root);
    currentSession.flush();
    currentSession.clear();
    List<Dto> dtos = currentSession.createQuery("select dtos.elements from CollectionRoot where id = :id", Dto.class)
      .setParameter("id", root.getId())
      .list();
    assertEquals(expectedDtos, dtos);
  }
}
