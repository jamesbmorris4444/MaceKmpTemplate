package com.jetbrains.handson.kmm.shared.cache

import co.touchlab.kermit.Logger

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllDonors()
        }
    }

    internal fun getAllDonors(): List<Donor> {
        return dbQuery.selectAllDonorsInfo<Donor>(::mapDonorSelecting).executeAsList()
    }

    private fun mapDonorSelecting(
        lastName: String,
        firstName: String,
        middleName: String,
        branch: String,
        aboRh: String,
        dob: String,
        gender: Boolean
    ): Donor {
        return Donor(
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            branch = branch,
            aboRh = aboRh,
            dob = dob,
            gender = gender
        )
    }

    internal fun createDonor(donors: List<Donor>) {
        dbQuery.transaction {
            donors.forEach { donor ->
                insertDonor(donor)
            }
        }
    }

    private fun insertDonor(donor: Donor) {
        dbQuery.insertDonor(
            lastName = donor.lastName,
            middleName = donor.middleName,
            firstName = donor.firstName,
            branch = donor.branch,
            aboRh = donor.aboRh,
            dob = donor.dob,
            gender = donor.gender
        )
    }

    internal fun getDonors(lastName: String): List<Donor> {
        Logger.d("JIMX getDonors repo ${dbQuery.selectDonorsInfo(lastNameQuery = lastName).executeAsList()}")
        return dbQuery.selectDonorsInfo(lastNameQuery = lastName).executeAsList()
    }
}