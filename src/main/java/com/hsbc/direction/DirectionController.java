package com.hsbc.direction;

import com.hsbc.map.RectangularMap;
import com.hsbc.car.Car;

/**
 * 方向控制器
 */
public interface DirectionController {
    /**
     * 根据当前方向currentO判断并返回允许转动的方向
     * @param currentO
     * @return
     */
    int nextDirection(int currentO);

    /**
     * 根据当前car位置判断如果前进是否越界, 默认true为前进将会越界, false为可以安全前进
     * @param map
     * @param car
     * @return true: 前进将会越界. <br/>
     *          false : 可以安全前进
     */
    boolean checkIfMoveBeyondBoundary(RectangularMap map, Car car);

    /**
     * 根据car的当前状态,计算目的地(x1,y1)在car的哪个方向
     * @param car
     * @param x1 目的地的X坐标
     * @param y1 目的地的Y坐标
     * @return
     */
    int calculateTargetDirection(Car car, int x1, int y1);
}
