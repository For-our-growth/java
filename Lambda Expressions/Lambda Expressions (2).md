# Lambda Expressions

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



람다식의 실행 블록에는 클래스의 멤버(필드와 메소드) 및 로컬 변수를 사용할 수 있다. 클래스의 멤버는 제약 사항 없이 사용 가능하지만, 로컬 변수는 제약 사항이 따른다.



#### 클래스의 멤버 사용

람다식 실행 블록에는 클래스의 멤버인 필드와 메소드를 제약 사항 없이 사용할 수 있다. 이때 this 키워드를 사용할 때는 주의가 필요하다. 일반적으로 익명 객체 내부에서 this는 익명 객체의 참조이지만, 람다식에서 this는 람다식을 실행한 객체의 참조이다. (내부적으로 생성되는 익명 객체의 참조가 아니다.)

아래의 코드는 중첩 객체 Inner에서 람다식을 실행했기 때문에 람다식 내부에서의 this는 중첩 객체 Inner이다.

```java
public interface MyFunctionalInterface {
	public void method();
}
```

```java
public class UsingThis {
	public int outterField = 10;
	
	class Inner {
		int innerField = 20;
		
		void method() {
			MyFunctionalInterface fi = () -> {
				System.out.println("outterField: " + outterField);
				System.out.println("outterField: " + Usingthis.this.outterField + "\n");
				
				System.out.println("innerField: " + innerField);
				System.out.println("innerField: " + this.innerField + "\n");
			};
			fi.method();
		}
	}
}
```

```java
public class UsingThisExample {
	public static void main(String[] args) {
		UsingThis usingThis = new UsingThis();
		UsingThis.Inner inner = usingThis.new Inner();
		inner.method();
	}
}
```



#### 로컬 변수 사용

람다식은 메소드 내부에서 주로 작성되기 때문에 로컬 익명 구현 객체를 생성시킨다고 봐야 한다. 람다식에서 바깥 클래스의 필드나 메소드는 제한 없이 사용할 수 있으나, 메소드의 매개 변수 또는 로컬 변수를 사용하면 이 두 변수는 final 특성을 가져야한다. 따라서 매개 변수 또는 로컬 변수를 람다식에서 읽는 것은 허용되지만, 람다식 내부 또는 외부에서 변경할 수 없다.

```java
public interface MyFunctionalInterface {
	public void method();
}
```

```java
public class UsingLocalVariable {
	void method(int arg) {		// arg은 final 특성을 가짐
		int localVar = 40;		// localVar은 final 특성을 가짐
		
		// arg = 31;		// final 특성 때문에 수정 불가
		// localVar = 41;	// final 특성 때문에 수정 불가
		
		MyFunctionalInterface fi = () -> {
			// 로컬 변수 읽기
			System.out.println("arg: " + arg);
			System.out.println("localVar: " + localVar + "\n");
		};
        fi.method();
	}
}
```

```java
public class UsingLocalVariableExample {
	public static void main(String[] args) {
		UsingLocalVariable ulv = new UsingLocalVariable();
		ulv.method(20);
	}
}
```



#### 표준 API의 함수적 인터페이스

자바에서 제공되는 표준 API에서 한 개의 추상 메소드를 가지는 인터페이스들은 모두 람다식을 이용해서 익명 구현 객체로 표현이 가능하다. 예를 들면 스레드의 작업을 정의하는 Runnable 인터페이스는 매개 변수와 리턴값이 없는 run() 메소드만 존재하기 때문에 아래와 같이 람다식을 이용해서 Runnable 인스턴스를 생성시킬 수 있다.

```java
public class RunnableExample {
	public static void main(String[] args) {
		Runnable runnable = () -> {
			for(int i = 0; i < 10; i++) {
				System.out.println(i);
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.start();
	}
}

// Thread 생성자를 호출할 때 람다식을 매개값으로 대입해도 된다.
/*
 * Thread thread = new Thread(() -> {
 *     for(int i = 0; i < 10; i++) {
 *         System.out.println(i);
 *     }
 * });
 */
```



자바 8부터는 빈번하게 사용되는 함수적 인터페이스는 java.util.function 표준 API  패키지로 제공한다. 이들은 인터페이스에 선언된 추상 메소드의 매개값과 리턴값의 유무로 구분된다.

| 종류      | 추상 메소드 특징                                             |
| --------- | ------------------------------------------------------------ |
| Consumer  | 매개값은 있고, 리턴값은 없다.                                |
| Supplier  | 매개값은 없고, 리턴값은 있다.                                |
| Function  | 매개값도 있고, 리턴값도 있다. 주로 매개값을 리턴값으로 매핑(타입 변환) |
| Operator  | 매개값도 있고, 리턴값도 있다. 주로 매개값을 연산하고 결과를 리턴 |
| Predicate | 매개값은 있고, 리턴 타입은 boolean. 매개값을 조사해서 true/false를 리턴 |

