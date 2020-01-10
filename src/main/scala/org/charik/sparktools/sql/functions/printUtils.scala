package org.charik.sparktools.sql.functions

import org.apache.spark.sql.DataFrame
import org.charik.sparktools.sql.functions.statsUtils.fillingRate

object printUtils {

  private[functions] def printFillingRate(df: DataFrame): Unit = {
    println(fillingRate(df))
  }
}
