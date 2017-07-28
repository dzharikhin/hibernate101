package ru.hh.test.hibernate.model.instrumentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "one_to_root")
@Entity
public class OneToRoot {

  @Id
  private Integer id;
  @Column(name = "text")
  private String text;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "one_to_root_id", referencedColumnName = "root_id")
  private Root root;

  public OneToRoot(Root root) {
    this.root = root;
  }

  private OneToRoot() {
  }

  public Integer getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
