# Stream

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



#### 루핑(peek(), forEach())

루핑(looping)은 요소 전체를 반복하는 것을 말한다. 루핑하는 메소드에는 peek(), forEach()가 있다. 이 두 메소드는 루핑한다는 기능에서는 동일하지만, 동작 방식은 다르다. peek()는 중간 처리 메소드이고, forEach()는 최종 처리 메소드다.

peek()는 중간 처리 단계에서 전체 요소를 루핑하면서 추가적인 작업을 하기 위해 사용한다. 최종 처리 메소드가 실행되지 않으면 지연되기 때문에 반드시 최종 처리 메소드가 호출되어야 동작한다.

예를 들어 필터링 후 어떤 요소만 남았는지 확인하기 위해 아래처럼 peek()를 마지막에서 호출할 경우, 스트림은 전혀 동작하지 않는다.

```java
intStream
	.filter( a -> a%2 == 0 )
	.peek( a -> System.out.println(a) )
```

요소 처리의 최종 단계가 합을 구하는 것이라면, peek() 메소드 호출 후 sum()을 호출해야만 peek()가 정상적으로 동작한다.

```java
intStream
	.filter( a -> a%2 == 0 )
	.peek( a -> System.out.println(a) )
	.sum()
```

하지만 forEach()는 최종 처리 메소드이기 때문에 파이프라인 마지막에 루핑하면서 요소를 하나씩 처리한다. forEach()는 요소를 소비하는 최종 처리 메소드이므로 이후에 sum()과 같은 다른 최종 메소드를 호출하면 안 된다.



#### 매칭(allMatch(), anyMatch(), noneMatch())

스트림 클래스는 최종 처리 단계에서 요소들이 특정 조건에 만족하는지 조사할 수 있도록 세 가지 매칭 메소드를 제공하고 있다. allMatch() 메소드는 모든 요소들이 매개값으로 주어진 Predicate의 조건을 만족하는지 조사하고, anyMatch() 메소드는 최소한 한 개의 요소가 매개값으로 주어진 Predicate의 조건을 만족하지 않는지 조사한다.

| 리턴 타입 | 메소드(매개 변수)                                            | 제공 인터페이스 |
| --------- | ------------------------------------------------------------ | --------------- |
| boolean   | allMatch(Predicate< T > predicate) / anyMatch(Predicate< T > predicate) / noneMatch(Predicate< T > predicate) | Stream          |
| boolean   | allMatch(IntPredicate predicate) / anyMatch(IntPredicate predicate) / noneMatch(IntPredicate predicate) | IntStream       |
| boolean   | allMatch(LongPredicate predicate) / anyMatch(LongPredicate predicate) / noneMatch(LongPredicate predicate) | LongStream      |
| boolean   | allMatch(DoublePredicate predicate) / anyMatch(DoublePredicate predicate) / noneMatch(DoublePredicate predicate) | DoubleStream    |



#### 기본 집계(sum(), count(), average(), max(), min())

집계(Aggregate)는 최종 처리 기능으로 요소들을 처리해서 카운팅, 합계, 평균값, 최대값, 최소값 등과 같이 하나의 값으로 산출하는 것을 말한다. 집계는 대량의 데이터를 가공해서 축소하는 리덕션(Reduction)이라고 볼 수 있다.

##### 스트림이 제공하는 기본 집계

스트림은 아래와 같은 기본 집계 메소드를 제공하고 있다.

| 리턴 타입                   | 메소드(매개 변수)            | 설명         |
| --------------------------- | ---------------------------- | ------------ |
| long                        | count()                      | 요소 개수    |
| OptionalXXX                 | findFirst()                  | 첫 번째 요소 |
| Optional< T > / OptionalXXX | max(Comparator< T >) / max() | 최대 요소    |
| Optional< T > / OptionalXXX | min(Comparator< T >) / min() | 최소 요소    |
| OptionalDouble              | average()                    | 요소 평균    |
| int, long, double           | sum()                        | 요소 총합    |

이 집계 메소드에서 리턴하는 OptionalXXX는 자바 8에서 추가된 java.util 패키지의 Optional, OptionalDouble, OptionalInt, OptionalLong 클래스 타입을 말한다. 이들은 값을 저장하는 값 기반 클래스(value-based class)들이다. 이 객체에서 값을 얻기 위해서는 get(), getAsDouble(), getAsInt(), getAsLong()을 호출하면 된다.

##### Optional 클래스

Optional, OptionalDouble, OptionalInt, OptionalLong 클래스는 저장하는 값이 타입만 다를 뿐 제공하는 기능은 거의 동일하다. Optional 클래스는 단순히 집계 값만 저장하는 것이 아니라, 집계 값이 존재하지 않을 경우 디폴트 값을 설정할 수도 있고, 집계 값을 처리하는 Consumer도 등록할 수 있다. 다음은 Optional 클래스들이 제공하는 메소드들이다.

| 리턴 타입               | 메소드(매개 변수)                                            | 설명                                        |
| ----------------------- | ------------------------------------------------------------ | ------------------------------------------- |
| boolean                 | isPresent()                                                  | 값이 저장되어 있는지 여부                   |
| T / double / int / long | orElse(T) / orElse(double) / orElse(int) / orElse(long)      | 값이 저장되어 있지 않을 경우 디폴트 값 지정 |
| void                    | ifPresent(Consumer) / ifPresent(DoubleConsumer) / ifPresent(IntConsumer) / ifPresent(LongConsumer) | 값이 지정되어 있을 경우 Consumer에서 처리   |



#### 커스텀 집계(reduce())

스트림은 기본 집계 메소드인 sum(), average(), count(), max(), min()을 제공하지만, 프로그램화해서 다양한 집계 결과물을 만들 수 있도록 reduce() 메소드도 제공한다.

| 인터페이스   | 리턴 타입     | 메소드(매개 변수)                                   |
| ------------ | ------------- | --------------------------------------------------- |
| Stream       | Optional< T > | reduce(BinaryOperator< T > accumulator)             |
| Stream       | T             | reduce(T identity, BinaryOperator< T > accumulator) |
| IntStream    | OptionalInt   | reduce(IntBinaryOperator op)                        |
| IntStream    | int           | reduce(int identity, IntBinaryOperator op)          |
| LongStream   | OptionalLong  | reduce(LongBinaryOperator op)                       |
| LongStream   | long          | reduce(long identity, LongBinaryOperator op)        |
| DoubleStream | OptionDouble  | reduce(DoubleBinaryOperator op)                     |
| DoubleStream | double        | reduce(double identity, DoubleBinaryOperator op)    |

각 인터페이스에는 매개 타입으로 XXXOperator, 리턴 타입으로 OptionalXXX, int, long, double을 가지는 reduce() 메소드가 오버로딩되어 있다. 스트림에 요소가 전혀 없을 경우 디폴트 값인 identity 매개값이 리턴된다. XXXOperator 매개값은 집계 처리를 위한 람다식을 대입한다.