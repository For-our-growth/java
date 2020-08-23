# Generic

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



#### 제네릭

제네릭은 컬렉션, 람다식, 스트림, NIO에서 널리 사용된다.

제네릭은 클래스와 인터페이스, 그리고 메소드를 정의할 때 타입(type)을 파라미터(parameter)로 사용할 수 있도록 한다. 타입 파라미터는 코드 작성 시 구체적인 타입으로 대체되어 다양한 코드를 생성하도록 해준다.

##### 컴파일 시 강한 타입 체크 가능

자바 컴파일러는 코드에서 **잘못 사용된 타입** 때문에 발생하는 문제점을 제거하기 위해 제네릭 코드에 대해 **강한 타입 체크**를 한다. 실행 시 타입 에러가 나는 것보다는 컴파일 시에 미리 타입을 강하게 체크해서 에러를 사전에 방지하는 것이 좋다.

##### 타입 변환(casting) 제거

비제네릭 코드는 불필요한 타입 변환을 하기 때문에 프로그램 성능에 악영향을 미친다. 아래 코드를 보면 List에 문자열 요소를 저장했지만, 요소를 찾아올 때는 반드시 String으로 타입 변환을 해야 한다.

```java
List list = new ArrayList();
list.add("hello");
String str = (String) list.get(0);	// 타입 변환을 해야 한다.
```

위 코드를 제네릭으로 수정하면 List에 저장되는 요소를 String 타입으로 국한하기 때문에 요소를 찾아올 때 타입 변환을 할 필요가 없어 프로그램 성능이 향상된다.

```java
List<String> list = new ArrayList<String>();
list.add("hello");
String str = list.get(0);	// 타입 변환을 하지 않는다.
```



#### 제네릭 타입 (class< T >, interface< T >)

제네릭 타입은 타입을 파라미터로 가지는 클래스와 인터페이스를 말한다. 제네릭 타입은 클래스 또는 인터페이스 이름 뒤에 "< >" 부호가 붙고, 사이에 타입 파라미터가 위치한다. 아래코드에서 타입 파라미터의 이름은 T이다.

```java
public class 클래스명<T> { ... }
public interface 인터페이스명<T> { ... }
```

타입 파라미터는 변수명과 동일한 규칙에 따라 작성할 수 있지만, 일반적으로 대문자 알파벳 한 글자로 표현한다. 제네릭 타입을 실제 코드에서 사용하려면 타입 파라미터에 구체적인 타입을 지정해야 한다.



#### 멀티 타입 파라미터 (class<K,V,...>, interface<K,V,...>)

제네릭 타입은 두 개 이상의 멀티 타입 파라미터를 사용할 수 있다. 이 경우 각 타입 파라미터를 콤마로 구분한다.

제네릭 타입 변수 선언과 객체 생성을 동시에 할 때 타입 파라미터 자리에 구체적인 타입을 지정하는 코드가 중복해서 나오기 때문에 복잡해질 수 있다. 자바 7부터 제네릭 타입 파라미터의 중복 기술을 줄이기 위해 다이아몬드 연산자 <>를 제공한다. 자바 컴파일러는 타입 파라미터 부분에 <> 연산자를 사용하면 타입 파라미터를 유추해서 자동으로 설정해준다.

```java
// 자바 6 이전 버전
Product<Tv, String> product = new Product<Tv, String>();
// 자바 7부터
Product<Tv, String> product = new Product<>();
```