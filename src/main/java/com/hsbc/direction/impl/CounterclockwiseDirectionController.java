package com.hsbc.direction.impl;

import com.hsbc.common.DirectionEnum;

/**
 * 逆时针方向控制器
 */
public class CounterclockwiseDirectionController extends AbstractDirectionController{

    @Override
    public int nextDirection(int currentO) {
        DirectionEnum[] directionEnums = DirectionEnum.values();//根据定义,directionEnums内元素默认是顺时针的.
        for (int i = directionEnums.length - 1; i >= 0; i--) {
            if (currentO == directionEnums[i].getValue()) {
                if (i == 0) {
                    return directionEnums[directionEnums.length - 1].getValue();
                }
                return directionEnums[i - 1].getValue();
            }
        }
        throw new RuntimeException("不存在的方向!");
    }
}
