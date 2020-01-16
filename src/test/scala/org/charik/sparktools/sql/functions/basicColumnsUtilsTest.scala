package org.charik.sparktools.sql.functions

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.charik.sparktools.UnitTestBase

class basicColumnsUtilsTest extends UnitTestBase {

  test("test WithColumnsSuffixed should rename a list of columns and add a given suffix") {
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

    val renameAllColumns = basicColumnsUtils.withColumnsSuffixed(inputDF, "_suffix").columns
    val renameSomeColumns = basicColumnsUtils.withColumnsSuffixed(inputDF, "_suffix", List("ID", "CLIENT_ID")).columns

    assert(renameAllColumns.mkString(",") === "ID_suffix,CLIENT_ID_suffix,year_suffix,created_dt_suffix")
    assert(renameSomeColumns.mkString(",") === "ID_suffix,CLIENT_ID_suffix,year,created_dt")
  }


  test("test WithColumnsPrefixed should rename a list of columns and add a given prefix") {
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

    val renameAllColumns = basicColumnsUtils.withColumnsPrefixed(inputDF, "prefix_").columns
    val renameSomeColumns = basicColumnsUtils.withColumnsPrefixed(inputDF, "prefix_", List("ID", "CLIENT_ID")).columns

    assert(renameAllColumns.mkString(",") === "prefix_ID,prefix_CLIENT_ID,prefix_year,prefix_created_dt")
    assert(renameSomeColumns.mkString(",") === "prefix_ID,prefix_CLIENT_ID,year,created_dt")
  }

  test("renameDuplicatedColumns should rename duplicated columns") {
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

    val dfDuplicatedColumns = inputDF.join(inputDF, Seq("ID"), "full")

    val result = basicColumnsUtils.renameDuplicatedColumns(dfDuplicatedColumns)
    println(result.show)
  }

}
