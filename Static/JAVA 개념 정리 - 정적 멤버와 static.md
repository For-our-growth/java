##  JAVA 개념 정리 07 - 정적 멤버와 static 

이것이 자바다 참고



**정적(static)**은 **'고정된'**이란 의미를 가지고 있습니다. 
정적 멤버는 클래스에 고정된 멤버로서 객체를 생성하지 않고 사용할 수 있는 필드와 메소드를 말합니다.

이들을 각각 정적 필드, 정적 메소드라고 합니다.
정적 멤버는 객체(인스턴스)에 소속된 멤버가 아니라 클래스에 소속된 멤버이기 때문에 클래스 멤버라고도 합니다.



#### 정적 멤버 선언

정적 필드와 메소드를 선언하려면 필드와 메소드 선언 시 **static** 키워드를 추가적으로 붙이면 됩니다.

 ```java
public class 클래스 {
	
	static 타입 필드 = 초기값;  // 정적 필드
	
	static 리턴 타입 메소드( 매개변수선언,    ) { ... }
}
 ```



필드를 선언할 때 인스턴스 필드로 선언할 것인가, 아니면 정적 필드로 선언할 것인가의 판단 기준은

객체마다 가지고 있어야 할 데이터라면 **인스턴스 필드**로 선언하고, 

객체마다 가지고 있을 필요성이 없는 공용적인 데이터라면 **정적 필드**로 선언하는 것이 좋아요.



예시 코드입니다.

예시

 ```java
[Calculator.java]

public class Calculator {
	static double pi = 3.14159;
    
    static int plus(int x, int y) {
        return x + y;
    }

    static int minus(int x, int y) {
        return x - y;
    }
}
 ```



```java
[CalculatorExample.java]

public class CalculatorExample {
    public static void main(String[] args) {
        double result1 = 10 * 10 * Calculator.pi;
        int result2 = Calculator.plus(10, 5);
        int result3 = Calculator.minus(10, 5);
        
        System.out.println("result1 : " + result1);
        System.out.println("result2 : " + result2);
        System.out.println("result3 : " + result3);
    }
}
```



출력

```java
result1 : 314.159
result2 : 15
result3 : 5
```





#### 정적 초기화 블록



정적 필드는 다음과 같이 필드 선언과 동시에 초기값을 주는 것이 보통이다.

```java
static double pi = 3.14159
```



그러나 계산이 필요한 초기화 작업이 있을 수 있다. 

인스턴스 필드는 생성자에서 초기화하지만, 정적 필드는 객체 없이도 사용해야 하므로 생성자에서 초기화 작업을

할 수 없다. 생성자는 객체 생성 시에만 실행되기 때문이다.  그럼 정적 필드를 위한 초기화는 어디서 할까.



자바는 정적 필드의 복잡한 초기화 작업을 위해 **정적 블록(static block)**을 제공한다. 

```java
static {
    ...
}
```

정적 블록은 클래스가 메모리로 로딩될 때 자동적으로 실행되고 클래스 내부에 여러 개가 선언되어도 상관없다.