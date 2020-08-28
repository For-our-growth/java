# Stream

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



스트림(Stream)은 자바 8부터 추가된 컬렉션(배열 포함)의 저장 요소를 하나씩 참조해서 람다식(함수적-스타일(functional-style))으로 처리할 수 있도록 해주는 반복자이다.



#### 반복자 스트림

자바 7 이전까지는 List< String > 컬렉션에서 요소를 순차적으로 처리하기 위해 Iterator 반복자를 아래처럼 사용했다.

```java
List<String> list = Arrays.asList("JAVA1", "JAVA2", "JAVA3");
Iterator<String> iterator = list.iterator();
while(iterator.hasNext()) {
	String name = iterator.next();
	System.out.println(name);
}
```

이 코드를 Stream을 사용해 변경하면 아래와 같다.

```java
List<String> list = Arrays.asList("JAVA1", "JAVA2", "JAVA3");
Stream<String> stream = list.stream();
stream.forEach( name -> System.out.println(name) );
```

컬렉션(java.util.Collection)의 stream() 메소드로 스트림 객체를 얻고 나서 stream.forEach(name -> System.out.println(name)); 메소드를 통해 컬렉션의 요소를 하나씩 콘솔에 출력한다. forEach() 메소드는 아래와 같이 Consumer 함수적 인터페이스 타입의 매개값을 가지므로 컬렉션의 요소를 소비할 코드를 람다식으로 기술할 수 있다.

```java
void forEach(Consumer<T> action)
```



#### 스트림의 특징

stream은 Iterator와 비슷한 역할을 하는 반복자이지만, 람다식으로 요소 처리 코드를 제공하는 점과 내부 반복자를 사용하므로 병렬 처리가 쉽다는 점 그리고 중간 처리와 최종 처리 작업을 수행하는 점에서 많은 차이를 가지고 있다.



##### 람다식으로 요소 처리 코드를 제공한다.

스트림이 제공하는 대부분의 요소 처리 메소드는 함수적 인터페이스 매개 타입을 가지기 때문에 람다식 또는 메소드 참조를 이용해서 요소 처리 내용을 매개값으로 전달할 수 있다.

##### 내부 반복자를 사용하므로 병렬 처리가 쉽다.

외부 반복자(external iterator)란 개발자가 코드로 직접 컬렉션의 요소를 반복해서 가져오는 코드 패턴을 말한다. index를 이용하는 for문 그리고 Iterator를 이용하는 while문은 모두 외부 반복자를 이용하는 것이다. 반면에 내부 반복자(internal iterator)는 컬렉션 내부에서 요소들을 반복시키고, 개발자는 요소당 처리해야 할 코드만 제공하는 코드 패턴을 말한다.

내부 반복자를 사용해서 얻는 이점은 컬렉션 내부에서 어떻게 요소를 반복시킬 것인가는 컬렉션에게 맡겨두고, 개발자는 요소 처리 코드에만 집중할 수 있다는 것이다. 내부 반복자는 요소들의 반복 순서를 변경하거나, 멀티 코어 CPU를 최대한 활용하기 위해 요소들을 분배시켜 병렬 작접을 할 수 있게 도와주기 때문에 하나씩 처리하는 순차적 외부 반복자보다는 효율적으로 요소를 반복시킬 수 있다.

Iterator는 컬렉션의 요소를 가져오는 것에서부터 처리하는 것까지 모두 개발자가 작성해야 하지만, 스트림은 람다식으로 요소 처리 내용만 전달할 뿐, 반복은 컬렉션 내부에서 일어난다. 스트림을 이용하면 코드도 간결해지지만, 무엇보다도 요소의 병렬 처리가 컬렉션 내부에서 처리되므로 일석이조의 효과를 가져온다.

병렬(parallel) 처리란 한 가지 작업을 서브 작업으로 나누고, 서브 작업들을 분리된 스레드에서 병렬적으로 처리하는 것을 말한다. 병렬 처리 스트림을 이용하면 런타임 시 하나의 작업을 서브 작업으로 자동으로 나누고, 서브 작업의 결과를 자동으로 결합해서 최종 결과물을 생성한다.

##### 스트림은 중간 처리와 최종 처리를 할 수 있다.

스트림은 컬렉션의 요소에 대해 중간 처리와 최종 처리를 수행할 수 있는데, 중간 처리에서는 매핑, 필터링, 정렬을 수행하고 최종 처리에서는 반복, 카운팅, 평균, 총합 등의 집계 처리를 수행한다.



#### 스트림의 종류

자바 8부터 새로 추가된 java.util.stream 패키지에는 스트림 API들이 포진하고 있다. BaseStream 인터페이스를 부모로 해서 자식 인터페이스들이 상속 관계를 이루고 있다.

BaseStream 인터페이스에는 모든 스트림에서 사용할 수 있는 공통 메소드들이 정의되어 있을 뿐 코드에서 직접적으로 사용되지는 않는다. 하위 스트림인 Stream, IntStream, LongStream, DoubleStream이 직접적으로 이용되는 스트림인데, Stream은  객체 요소를 처리하는 스트림이고, IntStream, LongStream, DoubleStream은 각각 기본 타입인 요소를 처리하는 스트림이다. 이 스트림 인터페이스의 구현 객체는 다양한 소스로부터 얻을 수 있다.

| 리턴 타입                                           | 메소드(매개 변수)                                            | 소스      |
| --------------------------------------------------- | ------------------------------------------------------------ | --------- |
| Stream< T >                                         | java.util.Collection.Stream() / java.util.Collection.parallelStream() | 컬렉션    |
| Stream< T > / IntStream / LongStream / DoubleStream | Arrays.stream(T[]), Stream.of(T[]), Arrays.stream(int[]), IntStream.of(int[]), Arrays.stream(long[]), LongStream.of(long[]), Arrays.stream(double[]), DoubleStream.of(double[]) | 배열      |
| IntStream                                           | IntStream.range(int, int) / IntStream.rangeClosed(int, int)  | int 범위  |
| LongStream                                          | LongStream.range(long, long) / LongStream.rangeClosed(long, long) | long 범위 |
| Stream< Path >                                      | Files.find(Path, int, BiPredicate, FileVisitOption) / Files.list(Path) | 디렉토리  |
| Stream< String >                                    | Files.lines(Path, Charset) / BufferedReader.lines()          | 파일      |
| DoubleStream / IntStream / LongStream               | Random.doubles(...) / Random.ints() / Random.longs()         | 랜덤 수   |



#### 스트림 파이프라인

대량의 데이터를 가공해서 축소하는 것을 일반적으로 리덕션(Reduction)이라고 하는데, 데이터의 합계, 평균값, 카운팅, 최대값, 최소값 등이 대표적인 리덕션의 결과물이라고 볼 수 있다. 그러나 컬렉션의 요소를 리덕션의 결과물로 바로 집계할 수 없을 경우에는 집계하기 좋도록 필터링, 매핑, 정렬, 그룹핑 등의 중간 처리가 필요하다.

#### 중간 처리와 최종 처리

스트림은 데이터의 필터링, 매핑, 정렬, 그룹핑 등의 중간 처리와 함께, 평균, 카운팅, 최대값, 최소값 등의 최종 처리를 파이프라인(pipelines)으로 해결한다. 파이프라인은 여러 개의 스트림이 연결되어 있는 구조를 말한다. 파이프라인에서 최종 처리를 제외하고는 모두 중간 처리 스트림이다.

중간 스트림이 생성될 때 요소들이 바로 중간 처리(필터링, 매핑, 정렬)되는 것이 아니라 최종 처리가 시작되기 전까지 중간 처리는 지연(lazy)된다. 최종 처리가 시작되면 비로소 컬렉션의 요소가 하나씩 중간 스트림에서 처리되고 최종 처리까지 오게 된다.

Stream 인터페이스에는 필터링, 매핑, 정렬 등의 많은 중간 처리 메소드가 있는데, 이 메소드들은 중간 처리된 스트림을 리턴한다. 그리고 이 스트림에서 다시 중간 처리 메소드를 호출해서 파이프라인을 형성하게 된다.

#### 중간 처리 메소드와 최종 처리 메소드

스트림이 제공하는 중간 처리와 최종 처리를 하는 메소드들.

| 종류      | -      | 리턴 타입                                      | 메소드(매개 변수)    | 소속된 인터페이스                   |
| --------- | ------ | ---------------------------------------------- | -------------------- | ----------------------------------- |
| 중간처리  | 필터링 | Stream / IntStream / LongStream / DoubleStream | distinct()           | 공통                                |
| "         | "      | "                                              | filter(...)          | 공통                                |
| "         | 매핑   | "                                              | flatMap(...)         | 공통                                |
| "         | "      | "                                              | flatMapToDouble(...) | Stream                              |
| "         | "      | "                                              | flatMapToInt(...)    | Stream                              |
| "         | "      | "                                              | flatMapToLong(...)   | Stream                              |
| "         | "      | "                                              | map(...)             | 공통                                |
| "         | "      | "                                              | mapToDouble(...)     | Stream, IntStream, LongStream       |
| "         | "      | "                                              | mapToInt(...)        | Stream, LongStream, DoubleStream    |
| "         | "      | "                                              | mapToLong(...)       | Stream, IntStream, DoubleStream     |
| "         | "      | "                                              | mapToObj(...)        | IntStream, LongStream, DoubleStream |
| "         | "      | "                                              | asDoubleStream()     | IntStream, LongStream               |
| "         | "      | "                                              | asLongStream()       | IntStream                           |
| "         | "      | "                                              | boxed()              | IntStream, LongStream, DoubleStream |
| "         | 정렬   | "                                              | sorted(...)          | 공통                                |
| "         | 루핑   | "                                              | peek(...)            | 공통                                |
| 최종 처리 | 매칭   | boolean                                        | allMatch(...)        | 공통                                |
| "         | "      | boolean                                        | anyMatch(...)        | 공통                                |
| "         | "      | boolean                                        | noneMatch(...)       | 공통                                |
| "         | 집계   | long                                           | count()              | 공통                                |
| "         | "      | OptionalXXX                                    | findfirst()          | 공통                                |
| "         | "      | OptionalXXX                                    | max(...)             | 공통                                |
| "         | "      | OptionalXXX                                    | min(...)             | 공통                                |
| "         | "      | OptionalDouble                                 | average()            | IntStream, LongStream, DoubleStream |
| "         | "      | OptionalXXX                                    | reduce(...)          | 공통                                |
| "         | "      | int, long, double                              | sum()                | IntStream, LongStream, DoubleStream |
| "         | 루핑   | void                                           | forEach(...)         | 공통                                |
| "         | 수집   | R                                              | collect(...)         | 공통                                |

중간 처리 메소드와 최종 처리 메소드를 쉽게 구분하는 방법은 리턴 타입을 보면 된다.

리턴 타입이 스트림이라면 중간 처리 메소드이고, 기본 타입이거나 OptionalXXX라면 최종 처리 메소드이다. 소속된 인터페이스에서 공통의 의미는 Stream, IntSream, LongStream, DoubleStream에서 모두 제공된다는 뜻이다.