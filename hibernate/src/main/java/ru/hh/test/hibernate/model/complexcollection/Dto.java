package ru.hh.test.hibernate.model.complexcollection;

import java.io.Serializable;
import java.util.Objects;

public class Dto implements Serializable {

  private static final long serialVersionUID = -9056971448440505837L;
  private Integer anInt;
  private String aString;

  public Dto(Integer anInt, String aString) {
    this.anInt = anInt;
    this.aString = aString;
  }

  public Dto() {
  }

  public Integer getAnInt() {
    return anInt;
  }

  public void setAnInt(Integer anInt) {
    this.anInt = anInt;
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
    if (!(o instanceof Dto)) {
      return false;
    }
    Dto dto = (Dto) o;
    return Objects.equals(anInt, dto.anInt)
      && Objects.equals(aString, dto.aString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(anInt, aString);
  }
}
