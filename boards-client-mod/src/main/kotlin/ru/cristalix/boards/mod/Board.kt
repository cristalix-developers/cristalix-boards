package ru.cristalix.boards.mod

import org.lwjgl.opengl.GL11
import ru.cristalix.boards.data.BoardContent
import ru.cristalix.boards.data.BoardStructure
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.Context3D
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.*
import java.util.*
import kotlin.math.max

class Board(
    private val structure: BoardStructure
) {

    val uuid: UUID = structure.uuid

    val context: Context3D = Context3D(V3(structure.x, structure.y, structure.z))

    private val body: CarvedRectangle = carved {

        var width = -2.0
        structure.columns.forEach {
            width += it.width + 2.0
        }
        size.x = max(width, 0.0)
        size.y = structure.linesDisplayed * 12.0

        origin = Relative.BOTTOM
        align = Relative.BOTTOM
        beforeRender = {
            GL11.glEnable(GL11.GL_STENCIL_TEST)
            GL11.glStencilMask(0xFF)
            GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF)
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_INCR)
        }
        afterRender = {
            GL11.glDisable(GL11.GL_STENCIL_TEST)
            GL11.glStencilMask(0)
        }
    }

    private val titles: Array<TextElement> = Array(5){
        text {
            offset.y = -body.size.y - 2.0
            origin = Relative.BOTTOM
            align = Relative.BOTTOM
            scale = V3(2.0, 2.0, 2.0)
            content = "§l" + structure.name

            if (it < 4) {
                beforeRender = {
                    GL11.glDepthMask(false)
                }
                afterRender = {
                    GL11.glDepthMask(true)
                }
                offset.x += (it % 2) - 0.5
                offset.y += (it / 2) - 0.5
                content = toBlackText(content)//"¨222200§l$content"
            }
        }
    }

    init {
        context.rotation = Rotation(structure.yaw.toDouble() / 180.0 * Math.PI, 0.0, -1.0, 0.0)
//        context.rotation = Rotation(0 / 180.0 * Math.PI, 0.0, -1.0, 0.0)
        context.scale.x *= 0.5
        context.scale.y *= 0.5
        context.scale.z *= 0.5
        context.addChild(body, *titles)
    }

    private fun toBlackText(string: String) = "¨222200" + string.replace(Regex("(§[0-9a-fA-F]|¨......)"), "¨222200")

    fun setContent(content: BoardContent) {

        body.children.clear()

        val c = ArrayList<BoardContent.BoardLine>(content.content.size + 1)
        c.add(BoardContent.BoardLine(null, structure.columns.map { "§e${it.header}" }.toTypedArray()))
        c.addAll(content.content)
        for ((index, boardLine) in c.withIndex()) {
            var x = 0.0
            for ((column, cellValue) in boardLine.columns.withIndex()) {
//                body.addChild(rectangle {
//
//                    color = Color(0, 0, 0, 0.5)
//                    size.x = info.columnWidths[column]
//                    size.y = 10.0
//                    offset.x = x
//                    offset.y = index * 12.0
////                    rotation = Rotation(Math.PI, 0.0, 1.0, 0.0)
//
//                })
                body.addChild(carved {


                    beforeRender = {
                        GL11.glDisable(GL11.GL_CULL_FACE)
                    }

                    afterRender = {
                        GL11.glEnable(GL11.GL_CULL_FACE)
                    }

                    color = Color(0, 0, 0, 0.5)
                    size.x = structure.columns[column].width
                    size.y = 10.0
//                    scale.z *= -1
                    offset.x = x
                    offset.y = index * 12.0
                    addChild(text {
                        beforeRender = {
                            GL11.glEnable(GL11.GL_CULL_FACE)
                        }
                        offset.z = -0.5
                        this.content = "" + cellValue
                        origin = Relative.CENTER
                        align = Relative.CENTER
                    })

                })

                x += structure.columns[column].width + 2
            }
        }

    }



}