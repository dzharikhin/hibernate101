package ru.hh.test.hibernate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import ru.hh.test.hibernate.model.instrumentation.ComplexPrimaryKeyManyToRoot;
import ru.hh.test.hibernate.model.instrumentation.ManyToRoot;
import ru.hh.test.hibernate.model.instrumentation.OneToRoot;
import ru.hh.test.hibernate.model.instrumentation.Root;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class JpaQueryTest extends AbstractHibernateTest {
  @Override
  protected Configuration configureSpecific(Configuration initialConfig) {
    return initialConfig
      .addAnnotatedClass(Root.class)
      .addAnnotatedClass(OneToRoot.class)
      .addAnnotatedClass(ManyToRoot.class)
      .addAnnotatedClass(ComplexPrimaryKeyManyToRoot.class);
  }

  @Test
  public void testComplexIdJpaQuery() {
    int salt = 1;
    Session currentSession = sessionFactory.getCurrentSession();
    Root root = prepareRoot(currentSession, salt);

    CriteriaBuilder cb = currentSession.getCriteriaBuilder();
    CriteriaQuery<ComplexPrimaryKeyManyToRoot> criteria = cb.createQuery(ComplexPrimaryKeyManyToRoot.class);
    javax.persistence.criteria.Root<ComplexPrimaryKeyManyToRoot> manyToRoot = criteria.from(ComplexPrimaryKeyManyToRoot.class);
    currentSession.clear();
    currentSession.flush();

    criteria = criteria.select(manyToRoot).where(cb.equal(manyToRoot.get("primaryKey").get("root"), root),
      manyToRoot.get("primaryKey").get("salt").in(Collections.singleton(salt))
    );
    currentSession.update(root);
    List<ComplexPrimaryKeyManyToRoot> result = currentSession.createQuery(criteria).getResultList();
    assertThat(
      result.stream().map(ComplexPrimaryKeyManyToRoot::getPrimaryKey).collect(toList()),
      contains(new ComplexPrimaryKeyManyToRoot.ComplexPrimaryKey(root, salt))
    );
  }

  private static Root prepareRoot(Session currentSession, int salt) {
    Root root = new Root();
    ComplexPrimaryKeyManyToRoot manyToOne1 = new ComplexPrimaryKeyManyToRoot();
    manyToOne1.setPrimaryKey(new ComplexPrimaryKeyManyToRoot.ComplexPrimaryKey(root, salt));
    ComplexPrimaryKeyManyToRoot manyToOne2 = new ComplexPrimaryKeyManyToRoot();
    manyToOne2.setPrimaryKey(new ComplexPrimaryKeyManyToRoot.ComplexPrimaryKey(root, salt + 1));
    root.setComplexPrimaryKeyManyToRoots(Arrays.asList(manyToOne1, manyToOne2));
    currentSession.save(root);
    currentSession.flush();
    currentSession.clear();
    return root;
  }
}
