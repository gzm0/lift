package net.liftweb.mapper

/*                                                *\
 (c) 2006-2007 WorldWide Conferencing, LLC
 Distributed under an Apache License
 http://www.apache.org/licenses/LICENSE-2.0
 \*                                                */

import java.sql.{ResultSet, Types}
import java.lang.reflect.Method
import java.util.Date
import net.liftweb.util.FatLazy 

class MappedBinary[T<:Mapper[T]](val fieldOwner: T) extends MappedField[Array[Byte], T] {
  private val data : FatLazy[Array[Byte]] =  FatLazy(defaultValue)
  private val orgData: FatLazy[Array[Byte]] = FatLazy(defaultValue)
  
  protected def real_i_set_!(value : Array[Byte]) : Array[Byte] = {
    data() = value
    this.dirty_?( true)
    value
  }
  
  def dbFieldClass = classOf[Array[Byte]]
  
  /**
   * Get the JDBC SQL Type for this field
   */
  //  def getTargetSQLType(field : String) = Types.BINARY
  def targetSQLType = Types.BINARY
  
  def defaultValue = null
  override def writePermission_? = true
  override def readPermission_? = true

  protected def i_is_! = data.get
  
  protected def i_was_! = orgData.get
  
  protected[mapper] def doneWithSave() {orgData.setFrom(data)}

  protected def i_obscure_!(in : Array[Byte]) : Array[Byte] = {
    new Array[Byte](0)
  }
  
  override def setFromAny(f: Any): Array[Byte] =
    this.set((if (f == null) null
	     else if (f.isInstanceOf[Array[Byte]]) f.asInstanceOf[Array[Byte]];
	     else f.toString.getBytes("UTF-8")))
  
  def jdbcFriendly(field : String) : Object = is
  
  def real_convertToJDBCFriendly(value: Array[Byte]): Object = value
  
  def buildSetActualValue(accessor: Method, inst: AnyRef, columnName: String): (T, AnyRef) => Unit = 
    (inst, v) => doField(inst, accessor, {case f: MappedBinary[T] =>
      val toSet = v match {
        case null => null
        case ba: Array[Byte] => ba
        case other => other.toString.getBytes("UTF-8")
      }
      f.data() = toSet
      f.orgData() = toSet
    })

  def buildSetLongValue(accessor : Method, columnName : String): (T, Long, Boolean) => Unit = null
  def buildSetStringValue(accessor : Method, columnName : String): (T, String) => Unit  = null
  def buildSetDateValue(accessor : Method, columnName : String): (T, Date) => Unit = null 
  def buildSetBooleanValue(accessor : Method, columnName : String): (T, Boolean, Boolean) => Unit = null
  
  /**
   * Given the driver type, return the string required to create the column in the database
   */
  def fieldCreatorString(dbType: DriverType, colName: String): String = colName + " " + dbType.binaryColumnType
}

class MappedText[T<:Mapper[T]](val fieldOwner: T) extends MappedField[String, T] {
  private val data : FatLazy[String] =  FatLazy(defaultValue)
  private val orgData: FatLazy[String] = FatLazy(defaultValue)
  
  protected def real_i_set_!(value: String): String = {
    data() = value
    this.dirty_?( true)
    value
  }
  
  def dbFieldClass = classOf[String]
  
  /**
   * Get the JDBC SQL Type for this field
   */
  //  def getTargetSQLType(field : String) = Types.BINARY
  def targetSQLType = Types.CLOB
  
  def defaultValue = null
  override def writePermission_? = true
  override def readPermission_? = true

  protected def i_is_! = data.get
  
  protected def i_was_! = orgData.get
  
  protected[mapper] def doneWithSave() {orgData.setFrom(data)}

  protected def i_obscure_!(in: String): String = ""
  
  override def setFromAny(f: Any): String =
    this.set(f match {
      case null => null
      case s: String => s
      case other => other.toString
    })
  
  def jdbcFriendly(field : String): Object = real_convertToJDBCFriendly(data.get)

  
  def real_convertToJDBCFriendly(value: String): Object = value match {
  case null => null
  case s => s
}
  
  def buildSetActualValue(accessor: Method, inst: AnyRef, columnName: String): (T, AnyRef) => Unit = 
    (inst, v) => doField(inst, accessor, {case f: MappedText[T] =>
      val toSet = v match {
        case null => null
        case s: String => s
        case ba: Array[Byte] => new String(ba, "UTF-8")
        case other => other.toString
      }
      f.data() = toSet
      f.orgData() = toSet
    })

  def buildSetLongValue(accessor : Method, columnName : String): (T, Long, Boolean) => Unit = null
  def buildSetStringValue(accessor : Method, columnName : String): (T, String) => Unit  = (inst, v) => doField(inst, accessor, {case f: MappedText[T] =>
  val toSet = v match {
  case null => null
  case other => other
}
f.data() = toSet
f.orgData() = toSet
})
  def buildSetDateValue(accessor : Method, columnName : String): (T, Date) => Unit = null 
  def buildSetBooleanValue(accessor : Method, columnName : String): (T, Boolean, Boolean) => Unit = null
  
  /**
   * Given the driver type, return the string required to create the column in the database
   */
  def fieldCreatorString(dbType: DriverType, colName: String): String = colName + " " + dbType.clobColumnType
}

class MappedFakeClob[T<:Mapper[T]](val fieldOwner: T) extends MappedField[String, T] {
  private val data : FatLazy[String] =  FatLazy(defaultValue)
  private val orgData: FatLazy[String] = FatLazy(defaultValue)
  
  protected def real_i_set_!(value: String): String = {
    data() = value
    this.dirty_?( true)
    value
  }
  
  def dbFieldClass = classOf[Array[Byte]]
  
  /**
   * Get the JDBC SQL Type for this field
   */
  //  def getTargetSQLType(field : String) = Types.BINARY
  def targetSQLType = Types.BINARY
  
  def defaultValue = null
  override def writePermission_? = true
  override def readPermission_? = true

  protected def i_is_! = data.get
  
  protected def i_was_! = orgData.get
  
  protected[mapper] def doneWithSave() {orgData.setFrom(data)}

  protected def i_obscure_!(in: String): String = ""
  
  override def setFromAny(f: Any): String =
    this.set(f match {
      case null => null
      case s: String => s
      case other => other.toString
    })
  
  def jdbcFriendly(field : String): Object = real_convertToJDBCFriendly(data.get)

  
  def real_convertToJDBCFriendly(value: String): Object = value match {
  case null => null
  case s => s.getBytes("UTF-8")
}
  
  def buildSetActualValue(accessor: Method, inst: AnyRef, columnName: String): (T, AnyRef) => Unit = 
    (inst, v) => doField(inst, accessor, {case f: MappedFakeClob[T] =>
      val toSet = v match {
        case null => null
        case ba: Array[Byte] => new String(ba, "UTF-8")
        case other => other.toString
      }
      f.data() = toSet
      f.orgData() = toSet
    })

  def buildSetLongValue(accessor : Method, columnName : String): (T, Long, Boolean) => Unit = null
  def buildSetStringValue(accessor : Method, columnName : String): (T, String) => Unit  = (inst, v) => doField(inst, accessor, {case f: MappedFakeClob[T] =>
  val toSet = v match {
  case null => null
  case other => other
}
f.data() = toSet
f.orgData() = toSet
})
  def buildSetDateValue(accessor : Method, columnName : String): (T, Date) => Unit = null 
  def buildSetBooleanValue(accessor : Method, columnName : String): (T, Boolean, Boolean) => Unit = null
  
  /**
   * Given the driver type, return the string required to create the column in the database
   */
  def fieldCreatorString(dbType: DriverType, colName: String): String = colName + " " + dbType.binaryColumnType
}
