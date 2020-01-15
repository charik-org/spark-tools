package org.charik.sparktools.sql.functions

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, count}

object statsUtils {
  private[functions] def fillingRate(df: DataFrame): DataFrame = {
    val filledAggs = df.columns.map(x => count(col(x)) / count("*") * 100 as x)
    df.agg(filledAggs.head, filledAggs.tail: _*)
  }
}
