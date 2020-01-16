package org.charik.sparktools.sql

import org.apache.spark.sql.{Column, DataFrame}

package object functions {

  implicit class DFWithExtraOperations(df: DataFrame) {
    /**
     * Flatten a DataFrame nested schema by replacing `.` with a separator.
     *
     * @param sep [[String]] a separator to replace the dot in column names. Default: `_`
     * @return [[DataFrame]] DataFrame with flatten schema
     */
    def flattenSchema(sep: String = "_"): DataFrame = {
      df.select(nestedColumnsUtils.getFlatSchemaExpr(df.schema, sep): _*)
    }

    /**
     * Adds a nested column to dataframe
     *
     * @param newColName [[String]] Name of the new column
     * @param newCol     [[Column]] Value of the new column
     * @return [[DataFrame]] DataFrame with additional column
     */
    def withColumnNested(newColName: String, newCol: Column): DataFrame = {
      nestedColumnsUtils.addNestedColumn(df, newColName, newCol)
    }

    /**
     * Computes the filling rates for all dataframe's columns.
     * Filling rate is defined as:  ( Number of Non Null values ) / ( Number of rows ) .
     *
     * @return [[DataFrame]] DataFrame with a single row, holding the filling rate for each column.
     */
    def fillingRate(): DataFrame = {
      statsUtils.fillingRate(df)
    }

    /**
     * Print to the console the fillingRate of a given DataFrame
     */
    def printFillingRate(): Unit = {
      printUtils.printFillingRate(df)
    }

    /**
     * Drop multiple columns.
     *
     * @param columns [[List[String] ]] List of column names.
     * @return [[DataFrame]] DataFrame without the dropped columns.
     */
    def dropColumns(columns: List[String]): DataFrame = {
      basicColumnsUtils.dropColumns(df, columns)
    }

    /**
     * Add a suffix to the name for all or a subset of a dataframe's columns.
     *
     * @param suffix   [[String]] A suffix to  add to column names.
     * @param colNames [[List[String] ]] A list of column names. If colNames is empty, all columns will be renamed.
     *                 Default: rename all columns.
     * @return [[DataFrame]] DataFrame with renamed columns.
     */
    def withColumnsSuffixed(suffix: String, colNames: List[String] = List.empty): DataFrame = {
      basicColumnsUtils.withColumnsSuffixed(df, suffix, colNames)
    }

    /**
     * Add a prefix to the name for all or a subset of a dataframe's columns.
     *
     * @param prefix   [[String]] A suffix to  add to column names.
     * @param colNames [[List[String] ]] A list of column names. If colNames is empty, all columns will be renamed.
     *                 Default: rename all columns.
     * @return [[DataFrame]] DataFrame with renamed columns.
     */
    def withColumnsPrefixed(prefix: String, colNames: List[String] = List.empty): DataFrame = {
      basicColumnsUtils.withColumnsPrefixed(df, prefix, colNames)
    }

    def renameDuplicatedColumns: DataFrame = {
      basicColumnsUtils.renameDuplicatedColumns(df)
    }

    def hasDuplicatedColumns: Boolean = {
      basicColumnsUtils.hasDuplicatedColumns(df)
    }

    def getDuplicatedColumns: Set[String] = {
      basicColumnsUtils.getDuplicatedColumns(df)
    }

  }

}
