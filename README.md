# Spark-Tools
A library of useful functions to extends the standard spark library.

## Additional functions

+ flattenSchema(sep: String)
+ withColumnNested(colName: String, newCol: Column)
+ dropColumns(columns: List[String])
+ fillingRate()
+ printFillingRate


## How to use:

```scala
import org.charik.sparktools.sql.functions._
val flatDF = df.flattenSchema("_")
```


## ToDo:

+ withColumnNestedRenamed(colName: String, newColName: String)