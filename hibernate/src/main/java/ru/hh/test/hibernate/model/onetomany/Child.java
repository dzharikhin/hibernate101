package ru.hh.test.hibernate.model.onetomany;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Child implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parent_id")
  private Parent parent;
  @Id
  @Column(name = "name", nullable = false)
  private String name;
  private String value;

  private Child() {
  }

  public Child(Parent parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public Parent getService() {
    return parent;
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  private void setValue(String value) {
    this.value = value;
  }

  public Child withValue(String value) {
    setValue(value);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Child)) {
      return false;
    }
    Child child = (Child) o;
    return Objects.equals(parent, child.parent) &&
      Objects.equals(name, child.name) &&
      Objects.equals(value, child.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parent, name, value);
  }

  @Override
  public String toString() {
    return "Child{" +
      "parent=" + parent +
      ", name='" + name + '\'' +
      ", value='" + value + '\'' +
      '}';
  }
}
