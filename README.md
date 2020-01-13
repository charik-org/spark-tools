# Spark-Tools
This is a collection of useful functions to extends the standard spark library.

## Install


| Spark   | Scala  | SparkTools |
| :------ |:------:| ----------:|
|   2.3.x |   2.11 / 2.12 |   ` "org.charik" %% "sparktools_2.3" % "1.0.0" ` |
|   2.4.x |   2.11 |   ` "org.charik" %% "sparktools_2.4" % "1.0.0" ` |
|   3.0.0 |   2.11 |    -- |

```
libraryDependencies += "org.charik" %% "sparktools" % "1.0.0"
```


## Additional functions

* Basic column utils: 
    + flattenSchema(sep: String): DataFrame
    + withColumnNested(colName: String, newCol: Column): DataFrame
    + withColumnsSuffixed(colNames: List[String]) : DataFrame
    + withColumnsPrefixed(colNames: List[String]) : DataFrame
    + dropColumns(columns: List[String]): DataFrame
    
* Data Quality Utils
    + fillingRate(): DataFrame
    + printFillingRate: unit
    
* SQL 
    + sqlAdvanced(sqlText: String): DataFrame

## How to use:

#### flattenSchema
```scala
import org.charik.sparktools.sql.functions._
val flatDF = df.flattenSchema("_")
```

#### withColumnNested
```scala
import org.charik.sparktools.sql.functions._
val nestedDF = df.withColumnNested("user.flag.active", lit(1))
```

#### withColumnsSuffixed
```scala
import org.charik.sparktools.sql.functions._
val renamedAllColumns = df.withColumnsSuffixed("_suffix")
val renamedSomeColumns = df.withColumnsSuffixed("_suffix", List("id", "sale_id"))
```

#### withColumnsPrefixed
```scala
import org.charik.sparktools.sql.functions._
val renamedAllColumns = df.withColumnsPrefixed("prefix_")
val renamedSomeColumns = df.withColumnsPrefixed("prefix_", List("id", "sale_id"))
```


#### dropColumns
```scala
import org.charik.sparktools.sql.functions._
val lightDF = df.dropColumns(List("name", "password", "email"))
```


#### sqlAdvanced
Execute multi-line sql requests and return the last request as DataFrame.
Support comments starting with `#` or `--`
```scala
import org.charik.sparktools.sql._
val df = spark.sqlAdvanced("""  
    CREATE TEMPORARY VIEW Table as (SELECT * FROM json.`src/test.json` );
    # This is a comment
    SELECT * FROM Table;
""")
```

## ToDo:
* sql.functions:
    + withColumnNestedRenamed(colName: String, newColName: String) : DataFrame
    + withColumnsConcatenated(colName: String, colNames: List[String], sep: String="_") : DataFrame
    + orderColumns(dir: String = "asc") : DataFrame
    + join(df: DataFrame, on, how: String="left", lsuffix: String, rsuffix: String)
* sql.testing:
    + compareSchema(df: DataFrame): Boolean
    + compareAll(df: DataFrame): Boolean
* sql.RefinedDataset
    + as[T]



## To publish in Maven Central:
```sbt
sbt publishSigned
```
