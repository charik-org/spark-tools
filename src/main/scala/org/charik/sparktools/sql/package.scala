package org.charik.sparktools

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}

package object sql {
  implicit class SCWithExtraOperations(sc: SparkSession) {
    def sqlAdvanced(sqlText: String): DataFrame = {
      SparkSessionUtils.sqlAdvanced(sc,  sqlText)
    }
  }
}
