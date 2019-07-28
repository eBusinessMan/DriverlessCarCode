package com.hsbc.map;

/**
 *长方形地图
 */
public interface RectangularMap {
    /**
     * 检测坐标是否范围内
     * @param x
     * @param y
     * @return true: 代表(x,y)在地图中<br/>
     *          false: 代表(x,y)不在地图中<br/>
     */
    public boolean check(int x , int y);
}
