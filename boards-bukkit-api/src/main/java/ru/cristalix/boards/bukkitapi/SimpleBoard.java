package ru.cristalix.boards.bukkitapi;

import dev.xdark.feder.NetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.cristalix.boards.data.BoardColumn;
import ru.cristalix.boards.data.BoardContent;
import ru.cristalix.boards.data.BoardStructure;
import ru.cristalix.core.GlobalSerializers;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@ToString
public class SimpleBoard implements Board {

    private final UUID uuid;
    private final BoardStructure info;
    private Location location;
    private BoardContent content;

    protected SimpleBoard() {
        this(UUID.randomUUID());
    }

    protected SimpleBoard(UUID uuid) {
        this.uuid = uuid;
        this.info = new BoardStructure(uuid);
        this.content = new BoardContent(uuid, new ArrayList<>());
    }

    @Override
    public Collection<Player> getWatchers() {
        if (this.location == null) return Collections.emptyList();
        return Boards.active.stream().filter(b -> b.getWorld() == location.getWorld()).collect(Collectors.toList());
    }

    public void setTitle(String title) {
        this.info.setName(title);
        this.updateStructure();
    }

    public String getTitle() {
        return this.info.getName();
    }

    public void addColumn(String header, double width) {
        this.info.getColumns().add(new BoardColumn(header, width));
        this.updateStructure();
    }

    @Override
    public void clearContent() {
        this.content.clear();
    }

    @Override
    public void addContent(UUID contentId, String... cellValues) {
        this.content.addLine(contentId, cellValues);
    }

    public void setLocation(Location location) {
        this.info.setX(location.getX());
        this.info.setY(location.getY());
        this.info.setZ(location.getZ());
        this.info.setYaw(location.getYaw());
//        this.info.setPitch(location.getPitch());
        this.location = location;
        this.updateStructure();
    }

    @Override
    public void updateStructure() {
        send("boards:new", encode(info), getWatchers());
    }

    @Override
    public void updateStructure(Player player) {
        send("boards:new", encode(info), Collections.singleton(player));
    }

    public void updateContent() {
        send("boards:content", encode(content), getWatchers());
    }

    @Override
    public void updateContent(Player player) {
        send("boards:content", encode(content), Collections.singleton(player));
    }

    public static void send(String channel, ByteBuf buffer, Iterable<Player> players) {
        for (Player player : players) {
            val packet = new PacketPlayOutCustomPayload(channel, new PacketDataSerializer(buffer.retainedSlice()));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static ByteBuf encode(Object object) {
        val buffer = Unpooled.buffer();
        val bytes = GlobalSerializers.toJson(object);
        NetUtil.writeUtf8(bytes, buffer);
        return buffer;
    }

}
