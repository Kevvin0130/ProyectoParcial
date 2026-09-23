package com.example.casetrack.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.casetrack.Data.CasoDao

@Database(
    entities = [CasoEntity::class], //Aca se especifica que la base de datos tiene la entidad CasoEntity
    version = 2, //Subimos de 1 a 2 porque agregamos hallazgos y evidencias a CasoEntity
    exportSchema = false
)
@TypeConverters(Converters::class) //Le decimos a Room que use Converters para poder guardar List<String>
abstract class CasoDatabase : RoomDatabase() {

    abstract fun casoDao(): CasoDao //Aca permite que la base de datos pueda obtener el DAO de casos

    companion object {

        @Volatile
        private var INSTANCE: CasoDatabase? = null

        fun obtenerDatabase(context: Context): CasoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( //El database builde crea una base de datos SQLite llamada casetrack_database usando nuestra configuración CasoDatabase
                    context.applicationContext,
                    CasoDatabase::class.java,
                    "casetrack_database"
                )
                    //Como es un proyecto de universidad sin datos reales que proteger, si cambia el
                    //esquema (como acabamos de hacer) simplemente se recrea la base de datos en vez
                    //de escribir una Migration manual.
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance //El instance es para asegurarnos de que nuestra aplicación utilice una sola instancia de la base de datos. No queremos que cada vez que abramos una pantalla se cree una nueva base de datos
            }
        }
    }
}