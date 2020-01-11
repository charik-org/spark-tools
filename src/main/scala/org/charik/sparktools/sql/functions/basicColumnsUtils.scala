package org.charik.sparktools.sql.functions

import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions.col

object basicColumnsUtils {
  def dropColumns(df: DataFrame, columns: List[String]): DataFrame = {
    df.drop(columns: _*)
  }

  def withColumnsSuffixed(df: DataFrame, suffix: String, colNames: List[String] = List.empty): DataFrame = {
    val cols: List[Column] = if(colNames.isEmpty) {
      df.columns.map(x => col(x).as(x+suffix)).toList
    } else {
      val notRenamedCols: List[String] = df.columns.diff(colNames).toList
      colNames.map(x => col(x).as(x+suffix)) ++ notRenamedCols.map(col)
    }
    df.select(cols: _*)
  }

  def withColumnsPrefixed(df: DataFrame, prefix: String, colNames: List[String] = List.empty): DataFrame = {
    val cols: List[Column] = if(colNames.isEmpty) {
      df.columns.map(x => col(x).as(prefix+x)).toList
    } else {
      val notRenamedCols: List[String] = df.columns.diff(colNames).toList
      colNames.map(x => col(x).as(prefix+x)) ++ notRenamedCols.map(col)
    }
    df.select(cols: _*)
  }
}