# Spark-Tools
This is a collection of useful functions to extends the standard spark library.

## Additional functions

* sql.functions: 
    + flattenSchema(sep: String): DataFrame
    + withColumnNested(colName: String, newCol: Column): DataFrame
    + dropColumns(columns: List[String]): DataFrame
    + fillingRate(): DataFrame
    + printFillingRate: unit
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
    + withColumnsSuffixed(colNames: List[String]) : DataFrame
    + withColumnsPreffixed(colNames: List[String]) : DataFrame
    + withColumnsConcatenated(colName: String, colNames: List[String], sep: String="_") : DataFrame
    + orderColumns(dir: String = "asc") : DataFrame
    + join(df: DataFrame, on, how: String="left", lsuffix: String, rsuffix: String)
* sql.RefinedDataset
    + as[T]
