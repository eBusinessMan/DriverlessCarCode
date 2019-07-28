package com.hsbc.car.impl;

import com.hsbc.direction.DirectionController;
import com.hsbc.map.RectangularMap;

public class BenzCar extends AbstractCar {
    public BenzCar(DirectionController directionController, RectangularMap map, int x, int y, int o) {
        super(directionController, map, x, y, o);
    }
}
