package ru.hh.test.hibernate.model.collection;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "root")
public class CollectionRoot {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "root_id")
  private int id;

  @OneToMany(cascade = CascadeType.ALL)
  private List<CollectionElement> collectionElements;

  public int getId() {
    return id;
  }

  public List<CollectionElement> getCollectionElements() {
    return collectionElements;
  }

  public void setCollectionElements(List<CollectionElement> collectionElements) {
    this.collectionElements = collectionElements;
  }
}
