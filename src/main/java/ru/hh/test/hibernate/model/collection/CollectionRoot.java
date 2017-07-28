package ru.hh.test.hibernate.model.collection;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Table(name = "collection_root")
@Entity
public class CollectionRoot {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "root_id")
  private Integer id;
  @ElementCollection
  @CollectionTable(name = "root_dto", joinColumns = @JoinColumn(name = "root_id"))
  @Column(name = "dto", nullable = false)
  @Type(
    type = "ru.hh.test.hibernate.model.collection.DtoType"
  )
  private List<Dto> dtos;

  public Integer getId() {
    return id;
  }

  public List<Dto> getDtos() {
    return dtos;
  }

  public void setDtos(List<Dto> dtos) {
    this.dtos = dtos;
  }
}
