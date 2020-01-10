package org.charik.sparktools.sql

import org.apache.spark.sql.{Column, DataFrame}

package object functions {
  implicit class DFWithExtraOperations(df: DataFrame) {
    def flattenSchema(sep: String): DataFrame = {
      df.select(nestedColumnsUtils.getFlatSchemaExpr(df.schema, sep): _*)
    }

    def withColumnNested(newColName: String, newCol: Column): DataFrame = {
      nestedColumnsUtils.addNestedColumn(df, newColName, newCol)
    }

    def fillingRate(): DataFrame = {
      statsUtils.fillingRate(df)
    }

    def printFillingRate(): Unit = {
      printUtils.printFillingRate(df)
    }

    def dropColumns(columns: List[String]): DataFrame = {
      basicColumnsUtils.dropColumns(df, columns)
    }

  }
}
