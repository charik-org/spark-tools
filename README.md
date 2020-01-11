# Spark-Tools
A library of useful functions to extends the standard spark library.

## Additional functions

+ flattenSchema(sep: String)
+ withColumnNested(colName: String, newCol: Column)
+ dropColumns(columns: List[String])
+ fillingRate()
+ printFillingRate
+ sqlAdvanced


## How to use:

#### flattenSchema
```scala
import org.charik.sparktools.sql.functions._
val flatDF = df.flattenSchema("_")
```

#### withColumnNested
```scala
import org.charik.sparktools.sql.functions._
val res = df.withColumnNested("user.name", lit("test"))
```

#### dropColumns
```scala
import org.charik.sparktools.sql.functions._
val res = df.dropColumns(List("name", "age", "password"))
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

+ withColumnNestedRenamed(colName: String, newColName: String)
+ compareDF(df2: DataFrame)