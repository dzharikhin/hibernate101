package ru.hh.test.hibernate;

import java.util.Arrays;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import ru.hh.test.hibernate.model.collection.CollectionElement;
import ru.hh.test.hibernate.model.collection.CollectionRoot;
import static org.junit.Assert.assertEquals;

public class DuplicatesOnJoinTest extends AbstractHibernateTest {
  @Override
  protected Configuration configureSpecific(Configuration initialConfig) {
    initialConfig.addAnnotatedClass(CollectionRoot.class)
      .addAnnotatedClass(CollectionElement.class);
    return initialConfig;
  }

  @Test
  public void testDuplicates() {
    final Session currentSession = sessionFactory.getCurrentSession();
    CollectionElement el1 = new CollectionElement("el1");
    CollectionElement el2 = new CollectionElement("el2");
    List<CollectionElement> elements = Arrays.asList(el1, el2);
    CollectionRoot root = new CollectionRoot();
    root.setCollectionElements(elements);
    //only one element
    currentSession.save(root);
    currentSession.flush();
    currentSession.clear();

    DetachedCriteria criteria = DetachedCriteria.forClass(CollectionRoot.class)
      //to fix - uncomment next line
//      .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
      .add(Restrictions.in("id", root.getId()))
      //so, we want to load all required data in one query cause it's more efficient
      .setFetchMode("collectionElements", FetchMode.JOIN);
    List<CollectionRoot> roots = criteria.getExecutableCriteria(currentSession)
      .list();
    assertEquals(elements.size(), roots.size());
    CollectionRoot root1 = roots.get(0);
    assertEquals(elements, root1.getCollectionElements());
    CollectionRoot root2 = roots.get(1);
    assertEquals(elements, root2.getCollectionElements());
  }
}
