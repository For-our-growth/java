##  JAVA 개념 정리 11 - 상속

이것이 자바다 참고



#### 상속

현실에서 말하는 상속은 부모자 자식에게 물려주는 것을 말한다. 

자식은 상속을 통해서 부모가 물려준 것들을 이용할 수 있다. 객체 지향 프로그래밍에서도 부모 클래스의 멤버를
자식 클래스에게 넘겨줄 수 있다. 

**부모 클래스 **를 **상위 클래스 **라고 부르기도 하고, **자식 클래스**를 **하위 클래스** , 또는 **파생 클래스** 라고 부릅니당.



#### 상속의 장점 !

상속은 이미 잘 개발되어 있는 클래스를 재사용해서 새로운 클래스를 만든다. 

그렇기 때문에 코드의 중복을 줄여줄 수 있다는 장점이 있다 ! 



**field1**, **field2**, **method1( )**, **method2( )** 를 가지는 클래스를 작성한다고 가정해보자. 4개를 모두 처음부터 

작성하는 것 보다는 **field1** 과 **method1( )** 을 가지고 있는 클래스가 있다면 이것을 상속하고, **field2** 와

**method2( )** 만 추가 작성하는 것이 보다 효율적이고 개발 시간을 절약시켜줄 것이다.



예시

```java
public class A {
	int field1;
	void method1() { ... }
}
```

​                                   **↑ A를 상속**

```java
public class B extends A { // 위의 클래스 A를 상속
	String field2;
	void method2() { ... }
}
```



```java
B b = new B();
b.field1 = 10;
b.method1();

b.field2 = "홍길동";
b.method2();
```



상속을 해도 부모 클래스의 모든 필드와 메소드들을 물려받는 것은 아니다. 

부모 클래스에서 **private** 접근 제한을 갖는 필드와 메소드는 상속 대상에서 제외된다. 그리고 부모 클래스와
자식 클래스가 다른 패키지에 존재한다면 **default** 접근 제한을 갖는 필드와 메소드도 상속 대상에서 제외된다.
그 외의 경우는 모두 상속의 대상이 된다.



상속을 이용하면 클래스의 수정을 최소화시킬 수 있다는 장점도 있다! 

부모 클래스의 수정으로 모든 자식 클래스들의 수정이 이루어지기 때문에 유지 보수 시간을 최소화해준다.
예시로, 클래스 B, C가 클래스 A를 상속할 경우 A의 필드와 메소드를 수정함으로써 B, C 를 수정하지 않아도 수정된
A의 필드와 메소드를 이용할 수 있다.





#### 클래스 상속

현실에서의 상속은 부모가 자식을 선택해 물려주지만, 프로그래밍에서는 자식이 부모를 선택한다.

자식 클래스를 선언할 때 어떤 부모 클래스를 상속받을 것인지를 결정하고 선택된 부모 클래스는 다음과 같이

``extends`` 뒤에 기술한다.

```java
class 자식클래스 extends 부모클래스 {
	// 필드
	// 생성자
	// 메소드
}
```



예를 들어  Game 클래스를 상속해서 Tekken7 클래스를 설계하고 싶다면 다음과 같이 하면 된다. 

```java
class Tekken7 extends Game {

}
```



자바는 다중 상속이 되지 않는다. 즉, 여러 개의 부모 클래스를 상속할 수 없다. 
그래서 다음과 같이 **extends** 뒤에는 단 하나의 부모 클래스만 와야 한다.

```java
class 자식클래스 extends 부모클래스1 {

}
```





#### 부모 생성자 호출

자식 객체를 실행하면, 부모 객체가 먼저 생성되고 자식 개체가 그 다음에 생성된다. 

아래 코드는 **DmbCellPhone** 객체만 생성하는 것처럼 보이지만, 사실 내부적으로 **Cellphone**  객체가 먼저 
생성되고, DmbCellPhone 객체가 생성된다.

```java
DmbCellPhone dmbCellPhone = new DmbCellPhone();
```



모든 객체는 클래스의 생성자를 호출해야만 생성된다. 부모 객체도 예외는 아니다. 그렇다면 부모 객체를
생성하기 위해 부모 생성자를 어디서 호출한 것일까? 부모 생성자는 자식 생성자의 맨 첫 줄에서 호출된다.

예를 들어 DmbCellPhone의 생성자가 명시적으로 선언되지 않았다면 컴파일러는 다음과 같은 기본 생성자를
만들어 낸다.

```java
public DmbCellPhone() {
	super();
}
```



첫 줄에 **super();** 가 추가된 것을 볼 수 있다. **super()** 는 부모의 기본 생성자를 호출한다. 
즉, CellPhone 클래스의 다음 생성자를 호출한다.

```java
public CellPhone() {
}
```



**CellPhone.java** 코드에서도 CellPhone의 생성자가 선언되지 않았지만 컴파일러에 의해 기본 생성자가 만들어지므로 문제없이 실행된다. 만약 직접 자식 생성자를 선언하고 명시적으로 부모 생성자를 호출하고 싶다면 
다음과 같이 작성하면 된다.

```java
자식클래스( 매개변수선언, ... ) {
	super( 매개값, ... );
	...
}
```



**super (매개값, ... )**  는 매개값의 타입과 일치하는 부모 생성자를 호출한다. 만약 매개값의 타입과 일치하는 부모 생성자가 없을 경우 컴파일 오류가 발생. **super (매개값, ... )** 가 생략되면 컴파일러에 의해 **super()** 가 자동으로
추가되기 때문에 부모의 기본 생성자가 존재해야 한다.

부모 클래스에 기본 생성자가 없고 매개 변수가 있는 생성자만 있다면 자식 생성자에서 반드시 부모 생성자 호출을 위해 **super (매개값, ... )** 를 명시적으로 호출해야 한다. **super (매개값, ... )** 는 반드시 자식 생성자 첫 줄에 위치해야 한다. 그렇지 않으면 컴파일 에러가 난다. 예제를 보장.



```java
[People.java] 부모 클래스

public class People {
	public String name;
	public String ssn;
	
	public People(String name, String ssn) {
		this.name = name;
		this.ssn = ssn;
	}
}
```

People 클래스는 기본 생성자가 없고 name 과 ssn 을 매개값으로 받아 객체를 생성시키는 생성자만 있다.
그렇기 때문에 People을 상속하는 Student 클래스는 생성자에서 **super (매개값, ... )** 으로 People 클래스의
생성자를 호출해야 한다.



```java
[Student.java] 자식 클래스

public class Student extends People {
	public int studentNo;
	
	public Student(String name, String ssn, int studentNo) {
        super(name, ssn);      // 
        this.studentNo = studentNo;
    }
}
```

Student 클래스의 생성자는 name, ssn, studentNo를 매개값으로 받아서 name과 ssn은 다시 부모 생성자를
호출하기 위해 매개값으로 넘겨준다. 

주석 표시해놓은 **super(name, ssn)**  은 People 생성자인 **People(String name, String ssn)** 을 호출한다.

```java
[StudentExample.java] 자식 객체 이용

public class StudentExample {
	public static void main(String[] args) {
		Student student = new Student("홍길동", "123456-1234567", 1);
		System.out.println("name : " + student.name);
        System.out.println("name : " + student.ssn);
        System.out.println("studentNO : " + student.studentNo);
	}
}
```



출력

```
name : 홍길동
ssn : 123456-1234567
studentNo : 1
```



