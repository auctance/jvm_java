package classpath;

// 解析命令行中输入的java命令 以下是参考命令
// java -version
// java -?
// java -help
// java -cp one/classpath oneclassname arg1 arg2
// java -classpath one/classpath oneclassname arg1 arg2

import com.sun.tools.corba.se.idl.StringGen;
public class Cmd{
    // 查看是否是正确的格式
    boolean isRight=true;
    // 查看是否是查看版本 是否是help
    boolean isHelp;
    boolean versionFlag;
    // 构造方法 当Cmd类实例化时候 自动被调用 Cmd类就有了parse方法
    public Cmd(String[] strs){
        parseCmd(strs);
    }

    // 定义parse方法
    // args 为字符串数组
    public void parseCmd(String[] args){
        // args参数位置
        int index=1;
        // 命令长度太短 不正常
        if (args.length<2){
            isRight=false;
            return;
        }
        // 判断开头是不是"java"
        if (!args[0].equals("java")){
            isRight=false;
        }else{
            if (args[1].equals("-?") || args[1].equals("-help")){
                isHelp=true;
            }
            else if (args[1].equals("-version") || args[1].equals("-V")){
                versionFlag=true;
            }
            else if (args[1].equals("-cp") || args[1].equals("-classpath")){
                if (args.length<4){
                    isRight=false;
                }
                // java -classpath one/classpath oneclassname arg1 arg2 形式
                index = 4;
                this.cpOption = args[2]; // classpath
            }
            else if (args[1].equals("-Xjre") ){
                if (args.length<4) isRight=false;
                index=4;
                this.XjreOption = args[2]; // classpath
            }
            this.clazz = args[index-1]; // classname
            // 接受cmd命令最后的参数
            this.args = new String[args.length-index];
            for (int i = index; i< args.length; i++){
                this.args[i-index] = args[i];
            }
        }

    }
    // 命令行输入错误 打印帮助信息
    public void printUsage(){
        System.out.println("Usage: java [-options] class [args...]\n");
    }

    public static void main(String[] args){
        Cmd cmd = new Cmd(args);
        if (!cmd.isRight) cmd.printUsage();
        else{
            if (cmd.versionFlag) System.out.println("java version....");
            else if(cmd.isHelp || cmd.args==null) cmd.printUsage();
            // 是正确的命令就 将命令作为参数 调用启动jvm方法
            else startJVM(cmd);
        }
    }

}