package com.app.sqldelightsample

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class DatabaseDriverFactory(
    private val context: Context,
    private val dbName: String,
) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = SqlSchemaHelper(),
            context = context,
            name = "$dbName.db"
        )
    }
}
