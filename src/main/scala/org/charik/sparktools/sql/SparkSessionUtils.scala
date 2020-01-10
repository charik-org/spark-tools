package org.charik.sparktools.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

private[sql] object SparkSessionUtils {
  /**
   * Executes a SQL query using Spark, returning the result as a `DataFrame`.
   * The dialect that is used for SQL parsing can be configured with 'spark.sql.dialect'.
   *
   * @since 2.0.0
   */
  def sqlAdvanced(sc: SparkSession, sqlText: String): DataFrame = {
    val sqlTextWithoutComments = sqlText.split("\n")
                                        .filter(!_.startsWith("#"))
                                        .filter(!_.startsWith("--"))
                                        .mkString("\n")
                                        .trim
    sqlTextWithoutComments.split(";")
                          .map(sc.sql)
                          .last
  }
}
