import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "srv")
public class Srv {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "srv_id")
  private int id;

  @Column
  private String value;

  @OneToMany(mappedBy = "srv", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<EmpSrv> empSrvs;

  public int getId() {
    return id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<EmpSrv> getEmpSrvs() {
    return empSrvs;
  }
}
