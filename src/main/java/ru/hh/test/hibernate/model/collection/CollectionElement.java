package ru.hh.test.hibernate.model.collection;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "element")
public class CollectionElement implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "element_id")
  private int id;
  @Column(name = "value")
  private String aString;

  public CollectionElement(String aString) {
    this.aString = aString;
  }

  public CollectionElement() {
  }

  public int getId() {
    return id;
  }

  public String getaString() {
    return aString;
  }

  public void setaString(String aString) {
    this.aString = aString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CollectionElement)) {
      return false;
    }
    CollectionElement that = (CollectionElement) o;
    return id == that.id &&
      Objects.equals(aString, that.aString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, aString);
  }
}
