package znative.java.lang;
// 虚拟机栈的信息
public class NStackTraceElement {
    // 类所在的java文件
    String fileName;
    // 声明方法的类名
    String className;
    // 调用的方法名
    String methodName;
    // 出现exception的行号
    int lineNumber;

    public NStackTraceElement(String fileName, String className, String methodName, int lineNumber) {
        this.fileName = fileName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return className+"."+methodName+"("+fileName+":"+lineNumber+")";
    }
}
