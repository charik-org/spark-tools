package org.charik.sparktools.sql.functions

import org.apache.spark.sql.functions.{col, struct, when}
import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{Column, DataFrame}

object nestedColumnsUtils {
  private def nullableCol(parentCol: Column, c: Column): Column = {
    when(parentCol.isNotNull, c)
  }

  private def nullableCol(c: Column): Column = {
    nullableCol(c, c)
  }

  private def createNestedStructs(splitted: Seq[String], newCol: Column): Column = {
    splitted
      .foldRight(newCol) {
        case (colName, nestedStruct) => nullableCol(struct(nestedStruct as colName))
      }
  }

  private def recursiveAddNestedColumn(splitted: Seq[String], col: Column, colType: DataType, nullable: Boolean, newCol: Column): Column = {
    colType match {
      case colType: StructType if splitted.nonEmpty => {
        var modifiedFields: Seq[(String, Column)] = colType.fields
          .map(f => {
            var curCol = col.getField(f.name)
            if (f.name == splitted.head) {
              curCol = recursiveAddNestedColumn(splitted.tail, curCol, f.dataType, f.nullable, newCol)
            }
            (f.name, curCol as f.name)
          })

        if (!modifiedFields.exists(_._1 == splitted.head)) {
          modifiedFields :+= (splitted.head, nullableCol(col, createNestedStructs(splitted.tail, newCol)) as splitted.head)
        }

        var modifiedStruct: Column = struct(modifiedFields.map(_._2): _*)
        if (nullable) {
          modifiedStruct = nullableCol(col, modifiedStruct)
        }
        modifiedStruct
      }
      case _ => createNestedStructs(splitted, newCol)
    }
  }

  private[sql] def addNestedColumn(df: DataFrame, newColName: String, newCol: Column): DataFrame = {
    if (newColName.contains('.')) {
      val splitted = newColName.split('.')

      val modifiedOrAdded: (String, Column) = df.schema.fields
        .find(_.name == splitted.head)
        .map(f => (f.name, recursiveAddNestedColumn(splitted.tail, col(f.name), f.dataType, f.nullable, newCol)))
        .getOrElse {
          (splitted.head, createNestedStructs(splitted.tail, newCol) as splitted.head)
        }

      df.withColumn(modifiedOrAdded._1, modifiedOrAdded._2)

    } else {
      df.withColumn(newColName, newCol)
    }
  }

  // todo add rename nested column

  private[sql] def getFlatSchemaExpr(schema: StructType, sep: String, colPrefix: String = null, castPrefix: String = null): Array[Column] = {
    schema.fields.flatMap(f => {
      val colName = if (colPrefix == null) f.name else (colPrefix + "." + f.name)
      val castName = if (castPrefix == null) f.name else (castPrefix + sep + f.name)
      f.dataType match {
        case st: StructType => getFlatSchemaExpr(st, sep, colName, castName)
        case _ => Array(col(colName).as(castName))
      }
    })
  }
}
