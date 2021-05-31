package ru.cristalix.boards.mod

import com.google.gson.Gson
import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.entry.ModMain
import dev.xdark.clientapi.event.network.PluginMessage
import dev.xdark.clientapi.event.render.GuiOverlayRender
import dev.xdark.feder.NetUtil
import io.netty.buffer.Unpooled
import ru.cristalix.boards.data.BoardContent
import ru.cristalix.boards.data.BoardStructure
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.clientapi.readUtf8
import ru.cristalix.uiengine.UIEngine
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.HashMap

class BoardMod : KotlinMod() {

    val gson = Gson()
    val boards: MutableMap<UUID, Board> = HashMap()

    override fun onEnable() {

        UIEngine.initialize(this)

//        val info = BoardInfo(
//            UUID.randomUUID(),
//            -108.5, 116.0, -37.5,
//            45f, "Статусы",
//            200.0, 100.0,
//            arrayOf("Игрок", "Статус"),
//            doubleArrayOf(80.0, 120.0)
//        )

        clientApi.clientConnection().sendPayload("boards:loaded", Unpooled.EMPTY_BUFFER)

        registerChannel("boards:new") {
            val readUtf8 = readUtf8()
            val boardInfo = gson.fromJson(readUtf8, BoardStructure::class.java)
            val board = Board(boardInfo)
            boards[boardInfo.uuid] = board
            UIEngine.worldContexts.add(board.context)
        }
        registerChannel("boards:content") {
            val boardData = gson.fromJson(readUtf8(), BoardContent::class.java)
            val board = boards[boardData.boardId]
            if (board == null) {
                clientApi.chat().printChatMessage("Received board update for non-existing board " + boardData.boardId)
            } else {
                board.setContent(boardData)
            }
        }
        registerChannel("boards:reset") {
            UIEngine.worldContexts.clear()
        }


//        val board = Board(info)
//        board.setContent(
//            BoardContent(
//                info.uuid,
//                arrayOf(
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("_Demaster_", "Знатный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Demannn", "Ненаглядный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Aviki", "Первоклассный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("lakaithree", "Мумия")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("COREits", "Элитный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Master_Chan", "Блаженный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("DelfikPro", "Пылкий")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("НекийЛач", "Самый няшный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("__xDark", "Сильный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Pepel_ok", "Беспорочный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Func", "Экстравагантный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("ItzDecron", "Необычный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Zabelov", "OwO")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("DiamondDen", "Бесподобный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("kasdo", "Стиляга")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Fiwka1338", "Первозванный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("xCarly", "Козырный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("tipok95", "Няшка милашка")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Anfanik", "Знаменитый")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("ilyafx", "Люксовый")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Kolobok228", "Лучший")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("_Dmax_", "Уютный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Kawa", "Настойчивый")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("FoxyArt1", "Безупречный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Daqovich", "Хороший")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Jebzou", "Совершенный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("_Luvik_", "Аутентичный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("PhenomenalBoy", "Крутой")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("MeyHousennn", "Именитый")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("PandosikPlay", "Признанный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("koala22", "Великолепный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Larkinov", "Несравненный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("JuiSer", "Мимимишечка")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("koteyka_nyan", "Превосходный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("RiDzIk", "Избранный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Kitusha", "Хайповый")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("nikson111", "Образцовый")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Nobanpls123", "Наилучший")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("PetyaKykin228", "Известный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Ne_VeZuN4iK", "Вразумительный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("mrwaydenhacker", "Признательный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("GeneralPolice91", "Первостатейный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("lordcutx", "Шедевральный")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("Jeffresh", "Выдающийся")),
//                    BoardContent.BoardLine(UUID.randomUUID(), arrayOf("KUBA228", "Трендовый")),
//                )
//            )
//        )


    }

}