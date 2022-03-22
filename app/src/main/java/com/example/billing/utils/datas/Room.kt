package com.example.billing.utils.datas

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailTypeDao {
    @Query("SELECT * FROM detailTypes")
    fun queryAll(): Flow<List<DetailType>>

    @Query("SELECT * FROM detailTypes WHERE name = :value")
    fun queryWithName(value: String): Flow<DetailType>

    @Query("SELECT * FROM detailTypes WHERE id = :value")
    fun queryWithId(value: Int): Flow<DetailType>

    @Query("SELECT * FROM detailTypes WHERE triad = :value")
    fun queryWithTriad(value: Boolean): Flow<List<DetailType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detailType: DetailType):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(detailTypes: List<DetailType>)

    @Query("DELETE FROM detailTypes")
    fun deleteAll()

    @Update
    fun updata(detailType: DetailType)

    @Delete
    fun delete(detailType: DetailType)

    @RawQuery(observedEntities = [DetailType::class])
    fun rawQuery(query: SupportSQLiteQuery): Flow<List<DetailType>>

    fun execSql(sql: String) = rawQuery(
        SimpleSQLiteQuery(
            sql,
            arrayOf()
        )
    )
}

@Dao
interface DetailDao {
    @Query("SELECT * FROM details")
    fun queryAll(): Flow<List<Detail>>

    @Query("SELECT * FROM details WHERE type LIKE :value")
    fun queryWithDetailType(value: String?): Flow<List<Detail>>

    @Query("SELECT * FROM details WHERE direction LIKE :value")
    fun queryWithDirection(value: String?): Flow<List<Detail>>

    @Query("SELECT * FROM details WHERE money BETWEEN :value1 AND :value2")
    fun queryWithMoney(value1: String?, value2: String?): Flow<List<Detail>>

    @Query("SELECT * FROM details WHERE time BETWEEN :value1 AND :value2")
    fun queryWithTime(value1: String?, value2: String?): Flow<List<Detail>>

    @Query(
        "SELECT * FROM details WHERE " +
                "time BETWEEN :startTime AND :endTime " +
                "AND money BETWEEN :maxMoney AND :minMoney " +
                "AND message LIKE :message " +
                "AND type LIKE :type " +
                "AND direction LIKE :direction " +
                "AND channel LIKE :channel " +
                "ORDER BY time DESC"
    )
    fun A(
        startTime: String, endTime: String,
        maxMoney: Double, minMoney: Double,
        message: String,
        type: String,
        direction: String,
        channel: String
    ): Flow<List<Detail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detail: Detail)

    @Query("DELETE FROM details")
    fun deleteAll()

    @Update
    fun updata(detail: Detail)

    @Delete
    fun delete(detail: Detail)

    @RawQuery(observedEntities = [Detail::class])
    fun rawQuery(query: SupportSQLiteQuery): Flow<List<Detail>>

    fun execSql(sql: String) = rawQuery(
        SimpleSQLiteQuery(
            sql,
            arrayOf()
        )
    )
}

@TypeConverters(DetailTypeConverters::class, MovDirectionConverters::class, TimeConverters::class)
@Database(entities = [Detail::class, DetailType::class], version = 1)
abstract class BillingDatabase : RoomDatabase() {
    abstract fun getDetailDao(): DetailDao
    abstract fun getDetailTypeDao(): DetailTypeDao
}