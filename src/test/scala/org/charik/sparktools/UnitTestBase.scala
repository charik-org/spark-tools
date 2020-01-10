package org.charik.sparktools

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.DataFrame
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}


/**
 * Unit test's base trait.
 */
trait UnitTestBase extends FunSuite
  with MockitoSugar
  with DataFrameSuiteBase
  with BeforeAndAfterAll {

  /**
   * Coverts a dataframe into an array of strings.
   *
   * @param df inout dataframe
   * @return seq
   */
  def toSeqString(df: DataFrame): Seq[String] = df.select("*").collect.map(_.toString)
}
