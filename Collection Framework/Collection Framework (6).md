# Collection Framework 

 

- ##### 책 '이것이 자바다'를 참고해서 작성한 문서입니다. 

 

 

 

#### Comparable과 Comparator 

TreeSet의 객체와 TreeMap의 키는 저장과 동시에 자동 오름차순으로 정렬되는데, 숫자(Integer, Double) 타입일 경우에는 값으로 정렬하고, 문자열(String) 타입일 경우에는 유니코드로 정렬한다. TreeSet과 TreeMap은 정렬을 위해 java.lang.Comparable을 구현한 객체를 요구하는데, Integer, Double, String은 모두 Comparable 인터페이스를 구현하고 있다. 사용자 정의 클래스도 Comparable을 구현한다면 자동 정렬이 가능하다. Comparable에는 compareTo() 메소드가 정의되어 있기 때문에 사용자 정의 클래스에서는 이 메소드를 오버라이딩해서 아래와 같이 리턴값을 만들어 내야 한다. 

| 리턴 타입 | 메소드         | 설명                                                         | 

| --------- | -------------- | ------------------------------------------------------------ | 

| int       | compareTo(T o) | 주어진 객체와 같으면 0을 리턴. 주어진 객체보다 적으면 음수를 리턴. 주어진 객체보다 크면 양수를 리턴 | 

 

TreeSet의 객체와 TreeMap의 키가 Comparable을 구현하고 있지 않을 경우에는 저장하는 순간 ClassCastException이 발생한다. TreeSet 또는 TreeMap 생성자의 매개값으로 정렬자(Comparator)를 제공하면 Comparable 비구현 객체도 정렬시킬 수 있다. 

```java
TreeSet<E> treeSet = new TreeSet<E>( new AscendingComparator() ); 

                  // 오름차순 또는 내림차순 정렬자 

TreeMap<K, V> treeMap = new TreeMap<K, V>( new DescendingComparator() ); 
```

정렬자는 Comparator 인터페이스를 구현한 객체를 말하는데, Comparator 인터페이스에는 아래처럼 메소드가 정의되어 있다. 

| 리턴 타입 | 메소드              | 설명                                                         | 

| --------- | ------------------- | ------------------------------------------------------------ | 

| int       | compare(T o1, T o2) | o1과 o2가 동등하다면 0을 리턴. o1이 o2보다 앞에 오게 하려면 음수를 리턴. o1이 o2보다 뒤에 오게 하려면 양수를 리턴 | 



### LIFO와 FIFO 컬렉션 

후입선출(LIFO: Last In First Out)은 나중에 넣은 객체가 먼저 빠져나가는 자료구조를 말한다. 반대로 선입선출(FIFO: First In First Out)은 먼저 넣은 객체가 먼저 빠져나가는 구조를 말한다. 

컬렉션 프레임워크에는 LIFO 자료구조를 제공하는 스택(Stack) 클래스와 FIFO 자료구조를 제공하는 큐(Queue) 인터페이스를 제공하고 있다. 

스택을 응용한 대표적인 예가 JVM 스택 메모리이다. 스택 메모리에 저장된 변수는 나중에 저장된 것부터 제거된다.   큐를 응용한 대표적인 예가 스레드풀의 작업 큐이다. 작업큐는 먼저 들어온 작업부터 처리한다. 



#### Stack 

Stack 클래스는 LIFO 자료구조를 구현한 클래스다. 

아래는 Stack 클래스의 주요 메소드들이다. 

| 리턴 타입 | 메소드       | 설명                                                         | 

| --------- | ------------ | ------------------------------------------------------------ | 

| E         | push(E item) | 주어진 객체를 스택에 넣는다.                                 | 

| E         | peek()       | 스택의 맨 위 객체를 가져온다. 객체를 스택에서 제거하지 않는다. | 

| E         | pop()        | 스택의 맨 위 객체를 가져온다. 객체를 스택에서 제거한다.      | 

 

Stack 객체를 생성하기 위해서는 저장할 객체 타입을 파라미터로 표기하고 기본 생성자를 호출하면 된다. 

```java
Stack<E> stack = new Stack<E>();
```

 

####  Queue 

Queue 인터페이스는 FIFO 자료구조에서 사용되는 메소드를 정의하고 있다. 

아래는 Queue 인터페이스에 정의되어 있는 메소드들이다. 

| 리턴 타입 | 메소드     | 설명                                                 | 

| --------- | ---------- | ---------------------------------------------------- | 

| boolean   | offer(E e) | 주어진 객체를 넣는다.                                | 

| E         | peek()     | 객체 하나를 가져온다. 객체를 큐에서 제거하지 않는다. | 

| E         | poll()     | 객체 하나를 가져온다. 객체를 큐에서 제거한다.        | 

 

Queue 인터페이스를 구현한 대표적인 클래스는 LinkedList이다. LinkedList는 List 인터페이스를 구현했기 때문에  List 컬렉션이기도 하다.  아래 코드는 LinkedList 객체를 Queue 인터페이스 타입으로 변환한 것이다. 

```java
Queue<E> queue = new LinkedList<E>(); 
```



#### 동기화된 컬렉션 

컬렉션 프레임워크의 대부분의 클래스들은 싱글 스레드 환경에서 사용할 수 있도록 설계되었다. 그렇기에 여러 스레드가 동시에 컬렉션에 접근한다면 의도하지 않게 요소가 변경될 수 있는 불안전한 상태가 된다. Vector와 Hashtable은 동기화된(synchronized) 메소드로 구성되어 있기 때문에 멀티 스레드 환경에서 안전하게 요소를 처리할 수 있지만, ArrayList와 HashSet, HashMap은 동기화된 메소드로 구성되어 있지 않아 멀티 스레드 환경에서 안전하지 않다. 

경우에 따라 ArrayList, HashSet, HashMap을 싱글 스레드 환경에서 사용하다 멀티 스레드 환경으로 전달할 필요도 있을 것이다. 이런 경우를 대비해 컬렉션 프레임워크는 비동기화된 메소드를 동기화된 메소드로 래핑하는 Collections의 synchronizedXXX() 메소드를 제공하고 있다. 매개값으로 비동기화된 컬렉션을 대입하면 동기화된 컬렉션을 리턴한다. 

| 리턴 타입 | 메소드(매개 변수)                | 설명                        | 

| --------- | -------------------------------- | --------------------------- | 

| List< T > | synchronizedList(List< T > list) | List를 동기화된 List로 리턴 | 

| Map<K, V> | synchronizedMap(Map<K, V> m)     | Map을 동기화된 Map으로 리턴 | 

| Set< T >  | synchronizedSet(Set< T > s)      | Set을 동기화된 Set으로 리턴 | 



#### 병렬 처리를 위한 컬렉션 

자바는 멀티 스레드가 컬렉션 요소를 병렬적으로 처리할 수 있도록 특별한 컬렉션을 제공한다. 

java.util.concurrent 패키지의 ConcurrentHashMap과 ConcurrentLinkedQueue이다. ConcurrentHashMap은 Map 구현 클래스이고, ConcurrentLinkedQueue는 Queue 구현 클래스이다. 

ConcurrentHashMap를 사용하면 스레드에 안전하면서도 멀티 스레드가 요소를 병렬적으로 처리할 수 있다. 이것이 가능한 이유는 ConcurrentHashMap은 부분(segment) 잠금을 사용하기 때문이다. 컬렉션에 10개의 요소가 저장되어 있을 경우, 1개를 처리할 동안 전체 10개의 요소를 다른 스레드가 처리하지 못하도록 하는 것이 전체 잠금이라면, 처리하는 요소가 포함된 부분만 잠금하고 나머지 부분은 다른 스레드가 변경할 수 있도록 하는 것이 부분 잠금이다. 아래는 ConcurrentHashMap 객체를 생성하는 코드다. 

```java
Map<K, V> map = new ConcurrentHashMap<K, V>(); 
```

사용하는 법은 다른 Map 구현 객체와 마찬가지로 Map 인터페이스의 메소드를 호출하면 된다. 

ConcurrentLinkedQueue는 락-프리(lock-free) 알고리즘을 구현한 컬렉션이다. 락-프리 알고리즘은 여러 개의 스레드가 동시에 접근할 경우, 잠금을 사용하지 않고도 최소한 하나의 스레드가 안전하게 요소를 저장하거나 얻도록 해준다. 아래는 ConcurrentLinkedQueue를 생성하는 코드다. 

```java
Queue<E> queue = new ConcurrentLinkedQueue<E>(); 
```

사용하는 법은 다른 Queue 구현 객체와 마찬가지로 Queue 인터페이스의 메소드를 호출하면 된다.