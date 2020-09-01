# Stream

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



#### 수집(collect())

스트림은 요소들을 필터링 또는 매핑한 후 요소들을 수집하는 최종 처리 메소드인 collect()를 제공하고 있다. 이 메소드를 이용하면 필요한 요소만 컬렉션으로 담을 수 있고, 요소들을 그룹핑한 후 집계(리덕션)할 수 있다.

##### 필터링한 요소 수집

Stream의 collect(Collection<T,A,R> collector) 메소드는 필터링 또는 매핑된 요소들을 새로운 컬렉션에 수집하고, 이 컬렉션을 리턴한다.

| 리턴 타입 | 메소드(매개 변수)                   | 인터페이스 |
| --------- | ----------------------------------- | ---------- |
| R         | collect(Collector<T,A,R> collector) | Stream     |

매개값인 Collector(수집기)는 어떤 요소를 어떤 컬렉션에 수집할 것인지를 결정한다. Collector의 타입 파라미터 T는 요소이고, A는 누적기(accumulator)이다. 그리고 R은 요소가 저장될 컬렉션이다. 풀어서 해석하면 T 요소를 A 누적기가 R에 저장한다는 의미이다. Collector의 구현 객체는 다음과 같이 Collectors 클래스의 다양한 정적 메소드를 이용해서 얻을 수 있다.

| 리턴 타입                            | Collectors의 정적 메소드                                     | 설명                                                         |
| ------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Collector<T, ?, List< T >>           | toList()                                                     | T를 List에 저장                                              |
| Collector<T, ?, Set< T >>            | toSet()                                                      | T를 Set에 저장                                               |
| Collector<T, ?, Collection< T >>     | toCollection( Supplier<Collection< T >> )                    | T를 Supplier가 제공한 Collection에 저장                      |
| Collector<T, ?, Map<K, U>>           | toMap( Function<T,K> keyMapper, Function<T,U> valueMapper)   | T를 K와 U로 매핑해서 K를 키로, U를 값으로 Map에 저장         |
| Collector<T, ?, ConcurrentMap<K, U>> | toConcurrentMap( Function<T,K> keyMapper, Function<T,U> valueMapper) | T를 K와 U로 매핑해서 K를 키로, U를 값으로 ConcurrentMap에 저장 |



##### 사용자 정의 컨테이너에 수집하기

스트림은 요소들을 필터링, 또는 매핑해서 사용자 정의 컨테이너 객체에 수집할 수 있도록 다음과 같이 collect() 메소드를 추가적으로 제공한다.

| 인터페이스   | 리턴 타입 | 메소드(매개 변수)                                            |
| ------------ | --------- | ------------------------------------------------------------ |
| Stream       | R         | collect(Supplier< R >, BiConsumer<R, ? super T>, BiConsumer<R,R>) |
| IntStream    | R         | collect(Supplier< R >, ObjIntConsumer< R >, BiConsumer<R,R>) |
| LongStream   | R         | collect(Supplier< R >, ObjLongConsumer< R >, BiConsumer<R,R>) |
| DoubleStream | R         | collect(Supplier< R >, ObjDoubleConsumer< R >, BiConsumer<R,R>) |

- 첫 번째 Supplier는 요소들이 수집될 컨테이너 객체(R)를 생성하는 역할을 한다. 순차 처리(싱글 스레드) 스트림에서는 단 한 번 Supplier가 실행되고 하나의 컨테이너 객체를 생성한다. 병렬 처리(멀티 스레드) 스트림에서는 여러 번 Supplier가 실행되고 스레드별로 여러 개의 컨테이너 객체를 생성한다. 하지만 최종적으로 하나의 컨테이너 객체로 결합된다.
- 두 번째 XXXConsumer는 컨테이너 객체(R)에 요소(T)를 수집하는 역할을 한다. 스트림에서 요소를 컨테이너에 수집할 때마다 XXXConsumer가 실행된다.
- 세 번째 BiConsumer는 컨테이너 객체(R)를 결합하는 역할을 한다. 순차 처리 스트림에서는 호출되지 않고, 병렬 처리 스트림에서만 호출되어 스레드별로 생성된 컨테이너 객체를 결합해서 최종 컨테이너 객체를 완성한다.

리턴 타입 R은 요소들이 최종 수집된 컨테이너 객체이다. 순차 처리 스트림에서는 리턴 객체가 첫 번째 Supplier가 생성한 객체지만, 병렬 처리 스트림에서는 최종 결합된 컨테이너 객체가 된다.

##### 요소를 그룹핑해서 수집

collect() 메소드는 단순히 요소를 수집하는 기능 이외에 컬렉션의 요소들을 그룹핑해서 Map 객체를 생성하는 기능도 제공한다. collect()를 호출할 때 Collectors의 groupingBy() 또는 groupingByConcurrent()가 리턴하는 Collector를 매개값으로 대입하면 된다. groupingBy()는 스레드에 안전하지 않은 Map을 생성하지만, groupingByConcurrent()는 스레드에 안전한 ConcurrenMap을 생성한다.

| 리턴 타입                                   | Collectors의 정적 메소드                                     | 설명                                                         |
| ------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Collector<T, ?, Map<K,List< T >>>           | groupingBy(Function<T,K> classifier)                         | T를 K로 매핑하고 K키에 저장된 List에 T를 저장한 Map 생성     |
| Collector<T, ?, ConcurrentMap<K,List< T >>> | groupingByConcurrent( Function<T,K> classifier )             | T를 K로 매핑하고 K키에 저장된 List에 T를 저장한 Map 생성     |
| Collector<T, ?, Map<K,D>>                   | groupingBy( Function<T,K> classifier, Collector<T,A,D> collector ) | T를 K로 매핑하고 K키에 저장된 D객체에 T를 누적한 Map 생성    |
| Collector<T, ?, ConcurrentMap<K,D>>         | groupingByConcurrent( Function<T,K> classifier, Collector<T,A,D> collector ) | T를 K로 매핑하고 K키에 저장된 D객체에 T를 누적한 Map 생성    |
| Collector<T, ?, Map<K,D>>                   | groupingBy( Function<T,K> classifier, Supplier<Map<K,D>> mapFactory, Collector<T,A,D> collector ) | T를 K로 매핑하고 Supplier가 제공하는 Map에서 K키에 저장된 D객체에 T를 누적 |
| Collector<T, ?, ConcurrentMap<K,D>>         | groupingByConcurrent( Function<T,K> classifier, Supplier<ConcurrentMap<K,D>> mapFactory, Collector<T,A,D> collector ) | T를 K로 매핑하고 Supplier가 제공하는 Map에서 K키에 저장된 D객체에 T를 누적 |

##### 그룹핑 후 매핑 및 집계

Collectors.groupingBy() 메소드는 그룹핑 후, 매핑이나 집계(평균, 카이팅, 연결, 최대, 최소, 합계)를 할 수 있도록 두 번째 매개값으로 Collector를 가질 수 있다. 이전 예제에서 그룹핑된 학생 객체를 학생 이름으로 매핑하기 위해 mapping() 메소드로 Collector를 얻었다. Collectors는 mapping() 메소드 이외에도 집계를 위해 다양한 Collector를 리턴하는 아래와 같은 메소드를 제공하고 있다.

| 리턴 타입                        | 메소드(매개 변수)                                            | 설명                                                    |
| -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------- |
| Collector<T,?,R>                 | mapping( Function<T,U> mapper, Collector<U,A,R> collector )  | T를 U로 매핑한 후, U를 R에 수집                         |
| Collector<T,?,Double>            | averagingDouble( ToDoubleFunction< T > mapper )              | T를 Double로 매핑한 후, Double의 평균값을 산출          |
| Collector<T,?,Long>              | counting()                                                   | T의 카운팅 수를 산출                                    |
| Collector<CharSequence,?,String> | joining(CharSequence delimiter )                             | CharSequence를 구분자(delimiter)로 연결한 String을 산출 |
| Collector<T,?,Optional< T >>     | maxBy( Comparator< T > comparator )                          | Comparator를 이용해서 최대 T를 산출                     |
| Collector<T,?,Optional< T >>     | minBy( Comparator< T > comparator )                          | Comparator를 이용해서 최소 T를 산출                     |
| Collector<T,?,Integer>           | summingInt(ToIntFunction) / summingLong(ToLongFunction) / summingDouble(ToDoubleFunction) | Int, Long, Double 타입의 합계 산출                      |