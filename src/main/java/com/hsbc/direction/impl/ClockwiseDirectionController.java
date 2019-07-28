package com.hsbc.direction.impl;

import com.hsbc.car.Car;
import com.hsbc.direction.DirectionController;
import com.hsbc.common.DirectionEnum;
import com.hsbc.map.RectangularMap;

/**
 * 顺时针方向控制器
 */
public class ClockwiseDirectionController extends AbstractDirectionController{

    @Override
    public int nextDirection(int currentO) {
        DirectionEnum[] directionEnums = DirectionEnum.values();
        for (int i = 0; i < directionEnums.length; i++) {
            if (currentO == directionEnums[i].getValue()) {
                if (i == directionEnums.length - 1) {
                    return directionEnums[0].getValue();
                }
                return directionEnums[i + 1].getValue();
            }
        }
        throw new RuntimeException("不存在的方向!");
    }
}
