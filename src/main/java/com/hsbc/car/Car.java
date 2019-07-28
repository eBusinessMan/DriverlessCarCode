package com.hsbc.car;

/**
 * Driverless Car的顶级接口 <br/>
 *
 * 基本命令类型:<br/>
 * 1. 仅仅指定(X,Y), 可以自己判断如何正确地走到那里 <br/>
 * 2. 仅仅指定要转动的方向 <br/>
 * 3. 仅仅指定向前走n步 <br/>
 */
public interface Car {

    /**
     * 命令类型:
     * 1. 仅仅指定(X,Y), 可以自己判断如何正确地走到那里<br/>
     * @param x
     * @param y
     * @return
     */
    boolean moveTo(int x, int y);

    /**
     * 命令类型:
     * 2. 仅仅指定方向<br/>
     * @return
     */
    boolean turn(int d);

    /**
     * 命令类型:
     * 3. 仅仅指定向前走n步, 会先判断是否出界<br/>
     */
    void moveForward(int n);

    /**
     * 获取当前car的X坐标
     * @return
     */
    int getPositionX();

    /**
     * 获取当前car的Y坐标
     * @return
     */
    int getPositionY();

    /**
     * 获取当前car的方向
     * @return
     */
    int getOrientation();
}
