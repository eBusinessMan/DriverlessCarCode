package com.hsbc.car.impl;

import com.hsbc.car.Car;
import com.hsbc.common.DirectionEnum;
import com.hsbc.direction.DirectionController;
import com.hsbc.map.RectangularMap;
import lombok.Getter;

/**
 * 基础car类:基于模板方法模式和策略模式, 方便使用者定制DirectionController和RectangularMap
 */
@Getter
public abstract class AbstractCar implements Car {

    /**
     * 方向控制器: <br/>
     * tips: car类本身不控制方向, 外交给方向控制器DirectionController. 可以定制各种不同的DirectionController以满足需要
     */
    private DirectionController directionController;
    /**
     * park地图
     */
    private RectangularMap map;
    /**
     * 横坐标 X
     */
    private int x;
    /**
     * 纵坐标 Y
     */
    private int y;
    /**
     * 当前方向 Orientation
     */
    private int o;

    public AbstractCar(DirectionController directionController, RectangularMap map, int x, int y, int o) {
        this.initCar(directionController, map, x, y, o);
    }

    /**
     * 命令类型: 仅仅指定(X,Y), 可以自己判断如何正确地走到那里 <br/>
     * <br/>
     * tips: 典型的模板方法模式, 根据定制个性化的方向控制器directionController
     */
    @Override
    public boolean moveTo(int x1, int y1) {
        //入参合法性检测
        if (!map.check(x1, y1)) {
            System.out.println("入参不合法!");
            return false;
        }

        //原地
        if (x1 == this.x && y1 == this.y) {
            System.out.println("    目的地即原地");
            return true;//依然为true吧~
        }

        //计算出目的地在car的哪个方向并转向那个方向
        int firstDirection = directionController.calculateTargetDirection(this, x1, y1);
        while (this.o != firstDirection) {//判断车向是否指向目的地方向. 若否,则一直移动并转至同向
            this.turnToNext();

            //查看是否是恰好贴近边界&指向边界, 若是,则循环转向直到找出不面向边界的方向
            while (directionController.checkIfMoveBeyondBoundary(map, this)) {
                this.turnToNext();
            }

            if (this.o == firstDirection) {
                System.out.println("    ....");
                break;
            }

            this.moveForward(1);//移动一格
        }

        //令car移动到和目的地格子同列/同行
        int tmpFirst = 0, tmpSecond = 0;
        boolean nOrS = this.o == DirectionEnum.NORTH.getValue() || this.o == DirectionEnum.SOUTH.getValue();
        if (nOrS) {//如果当前方向是N或S, 则移动到同一行:
            tmpFirst = y1 - this.y;
            tmpSecond = x1 - this.x;
        } else {//如果当前方向是W或E, 则移动到同一列:
            tmpFirst = x1 - this.x;
            tmpSecond = y1 - this.y;
        }
        this.moveForward(tmpFirst > 0 ? tmpFirst : -tmpFirst);

        //car目前已经和目的地同列/同行位置, 接下来转一个方向直走即可抵达
        if (!(x1 == this.x && y1 == this.y)) {//如果此时已经抵达位置(即(x1 == x && y1 == y)), 则不再转
            this.turnToNext();
            //移动到目的地格子
            this.moveForward(tmpSecond > 0 ? tmpSecond : -tmpSecond);
        }

        return true;
    }

    /**
     * 令车转向下一个方向.<br/>
     * 过程: 通过directionController.nextDirection计算下一个方向
     */
    public void turnToNext() {
        this.o = directionController.nextDirection(this.o);
        System.out.println("    转向:" + this.toString());
    }

    /**
     * 将车转向指定方向. <BR/>
     * 根据题目意思,在此设定: 指定转向的direction应该等于符合 directionController.nextDirection的返回值.<BR/>
     * tips: 可以通过子类覆盖此方法
     *
     * @param direction
     * @return
     */
    @Override
    public boolean turn(int direction) {
        int tmp = directionController.nextDirection(this.o);
        if (tmp == direction) {
            this.o = directionController.nextDirection(this.o);
            System.out.println("    转向:" + this.toString());
            return true;
        }

        return false;
    }

    /**
     * 命令类型:
     * 3. 仅仅指定向前走n步, 会先判断是否出界<br/>
     *      逐步向前移动, 同时n--, 遇见边界后如果n依然大于0,那么提前抛异常退出.<br/>
     */
    @Override
    public void moveForward(int n) {
        if (n < 0) {
            throw new RuntimeException("入参: " + n + " 不合法");
        }

        while (n > 0) {
            if (directionController.checkIfMoveBeyondBoundary(map, this)) {
                throw new RuntimeException("moveForward(" + n + ")越界!");
            }

            if (this.o == DirectionEnum.NORTH.getValue()) {
                this.y++;
            } else if (this.o == DirectionEnum.EAST.getValue()) {
                this.x++;
            } else if (this.o == DirectionEnum.SOUTH.getValue()) {
                this.y--;
            } else {
                this.x--;
            }
            System.out.println("    前进一步:" + this.toString());
            n--;
        }
    }

    /**
     * 初始化方向控制器/park地图/初始坐标/初始方向
     *
     * @param directionController
     * @param map
     * @param x
     * @param y
     * @param o
     * @return
     */
    private boolean initCar(DirectionController directionController, RectangularMap map, int x, int y, int o) {
        //合法检测
        if (!map.check(x, y)) {
            throw new RuntimeException("初始坐标(x,y)不合法!");
        }

        boolean flag = false;//初始化没命中
        for (DirectionEnum d : DirectionEnum.values()) {
            if (o == d.getValue()) {
                flag = true;
            }
        }
        if (!flag)
            throw new RuntimeException("初始方向:" + o + " 不合法!");

        this.directionController = directionController;
        this.map = map;
        this.x = x;
        this.y = y;
        this.o = o;

        return true;
    }

    /**
     * 重置方向控制器/park地图/初始坐标/初始方向
     *
     * @param map
     * @param x
     * @param y
     * @param o
     * @return
     */
    public boolean resetCar(DirectionController directionController, RectangularMap map, int x, int y, int o) {
        return this.initCar(directionController, map, x, y, o);
    }

    @Override
    public int getPositionX() {
        return x;
    }

    @Override
    public int getPositionY() {
        return y;
    }

    @Override
    public int getOrientation() {
        return o;
    }

    @Override
    public String toString() {
        return "当前车的状态:Car{" +
                "x=" + x +
                ", y=" + y +
                ", o=" + o +
                ", map=" + map.toString()
                + " } ";
    }
}
