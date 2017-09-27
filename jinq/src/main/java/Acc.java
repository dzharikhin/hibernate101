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
@Table(name = "acc")
public class Acc {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "acc_id")
  private int id;

  @ManyToOne
  private Emp emp;

  @OneToMany(mappedBy = "acc", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<EmpSrv> empSrvs;

  public int getId() {
    return id;
  }

  public Emp getEmp() {
    return emp;
  }

  public void setEmp(Emp emp) {
    this.emp = emp;
  }

  public List<EmpSrv> getEmpSrvs() {
    return empSrvs;
  }
}
