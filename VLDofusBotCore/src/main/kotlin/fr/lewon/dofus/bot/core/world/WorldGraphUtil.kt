package fr.lewon.dofus.bot.core.world

import fr.lewon.dofus.bot.core.criterion.DofusCriterionParser
import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.model.charac.DofusCharacterBasicInfo
import java.util.*

object WorldGraphUtil {

    private val vertices = HashMap<Double, HashMap<Int, Vertex>>()
    private val edges = HashMap<Double, HashMap<Double, Edge>>()
    private val outgoingEdges = HashMap<Double, ArrayList<Edge>>()
    private val transitionsByInteractiveId = HashMap<Double, ArrayList<Transition>>()
    private var vertexUid = 0.0
    private val invalidInteractiveIds = ArrayList<Double>()
    private val invalidMapIds = ArrayList<Double>()

    fun init(stream: ByteArrayReader) {
        val edgeCount = stream.readInt()
        for (i in 0 until edgeCount) {
            val from = addVertex(stream.readDouble(), stream.readInt())
            val dest = addVertex(stream.readDouble(), stream.readInt())
            val edge = addEdge(from, dest)
            val transitionCount = stream.readInt()
            for (j in 0 until transitionCount) {
                val transition = Transition(
                    edge,
                    stream.readUnsignedByte(),
                    TransitionType.fromInt(stream.readUnsignedByte()),
                    stream.readInt(),
                    stream.readString(stream.readInt()),
                    stream.readDouble(),
                    stream.readInt(),
                    stream.readDouble()
                )
                if (transition.type == TransitionType.INTERACTIVE) {
                    transitionsByInteractiveId.computeIfAbsent(transition.id) { ArrayList() }
                        .add(transition)
                }
                edge.transitions.add(transition)
            }
        }
    }

    fun addEdge(from: Vertex, dest: Vertex): Edge {
        getEdge(from, dest)?.let { return it }
        val edge = Edge(from, dest)
        val fromEdges = edges.computeIfAbsent(from.uid) { HashMap() }
        fromEdges[dest.uid] = edge
        val outgoing = outgoingEdges.computeIfAbsent(from.uid) { ArrayList() }
        outgoing.add(edge)
        return edge
    }

    private fun getEdge(from: Vertex, dest: Vertex): Edge? {
        return edges[from.uid]?.get(dest.uid)
    }

    private fun addVertex(mapId: Double, zone: Int): Vertex {
        val mapVertices = vertices.computeIfAbsent(mapId) { HashMap() }
        return mapVertices.computeIfAbsent(zone) { Vertex(mapId, zone, vertexUid++) }
    }

    fun getPath(
        fromMapId: Double,
        fromZone: Int,
        toMapIds: List<Double>,
        characterInfo: DofusCharacterBasicInfo,
    ): List<Transition>? {
        val fromVertex = vertices[fromMapId]?.get(fromZone) ?: return null
        return getPath(listOf(fromVertex), toMapIds, characterInfo)
    }

    fun getPath(
        fromVertices: List<Vertex>,
        toMapIds: List<Double>,
        characterInfo: DofusCharacterBasicInfo,
    ): List<Transition>? {
        var destVertices = toMapIds.flatMap { getVertices(it) }.takeIf { it.isNotEmpty() }
            ?: return null
        destVertices = destVertices.filter { it.zoneId == 1 }.takeIf { it.isNotEmpty() }
            ?: destVertices
        return getPath(fromVertices, destVertices, characterInfo)
    }

    @JvmName("getPath2")
    fun getPath(
        fromVertices: List<Vertex>,
        destVertices: List<Vertex>,
        characterInfo: DofusCharacterBasicInfo,
    ): List<Transition>? {
        val explored = ArrayList<Vertex>()
        var frontier = ArrayList<Node>()
        val initialNodes = fromVertices.map { Node(null, it, null) }
        explored.addAll(fromVertices)
        frontier.addAll(initialNodes)
        while (frontier.isNotEmpty()) {
            val newFrontier = ArrayList<Node>()
            for (node in frontier) {
                if (destVertices.contains(node.vertex)) {
                    return node.getTransitions()
                }
                val newNodes = getOutgoingEdges(node.vertex)
                    .filter { !explored.contains(it.to) }
                    .flatMap { buildNodes(node, it, characterInfo) }
                    .onEach { explored.add(it.vertex) }
                newFrontier.addAll(newNodes)
            }
            frontier = newFrontier
        }
        return null
    }

    private fun buildNodes(parentNode: Node, edge: Edge, characterInfo: DofusCharacterBasicInfo): List<Node> =
        getFilteredTransitions(edge, characterInfo).map { Node(parentNode, edge.to, it) }

    fun getFilteredTransitions(edge: Edge, characterInfo: DofusCharacterBasicInfo) =
        getSortedTransitions(edge.transitions.filter { isTransitionValid(it, characterInfo) })

    fun getSortedTransitions(transitions: List<Transition>) = transitions.sortedWith(
        compareBy({ it.type }, { it.direction })
    )

    fun isTransitionValid(transition: Transition, characterInfo: DofusCharacterBasicInfo): Boolean {
        if (invalidInteractiveIds.contains(transition.id)) {
            return false
        }
        if (transition.type == TransitionType.INTERACTIVE) {
            val transitionsForInteractiveCount = transitionsByInteractiveId[transition.id]
                ?.map { it.transitionMapId }?.distinct()?.size ?: 0
            if (transitionsForInteractiveCount != 1) {
                return false
            }
        }
        return isCriterionValid(transition.criterion, characterInfo)
    }

    private fun isCriterionValid(criterion: String, characterInfo: DofusCharacterBasicInfo): Boolean {
        if (criterion.isEmpty()) {
            return true
        }
        return DofusCriterionParser.parse(criterion).check(characterInfo)
    }

    fun getVertex(mapId: Double, zoneId: Int) = vertices[mapId]?.get(zoneId)

    fun getVertices(mapId: Double) = vertices[mapId]?.values?.toList()
        ?: emptyList()

    fun getOutgoingEdges(vertex: Vertex) = outgoingEdges[vertex.uid]?.filter {
        !invalidMapIds.contains(it.to.mapId)
    } ?: emptyList()

    private class Node(private val parent: Node?, val vertex: Vertex, val transition: Transition?) {
        fun getTransitions(): List<Transition> {
            val transitions = LinkedList<Transition>()
            var currentNode: Node? = this
            while (currentNode?.transition != null) {
                transitions.addFirst(currentNode.transition)
                currentNode = currentNode.parent
            }
            return transitions
        }
    }

    fun addInvalidInteractiveId(id: Double) {
        invalidInteractiveIds.add(id)
    }

    fun addInvalidMapId(id: Double) {
        invalidMapIds.add(id)
    }

}