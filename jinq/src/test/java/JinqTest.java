import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class JinqTest extends AbstractTest {

  protected Session currentSession;

  protected Configuration configureSpecific(Configuration initialConfig) {
    return initialConfig
      .addAnnotatedClass(Srv.class)
      .addAnnotatedClass(Emp.class)
      .addAnnotatedClass(Acc.class)
      .addAnnotatedClass(EmpSrv.class)
      .addAnnotatedClass(EmpSrvCollectionElement.class);
  }

  @Override
  @Before
  public void setUp() {
    super.setUp();
    currentSession = sessionFactory.getCurrentSession();
    prepareData(currentSession);
  }

  @Test
  public void testJinq() {
    List<EmpSrvCollectionElement> hql = null;

    System.out.println("Getting via HQL:");
    hql = currentSession
      .createQuery("SELECT el FROM Emp AS e " +
        "INNER JOIN e.accs AS a " +
        "INNER JOIN a.empSrvs AS es " +
        "INNER JOIN es.elements AS el WHERE e.id = 1", EmpSrvCollectionElement.class)
      .list();

    System.out.println("Getting via jinq:");
    List<EmpSrvCollectionElement> jinq = streams.streamAll(currentSession, Emp.class)
      .joinList(Emp::getAccs)
      .joinList(empAccTuple -> empAccTuple.getTwo().getEmpSrvs())
      .where(empAccEmpSrvTuple -> empAccEmpSrvTuple.getOne().getOne().getId() == 1 || empAccEmpSrvTuple.getOne().getOne().getId() == 2)
      .selectAllList(empAccEmpSrvTuple -> empAccEmpSrvTuple.getTwo().getElements())
      .toList();

    assertEquals(hql, jinq);

  }

  private static void prepareData(Session currentSession) {

    Srv srv = new Srv();
    srv.setValue("srv1");
    currentSession.save(srv);

    Emp emp1 = new Emp();
    emp1.setValue("emp1");
    currentSession.save(emp1);

    Emp emp2 = new Emp();
    emp2.setValue("emp2");
    currentSession.save(emp2);

    Acc acc1 = new Acc();
    acc1.setEmp(emp1);
    currentSession.save(acc1);

    Acc acc2 = new Acc();
    acc2.setEmp(emp1);
    currentSession.save(acc2);

    EmpSrv empSrv1 = new EmpSrv();
    empSrv1.setSrv(srv);
    empSrv1.setAcc(acc1);
    empSrv1.setElements(IntStream.range(0, 3).mapToObj(i -> createEl(empSrv1)).collect(toList()));
    currentSession.save(empSrv1);

    EmpSrv empSrv2 = new EmpSrv();
    empSrv2.setSrv(srv);
    empSrv2.setAcc(acc1);
    empSrv2.setElements(Collections.singletonList(createEl(empSrv2)));
    currentSession.save(empSrv2);

    EmpSrv empSrv3 = new EmpSrv();
    empSrv3.setSrv(srv);
    empSrv3.setAcc(acc2);
    empSrv3.setElements(IntStream.range(0, 1).mapToObj(i -> createEl(empSrv3)).collect(toList()));
    currentSession.save(empSrv3);

    flushData(currentSession);
  }

  private static EmpSrvCollectionElement createEl(EmpSrv empSrv) {
    EmpSrvCollectionElement el = new EmpSrvCollectionElement();
    el.setEmpSrv(empSrv);
    return el;

  }

  private static void flushData(Session session) {
    session.flush();
    session.clear();
  }

}
