package org.charik.sparktools.sql

import org.apache.spark.sql.{Column, DataFrame}

package object functions {
  implicit class DFWithExtraOperations(df: DataFrame) {
    /**
     * Flatten a DataFrame nested schema by replacing `.` with a separator.
     * @param sep [[String]] a separator to replace the dot in column names. Default: `_`
     * @return [[DataFrame]] DataFrame with flatten schema
     */
    def flattenSchema(sep: String = "_"): DataFrame = {
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

    def withColumnsSuffixed(suffix: String, colNames: List[String] = List.empty): DataFrame = {
      basicColumnsUtils.withColumnsSuffixed(df, suffix, colNames)
    }

    def withColumnsPrefixed(prefix: String, colNames: List[String] = List.empty): DataFrame = {
      basicColumnsUtils.withColumnsPrefixed(df, prefix, colNames)
    }

  }
}
