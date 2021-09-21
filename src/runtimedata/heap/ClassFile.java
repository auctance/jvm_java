package runtimedata.heap;

public class ClassFile {
    private int minorVersion;
    private int majorVersion;
    public ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;         //同 thisClass 的索引值。
    private int[] interfaces;     //存放所实现的接口在常量池中的索引。同 thisClass 的索引值。
    private MemberInfo[] fields;    //存放类中所有的字段，包括静态的非静态的；不同的属性通过字段的访问修饰符来读取；
    private MemberInfo[] methods;   //存放类中所有的方法，包括静态的非静态的；不同的属性通过方法的访问修饰符来读取；
    private AttributeInfo[] attributes; //属性表，存放类的属性；
}
