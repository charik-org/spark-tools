package org.charik.sparktools.sql.checks

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.charik.sparktools.UnitTestBase

class ValidationUtilsTest extends UnitTestBase {

  test("testIsPrimaryKey") {
    val inputDF = spark.createDataFrame(
      spark.sparkContext.parallelize(Seq(
        Row("1", "C1", "2018", "2018-04-22T16:39:12.000Z"),
        Row("2", "C1", "2018", "2018-05-10T16:39:12.000Z"),
        Row("3", "C1", "2018", "2018-05-10T16:39:13.000Z")
      )),
      StructType(Seq(
        StructField("ID", StringType, nullable = true),
        StructField("CLIENT_ID", StringType, nullable = true),
        StructField("year", StringType, nullable = true),
        StructField("created_dt", StringType, nullable = true)
      ))
    )

    assertTrue(ValidationUtils.isPrimaryKey(inputDF, List("ID", "CLIENT_ID")))
    assertTrue(!ValidationUtils.isPrimaryKey(inputDF, List("CLIENT_ID", "year")))

  }

  test("testCountDuplicatedKey") {
    val inputDF = spark.createDataFrame(
      spark.sparkContext.parallelize(Seq(
        Row("1", "C1", "2018", "2018-04-22T16:39:12.000Z"),
        Row("2", "C1", "2018", "2018-05-10T16:39:12.000Z"),
        Row("3", "C1", "2018", "2018-05-10T16:39:13.000Z")
      )),
      StructType(Seq(
        StructField("ID", StringType, nullable = true),
        StructField("CLIENT_ID", StringType, nullable = true),
        StructField("year", StringType, nullable = true),
        StructField("created_dt", StringType, nullable = true)
      ))
    )

    assert(ValidationUtils.countDuplicatedKey(inputDF, List("ID", "CLIENT_ID")) === 0)
    assert(ValidationUtils.countDuplicatedKey(inputDF, List("CLIENT_ID", "year")) === 1)
  }

}
