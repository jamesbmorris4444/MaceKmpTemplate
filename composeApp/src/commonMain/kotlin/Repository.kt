import co.touchlab.kermit.Logger
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.entity.RocketLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface Repository {
//    fun setAppDatabase(app: Application)
//    fun isAppDatabaseInvalid(): Boolean
//    fun saveStagingDatabase(databaseName: String, db: File)
    suspend fun refreshDatabase(sdk: SpaceXSDK, refreshCompleted: (List<RocketLaunch>) -> Unit, refreshFailure: (String) -> Unit)
//    fun insertDonorIntoDatabase(donor: Donor)
//    fun insertDonorAndProductsIntoDatabase(donor: Donor, products: List<Product>)
//    fun stagingDatabaseDonorAndProductsList(): List<DonorWithProducts>
//    fun mainDatabaseDonorAndProductsList(): List<DonorWithProducts>
//    fun donorsFromFullNameWithProducts(searchLast: String, dob: String): List<DonorWithProducts>
//    fun handleSearchClick(searchKey: String) : List<Donor>
//    fun handleSearchClickWithProducts(searchKey: String) : List<DonorWithProducts>
//    fun insertReassociatedProductsIntoDatabase(donor: Donor, products: List<Product>)
//    fun donorFromNameAndDateWithProducts(donor: Donor): DonorWithProducts
}

class RepositoryImpl : Repository {

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

    override suspend fun refreshDatabase(
        sdk: SpaceXSDK,
        refreshCompleted: ((List<RocketLaunch>)) -> Unit,
        refreshFailure: (String) -> Unit): Unit = withContext(Dispatchers.IO) {
            runCatching {
                Logger.d("JIMX refreshDatabase start")
                sdk.getLaunches(true)
            }.onSuccess {
                Logger.d("JIMX refreshDatabase success: ${it.size}")
                refreshCompleted(it)
            }.onFailure {
                Logger.e("JIMX refreshDatabase failure: ${it.message}")
                refreshFailure(it.message ?: "failure")
            }

//        var disposable: Disposable? = null
//        disposable = donorsService.getDonors(Constants.API_KEY, Constants.LANGUAGE, 13)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .timeout(15L, TimeUnit.SECONDS)
//            .subscribe ({ donorResponse ->
//                disposable?.dispose()
//                initializeDataBase(refreshCompleted, donorResponse.results, donorResponse.products)
//                refreshFailure("")
//                LogUtils.D(LOG_TAG, LogUtils.FilterTags.withTags(LogUtils.TagFilter.RPO), "refreshDatabase success: donorsSize=${donorResponse.results.size}       productsSize=${donorResponse.products.size}")
//            },
//                { throwable ->
//                    refreshFailure(throwable.message ?: "No Message")
//                    LogUtils.D(LOG_TAG, LogUtils.FilterTags.withTags(LogUtils.TagFilter.RPO), "refreshDatabase failure: message=${throwable.message}")
//                    disposable?.dispose()
//                })
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
//    override fun insertDonorIntoDatabase(donor: Donor) {
//        stagingAppDatabase.databaseDao().insertDonor(donor)
//    }
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
//    override fun donorFromNameAndDateWithProducts(donor: Donor): DonorWithProducts {
//        return stagingAppDatabase.databaseDao().donorFromNameAndDateWithProducts(donor.lastName, donor.dob)
//    }
//
//    override fun mainDatabaseDonorAndProductsList(): List<DonorWithProducts> {
//        return mainAppDatabase.databaseDao().loadAllDonorsWithProducts()
//    }
//
//    private fun databaseDonorCount(database: AppDatabase): Int {
//        return database.databaseDao().getDonorEntryCount()
//    }
//
//    override fun handleSearchClick(searchKey: String) : List<Donor> {
//        val fullNameResponseList = listOf(
//            donorsFromFullName(mainAppDatabase, searchKey),
//            donorsFromFullName(stagingAppDatabase, searchKey)
//        )
//        val stagingDatabaseList = fullNameResponseList[1]
//        val mainDatabaseList = fullNameResponseList[0]
//        val newList = stagingDatabaseList.union(mainDatabaseList).distinctBy { donor -> Utils.donorComparisonByString(donor) }
//        LogUtils.D(LOG_TAG, LogUtils.FilterTags.withTags(LogUtils.TagFilter.RPO), "handleSearchClick success: searchKey=$searchKey     returnList=$newList")
//        return newList
//    }
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