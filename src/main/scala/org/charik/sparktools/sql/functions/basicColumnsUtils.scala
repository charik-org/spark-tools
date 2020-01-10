package org.charik.sparktools.sql.functions

import org.apache.spark.sql.DataFrame

object basicColumnsUtils {
  def dropColumns(df: DataFrame, columns: List[String]): DataFrame = {
    df.drop(columns: _*)
  }
}