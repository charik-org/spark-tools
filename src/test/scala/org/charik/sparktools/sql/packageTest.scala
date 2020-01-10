package org.charik.sparktools.sql

import org.apache.spark.sql.DataFrame
import org.charik.sparktools.UnitTestBase

class packageTest extends UnitTestBase {

  test("testSCWithExtraOperations") {
    val nbaDF = spark
      .read
      .format("csv")
      .options(Map("sep" -> ",", "header" -> "true"))
      .load("./src/test/resources/nba.csv")

    nbaDF.createOrReplaceTempView("NBA")
    val results: DataFrame = spark.sqlAdvanced("SELECT * from NBA;")

  }

}