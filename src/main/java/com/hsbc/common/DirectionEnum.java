package com.hsbc.common;

/**
 * 方向枚举 <br/>
 * tips:<br/>
 * 1. 要求相邻的方向的声明顺序也相邻<br/>
 * 2. 此处要求是顺时针方向声明顺序
 */
public enum DirectionEnum {
    /*tips:要求方向的声明顺序相邻*/
    NORTH(0, "N"), EAST(1, "E"), SOUTH(2, "S"), WEST(3, "W");

    private int value;
    private String name;

    private DirectionEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
