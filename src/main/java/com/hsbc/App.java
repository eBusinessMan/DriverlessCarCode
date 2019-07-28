package com.hsbc;

import com.hsbc.car.impl.BenzCar;
import com.hsbc.direction.DirectionController;
import com.hsbc.common.DirectionEnum;
import com.hsbc.direction.impl.ClockwiseDirectionController;
import com.hsbc.direction.impl.CounterclockwiseDirectionController;
import com.hsbc.map.RectangularMap;
import com.hsbc.map.impl.CarPark;

import java.util.Scanner;

/**
 * Driverless car Game, 展示使用.
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("!欢迎尝试Driverless car!");
        System.out.println();
        System.out.println("首先我们需要初始化汽车, 请按照指令进行.");
        System.out.println("按顺序指定长方形地图边界(startX, endX, startY, endY), 以逗号隔开:");
        String tmp = scanner.nextLine();
        String[] tmpMap = tmp.split(",");
        RectangularMap map = new CarPark(
                Integer.valueOf(tmpMap[0].trim()), Integer.valueOf(tmpMap[1].trim()),
                Integer.valueOf(tmpMap[2].trim()), Integer.valueOf(tmpMap[3].trim()));

        System.out.println("按顺序指定汽车初始坐标(x,y), 以逗号隔开:");
        String[] tmpCar = scanner.nextLine().split(",");
        int x = Integer.valueOf(tmpCar[0].trim());
        int y = Integer.valueOf(tmpCar[1].trim());

        System.out.println("指定汽车初始方向, 0-N, 1-E, 2-S, 3-W :");
        int o = Integer.valueOf(scanner.nextLine().trim());

        System.out.println("指定方向控制器: 0-顺时针, 1-逆时针");
        int dc = Integer.valueOf(scanner.nextLine().trim());
        DirectionController directionController = dc==0? new ClockwiseDirectionController(): new CounterclockwiseDirectionController();

        System.out.println("设置完毕! 汽车初始化...");
        BenzCar benzCar = new BenzCar(directionController, map, x, y, o);
        System.out.println("汽车初始化完毕: "+benzCar.toString());

        boolean isEnd = false;
        while (!isEnd) {
            try{
                System.out.println();
                System.out.println("请输入汽车指令, 1-去往指定目的地, 2-转一次方向, 3-前进, 4-获取汽车当前信息, 5-退出 : ");
                int command = Integer.valueOf(scanner.nextLine().trim());
                switch (command) {
                    case 1:
                        System.out.println("  按顺序指定目的地坐标(x,y), 以逗号隔开:");
                        String[] tmpTarget = scanner.nextLine().split(",");
                        int tx = Integer.valueOf(tmpTarget[0].trim());
                        int ty = Integer.valueOf(tmpTarget[1].trim());
                        System.out.println("    car行驶轨迹:");
                        benzCar.moveTo(tx, ty);
                        break;

                    case 2:
                        System.out.println("  指定方向, 0-N, 1-E, 2-S, 3-W :");
                        int tO = Integer.valueOf(scanner.nextLine().trim());
                        if (!benzCar.turn(tO)) {
                            System.out.println("    转向失败!指令不匹配方向控制器计算.");
                        }
                        break;

                    case 3:
                        System.out.println("  指定前进步数:");
                        int tSteps = Integer.valueOf(scanner.nextLine().trim());
                        benzCar.moveForward(tSteps);
                        break;

                    case 4:
                        System.out.println("    "+benzCar.toString());
                        break;

                    case 5:
                        isEnd = true;
                        break;

                    default:
                        System.out.println("  !WARN: 未支持指令!");
                }
            } catch (Exception e){
                System.out.println("!ERROR["+e.getMessage()+"] "+benzCar.toString());
            }
        }

        System.out.println("Game End! ByeBye.");
    }
}
