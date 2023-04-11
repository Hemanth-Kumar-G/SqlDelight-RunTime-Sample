package com.app.sqldelightsample

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class DatabaseDriverFactory(
    private val dbName: String,
) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = SqlSchemaHelper(),
            name = "$dbName.db"
        )
    }
}