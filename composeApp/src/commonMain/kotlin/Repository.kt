
import co.touchlab.kermit.Logger
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.cache.Database
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import com.jetbrains.handson.kmm.shared.cache.Donor
import com.jetbrains.handson.kmm.shared.entity.DonorWithProducts
import com.jetbrains.handson.kmm.shared.entity.RocketLaunch
import kotlinx.coroutines.CoroutineScope

interface Repository {
//    fun setAppDatabase(app: Application)
//    fun isAppDatabaseInvalid(): Boolean
//    fun saveStagingDatabase(databaseName: String, db: File)
    suspend fun refreshDatabase(composableScope: CoroutineScope): Pair<List<RocketLaunch>, String>
    fun refreshDonors()
    fun insertDonorIntoDatabase(donor: Donor)
//    fun insertDonorAndProductsIntoDatabase(donor: Donor, products: List<Product>)
//    fun stagingDatabaseDonorAndProductsList(): List<DonorWithProducts>
//    fun mainDatabaseDonorAndProductsList(): List<DonorWithProducts>
//    fun donorsFromFullNameWithProducts(searchLast: String, dob: String): List<DonorWithProducts>
    fun handleSearchClick(searchKey: String) : List<Donor>
//    fun handleSearchClickWithProducts(searchKey: String) : List<DonorWithProducts>
//    fun insertReassociatedProductsIntoDatabase(donor: Donor, products: List<Product>)
    fun donorFromNameAndDateWithProducts(donor: Donor): DonorWithProducts?
}

class RepositoryImpl(private val sdk: SpaceXSDK, private val databaseDriverFactory: DatabaseDriverFactory) : Repository {

//    private lateinit var mainAppDatabase: AppDatabase
//    private lateinit var stagingAppDatabase: AppDatabase
//    private val donorsService: APIInterface = APIClient.client

//    override fun setAppDatabase(app: Application) {
//        val dbList = AppDatabase.newInstance(app.applicationContext, MAIN_DATABASE_NAME, MODIFIED_DATABASE_NAME)
//        mainAppDatabase = dbList[0]
//        stagingAppDatabase = dbList[1]
//    }
//
//    override fun isAppDatabaseInvalid(): Boolean {
//        return databaseDonorCount(mainAppDatabase) == 0
//    }

    override suspend fun refreshDatabase(composableScope: CoroutineScope): Pair<List<RocketLaunch>, String> {
        var result: List<RocketLaunch> = listOf()
        var message = ""
        try {
            result = sdk.getLaunches(true)
            Logger.d("JIMX refreshDatabase success: ${result.size}")
        } catch (e: Exception) {
            message = e.message ?: "NULL message"
            Logger.e("JIMX refreshDatabase failure: ${e.message}")
        }
        return Pair(result, message)
    }

    override fun refreshDonors() {
        Database(databaseDriverFactory).clearDatabase()
        Database(databaseDriverFactory).createDonor(createListOfDonors())
        val list = Database(databaseDriverFactory).getAllDonors()
        Logger.d("JIMX getAllDonorsSize ${list.size}")
    }

//    private fun createListOfProducts(donors: Int): List<Product> {
//        for (index in 0 until donors) {
//            val aboRh: String  = when (index) {
//                0 -> { "O-Positive" }
//                1 -> { "O-Negative" }
//                2 -> { "A-Positive" }
//                3 -> { "A-Negative" }
//                4 -> { "B-Positive" }
//                5 -> { "B-Negative" }
//                6 -> { "AB-Positive" }
//                7 -> { "AB-Negative" }
//                8 -> { "O-Positive" }
//                9 -> { "O-Negative" }
//                10 -> { "A-Positive" }
//                11 -> { "A-Negative" }
//                12 -> { "B-Positive" }
//                13 -> { "B-Negative" }
//                14 -> { "AB-Positive" }
//                15 -> { "AB-Negative" }
//                16 -> { "O-Positive" }
//                17 -> { "O-Negative" }
//                18 -> { "A-Positive" }
//                19 -> { "A-Negative" }
//                else -> { "" }
//            }
//            val productCode = (random.nextInt(10000) + 9990000).toString()
//            val jsonSubArray = JSONArray()
//            for (productIndex in 0 until productCount) {
//                val jsonObject = JSONObject()
//                jsonObject.put("din", din)
//                jsonObject.put("abo_rh", aboRh)
//                jsonObject.put("product_code", productCode)
//                jsonObject.put("expiration_date", "01 Jan 2020")
//                jsonSubArray.put(jsonObject)
//            }
//
//        }
//        return jsonTopArray
//    }

//    private fun createListOfProducts(donors: Int) : List<Product> {
//        val random = Random()
//        val jsonTopArray = JSONArray()
//        for (index in 0 until donors) {
//            val productCount = random.nextInt(4)
//            val din = random.nextInt(1000).toString()
//            val aboRh: String  = when (index) {
//                0 -> { "O-Positive" }
//                1 -> { "O-Negative" }
//                2 -> { "A-Positive" }
//                3 -> { "A-Negative" }
//                4 -> { "B-Positive" }
//                5 -> { "B-Negative" }
//                6 -> { "AB-Positive" }
//                7 -> { "AB-Negative" }
//                8 -> { "O-Positive" }
//                9 -> { "O-Negative" }
//                10 -> { "A-Positive" }
//                11 -> { "A-Negative" }
//                12 -> { "B-Positive" }
//                13 -> { "B-Negative" }
//                14 -> { "AB-Positive" }
//                15 -> { "AB-Negative" }
//                16 -> { "O-Positive" }
//                17 -> { "O-Negative" }
//                18 -> { "A-Positive" }
//                19 -> { "A-Negative" }
//                else -> { "" }
//            }
//            val productCode = (random.nextInt(10000) + 9990000).toString()
//            val jsonSubArray = JSONArray()
//            for (productIndex in 0 until productCount) {
//                val jsonObject = JSONObject()
//                jsonObject.put("din", din)
//                jsonObject.put("abo_rh", aboRh)
//                jsonObject.put("product_code", productCode)
//                jsonObject.put("expiration_date", "01 Jan 2020")
//                jsonSubArray.put(jsonObject)
//            }
//            jsonTopArray.put(jsonSubArray)
//        }
//        return jsonTopArray
//    }

    private fun createListOfDonors() : List<Donor> {
        val donorList: MutableList<Donor> = mutableListOf()
        for (index in 0 until 20) {
            val lastName: String  = when (index) {
                0 -> { "Morris01" }
                1 -> { "Smith01" }
                2 -> { "Taylor01" }
                3 -> { "Lewis01" }
                4 -> { "Snowdon01" }
                5 -> { "Miller01" }
                6 -> { "Jones01" }
                7 -> { "Johnson01" }
                8 -> { "Early01" }
                9 -> { "Wynn01" }
                10 -> { "Morris02" }
                11 -> { "Smith02" }
                12 -> { "Taylor02" }
                13 -> { "Lewis02" }
                14 -> { "Snowdon02" }
                15 -> { "Miller02" }
                16 -> { "Jones02" }
                17 -> { "Johnson02" }
                18 -> { "Early02" }
                19 -> { "Wynn02" }
                else -> { "" }
            }
            val firstName: String  = when (index) {
                0 -> { "FirstMorris01" }
                1 -> { "FirstSmith01" }
                2 -> { "FirstTaylor01" }
                3 -> { "FirstLewis01" }
                4 -> { "FirstSnowdon01" }
                5 -> { "FirstMiller01" }
                6 -> { "FirstJones01" }
                7 -> { "FirstJohnson01" }
                8 -> { "FirstEarly01" }
                9 -> { "FirstWynn01" }
                10 -> { "FirstMorris02" }
                11 -> { "FirstSmith02" }
                12 -> { "FirstTaylor02" }
                13 -> { "FirstLewis02" }
                14 -> { "FirstSnowdon02" }
                15 -> { "FirstMiller02" }
                16 -> { "FirstJones02" }
                17 -> { "FirstJohnson02" }
                18 -> { "FirstEarly02" }
                19 -> { "FirstWynn02" }
                else -> { "" }
            }
            val middleName: String  = when (index) {
                0 -> { "MiddleMorris01" }
                1 -> { "MiddleSmith01" }
                2 -> { "MiddleTaylor01" }
                3 -> { "MiddleLewis01" }
                4 -> { "MiddleSnowdon01" }
                5 -> { "MiddleMiller01" }
                6 -> { "MiddleJones01" }
                7 -> { "MiddleJohnson01" }
                8 -> { "MiddleEarly01" }
                9 -> { "MiddleWynn01" }
                10 -> { "MiddleMorris02" }
                11 -> { "MiddleSmith02" }
                12 -> { "MiddleTaylor02" }
                13 -> { "MiddleLewis02" }
                14 -> { "MiddleSnowdon02" }
                15 -> { "MiddleMiller02" }
                16 -> { "MiddleJones02" }
                17 -> { "MiddleJohnson02" }
                18 -> { "MiddleEarly02" }
                19 -> { "MiddleWynn02" }
                else -> { "" }
            }
            val aboRh: String  = when (index) {
                0 -> { "O-Positive" }
                1 -> { "O-Negative" }
                2 -> { "A-Positive" }
                3 -> { "A-Negative" }
                4 -> { "B-Positive" }
                5 -> { "B-Negative" }
                6 -> { "AB-Positive" }
                7 -> { "AB-Negative" }
                8 -> { "O-Positive" }
                9 -> { "O-Negative" }
                10 -> { "A-Positive" }
                11 -> { "A-Negative" }
                12 -> { "B-Positive" }
                13 -> { "B-Negative" }
                14 -> { "AB-Positive" }
                15 -> { "AB-Negative" }
                16 -> { "O-Positive" }
                17 -> { "O-Negative" }
                18 -> { "A-Positive" }
                19 -> { "A-Negative" }
                else -> { "" }
            }
            val dob: String  = when (index) {
                0 -> { "01 Jan 1995" }
                1 -> { "02 Jan 1995" }
                2 -> { "03 Jan 1995" }
                3 -> { "04 Jan 1995" }
                4 -> { "05 Jan 1995" }
                5 -> { "06 Jan 1995" }
                6 -> { "07 Jan 1995" }
                7 -> { "08 Jan 1995" }
                8 -> { "09 Jan 1995" }
                9 -> { "10 Jan 1995" }
                10 -> { "11 Jan 1995" }
                11 -> { "12 Jan 1995" }
                12 -> { "13 Jan 1995" }
                13 -> { "14 Jan 1995" }
                14 -> { "15 Jan 1995" }
                15 -> { "16 Jan 1995" }
                16 -> { "17 Jan 1995" }
                17 -> { "18 Jan 1995" }
                18 -> { "19 Jan 1995" }
                19 -> { "20 Jan 1995" }
                else -> { "" }
            }
            val branch: String  = when (index) {
                0 -> { "The Army" }
                1 -> { "The Army" }
                2 -> { "The Army" }
                3 -> { "The Army" }
                4 -> { "The Army" }
                5 -> { "The Army" }
                6 -> { "The Marines" }
                7 -> { "The Marines" }
                8 -> { "The Marines" }
                9 -> { "The Marines" }
                10 -> { "The Navy" }
                11 -> { "The Navy" }
                12 -> { "The Navy" }
                13 -> { "The Navy" }
                14 -> { "The Navy" }
                15 -> { "The Air Force" }
                16 -> { "The Air Force" }
                17 -> { "The Air Force" }
                18 -> { "The JCS" }
                19 -> { "The JCS" }
                else -> { "" }
            }
            donorList.add(Donor(id = 1L, lastName = lastName, middleName = middleName, firstName = firstName, aboRh = aboRh, dob = dob, branch = branch, gender = true))
        }
        return donorList
    }

//    private fun initializeDataBase(refreshCompleted: () -> Unit, donors: List<Donor>, products: List<List<Product>>) {
//        List(donors.size) { donorIndex -> List(products[donorIndex].size) { productIndex -> products[donorIndex][productIndex].donorId = donors[donorIndex].id } }
//        LogUtils.D(LOG_TAG, LogUtils.FilterTags.withTags(LogUtils.TagFilter.RPO), "initializeDataBase complete: donorsSize=${donors.size}")
//        insertDonorsAndProductsIntoLocalDatabase(donors, products)
//        refreshCompleted()
//    }
//
//    private fun insertDonorsAndProductsIntoLocalDatabase(donors: List<Donor>, products: List<List<Product>>) {
//        mainAppDatabase.databaseDao().insertDonorsAndProductLists(donors, products)
//    }
//
//    override fun saveStagingDatabase(databaseName: String, db: File) {
//        val dbShm = File(db.parent, "$databaseName-shm")
//        val dbWal = File(db.parent, "$databaseName-wal")
//        val dbBackup = File(db.parent, "$databaseName-backup")
//        val dbShmBackup = File(db.parent, "$databaseName-backup-shm")
//        val dbWalBackup = File(db.parent, "$databaseName-backup-wal")
//        if (db.exists()) {
//            db.copyTo(dbBackup, true)
//        }
//        if (dbShm.exists()) {
//            dbShm.copyTo(dbShmBackup, true)
//        }
//        if (dbWal.exists()) {
//            dbWal.copyTo(dbWalBackup, true)
//        }
//        LogUtils.D(LOG_TAG, LogUtils.FilterTags.withTags(LogUtils.TagFilter.RPO), "Path Name $db exists and was backed up")
//    }
//
//    /*
//     *  The code below here does CRUD on the database
//     */
//    /**
//     * The code below here does CRUD on the database
//     * Methods:
//     *   insertDonorIntoDatabase
//     *   insertDonorAndProductsIntoDatabase
//     *   insertReassociatedProductsIntoDatabase
//     *   stagingDatabaseDonorAndProductsList
//     *   donorFromNameAndDateWithProducts
//     *   mainDatabaseDonorAndProductsList
//     *   databaseDonorCount
//     *   handleSearchClick
//     *   handleSearchClickWithProducts
//     *   donorsFromFullNameWithProducts
//     */
//
    override fun insertDonorIntoDatabase(donor: Donor) {
        Database(databaseDriverFactory).insertDonor(donor)
    }
//
//    override fun insertDonorAndProductsIntoDatabase(donor: Donor, products: List<Product>) {
//        stagingAppDatabase.databaseDao().insertDonorAndProducts(donor, products)
//    }
//
//    override fun insertReassociatedProductsIntoDatabase(donor: Donor, products: List<Product>) {
//        stagingAppDatabase.databaseDao().insertDonorAndProducts(donor, products)
//    }
//
//    override fun stagingDatabaseDonorAndProductsList(): List<DonorWithProducts> {
//        return stagingAppDatabase.databaseDao().loadAllDonorsWithProducts()
//    }
//
    override fun donorFromNameAndDateWithProducts(donor: Donor): DonorWithProducts? {
        return Database(databaseDriverFactory).donorFromNameAndDateWithProducts(donor.lastName, donor.dob)
    }
//
//    override fun mainDatabaseDonorAndProductsList(): List<DonorWithProducts> {
//        return mainAppDatabase.databaseDao().loadAllDonorsWithProducts()
//    }
//
//    private fun databaseDonorCount(database: AppDatabase): Int {
//        return database.databaseDao().getDonorEntryCount()
//    }
//
    override fun handleSearchClick(searchKey: String) : List<Donor> {
        return Database(databaseDriverFactory).getDonors(searchKey)

    }
//
//    private fun donorsFromFullName(database: AppDatabase, search: String): List<Donor> {
//        val searchLast: String
//        var searchFirst = "%"
//        val index = search.indexOf(',')
//        if (index < 0) {
//            searchLast = "$search%"
//        } else {
//            val last = search.substring(0, index)
//            val first = search.substring(index + 1)
//            searchFirst = "$first%"
//            searchLast = "$last%"
//        }
//        return database.databaseDao().donorsFromFullName(searchLast, searchFirst)
//    }
//
//    override fun handleSearchClickWithProducts(searchKey: String) : List<DonorWithProducts> {
//        val fullNameResponseList = listOf(
//            donorsFromFullNameWithProducts(mainAppDatabase, searchKey),
//            donorsFromFullNameWithProducts(stagingAppDatabase, searchKey)
//        )
//        val stagingDatabaseList = fullNameResponseList[1]
//        val mainDatabaseList = fullNameResponseList[0]
//        val newList = stagingDatabaseList.union(mainDatabaseList).distinctBy { donor -> Utils.donorComparisonByStringWithProducts(donor) }
//        LogUtils.D(LOG_TAG, LogUtils.FilterTags.withTags(LogUtils.TagFilter.RPO), "handleSearchClickWithProducts success: searchKey=$searchKey     returnList=$newList")
//        return newList
//    }
//
//    private fun donorsFromFullNameWithProducts(database: AppDatabase, search: String): List<DonorWithProducts> {
//        val searchLast: String
//        var searchFirst = "%"
//        val index = search.indexOf(',')
//        if (index < 0) {
//            searchLast = "$search%"
//        } else {
//            val last = search.substring(0, index)
//            val first = search.substring(index + 1)
//            searchFirst = "$first%"
//            searchLast = "$last%"
//        }
//        return database.databaseDao().donorsFromFullNameWithProducts(searchLast, searchFirst)
//    }
//
//    override fun donorsFromFullNameWithProducts(searchLast: String, dob: String): List<DonorWithProducts> {
//        return stagingAppDatabase.databaseDao().donorsFromFullNameWithProducts(searchLast, dob)
//    }

}