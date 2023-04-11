package com.app.sqldelightsample.android

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.cash.sqldelight.db.SqlDriver
import com.app.sqldelightsample.DatabaseDriverFactory
import com.app.sqldelightsample.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val driver = DatabaseDriverFactory(context = applicationContext).createDriver()
//        driver.executeQuery(
//            identifier = null,
//            sql = """CREATE TABLE Persons(PersonID int,
//                    LastName varchar(255),
//                    FirstName varchar(255),
//                    Address varchar(255),
//                    City varchar(255)
//                    ) """,
//            mapper = {
//
//            },
//            parameters = 0,
//            binders = null
//        )
//        val result = driver.executeQuery(null, "select * from Persons", mapper = {
//
//            print("executed")
//            print("_________________" + it.next() + "____________________")
//        }, 0)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GreetingView(Greeting().greet())
                    val dbName = remember {
                        mutableStateOf(1)
                    }
                    Button(onClick = {
                        dbName.value = dbName.value + 1

                        test(
                            DatabaseDriverFactory(
                                applicationContext,
                                dbName.value.toString()
                            ).createDriver()
                        )
                    }) {
                        Text(text = "dbName ${dbName.value}.db")
                    }
                }
            }
        }

    }

    fun test(driver: SqlDriver) {
        val tableName = "table2"
        val identifier = 1

        // identifier here must be null to avoid SQLiteBindOrColumnIndexOutOfRangeException
        // in INSERT statements below
        driver.execute(
            null,
            "CREATE TABLE IF NOT EXISTS $tableName (player_number INTEGER NOT NULL, full_name TEXT NOT NULL)",
            0,
            null
        )
        driver.execute(
            null,
            "CREATE TABLE IF NOT EXISTS Persons(PersonID int,\n" +
                    "                    LastName varchar(255),\n" +
                    "                    FirstName varchar(255),\n" +
                    "                    Address varchar(255),\n" +
                    "                    City varchar(255)\n" +
                    "                    )",
            0,
            null
        )


        driver.executeQuery(
            identifier = identifier,
            sql = "SELECT * FROM $tableName",
            mapper = {
                if (!it.next()) {
                    Log.e("TAG", "EMPTY TEST DB")
                    driver.execute(
                        identifier,
                        "INSERT INTO $tableName (player_number, full_name) VALUES (?, ?)",
                        2
                    ) {
                        // indices start here from 1
                        bindLong(0, 1)
                        bindString(1, "John")
                    }
                    driver.execute(
                        identifier,
                        "INSERT INTO $tableName (player_number, full_name) VALUES (?, ?)",
                        2
                    ) {
                        bindLong(0, 2)
                        bindString(1, "Mike")
                    }
                }
            },
            parameters = 0,
            binders = null
        )

        driver.executeQuery(
            identifier = identifier,
            sql = "SELECT * FROM $tableName",
            mapper = { cursor ->
                while (cursor.next()) {
                    // indices start here from 0
                    val number = cursor.getLong(0)
                    val name = cursor.getString(1)
                    Log.e("TAG", "Player #$number $name")
                }
            },
            parameters = 0,
            binders = null
        )


    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
