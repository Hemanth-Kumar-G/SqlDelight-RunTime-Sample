package com.app.sqldelightsample

import app.cash.sqldelight.db.*


class SqlSchemaHelper : SqlSchema {
    override val version: Int
        get() = 9

    override fun create(driver: SqlDriver): QueryResult<Unit> {

        driver.execute(
            identifier = null,
            sql = """CREATE TABLE IF NOT EXISTS Per(PersonID int,
                    LastName varchar(255),
                    FirstName varchar(255),
                    Address varchar(255),
                    City varchar(255)
                    ) """,

            parameters = 0,
            binders = null
        )

        driver.execute(
            identifier = null,
            sql = """CREATE TABLE IF NOT EXISTS Per1(PersonID int,
                    LastName varchar(255),
                    FirstName varchar(255),
                    Address varchar(255),
                    City varchar(255)
                    ) """,

            parameters = 0,
            binders = null
        )

        driver.execute(
            identifier = null,
            sql = """CREATE TABLE IF NOT EXISTS Per2(PersonID int,
                    LastName varchar(255),
                    FirstName varchar(255),
                    Address varchar(255),
                    City varchar(255)
                    ) """,

            parameters = 0,
            binders = null
        )
        return QueryResult.Unit
    }

    override fun migrate(
        driver: SqlDriver,
        oldVersion: Int,
        newVersion: Int,
        vararg callbacks: AfterVersion,
    ): QueryResult<Unit> {

        driver.execute(
            identifier = null,
            sql = """CREATE TABLE IF NOT EXISTS Per5(PersonID int,
                    LastName varchar(255),
                    FirstName varchar(255),
                    Address varchar(255),
                    City varchar(255)
                    ) """,

            parameters = 0,
            binders = null
        )
        when (oldVersion) {
            2 -> {
                print("version 2")
            }
        }
        return QueryResult.Unit
    }


}