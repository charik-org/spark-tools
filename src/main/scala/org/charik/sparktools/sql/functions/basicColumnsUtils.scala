package org.charik.sparktools.sql.functions

import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Column, DataFrame}

object basicColumnsUtils {
  def dropColumns(df: DataFrame, columns: List[String]): DataFrame = {
    df.drop(columns: _*)
  }

  def withColumnsSuffixed(df: DataFrame, suffix: String, colNames: List[String] = List.empty): DataFrame = {
    val cols: List[Column] = if (colNames.isEmpty) {
      df.columns.map(x => col(x).as(x + suffix)).toList
    } else {
      val notRenamedCols: List[String] = df.columns.diff(colNames).toList
      colNames.map(x => col(x).as(x + suffix)) ++ notRenamedCols.map(col)
    }
    df.select(cols: _*)
  }

  def withColumnsPrefixed(df: DataFrame, prefix: String, colNames: List[String] = List.empty): DataFrame = {
    val cols: List[Column] = if (colNames.isEmpty) {
      df.columns.map(x => col(x).as(prefix + x)).toList
    } else {
      val notRenamedCols: List[String] = df.columns.diff(colNames).toList
      colNames.map(x => col(x).as(prefix + x)) ++ notRenamedCols.map(col)
    }
    df.select(cols: _*)
  }

  def hasDuplicatedColumns(df: DataFrame): Boolean = {
    val duplicatedColumns: Set[String] = getDuplicatedColumns(df)
    duplicatedColumns.nonEmpty
  }

  def getDuplicatedColumns(df: DataFrame): Set[String] = {
    val frame = df.select("*")
    val expressions = frame.queryExecution.analyzed.asInstanceOf[Project].projectList
    expressions
      .map(_.name)
      .groupBy(identity)
      .filter(_._2.size > 1)
      .keys
      .toSet
  }

  def renameDuplicatedColumns(df: DataFrame): DataFrame = {
    val frame = df.select("*")
    val expressions = frame.queryExecution.analyzed.asInstanceOf[Project].projectList
    val duplicatedColumns = expressions
      .map(_.name)
      .groupBy(identity)
      .filter(_._2.size > 1)
      .keys
      .toSet

    if (duplicatedColumns.isEmpty) df
    else {

      type NameCount = Map[String, Int]
      type Res = (NameCount, Seq[Column])

      val zero: Res = (Map.empty, Nil)

      val (_, columns) =
        expressions.foldLeft(zero) {
          case ((counts, cols), exp) =>
            if (duplicatedColumns(exp.name)) {
              val i = counts.getOrElse(exp.name, 0)
              val alias_name = if (i == 0) exp.name else exp.name + "_" + i
              val col = new Column(exp).as(alias_name)

              (counts + (exp.name -> (i + 1)), cols :+ col)
            } else {
              (counts, cols :+ new Column(exp))
            }
        }

      df.select(columns: _*)
    }
  }
}