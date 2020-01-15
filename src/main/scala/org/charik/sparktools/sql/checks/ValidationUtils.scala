package org.charik.sparktools.sql.checks

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, concat_ws}

private object ValidationUtils {
  def isPrimaryKey(df: DataFrame, colNames: List[String]): Boolean = {
    val countDuplicatedKeys = countDuplicatedKey(df, colNames)
    countDuplicatedKeys == 0
  }

  def countDuplicatedKey(df: DataFrame, colNames: List[String]): Long = {
    df
      .select(concat_ws("_", colNames.map(col): _*).as("pk"))
      .groupBy("pk")
      .count()
      .where(col("count") > 1)
      .count()
  }



}
