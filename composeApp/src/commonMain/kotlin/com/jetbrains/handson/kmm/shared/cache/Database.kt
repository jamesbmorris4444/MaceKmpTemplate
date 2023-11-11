package com.jetbrains.handson.kmm.shared.cache

import co.touchlab.kermit.Logger
import com.jetbrains.handson.kmm.shared.entity.DonorWithProducts

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries
    private val donorIdMap: HashMap<String, Long> = hashMapOf()

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllDonors()
        }
    }

    internal fun getAllDonors(): List<Donor> {
        return dbQuery.selectAllDonorsInfo(::mapDonorSelecting).executeAsList()
    }

    private fun mapDonorSelecting(
        id: Long,
        lastName: String,
        firstName: String,
        middleName: String,
        branch: String,
        aboRh: String,
        dob: String,
        gender: Boolean
    ): Donor {
        return Donor(
            id = id,
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            branch = branch,
            aboRh = aboRh,
            dob = dob,
            gender = gender
        )
    }

    internal fun insertOrReplaceDonor(donor: Donor) {
        dbQuery.insertOrReplace(
            lastName = donor.lastName,
            middleName = donor.middleName,
            firstName = donor.firstName,
            branch = donor.branch,
            aboRh = donor.aboRh,
            dob = donor.dob,
            gender = donor.gender
        )
    }

    internal fun createDonor(donors: List<Donor>) {
        dbQuery.transaction {
            donors.forEach { donor ->
                insertDonor(donor)
                Logger.d("JIMX==== ${donor.lastName},${donor.dob}       ${lastInsertDonorId()}")
                donorIdMap["${donor.lastName},${donor.dob}"] = lastInsertDonorId()
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
        return dbQuery.selectDonorsInfo("$lastName%").executeAsList()
    }

    internal fun lastInsertDonorId(): Long {
        return dbQuery.lastInsertDonorId().executeAsOne()
    }

    internal fun createProduct(products: List<Product>) {
        dbQuery.transaction {
            products.forEach { product ->
                insertProduct(product)
            }
        }
    }

    private fun insertProduct(product: Product) {
        dbQuery.insertProduct(
            donorId = 0L,
            din = product.din,
            aboRh = product.aboRh,
            productCode = product.productCode,
            expirationDate = product.expirationDate
        )
    }

    private fun getAllProducts(): List<Product> {
        return dbQuery.selectAllProductsInfo(::mapProductSelecting).executeAsList()
    }

    private fun mapProductSelecting(
        donorId: Long,
        din: String,
        aboRh: String,
        productCode: String,
        expirationDate: String
    ): Product {
        return Product(
            donorId = donorId,
            din = din,
            aboRh = aboRh,
            productCode = productCode,
            expirationDate = expirationDate
        )
    }

    internal fun donorFromNameAndDateWithProducts(lastName: String, dob: String): DonorWithProducts? {
        Logger.d("JIMX getDonors repo zzzz $lastName% ${dbQuery.donorFromNameAndDateWithProducts(lastName, dob).executeAsList()}")
        val donors: List<Donor> = dbQuery.donorFromNameAndDateWithProducts(lastName, dob).executeAsList()
        return if (donors.size == 1) {
            val donor = donors[0]
            val products: List<Product> = getAllProducts()
            val donorProducts = products.filter { it.donorId == donor.id }
            DonorWithProducts(donor = donor, products = donorProducts)
        } else {
            null
        }
    }


}