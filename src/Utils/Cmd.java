package Utils;

// 解析命令行中输入的java命令 以下是参考命令
// java -version
// java -?
// java -help
// java -cp one/classpath oneclassname arg1 arg2
// java -classpath one/classpath oneclassname arg1 arg2

public class Cmd{
    // 查看是否是正确的格式
    boolean isRightFmt = true;
    boolean isRigthOpt = true;
    // 查看是否是查看版本 是否是help
    boolean isHelp;
    boolean versionFlag;
    // classpath的路径 -classpath -cp
    private String cpOption;

    // 这是非标准选项 使用制定路径来加载class文件
    // 对应情况为 JAVA_HOME/jre 中的文件内容
    private String XjreOption;
    // 包含main方法的class文件
    private String clazz;
    // class文件需要的参数
    private String[] args;

    // 解析命令
    public Cmd(String cmdLine){
        parseCmd(cmdLine);
    }
    // 构造方法 当Cmd类实例化时候 自动被调用 Cmd类就有了parse方法
    // 可能有多个string语句
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
            isRightFmt=false;
            return;
        }
        // 判断开头是不是"java"
        if (!args[0].equals("java")){
            isRightFmt=false;
        }else{
            if (args[1].equals("-?") || args[1].equals("-help")){
                isHelp=true;
            }
            else if (args[1].equals("-version") || args[1].equals("-V")){
                versionFlag=true;
            }
            else if (args[1].equals("-cp") || args[1].equals("-classpath")){
                if (args.length<4){
                    isRightFmt=false;
                }
                // java -classpath one/classpath oneclassname arg1 arg2 形式
                index = 3;
                this.cpOption = args[2]; // classpath
            }
            else if (args[1].equals("-Xjre") ){
                if (args.length<4) isRightFmt=false;
                index=3;
                this.XjreOption = args[2]; // classpath
            }else if(args[1].startsWith("-")){
                isRigthOpt = false;
            }
            this.clazz = args[index]; // classname
            // 接受cmd命令最后的参数
            this.args = new String[args.length-index-1];
            for (int i = index+1; i< args.length; i++){
                this.args[i-index-1] = args[i];
            }
        }
    }

    // 重载解析方法 解析cmdline
    public void parseCmd(String cmdLine){
        String[] args = cmdLine.trim().split("\\s+");
        parseCmd(args);
    }

    // 命令行输入错误 打印帮助信息
    public void printUsage(){
        System.out.println("Usage: java [-options] class [args...]\n");
    }

//    public static void main(String[] args){
//        Cmd cmd = new Cmd(args);
//        if (!cmd.isRight) cmd.printUsage();
//        else{
//            if (cmd.versionFlag) System.out.println("java version....");
//            else if(cmd.isHelp || cmd.args==null) cmd.printUsage();
//            // 是正确的命令就 将命令作为参数 调用启动jvm方法
//            else startJVM(cmd);
//        }
//    }

    // get 方法
    // 这里这么写 有点类似于构造方法的作用
    // 直接能拿到变量的boolean类型信息

    public boolean isRightFmt() {
        return isRightFmt;
    }

    public boolean isRigthOpt() {
        return isRigthOpt;
    }

    public boolean isHelp() {
        return isHelp;
    }

    public boolean isVersionFlag() {
        return versionFlag;
    }

    public String getCpOption() {
        return cpOption;
    }

    public String getXjreOption() {
        return XjreOption;
    }

    public String getClazz() {
        return clazz;
    }

    public String[] getArgs() {
        return args;
    }
}