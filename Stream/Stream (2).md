# Stream

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



#### 필터링(distinct(), filter())

필터링은 중간 처리 기능으로 요소를 걸러내는 역할을 한다. 필터링 메소드인 distinct()와 filter() 메소드는 모든 스트림이 가지고 있는 공통 메소드이다.

| 리턴 타입                                      | 메소드(매개 변수)       | 설명        |
| ---------------------------------------------- | ----------------------- | ----------- |
| Stream / IntStream / LongStream / DoubleStream | distinct()              | 중복 제거   |
| "                                              | filter(Predicate)       | 조건 필터링 |
| "                                              | filter(IntPredicate)    | "           |
| "                                              | filter(LongPredicate)   | "           |
| "                                              | filter(DoublePredicate) | "           |

distinct() 메소드는 중복을 제거하는데, Stream의 경우 Object.equals (Object) 가 true이면 동일한 객체로 판단하고 중복을 제거한다. IntStream, LongStream, DoubleStream은 동일값일 경우 중복을 제거한다.

filter() 메소드는 매개값으로 주어진 Predicate가 true를 리턴하는 요소만 필터링한다.



#### 매핑(flatMapXXX(), mapXXX(), asXXXStream(), boxed())

매핑(mapping)은 중간 처리 기능으로 스트림의 요소를 다른 요소를 대체하는 작업을 말한다. 스트림에서 제공하는 매핑 메소드는 flatXXX()와 mapXXX(), 그리고 asDoubleStream(), asLongStream(), boxed()가 있다.



##### flatMapXXX() 메소드

flatMapXXX() 메소드는 요소를 대체하는 복수 개의 요소들로 구성된 새로운 스트림을 리턴한다.

flatMapXXX() 메소드의 종류

| 리턴 타입    | 메소드(매개 변수)                          | 요소 -> 대체 요소      |
| ------------ | ------------------------------------------ | ---------------------- |
| Stream< R >  | flatMap(Function<T, Stream< R >>)          | T -> Stream< R >       |
| DoubleStream | flatMap(DoubleFunction< DoubleStream >)    | double -> DoubleStream |
| IntStream    | flatMap(IntFunction< IntStream >)          | int -> IntStream       |
| LongStream   | flatMap(LongFunction< LongStream >)        | long -> LongStream     |
| DoubleStream | flatMapToDouble(Function<T, DoubleStream>) | T -> DoubleStream      |
| IntStream    | flatMapToInt(Function<T, IntStream>)       | T -> IntStream         |
| LongStream   | flatMapToLong(Function<T, LongStream>)     | T -> LongStream        |



##### mapXXX() 메소드

mapXXX() 메소드는 요소를 대체하는 요소로 구성된 새로운 스트림을 리턴한다.

mapXXX() 메소드의 종류

| 리턴 타입    | 메소드(매개 변수)                  | 요소 -> 대체 요소 |
| ------------ | ---------------------------------- | ----------------- |
| Stream< R >  | map(Function<T, R>)                | T -> R            |
| DoubleStream | mapToDouble(ToDoubleFunction< T >) | T -> double       |
| IntStream    | mapToInt(ToIntFuction< T >)        | T -> int          |
| LongStream   | mapToLong(ToLongFunction< T >)     | T -> long         |
| DoubleStream | map(DoubleUnaryOperator)           | double -> double  |
| IntStream    | mapToInt(DoubleToIntFunction)      | double -> int     |
| LongStream   | mapToLong(DoubleToLongFunction)    | double -> long    |
| Stream< U >  | mapToObj(DoubleFunction< U >)      | double -> U       |
| IntStream    | map(IntUnaryOperator)              | int -> int        |
| DoubleStream | mapToDouble(IntToDoubleFunction)   | int ->double      |
| LongStream   | mapToLong(IntToLongFunction)       | int -> long       |
| Stream< U >  | mapToObj(IntFunction< U >)         | int -> U          |
| LongStream   | map(LongUnaryOperator)             | long -> long      |
| DoubleStream | mapToDouble(LongToDoubleFunction)  | long -> double    |
| IntStream    | mapToInt(LongToIntFunction)        | long -> int       |
| Stream< U >  | mapToObj(LongFunction< U >)        | long -> U         |



##### asDoubleStream(), asLongStream(), boxed() 메소드

asDoubleStream() 메소드는 IntStream의 int 요소 또는 LongStream의 long 요소를 double 요소로 타입 변환해서 DoubleStream을 생성한다. 마찬가지로 asLongStream() 메소드는 IntStream의 int 요소를 long 요소로 타입 변환해서 LongStream을 생성한다. boxed() 메소드는 int, long, double 요소를 Integer, Long, Double 요소로 박싱해서 Stream을 생성한다.

| 리턴 타입                                               | 메소드(매개 변수) | 설명                                             |
| ------------------------------------------------------- | ----------------- | ------------------------------------------------ |
| DoubleStream                                            | asDoubleStream()  | int -> double / long -> double                   |
| LongStream                                              | asLongStream()    | int -> long                                      |
| Stream< Integer > / Stream < Long > / Stream < Double > | boxed()           | int -> Integer / long -> Long / double -> Double |



#### 정렬(sorted)

스트림은 요소가 최종 처리되기 전에 중간 단계에서 요소를 정렬해서 최종 처리 순서를 변경할 수 있다.

요소를 정렬하는 메소드

| 리턴 타입    | 메소드(매개 변수)       | 설명                                    |
| ------------ | ----------------------- | --------------------------------------- |
| Stream< T >  | sorted()                | 객체를 Comparable 구현 방법에 따라 정렬 |
| Stream< T >  | sorted(Comparator< T >) | 객체를 주어진 Comparator에 따라 정렬    |
| DoubleStream | sorted()                | double 요소를 오름차순으로 정렬         |
| IntStream    | sorted()                | int 요소를 오름차순으로 정렬            |
| LongStream   | sorted()                | long 요소를 오름차순으로 정렬           |

객체 요소일 경우에는 클래스가 Comparable을 구현하지 않으면 sorted() 메소드를 호출했을 때 ClassCastException이 발생하기 때문에 Comparable을 구현한 요소에서만 sorted() 메소드를 호출해야 한다.



객체 요소가 Comparable을 구현한 상태에서 기본 비교(Comparable) 방법으로 정렬하고 싶다면 아래 세 방법 중 하나를 선택해서 sorted()를 호출하면 된다.

```java
sorted();
sorted( (a, b) -> a.compareTo(b) );
sorted( Comparator.naturalOrder() );
```

만약 객체 요소가 Comparable을 구현하고 있지만, 기본 비교 방법과 정반대 방법으로 정렬하고 싶다면 아래와 같이 sorted()를 호출하면 된다.

```java
sorted( (a, b) -> b.compareTo(a) );
sorted( Comparator.reverseOrder() );
```

객체 요소가 Comparable를 구현하지 않았다면 Comparator를 매개값으로 갖는 sorted() 메소드를 사용하면 된다. Comparator는 함수적 인터페이스이므로 아래와 같이 람다식으로 매개값을 작성할 수 있다.

```java
sorted( (a, b) -> { ... } )
```

중괄호 { } 안에는 a와 b를 비교해서 a가 작으면 음수, 같으면 0, a가 크면 양수를 리턴하는 코드를 작성하면 된다.