package org.charik.sparktools.sql

import org.apache.spark.sql.DataFrame

package object checks {

  implicit class DFWithExtraOperations(df: DataFrame) {

    /**
     * Returns whether a set of columns can be a primary key or not.
     *
     * @param colNames [[ List[String] ]] List of columns constructing the primary key.
     * @return [[Boolean]] True if no duplicates found for the list of columns
     */
    def isPrimaryKey(colNames: List[String]): Boolean = {
      ValidationUtils.isPrimaryKey(df, colNames)
    }

    /**
     * Count duplicated key created from a set of columns.
     *
     * @param colNames [[ List[String] ]] List of columns constructing the primary key.
     * @return [[Long]] count of key duplicates.
     */
    def countDuplicatedKey(colNames: List[String]): Long = {
      ValidationUtils.countDuplicatedKey(df, colNames)
    }

    def isUnique(colName: String): Boolean = {
      ValidationUtils.isUnique(df, colName)
    }
  }

}
