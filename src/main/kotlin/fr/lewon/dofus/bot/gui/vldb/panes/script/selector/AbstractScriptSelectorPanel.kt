package fr.lewon.dofus.bot.gui.vldb.panes.script.selector

import fr.lewon.dofus.bot.gui.vldb.panes.script.parameters.ParametersPanel
import fr.lewon.dofus.bot.gui.util.AppFonts
import fr.lewon.dofus.bot.gui.util.ImageUtil
import fr.lewon.dofus.bot.gui.util.UiResource
import fr.lewon.dofus.bot.gui.vldb.panes.script.DofusScriptComboBox
import fr.lewon.dofus.bot.model.characters.DofusCharacter
import fr.lewon.dofus.bot.scripts.DofusBotScript
import fr.lewon.dofus.bot.scripts.parameters.DofusBotParameter
import fr.lewon.dofus.bot.util.script.DofusBotScriptEndType
import fr.lewon.dofus.bot.util.script.ScriptRunnerListener
import net.miginfocom.swing.MigLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.border.EmptyBorder

abstract class AbstractScriptSelectorPanel : JPanel(MigLayout()), ScriptRunnerListener {

    companion object {
        private const val BUTTON_SZ = 36
    }

    private val scriptComboBox = DofusScriptComboBox()
    private val descriptionTextArea = JTextArea()

    private val parametersSeparator = JSeparator(JSeparator.HORIZONTAL)
    private val parametersLabel = JLabel("Parameters").also { it.font = AppFonts.TITLE_FONT }
    private val parametersScrollPane = JScrollPane()

    private val startStopScriptButton = JButton().also {
        it.isBorderPainted = false
        it.border = null
        it.margin = Insets(0, 0, 0, 0)
        it.isContentAreaFilled = false
        it.addActionListener { toggleScript() }
    }

    private var scriptRunning = false

    init {
        SwingUtilities.invokeLater {
            addPanes()
        }
    }

    protected open fun addPanes() {
        updateButton()
        addScriptLauncherPane()
        addDescriptionPane()
        addParametersPane()
    }

    private fun getScript(): DofusBotScript {
        return scriptComboBox.selectedItem as DofusBotScript
    }

    fun updateButton(running: Boolean = isRunning()) {
        if (running) {
            scriptRunning = true
            updateStartStopButton(UiResource.STOP)
        } else {
            scriptRunning = false
            updateStartStopButton(UiResource.PLAY_ARROW)
        }
    }

    protected abstract fun isRunning(): Boolean

    private fun updateStartStopButton(uiResource: UiResource) {
        val imageData = uiResource.imageData
        val filledImageData = uiResource.filledImageData
        startStopScriptButton.icon = ImageIcon(ImageUtil.getScaledImage(imageData, BUTTON_SZ, BUTTON_SZ))
        startStopScriptButton.rolloverIcon =
            ImageIcon(ImageUtil.getScaledImage(filledImageData, BUTTON_SZ, BUTTON_SZ))
        startStopScriptButton.isEnabled = true
    }

    protected open fun addScriptLauncherPane() {
        scriptComboBox.addItemListener { updateScript() }
        updateScript()
        add(JLabel("Script").also { it.font = AppFonts.TITLE_FONT })
        add(scriptComboBox, "al right")
        add(startStopScriptButton, "al right, wrap")
    }

    private fun addDescriptionPane() {
        descriptionTextArea.wrapStyleWord = true
        descriptionTextArea.lineWrap = true
        descriptionTextArea.isEditable = false
        descriptionTextArea.isFocusable = false
        descriptionTextArea.isOpaque = false
        descriptionTextArea.border = EmptyBorder(5, 5, 5, 5)
        add(JSeparator(JSeparator.HORIZONTAL), "span 3 1, width max, wrap")
        add(JLabel("Description").also { it.font = AppFonts.TITLE_FONT }, "wrap")
        val descriptionScrollPane = JScrollPane(descriptionTextArea)
        descriptionScrollPane.horizontalScrollBar = null
        add(descriptionScrollPane, "span 3 1, width max, height 120:120:120, wrap")
    }

    private fun addParametersPane() {
        add(parametersSeparator, "span 3 1, width max, wrap")
        add(parametersLabel, "wrap")
        parametersScrollPane.horizontalScrollBar = null
        add(parametersScrollPane, "span 3 1, h max, width max, wrap")
    }

    private fun buildParametersPane(): JPanel {
        val dofusScript = getScript()
        val parameters = dofusScript.getParameters()
        parameters.forEach {
            it.value = getInitialParameterValue(it, dofusScript)
        }
        val parametersPanel = ParametersPanel(parameters) { p, _ -> updateParam(dofusScript, p) }
        parametersSeparator.isVisible = parameters.isNotEmpty()
        parametersLabel.isVisible = parameters.isNotEmpty()
        parametersScrollPane.isVisible = parameters.isNotEmpty()
        return parametersPanel
    }

    protected abstract fun getInitialParameterValue(parameter: DofusBotParameter, script: DofusBotScript): String

    private fun updateParam(script: DofusBotScript, param: DofusBotParameter) {
        updateDescription()
        onParamUpdate(script, param)
    }

    protected abstract fun onParamUpdate(script: DofusBotScript, param: DofusBotParameter)

    private fun updateScript() {
        parametersScrollPane.setViewportView(buildParametersPane())
        updateDescription()
        super.updateUI()
    }

    private fun updateDescription() {
        descriptionTextArea.text = getScript().getDescription()
    }

    private fun toggleScript() {
        startStopScriptButton.isEnabled = false
        if (scriptRunning) {
            stopScript()
        } else {
            startScript()
        }
    }

    private fun startScript() {
        runScript(scriptComboBox.selectedItem as DofusBotScript)
    }

    protected abstract fun runScript(script: DofusBotScript)

    protected abstract fun stopScript()

    override fun onScriptEnd(character: DofusCharacter, endType: DofusBotScriptEndType) {
        if (scriptEnded(character)) {
            updateButton(false)
            onScriptEnd()
        }
    }

    protected abstract fun onScriptEnd()

    protected abstract fun scriptEnded(character: DofusCharacter): Boolean

    override fun onScriptStart(character: DofusCharacter, script: DofusBotScript) {
        if (scriptStarted(character)) {
            onScriptStart(script)
            updateButton(true)
        }
    }

    protected abstract fun onScriptStart(script: DofusBotScript)

    protected abstract fun scriptStarted(character: DofusCharacter): Boolean

}