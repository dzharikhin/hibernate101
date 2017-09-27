package ru.hh.test.hibernate.model.instrumentation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "complex_pk_many_to_root")
@Entity
public class ComplexPrimaryKeyManyToRoot {

  @EmbeddedId
  private ComplexPrimaryKey primaryKey;

  public ComplexPrimaryKey getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(ComplexPrimaryKey primaryKey) {
    this.primaryKey = primaryKey;
  }

  @Embeddable
  public static class ComplexPrimaryKey implements Serializable {
    @JoinColumn(name = "complex_pk_many_to_root_id", referencedColumnName = "root_id")
    @ManyToOne
    private Root root;
    @Column(name = "salt")
    private int salt;

    public ComplexPrimaryKey(Root root, int salt) {
      this.root = root;
      this.salt = salt;
    }

    private ComplexPrimaryKey() { }

    public Root getRoot() {
      return root;
    }
  }
}
