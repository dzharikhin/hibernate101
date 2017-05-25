package ru.hh.test.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "many_to_root")
@Entity
public class ManyToRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "many_to_root_id")
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "root_id")
  private Root root;

  public ManyToRoot(Root root) {
    this.root = root;
  }

  private ManyToRoot() {
  }

  public Integer getId() {
    return id;
  }

  public Root getRoot() {
    return root;
  }
}
