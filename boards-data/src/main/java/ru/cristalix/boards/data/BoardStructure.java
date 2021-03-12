package ru.cristalix.boards.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class BoardStructure {

	private final UUID uuid;
	private double x;
	private double y;
	private double z;
	private float yaw;
//	private float pitch;
	private String name;
	private int linesDisplayed;
	private final List<BoardColumn> columns = new ArrayList<>();

}
