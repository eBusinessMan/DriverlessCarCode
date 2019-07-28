package com.hsbc.direction.impl;

import com.hsbc.car.Car;
import com.hsbc.common.DirectionEnum;
import com.hsbc.direction.DirectionController;
import com.hsbc.map.RectangularMap;

public abstract class AbstractDirectionController implements DirectionController {

    @Override
    public int calculateTargetDirection(Car car, int x1, int y1) {
        int x = car.getPositionX();
        int y = car.getPositionY();

        int firstDirection = 0;
        if (x1 > x) {
            if (y1 > y) {
                firstDirection = DirectionEnum.NORTH.getValue();
            } else {// y1 <= y
                firstDirection = DirectionEnum.EAST.getValue();
            }
        } else if (x1 < x) {
            if (y1 >= y) {
                firstDirection = DirectionEnum.WEST.getValue();
            } else {
                firstDirection = DirectionEnum.SOUTH.getValue();
            }
        } else {//x1 == x
            if (y1 > y) {
                firstDirection = DirectionEnum.NORTH.getValue();
            } else {
                firstDirection = DirectionEnum.SOUTH.getValue();
            }
        }

        return firstDirection;
    }

    @Override
    public boolean checkIfMoveBeyondBoundary(RectangularMap map, Car car) {
        int tmpX = car.getPositionX(), tmpY = car.getPositionY();

        if (car.getOrientation() == DirectionEnum.NORTH.getValue()) {
            tmpY ++;
        } else if (car.getOrientation() == DirectionEnum.EAST.getValue()) {
            tmpX ++;
        } else if (car.getOrientation() == DirectionEnum.SOUTH.getValue()) {
            tmpY --;
        } else {
            tmpX --;
        }

        return !map.check(tmpX, tmpY);
    }
}
