import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "emp_srv_coll_elem")
public class EmpSrvCollectionElement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_srv_coll_elem_id")
  private int id;

  @ManyToOne
  private EmpSrv empSrv;

  public int getId() {
    return id;
  }

  public EmpSrv getEmpSrv() {
    return empSrv;
  }

  public void setEmpSrv(EmpSrv empSrv) {
    this.empSrv = empSrv;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EmpSrvCollectionElement)) {
      return false;
    }
    EmpSrvCollectionElement that = (EmpSrvCollectionElement) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "EmpSrvCollectionElement{" +
      "id=" + id +
      '}';
  }
}
