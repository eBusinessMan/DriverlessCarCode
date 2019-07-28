# 汇丰软件笔试题-Driverless Car
  本题目要求实现一辆无人驾驶汽车car, 在长方形停车场里,固定地按照顺时针方向行驶. 其次还要满足:
  * 要求长方形停车场的长宽可配置.
  * 要求car时刻记录自己的当前坐标和当前方向.
  * 要求car可以往当前方向一直前进,直到遇见长方形停车场的边界.
  * car一旦行驶到触碰长方形停车场的边界, 仍然往前行驶则抛异常.<br/><br/>
  设计程序时考虑一下:
  * Future extensibility for new Car features should be kept in mind
  * Consider OO modeling for the application
  * Keep minimal and viable for the final workable application
   
# 需求分析
  我认为这是一条开放性的题目. 参考样例, 我认为只要满足上述的一系列要求, 可以适当发挥.<br/>
  关键点:<br/>
  * car在长方形停车场的行驶轨迹要符合顺时针方向. 如果car在同一个位置转动多次后再前进的话, 就可能没法形成顺时针的行驶轨迹. 
  <br/>故为了简化,约定:<br/>
    car行驶到一个位置只能顺时针转动一个方向, 如果转方向后指向长方形停车场边界则可以继续转动直到不指向长方形停车场边界为止.<br/>
  
  扩展性考虑:<br/>
  * 为适应各种场景, "长方形停车场的长宽"采用坐标形式划定更灵活:(起始X,结束X,起始Y,结束Y), 故:长度=结束X - 起始X, 宽度=结束Y - 起始Y.
  * 需求将来可能会改成"在长方形停车场中逆时针方向". 我们代码应该要应对这类需求变动.(在本答案中附带了逆时针方向的功能)
    
# 实现
  本答案以一个基于控制台的交互型小游戏呈现:自动驾驶汽车
  * 环境准备: jdk1.8
  * 由于在工程中采用lombok包,所以IDE(eclipse/Ideaj)需要安装lombok插件.
  * 导入工程后, run/debug执行com.hsbc.App即可.
  示意图:
  ![image](https://github.com/eBusinessMan/DriverlessCarCode/blob/master/src/main/resources/pic/GameExample.png)
    
  
# 工程代码分析
  ### 方向枚举--com.hsbc.common.DirectionEnum <br/>
  要求:<br/>
  * 相邻的方向的声明顺序也相邻 <br/>
  * 此处要求是顺时针方向声明顺序 <br/>
     ```java
          public enum DirectionEnum {
              /*tips:要求方向的声明顺序相邻*/
              NORTH(0, "N"), EAST(1, "E"), SOUTH(2, "S"), WEST(3, "W");
          }
    ```
 
  ### 汽车接口--com.hsbc.car.Car <br/>
  基本命令类型:<br/>
  * 仅仅指定(X,Y), 可以自己判断如何正确地走到那里 <br/>
  * 仅仅指定要转动的方向 <br/>
  * 仅仅指定向前走n步 <br/>
     ```java
      public interface Car {
          boolean moveTo(int x, int y);
          boolean turn(int d);
          void moveForward(int n);
          int getPositionX();
          int getPositionY();
          int getOrientation();
      }
      ```
      
  ### 汽车抽象基础类--com.hsbc.car.impl.AbstractCar <br/>
  * 实现了Car接口. 作为car继承体系的基础父类, 提供一系列Car接口方法的基本实现. 子类可以通过继承AbstractCar而快速实现功能, 或覆盖之以实现个性化需求.
  * 组合方向控制器DirectionController和地图RectangularMap (下文介绍) <br/>
    Tips: 汽车本身不具备判断方向的能力, 交由DirectionController实现类判断.<br/>
  * 提供记录汽车当前状态信息的成员属性:横坐标x, 纵坐标y, 方向o
  * 采用抽象模板方法模式和策略模式, 方便使用者定制DirectionController和RectangularMap的不同实现.
     ```java
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
          ...
      }
      ```  
<br/>
       
  ### 方向控制器接口--com.hsbc.direction.DirectionController <br/>
  * 提供一级实现基础类:com.hsbc.direction.impl.AbstractDirectionController, 提供checkIfMoveBeyondBoundary和calculateTargetDirection成员方法的基本实现.
  * 提供顺时针方向控制器:com.hsbc.direction.impl.ClockwiseDirectionController, 提供nextDirection成员方法的基本实现.
  * 提供逆时针方向控制器:com.hsbc.direction.impl.CounterclockwiseDirectionController, 提供nextDirection成员方法的基本实现.
     ```java
      public interface DirectionController {
          /**
           * 根据当前方向currentO判断并返回允许转动的方向
           */
          int nextDirection(int currentO);
          /**
           * 根据当前car位置判断如果前进是否越界, 默认true为前进将会越界, false为可以安全前进
           * @return true: 前进将会越界. <br/>
           *          false : 可以安全前进
           */
          boolean checkIfMoveBeyondBoundary(RectangularMap map, Car car);
          /**
           * 根据car的当前状态,计算目的地(x1,y1)在car的哪个方向
           * @param x1 目的地的X坐标
           * @param y1 目的地的Y坐标
           * @return
           */
          int calculateTargetDirection(Car car, int x1, int y1);
      }
      ```
<br/>

  ### 地图的顶级接口--com.hsbc.map.RectangularMap <br/>
  * 提供判断某个坐标是否在地图内的方法.
     ```java
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
     ```
  
  ### 地图的一个实现--com.hsbc.map.impl.CarPark <br/>
  * 构造方法会校验传入的参数是否正确. 
      ```java
      public class CarPark implements RectangularMap {
        private final int startX;
        private final int endX;
        private final int startY;
        private final int endY;
        ...
      }
     ```

 
