package ru.hh.test.hibernate.model.collection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import javax.sql.rowset.serial.SerialBlob;
import org.hibernate.HibernateException;
import org.hibernate.InstantiationException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

public class DtoType implements UserType {
  @Override
  public int[] sqlTypes() {
    return new int[] {Types.BLOB};
  }

  @Override
  public Class returnedClass() {
    return Dto.class;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    return Objects.equals(x, y);
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return x.hashCode();
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
    if (rs.wasNull()) {
      return null;
    };
    Blob blob = StandardBasicTypes.BLOB.nullSafeGet(rs, names[0], session);
    try (ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream())) {
      return ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new InstantiationException("Failed to deserialize instance", Dto.class, e);

    }
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, Types.OTHER);
      return;
    }
    Dto dto = (Dto) value;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ObjectOutputStream os = new ObjectOutputStream(baos)) {
      os.writeObject(dto);
      Blob blob = new SerialBlob(baos.toByteArray());
      StandardBasicTypes.BLOB.nullSafeSet(st, blob, index, session);
    } catch (IOException e) {
      throw new InstantiationException("Failed to serialize instance", Dto.class, e);
    }

  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public boolean isMutable() {
    return true;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return cached;
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }
}
