package ru.cristalix.boards.data;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BoardContent {

	private final UUID boardId;
	private final List<BoardLine> content;

	public BoardContent clear() {
		this.content.clear();
		return this;
	}

	public BoardContent addLine(UUID player, String... columnValues) {
		this.content.add(new BoardLine(player, columnValues));
		return this;
	}

	@Data
	public static class BoardLine {

		private final UUID player;
		private final String[] columns;

	}

}
