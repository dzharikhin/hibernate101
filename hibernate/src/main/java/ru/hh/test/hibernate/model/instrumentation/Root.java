package ru.hh.test.hibernate.model.instrumentation;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Table(name = "root")
@Entity
public class Root {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "root_id")
  private Integer id;
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @LazyGroup("lazyColumn")
  private String lazyColumn;
  @OneToOne(mappedBy = "root", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @LazyToOne(LazyToOneOption.NO_PROXY)
  @LazyGroup("oneToRoot")
  //seems doesn't work for lazy loaders - if not found - exception is thrown anyway
//  @NotFound(action = NotFoundAction.IGNORE)
  private OneToRoot oneToRoot;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Collection<ManyToRoot> manyToRoots = new ArrayList<>();
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Collection<ComplexPrimaryKeyManyToRoot> complexPrimaryKeyManyToRoots = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public String getLazyColumn() {
    return lazyColumn;
  }

//  uncomment to break instrumentation
//  public boolean isLazyColumn() {
//    return lazyColumn != null;
//  }

  public void setLazyColumn(String lazyColumn) {
    this.lazyColumn = lazyColumn;
  }

  public OneToRoot getOneToRoot() {
    return oneToRoot;
  }

  public void setOneToRoot(OneToRoot oneToRoot) {
    this.oneToRoot = oneToRoot;
  }

  public Collection<ManyToRoot> getManyToRoots() {
    return manyToRoots;
  }

  public void setManyToRoots(Collection<ManyToRoot> manyToRoots) {
    this.manyToRoots = manyToRoots;
  }

  public Collection<ComplexPrimaryKeyManyToRoot> getComplexPrimaryKeyManyToRoots() {
    return complexPrimaryKeyManyToRoots;
  }

  public void setComplexPrimaryKeyManyToRoots(Collection<ComplexPrimaryKeyManyToRoot> complexPrimaryKeyManyToRoots) {
    this.complexPrimaryKeyManyToRoots = complexPrimaryKeyManyToRoots;
  }
}
