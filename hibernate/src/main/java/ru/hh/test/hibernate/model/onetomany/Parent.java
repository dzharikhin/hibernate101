package ru.hh.test.hibernate.model.onetomany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Parent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "parent_id")
  private Integer id;

  @OneToMany(orphanRemoval = true, mappedBy = "parent", cascade = javax.persistence.CascadeType.ALL)
  private Collection<Child> children = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public Collection<Child> getChildren() {
    return children;
  }

  public void setChildren(Collection<Child> children) {
    this.children.clear();
    this.children.addAll(children);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Parent)) {
      return false;
    }
    Parent parent = (Parent) o;
    return Objects.equals(id, parent.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
