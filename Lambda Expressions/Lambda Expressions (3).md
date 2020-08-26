# Lambda Expressions 

 

#####  - 책 '이것이 자바다'를 참고해서 작성한 문서입니다. 

 

 

 

#### 메소드 참조 

메소드 참조(Method References)는 말 그대로 메소드를 참조해서 매개 변수의 정보 및 리턴 타입을 알아내어, 람다식에서 불필요한 매개 변수를 제거하는 것이 목적이다. 람다식은 종종 기존 메소드를 단순히 호출만 하는 경우가 많다. 

예를 들어 두 값을 받아 큰 수를 리턴하는 Math 클래스의 max() 정적 메소드를 호출하는 람다식은 아래와 같다. 

```java
(left, right) -> Math.max(left, right);
```

람다식은 단순히 두 값을 Math.max() 메소드의 매개값으로 전달하는 역할만 하기 때문에 다소 불편해 보인다. 이 경우, 아래와 같이 메소드 참조를 이용하면 매우 깔끔하게 처리할 수 있다. 

```java
Math :: max; 
```

메소드 참조도 람다식과 마찬가지로 인터페이스의 익명 구현 객체로 생성되므로 타겟 타입인 인터페이스의 추상 메소드가 어떤 매개 변수를 가지고, 리턴 타입이 무엇인가에 따라 달라진다. 

IntBinaryOperator 인터페이스는 두 개의 int 매개값을 받아 int 값을 리턴하므로 Math :: max 메소드 참조를 대입할 수 있다. 

```JAVA
IntBinaryOperator operator = Math :: max; 
```

메소드 참조는 정적 또는 인스턴스 메소드를 참조할 수 있고, 생성자 참조도 가능하다. 

 

#### 정적 메소드와 인스턴스 메소드 참조 

정적(static) 메소드를 참조할 경우에는 클래스 이름 뒤에 :: 기호를 붙이고 정적 메소드 이름을 기술하면 된다. 

```java
클래스 :: 메소드 
```

인스턴스 메소드일 경우에는 먼저 객체를 생성한 다음, 참조 변수 뒤에 :: 기호를 붙이고 인스턴스 메소드 이름을 기술하면 된다. 

```java
참조변수 :: 메소드 
```

아래 예제는 Calculator의 정적 및 인스턴스 메소드를 참조한다. 



```java
public class Calculator { 

  public static int staticMethod(int x, int y) {   // 정적 메소드 

    return x + y; 

  } 

   

  public int instanceMethod(int x, int y) {      // 인스턴스 메소드 

    return x + y; 

  } 

} 
```



```java
import java.util.function.IntBinaryOperator; 

 

public class MethodReferencesExample { 

  public static void main(String[] args) { 

    IntBinaryOperator operator; 

     

    operator = (x, y) -> Calculator.staticMethod(x, y); 

    System.out.println("결과1: " + operator.applyAsInt(1, 2)); 

     

    operator = Calculator :: staticMethod; 

    System.out.println("결과2: " + operator.applyAsInt(3, 4)); 

     

    Calculator obj = new Calculator(); 

    operator = (x, y) -> obj.instanceMethod(x, y); 

    System.out.println("결과3: " + operator.applyAsInt(5, 6)); 

     

    operator = obj :: instanceMethod; 

    System.out.println("결과4: " + operator.applyAsInt(7, 8)); 

  } 

} 
```



#### 매개 변수의 메소드 참조 

메소드는 람다식 외부의 클래스 멤버일 수도 있고, 람다식에서 제공되는 매개 변수의 멤버일 수도 있다. 이전 예제는 람다식 외부의 클래스 멤버인 메소드를 호출하였다. 그러나 다음과 같이 람다식에서 제공되는 a 매개 변수의 메소드를 호출해서 b 매개 변수를 매개값으로 사용되는 경우도 있다. 

```java
(a, b) -> { a.instanceMethod(b); } 
```

이를 메소드 참조로 표현하면 아래와 같다. a의 클래스 이름 뒤에 :: 기호를 붙이고 메소드 이름을 기술하면 된다. 작성 방법은 정적 메소드 참조와 동일하지만, a의 인스턴스 메소드가 참조되므로 전혀 다른 코드가 실행된다. 

```java
클래스 :: instanceMethod 
```

아래 예제는 두 문자열이 대소문자와 상관없이 동일한 알파벳으로 구성되어 있는지 비교한다. 비교를 위해 사용된 메소드는 String의 인스턴스 메소드인 compareTolgnoreCase()이다. a.compareTolgnoreCase(b)로 호출될 때 사전 순으로 a가 b보다 먼저 오면 음수를, 동일하면 0을, 나중에 오면 양수를 리턴한다. 사용된 함수적 인터페이스는  두 String 매개값을 받고 int 값을 리턴하는 ToIntBiFunction<String, String>이다. 

```java
import java.util.function.ToIntBiFunction; 

 

public class ArgumentMethodReferencesExample { 

  public static void main(String[] args) { 

    ToIntBiFunction<String, String> function; 

     

    function = (a, b) -> a.compareToIgnoreCase(b); 

    print(function.applyAsInt("Java", "Java")); 

     

    function = String :: compareToIgnoreCase; 

    print(function.applyAsInt("Java", "Java")); 
     

  } 

   

  public static void print(int order) { 

    if(order < 0) { System.out.println("사전순으로 먼저 옵니다"); } 

    else if(order == 0) { System.out.println("동일한 문자열입니다."); } 

    else { System.out.println("사전순으로 나중에 옵니다."); } 

  } 

} 
```

 

#### 생성자 참조 

메소드 참조(method reference)는 생성자 참조도 포함한다. 생성자를 참조한다는 것은 객체  생성을 의미한다. 단순히 메소드 호출로 구성된 람다식을 메소드 참조로 대치할 수 있듯이, 단순히 객체를 생성하고 리턴하도록 구성된 람다식은 생성자 참조로 대치할 수 있다. 아래 코드를 보면 람다식은 단순히 객체 생성 후 리턴만 한다. 

```java
(a, b) -> { return new 클래스(a, b); } 
```

이 경우, 생성자 참조로 표현하면 아래와 같다. 클래스 이름 뒤에 :: 기호를 붙이고 new 연산자를 기술하면 된다. 생성자가 오버로딩되어 여러 개가 있을 경우, 컴파일러는 함수적 인터페이스의 추상 메소드와 동일한 매개 변수 타입과 개수를 가지고 있는 생성자를 찾아 실행한다. 만약 해당 생성자가 존재하지 않으면 컴파일 오류가 발생한다. 

```java
클래스 :: new 
```


