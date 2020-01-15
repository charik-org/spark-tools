package org.charik.sparktools.sql.functions

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.lit
import org.charik.sparktools.UnitTestBase

class packageTest extends UnitTestBase {

  test("test flattenSchema should return flat dataframe schema.") {
    val nestedDF: DataFrame = spark.read.json("./src/test/resources/nested_df.json")
    val expectedColumns = Seq("client_id", "emails", "profession", "typology_classification", "typology_flg_hasMail", "typology_flg_hasPhone", "typology_type")
    val flattenColumns = nestedDF.flattenSchema("_").columns

    flattenColumns.map(col => assert(expectedColumns.contains(col)))
  }

  test("test withColumnNested should add a nested field to DataFrame.") {
    val nestedDF: DataFrame = spark.read.json("./src/test/resources/nested_df.json")
    val resultDF = nestedDF
      .withColumnNested("typology.1stLevelNestedField", lit(1))
      .withColumnNested("typology.flg.2ndLevelNestedField", lit(1))
      .withColumnNested("fromScratch.1stLevelNestedField", lit(1))
      .withColumnNested("fromScratch.level1.2ndLevelNestedField", lit(1))

    assert(resultDF.select("typology.1stLevelNestedField").take(1).head.getAs[Int]("1stLevelNestedField") == 1)
    assert(resultDF.select("typology.flg.2ndLevelNestedField").take(1).head.getAs[Int]("2ndLevelNestedField") == 1)

    assert(resultDF.select("fromScratch.1stLevelNestedField").take(1).head.getAs[Int]("1stLevelNestedField") == 1)
    assert(resultDF.select("fromScratch.level1.2ndLevelNestedField").take(1).head.getAs[Int]("2ndLevelNestedField") == 1)

    // todo test when "fromScratch" that the nested field is nullable
  }

  test("test deleteMultiple columns.") {
    val df: DataFrame = spark.read.json("./src/test/resources/nested_df.json")
    val resultDF = df.dropColumns(List("client_id", "typology.type")).columns

    assert(!resultDF.contains("client_id"))
    assert(!resultDF.contains("typology.type"))
  }

}
