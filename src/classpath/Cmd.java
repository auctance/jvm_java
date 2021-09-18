package classpath;


// aim to parse these order
//    java -version
//    java -?
//    java -help
//    java -cp your/classpath yourClassName arg1 arg2 ...
//    java -classpath your/classpath yourClassName arg1 arg2 ...



public class Cmd {
    boolean isRightFmt = true;
    boolean helpFlag;
    boolean versionFlag;
    String cpOption = "";
    String clazz;
    String[] args;
    public Cmd(String[] strs){
        parseCmd(strs);
    }

    private void parseCmd(String[] args) { // input path, if it is correct
        int index = 1;
        if (args.length < 2){
            isRightFmt=false;
            return;
        }
        if (!args[0].equals("java")){
            isRightFmt=false;
        }else {
            if (args[1].equals("-help") || args[1].equals("-?")){
                helpFlag=true;
            }else if(args[1].equals("-version")){
                versionFlag=true;
            }else if(args[1].equals("-cp") || args[1].equals("classpath")){
                if (args.length<4) isRightFmt=false;
                index = 4;
                this.cpOption=args[2];
            }else if (args[1].equals("-Xjre")){
                if (args.length<4) isRightFmt=false;
                index=4;
                this.XjreOption=args[2];
            }
            this.clazz=args[index-1];
            this.args=new String[args.length-index];
            for (int i = index; i<args.length; i++){
                this.args[i-index]=args[i];
            }
        }
    }

    public void printUsage(){
        System.out.println("Usage: java[-options] class [args...]\n");
    }

    public static void main(String[] args){
        Cmd cmd = new Cmd(args);
        if (!cmd.isRightFmt) cmd.printUsage();
        else {
            if (cmd.versionFlag){
                System.out.println("java version \"1.8.0_20\"\n"
                        + "Java(TM) SE Runtime Environment (build 1.8.0_20-b26)\n"
                        + "Java HotSpot(TM) 64-Bit Server VM (build 25.20-b23, mixed mode)");
            }else if (cmd.helpFlag || cmd.args==null){
                cmd.printUsage();
            }else startJVM(cmd);
        }
    }
}
