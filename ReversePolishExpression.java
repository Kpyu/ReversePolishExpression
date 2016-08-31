import java.io.*;
import java.util.Stack;
import java.util.Vector;

public class ReversePolishExpression {

 // 操作数栈
 public static Stack<String> operatorStack = new Stack<String>();
 // 运算符栈
 public static Stack<String> operandStack = new Stack<String>();

 /***
  *  算术表达式输入
  * @return 返回算数表达式
  */
 public String getExpression() {
  String expression = null;
  BufferedReader reader = new BufferedReader(new InputStreamReader(
    System.in));
  try {
   expression = reader.readLine();
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return expression;
 }


 /***
  *  运算符判断并返回优先级
  * @param string
  * @return 返回运算符优先级
  */
 public int isOperand(String string) {

  if (string.equals("-") || string.equals("+")) {
   return 2  ;
  }
  if (string.equals("*")||string.equals("/")){
   return 3;
  }
  if (string.equals("(")||string.equals(")")){
   return 1;
  }
  else
   return 0;
 }

 /***
  * 操作数判断，若是操作数则返回真
  * @param s
  * @return 返回是否为操作数的真值
  */
 public boolean isOperator(String s) 
 {
  char[] arry=s.toCharArray();
  boolean check=true;
  
  try {
    for(int i=0;i<arry.length;i++)
    {
     if(arry[i]=='.')
     check= true;
     if(Integer.valueOf(String.valueOf(arry[i]))>=0&&Integer.valueOf(String.valueOf(arry[i]))<=9)
     check= true;
     else
     {
      check=false;
      break;
     }
    }
   } 
  catch (NumberFormatException e) 
  {
   check=false;
  }
  return check;
 }
 
/***
 * 扫描算数表达式
 * @param expression
 */
 // 扫描表达式字符串
 public void scanner(String expression) 
 {
  char[] exp = expression.toCharArray();
  //存储操作数
  String opString="";
  //获取操作数
  for (int i = 0; i < exp.length; i++) 
  {
   if(isOperator(String.valueOf(exp[i])))
   {    
    opString+=String.valueOf(exp[i]);
    continue;
   }
   //当扫描选项为运算符时的操作
   if (isOperand(String.valueOf(exp[i])) > 0) 
   {
    //将操作数压入操作数栈
    if(!opString.equals(""))
    {
     operatorStack.push(opString);
    }
    opString="";
    // 1.将运算符之前的字符串作为操作数并入栈
    // 2.运算符为非括号运算符    
    if (exp[i] != '('&&exp[i] !=')') 
    {      
     if(operandStack.isEmpty())
     {
      operandStack.push(String.valueOf(exp[i]));
      continue;
     }
     else
     {
      String s = operandStack.pop();
      // 若运算符堆栈栈顶的运算符为左括号，则直接存入运算符堆栈
      if (s == "(") 
      {
       operandStack.push(s);
       operandStack.push(String.valueOf(exp[i]));
       continue;
      }
      // 若比运算符堆栈栈顶的运算符优先级高，则直接存入运算符堆栈。
      if (isOperand(s) < isOperand(String.valueOf(exp[i]))
       && s != "(") 
      {
       operandStack.push(s);
       operandStack.push(String.valueOf(exp[i]));
       continue;
      }
      // 若比运算符堆栈栈顶的运算符优先级低或相等，则输出栈顶运算符到操作数堆栈，并将当前运算符压入运算符堆栈。
      if (isOperand(s) >= isOperand(String.valueOf(exp[i]))&& s != "(")        
      {
       operatorStack.push(s);
      
       operandStack.push(String.valueOf(exp[i]));
       continue;
      }
     }
    }
    // 若遇到左括号则直接压入运算符栈
    if (exp[i] == '(') 
    {
     operandStack.push(String.valueOf(exp[i]));

     continue;
    }
    // 该运算符为右括号")"，则输出运算符堆栈中的运算符到操作数堆栈，直到遇到左括号为止
    if (exp[i] == ')') 
    {
          
     //operatorStack.push(opString);
     String s = "";// operandStack.pop();
     while ( !s.equals("(")) 
     {
      s = operandStack.pop(); 
      if(s.equals("("))
      {
       break;
      }
      operatorStack.push(s);              
     }
     continue;
    }
   }
  }
  //operatorStack.push(expression.substring(n, expression.length()));
  //将运算符栈中运算符依次压入操作数栈
    while(!operandStack.isEmpty())
    {
     String s= operandStack.pop();
     operatorStack.push(s);
    }
 }
 
 
/***
 * 获取逆波兰式
 */
 public Vector<String>  getReversePolishExpression(Stack<String> operatorStack)
 {  
  //获取逆波兰式
  Vector<String >reversePolishExpression = new Vector<String>();
  while(!operatorStack.isEmpty())
  {
   String s=operatorStack.pop();
   reversePolishExpression.add(s);
   continue;
  }
  return reversePolishExpression;
 }
 
 /**
  *对操作数堆栈执行计算算法
  */
 public void calculate(Stack<String> operatorStack,Vector<String >reversePolishExpression)
 {
  for(int i=reversePolishExpression.size()-1;i>=0;i--)
  {   
   //若是操作符优先级为1
   if(isOperand(reversePolishExpression.elementAt(i))==2)
   {
    //执行减法
    if(reversePolishExpression.elementAt(i).equals("-"))
    {
     float num1=Float.valueOf(operatorStack.pop());
     float num2=Float.valueOf(operatorStack.pop());
     float result=num2-num1;
     operatorStack.push(String.valueOf(result));
     continue;
    }
    //执行加法
    if(reversePolishExpression.elementAt(i).equals("+"))
    {
     float num1=Float.valueOf(operatorStack.pop());
     float num2=Float.valueOf(operatorStack.pop());
     float result=num2+num1;
     operatorStack.push(String.valueOf(result));
     continue;
    }
   }
   //若运算符优先级为2
   if(isOperand(reversePolishExpression.elementAt(i))==3)
   {
    //执行乘法
    if(reversePolishExpression.elementAt(i).equals("*"))
    {
     float num1=Float.valueOf(operatorStack.pop());
     float num2=Float.valueOf(operatorStack.pop());
     float result=num2*num1;
     operatorStack.push(String.valueOf(result));
     continue;
    }
    //执行除法
    if(reversePolishExpression.elementAt(i).equals("/"))
    {
     float num1=Float.valueOf(operatorStack.pop());
     float num2=Float.valueOf(operatorStack.pop());
     float result;
     try 
     {
      result = num2 / num1;
      operatorStack.push(String.valueOf(result));
      continue;
     }
     catch (Exception e)
     {
      // TODO: handle exception
      System.out.println("运算除零，程序退出");
      break;
     }     
    }    
   }
   //若运算符优先级为1即运算符为“(”则不作处理
   if(isOperand(reversePolishExpression.elementAt(i))==1)
   {
    continue;
   }
   //若元素为操作数则直接压入操作数栈
   else     
   {
    operatorStack.push(reversePolishExpression.elementAt(i));
   }
  }
 }
 
 /**
  * 主方法
  */
 public static void main(String[] args)
 {  
  //实例一个逆波兰式的类
  ReversePolishExpression reversePolishExpression = new ReversePolishExpression();
  
  //从键盘输入算术表达式
  System.out.println("请输入算术表达式（注：本程序不支持负数运算）:");
  String expression = reversePolishExpression.getExpression();
  
  //左至右扫描一中缀表达式，并进行相应的栈操作
  reversePolishExpression.scanner(expression);
  
  //保存语法单元的项目
  Vector<String >rpExpression=reversePolishExpression.getReversePolishExpression(operatorStack);
  
  //对后缀表达式，即逆波兰式执行计算算法
  reversePolishExpression.calculate(operatorStack, rpExpression);
  
  //输出计算结果
  System.out.println("运算结果为"+operatorStack.pop());  
 }
}
