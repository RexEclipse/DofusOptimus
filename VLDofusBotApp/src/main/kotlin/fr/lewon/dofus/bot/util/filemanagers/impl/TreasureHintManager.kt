package fr.lewon.dofus.bot.util.filemanagers.impl

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.lewon.dofus.bot.core.d2p.elem.D2PElementsAdapter
import fr.lewon.dofus.bot.core.d2p.elem.graphical.impl.NormalGraphicalElementData
import fr.lewon.dofus.bot.core.d2p.maps.D2PMapsAdapter
import fr.lewon.dofus.bot.core.d2p.maps.element.GraphicalElement
import fr.lewon.dofus.bot.core.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.core.model.hunt.DofusPointOfInterest
import fr.lewon.dofus.bot.core.model.maps.DofusMap
import fr.lewon.dofus.bot.game.DofusBoard
import fr.lewon.dofus.bot.model.hint.GfxIdsByPoiLabel
import fr.lewon.dofus.bot.util.filemanagers.ToInitManager
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

object TreasureHintManager : ToInitManager {

    private lateinit var gfxIdsByPoiLabel: GfxIdsByPoiLabel
    private lateinit var gfxIdsByPoiLabelFile: File

    override fun initManager() {
        val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        gfxIdsByPoiLabelFile = File("${VldbFilesUtil.getVldbConfigDirectory()}/hint_gfx_ids_by_label")
        if (gfxIdsByPoiLabelFile.exists()) {
            gfxIdsByPoiLabel = objectMapper.readValue(gfxIdsByPoiLabelFile)
        } else {
            val inputStream = javaClass.getResourceAsStream("/default_files/default_hint_gfx_ids_by_label")
                ?: error("Couldn't load default hints file")
            gfxIdsByPoiLabel = objectMapper.readValue(inputStream)
            saveHintStoreContent()
        }
    }

    override fun getNeededManagers(): List<ToInitManager> {
        return emptyList()
    }

    private fun saveHintStoreContent() {
        with(OutputStreamWriter(FileOutputStream(gfxIdsByPoiLabelFile, false), StandardCharsets.UTF_8)) {
            write(ObjectMapper().writeValueAsString(gfxIdsByPoiLabel))
            close()
        }
    }

    fun isPointOfInterestOnMap(map: DofusMap, pointOfInterest: DofusPointOfInterest): Boolean {
        val gfxIds = gfxIdsByPoiLabel[pointOfInterest.label] ?: error("Hint not registered : ${pointOfInterest.label}")
        val mapData = D2PMapsAdapter.getMapData(map.id)
        return mapData.completeCellDataByCellId.flatMap { it.value.graphicalElements }
            .asSequence()
            .filter { isValidGraphicalElement(it) }
            .map { D2PElementsAdapter.getElement(it.elementId) }
            .filterIsInstance<NormalGraphicalElementData>()
            .map { it.gfxId }
            .plus(mapData.backgroundFixtures.map { it.fixtureId })
            .plus(mapData.foregroundFixtures.map { it.fixtureId })
            .toList()
            .intersect(gfxIds)
            .isNotEmpty()
    }

    private fun isValidGraphicalElement(ge: GraphicalElement): Boolean {
        val cellId = ge.cell.cellId
        val mapCellsCount = DofusBoard.MAP_CELLS_COUNT
        val mapWidth = DofusBoard.MAP_WIDTH
        val cellHalfHeight = D2PMapsAdapter.CELL_HALF_HEIGHT
        val cellHalfWidth = D2PMapsAdapter.CELL_HALF_WIDTH
        val topOk = cellId >= mapWidth * 2 || ge.pixelOffset.y >= -2 * cellHalfHeight
        val bottomOk = cellId <= mapCellsCount - mapWidth * 2 || ge.pixelOffset.y <= cellHalfHeight
        val divideLeftover = cellId % (mapWidth * 2)
        val leftOk = divideLeftover != 0 && divideLeftover != mapWidth
            || divideLeftover == 0 && ge.pixelOffset.x >= -1.2 * cellHalfWidth
            || divideLeftover == mapWidth && ge.pixelOffset.x >= -2.3 * cellHalfWidth
        val rightOk = divideLeftover != mapWidth * 2 - 1 && divideLeftover != mapWidth - 1
            || divideLeftover == mapWidth * 2 - 1 && ge.pixelOffset.x <= cellHalfWidth * 1.2f
            || divideLeftover == mapWidth - 1 && ge.pixelOffset.x <= cellHalfWidth * 2.3f
        return topOk && bottomOk && leftOk && rightOk
    }

    fun addHintGfxMatch(pointOfInterestLabel: String, gfxId: Int) {
        gfxIdsByPoiLabel.computeIfAbsent(pointOfInterestLabel) { HashSet() }
            .add(gfxId)
        saveHintStoreContent()
    }

    fun addStore(gfxIdsByLabel: Map<String, HashSet<Int>>) {
        gfxIdsByLabel.entries.forEach {
            val gfxIds = gfxIdsByPoiLabel[it.key] ?: HashSet()
            gfxIds.addAll(it.value)
            gfxIdsByPoiLabel[it.key] = gfxIds
        }
        saveHintStoreContent()
    }

    fun removeHintGfxMatch(pointOfInterestLabel: String, gfxId: Int) {
        val gfxIds = gfxIdsByPoiLabel.computeIfAbsent(pointOfInterestLabel) { HashSet() }
        gfxIds.remove(gfxId)
        if (gfxIds.isEmpty()) {
            gfxIdsByPoiLabel.remove(pointOfInterestLabel)
        }
        saveHintStoreContent()
    }

    fun getGfxIdsByPoiLabel(): GfxIdsByPoiLabel {
        return GfxIdsByPoiLabel(gfxIdsByPoiLabel)
    }
}