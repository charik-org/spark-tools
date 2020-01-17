[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.charik/sparktools_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.charik/sparktools_2.11)

# Spark-Tools

This is a collection of useful functions to extends the standard spark
library.

## Install

Available via
[maven central](https://mvnrepository.com/artifact/org.charik/sparktools).
Add the latest release as a dependency to your project: Maven

| Spark | Scala |      SparkTools              |
|:------|:-----:|-----------------------------:|
| 2.3.x | 2.11  | `"sparktools_2.3" % "1.0.1"` |
| 2.4.x | 2.11  | `"sparktools_2.4" % "1.0.1"` |

**sbt**

```
libraryDependencies += "org.charik" %% "sparktools" % "1.0.0"
```

**Maven**

```
<dependency>
    <groupId>org.charik</groupId>
    <artifactId>sparktools_2.11</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Additional functions

* sql. [functions](docs/functions.ms)

## Examples:

### Basic column utils

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

Execute multi-line sql requests and return the last request as
DataFrame. Support comments starting with `#` or `--`

```scala
import org.charik.sparktools.sql._
val df = spark.sqlAdvanced("""  
    CREATE TEMPORARY VIEW Table as (SELECT * FROM json.`src/test.json` );
    # This is a comment
    SELECT * FROM Table;
""")
```

### Data Quality Utils

**isPrimaryKey**

```scala
import org.charik.sparktools.sql.checks._
df.isPrimaryKey(List("id", "sale_id"))
df.isUnique("id")
```

## More examples

Our library contains much more functionality than what we showed in the
basic example. We are in the process of adding more examples for its
advanced features.


## RoadMap:

* sql.functions:
  + withColumnNestedRenamed(colName: String, newColName: String) :
    DataFrame
  + withColumnsConcatenated(colName: String, colNames: List[String],
    sep: String="_") : DataFrame
  + orderColumns(dir: String = "asc") : DataFrame
  + join(df: DataFrame, on, how: String="left", lsuffix: String,
    rsuffix: String)
  + dropNestedColumn
* sql.testing:
  + compareSchema(df: DataFrame): Boolean
  + compareAll(df: DataFrame): Boolean
* sql.checks
  + isSchemaFlat: Boolean
  + isComplete(colName: String): Boolean
* sql.refined
  + isConstraintValid(colName: String, constraint: RefinedType): Boolean
+ sql.dates
  + addDays
  + addYears
  + addHours
  + litToday

# Contributing

The main mechanisms for contribution are:

* Reporting issues, suggesting improved functionality on Github issue
  tracker
* Suggesting new features in this discussion thread (see
  [RoadMap](https://github.com/helkaroui/spark-tools/issues/1) for
  details)
* Submitting Pull Requests (PRs) to fix issues, improve functionality.
