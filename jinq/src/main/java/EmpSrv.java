import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "emp_srv")
public class EmpSrv {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_srv_id")
  private int id;

  @ManyToOne
  private Srv srv;

  @ManyToOne
  private Acc acc;

  @OneToMany(mappedBy = "empSrv", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<EmpSrvCollectionElement> elements;

  public int getId() {
    return id;
  }

  public Srv getSrv() {
    return srv;
  }

  public void setSrv(Srv srv) {
    this.srv = srv;
  }

  public Acc getAcc() {
    return acc;
  }

  public void setAcc(Acc acc) {
    this.acc = acc;
  }

  public List<EmpSrvCollectionElement> getElements() {
    return elements;
  }

  public void setElements(List<EmpSrvCollectionElement> elements) {
    this.elements = elements;
  }
}
