package com.hsbc.map.impl;

import com.hsbc.map.RectangularMap;
import lombok.Getter;
import lombok.Setter;

/**
 * 停车场park
 */
@Getter
public class CarPark implements RectangularMap {
    private final int startX;
    private final int endX;
    private final int startY;
    private final int endY;

    public CarPark(int startX, int endX, int startY, int endY) {
        if (startX >= endX || startY >= endY)
            throw new RuntimeException("地图边界不合法!");

        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    public boolean check(int x, int y) {
        //入参合法性检测
        if (x < startX || x > endX || y < startY || y > endY) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarPark{" +
                "startX=" + startX +
                ", endX=" + endX +
                ", startY=" + startY +
                ", endY=" + endY +
                '}';
    }
}
