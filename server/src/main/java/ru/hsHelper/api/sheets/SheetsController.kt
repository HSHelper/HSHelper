package ru.hsHelper.api.sheets

import com.google.api.services.drive.Drive
import com.google.api.services.sheets.v4.Sheets
import org.redisson.api.RMap
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.hsHelper.api.services.UserService
import ru.hsHelper.api.services.WorkService
import ru.hsHelper.api.sheets.data.SheetInitData
import ru.hsHelper.api.sheets.data.SheetStoredData
import ru.hsHelper.api.sheets.processing.CreateProcessor
import ru.hsHelper.api.sheets.processing.ObserveProcessor
import ru.hsHelper.api.sheets.processing.UpdateProcessor
import java.util.concurrent.Executors


@RestController
@RequestMapping("/sheets")
class SheetsController @Autowired constructor(
    userService: UserService,
    workService: WorkService,
    redissonClient: RedissonClient,
    sheets: Sheets,
    drive: Drive
) {
    private val map: RMap<Long, SheetStoredData> = redissonClient.getMap("Sheets")
    private val updateProcessor = UpdateProcessor(userService, workService, sheets)
    private val createProcessor = CreateProcessor()
    private val observeProcessor = ObserveProcessor("https://84.252.137.106:1337/sheets/update/", drive)
    private val executor = Executors.newSingleThreadExecutor()


    @PostMapping("/update/{id}")
    fun updateSheet(@PathVariable id: Long) {
        val sheetData = map[id]
        if (sheetData != null) {
            executor.execute {
                updateProcessor.update(sheetData)
                map[id] = sheetData
            }
        } else {
            throw IllegalArgumentException("No table with such id")
        }
    }

    @PostMapping("/create")
    fun createSheet(@RequestBody sheetInitData: SheetInitData): Long {
        val key = map.newKey()
        val sheetStoredData = createProcessor.create(sheetInitData)
        observeProcessor.observe(key, sheetInitData.sheetLink.id)
        executor.execute {
            updateProcessor.update(sheetStoredData)
            map[key] = sheetStoredData
        }
        return key
    }
}
