package ConnectionTest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import  java.util.Properties;
public class ConnectionTest1 {
    @Test
    //方式一
    public  void testConnection1() throws  SQLException{
        //获取Driver类实现类对象
        Driver driver = new com.mysql.jdbc.Driver();
        //jdbc:mysql:协议
        //localhost:ip协议
        //3306默认mysql端口
        String url="jdbc:mysql://localhost:3306/atguigu";
        //用户名和密码封装在Properties中
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connection=driver.connect(url,info);
        System.out.println(connection);
    }
    @Test
    //方式二是对方式一的迭代 不出现第三方的Api 提供程序的移植性
    public  void testConnection2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //1.获取Driver实现类对象，使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver=(Driver) clazz.newInstance();
        //2.提供要链接的数据库
        String url="jdbc:mysql://localhost:3306/atguigu";
        //3.提供链接需求的用户名和密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connection=driver.connect(url,info);
        System.out.println(connection);
    }
    @Test
    //方式三使用DriverManager替换Driver
    public void testConnection3() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //1.获取Driver实现类的对象
        Class clazz=Class.forName("com.mysql.jdbc.Driver");//反射出这个类了，但是还没有对象
        Driver driver=(Driver) clazz.newInstance(); //生成实例化对象
        //提供其他三个链接的基本信息
        String url="jdbc:mysql://localhost:3306/atguigu";
        String user = "root";
        String password="root";
        //注册驱动
        DriverManager.registerDriver(driver);
        //获取链接
        DriverManager.getConnection(url,user,password);
    }
    @Test
    //方式四,可以不注册，因为反射加载注册
    public  void testConnection4() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        //提供其他三个链接的基本信息
        String url="jdbc:mysql://localhost:3306/atguigu";
        String user = "root";
        String password="root";
        //注册驱动
        //获取Driver实现类的对象
        Class clazz=Class.forName("com.mysql.jdbc.Driver");//反射出这个类了，但是还没有对象,但是已加载了
        // 静态代码块中，他会自动注册
//        Driver driver=(Driver) clazz.newInstance(); //生成实例化对象
//        DriverManager.registerDriver(driver);

        //获取链接
        Connection connection=DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }
    @Test
    //方式五(final版)：将数据库链接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取链接
    //url,user,password采用软编码
    //1、实现了数据和程序解耦
    //2.如果需要修改配置文件信息，可以避免程序重新打包
    public void testConnection5() throws IOException, ClassNotFoundException, SQLException {
        //1.读取配置文件的4个基本信息
        InputStream is = ConnectionTest1.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url =pros.getProperty("url");
        String driver = pros.getProperty("driver");
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }
}
