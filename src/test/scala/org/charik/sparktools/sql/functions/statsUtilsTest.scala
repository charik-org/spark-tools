package org.charik.sparktools.sql.functions

import org.apache.spark.sql.DataFrame
import org.charik.sparktools.UnitTestBase
import org.charik.sparktools.sql.functions.statsUtils.fillingRate

class statsUtilsTest extends UnitTestBase {

  test("test fillingRate should return the rate of non None values for each field.") {
    val nbaDF = spark
      .read
      .format("csv")
      .options(Map("sep" -> ",", "header" -> "true"))
      .load("./src/test/resources/nba.csv")

    val result: DataFrame = fillingRate(nbaDF)
    assert(result.select("Name").take(1).head.getAs[Float]("Name") === 99.78165938864629)
  }

}
