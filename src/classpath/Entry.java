package classpath;
import java.io.File;
import java.io.IOException;

// 根据命令行输入 搜寻class文件所在的位置
// 这里提供一个抽象类
// 等其他具体的类去实例化

// 命令行中可能存在的输入
// java -cp p/a/t/h classname arg1 arg2 路径+类名
// java -cp p/a/t/h.jar classname arg1 arg2 jar包路径+类名
// java -cp p/a/t/* classname arg1 arg2  模糊路径+类名
// java -cp p/a/t/h; p/a/t/h/2 classname arg1 arg2 多路径+类名

// 抽象类 定义了一些方法 可以被继承使用
public abstract class Entry{
    // 系统分隔符 win为； linux为：
    public static final String pathListSeprator = System.getProperty("os.name").contains("Windows") ? ";":":";
    // 查找class文件 路径之间有/分隔 文件名为.class后缀
    // 由实例化的类重写
    abstract byte[] readClass(String className) throws IOException;// 方法类别（共有，私有，抽象） 方法返回类型 方法名 参数
    // 返回className的字符串形式
    abstract String printClassName();
    // 工厂方法 接口抽象 能根据不同的情况选择不同的接口
    static Entry createEntry(String path){
        if (path!=null) {
            // 针对不同输入路径寻找class文件的 不同方法
            if (path.contains(pathListSeprator)){
                // 输入了多个路径的情况
                return new CompositeEntry(path,pathListSeprator);
            }else if (path.contains("*")){
                // 模糊路径
                return new WildcardEntry("");
            }else if (path.contains(".jar") || path.contains(".JAR")
                    || path.contains(".zip") || path.contains(".ZIP")
            ){
                return new ZipJarEntry(path);
            }
            // 标准路径
            return new DirEntry(path);
        }else{
            File file = new File("");
            try{
                path = file.getCanonicalPath();
                return new DirEntry(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("illegal classpath format!");

    }
}