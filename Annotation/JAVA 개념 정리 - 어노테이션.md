##  JAVA 개념 정리 - 어노테이션

이것이 자바다 참고


#### 어노테이션(Annotation)

어노테이션은 메타데이터(metadata) 라고 볼 수 있다. 
메타데이터란 애플리케이션이 처리해야 할 데이터가 아니라, 컴파일 과정과 실행 과정에서 코드를 어떻게
컴파일하고 처리할 것인지를 알려주는 정보이다. 

어노테이션은 다음과 같이 작성한다.  

```java
@AnnotationName
```


##### 어노테이션은 다음 세 가지 용도로 사용된다.

1. 컴파일러에게 코드 문법 에러를 체크하도록 정보를 제공
2. 소프트웨어 개발 툴이 빌드나 배치 시 코드를 자동으로 생성할 수 있도록 정보를 제공
3. 실행 시 특정 기능을 실행하도록 정보를 제공    
  

컴파일러에게 코드 문법 에러를 체크하도록 정보를 제공하는 대표적인 예는 **@Override** 어노테이션이다.

**@Override** 는 메소드 선언 시 사용하는데, 메소드가 오버라이드(재정의) 된 것임을 컴파일러에게 알려주어

컴파일러가 오버라이드 검사를 하도록 해준다. 정확히 오버라이드가 되지 않으면 컴파일러는 에러를 발생시킨다.


#### 어노테이션 타입 정의와 적용

어노테이션 타입을 정의하는 방법은 인터페이스를 정의하는 것과 비슷하다.

다음과 같이 **@interface** 를 사용해서 어노테이션을 정의하며, 그 뒤에 사용할 어노테이션 이름이 온다.

```java
public @interface AnnotationName {
}
```


이렇게 정의한 어노테이션은 코드에서 다음과 같이 사용한다.

```java
@AnnotationName
```


어노테이션은 엘리먼트 멤버를 가질 수 있다. 각 엘리먼트는 타입과 이름으로 구성되며, 디폴트 값을 가질 수 있다.

```java
public @interface AnnotationName {
	타입 elementName() [default 값];  // 엘리먼트 선언
	...
}
```


엘리먼트 타입으로는 **int**, **double** 과 같은 기본 데이터 타입이나 **String**, **열거 타입**, **Class 타입**, 그리고

이들의 배열 타입을 사용할 수 있다. 엘리먼트의 이름 뒤에는 메소드를 작성하는 것처럼   **( )**   를 붙여야 한다.


예시

```java
public @interface AnnotationName {
    String elementName1();
    int elementName2() default 5;
}
```


위처럼 정의한 어노테이션을 코드에서 적용할 때에는 다음과 같이 쓴다.

```java
@AnnotationName(elementName1="값", elementName2=3);

또는

@AnnotationName(elementName1="값");

}
```


**elementName1** 은 디폴트 값이 없기 때문에 반드시 값을 기술해야 하고,

**elementName2** 는 디폴트 값이 있기 때문에 생략 가능하다. 어노테이션은 기본 엘리먼트 value를 가질 수 있다.

```java
public @interface AnnotationName {
	String value();
	int elementName() default 5;
}
```


Value 엘리먼트를 가진 어노테이션을 코드에서 적용할 때에는 다음과 같이 값만 기술할 수 있다.

이 값은 기본 엘리먼트인 value 값으로 자동 설절된다.

```java
@AnnotationName("값");
```


만약 value 엘리먼트가 다른 엘리먼트의 값을 동시에 주고 싶다면 다음과 같이 정상적인 방법으로 지정하면 된다.

```java
@AnnotationName(value="값", elementName=3);
```
