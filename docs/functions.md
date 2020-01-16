* Basic column utils:
    + flattenSchema(sep: String): DataFrame
    + withColumnNested(colName: String, newCol: Column): DataFrame
    + withColumnsSuffixed(colNames: List[String]) : DataFrame
    + withColumnsPrefixed(colNames: List[String]) : DataFrame
    + dropColumns(columns: List[String]): DataFrame
    + renameDuplicatedColumns: DataFrame
    + hasDuplicatedColumns: Boolean
    + getDuplicatedColumns: Set[String]

* Data Quality Utils
    + fillingRate(): DataFrame
    + printFillingRate: unit
    + isPrimaryKey(colNames: List[String]): Boolean
    + isUnique(colName: String): Boolean

* SQL
    + sqlAdvanced(sqlText: String): DataFrame



## Basic column utils
**flattenSchema**
```scala
import org.charik.sparktools.sql.functions._
val flatDF = df.flattenSchema("_")
```

**withColumnNested**
```scala
import org.charik.sparktools.sql.functions._
val nestedDF = df.withColumnNested("user.flag.active", lit(1))
```

**withColumnsSuffixed**
```scala
import org.charik.sparktools.sql.functions._
val renamedAllColumns = df.withColumnsSuffixed("_suffix")
val renamedSomeColumns = df.withColumnsSuffixed("_suffix", List("id", "sale_id"))
```

**withColumnsPrefixed**
```scala
import org.charik.sparktools.sql.functions._
val renamedAllColumns = df.withColumnsPrefixed("prefix_")
val renamedSomeColumns = df.withColumnsPrefixed("prefix_", List("id", "sale_id"))
```

**dropColumns**
```scala
import org.charik.sparktools.sql.functions._
val lightDF = df.dropColumns(List("name", "password", "email"))
```

**sqlAdvanced**

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
## Data Quality Utils
**isPrimaryKey**
```scala
import org.charik.sparktools.sql.checks._
df.isPrimaryKey(List("id", "sale_id"))
df.isUnique("id")
```