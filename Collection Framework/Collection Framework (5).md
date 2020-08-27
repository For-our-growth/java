# Collection Framework 

 

- ##### 책 '이것이 자바다'를 참고해서 작성한 문서입니다. 

 

 

 

### 검색 기능을 강화시킨 컬렉션 

컬렉션 프레임워크는 검색 기능을 강화시킨 TreeSet과 TreeMap을 제공하고 있다. TreeSet은 Set 컬렉션이고,  TreeMap은 Map 컬렉션이다. 이 컬렉션들은 이진 트리(binary tree)를 이용해 계층적 구조(Tree 구조)를 가지면서 객체를 저장한다. 



#### 이진 트리 구조 

이진 트리(binary tree)는 여러 개의 노드(node)가 트리 형태로 연결된 구조로, 루트 노드(root node)라고 불리는 하나의 노드에서부터 시작해서 각 노드에 최대 2개의 노드를 연결할 수 있는 구조를 가지고 있다. 

위아래로 연결된 두 노드를 부모-자식관계에 잇다고 하며 위의 노드를 부모 노드, 아래의 노드를 자식 노드라고 한다. 하나의 부모 노드는 최대 두 개의 자식 노드와 연결될 수 있다. 

이진 트리는 부모 노드의 값보다 작은 노드는 왼쪽에 위치시키고, 부모 노드의 값보다 큰 노드는 오른쪽에 위치시킨다. 



#### TreeSet 

TreeSet은 이진 트리(binary tree)를 기반으로한 Set 컬렉션이다. 하나의 노드는 노드값인 value와 왼쪽과 오른쪽 자식 노드를 참조하기 위한 두 개의 변수로 구성된다. TreeSet에 객체를 저장하면 자동으로 정렬되는데 부모값과 비교해서 낮은 것은 왼쪽 자식 노드에, 높은 것은 오른쪽 자식 노드에 저장한다. 

TreeSet을 생성하기 위해서는 저장할 객체 타입을 파라미터로 표기하고 기본 생성자를 호출하면 된다. 

```java
TreeSet<E> treeSet = new TreeSet<E>(); 
```

String 객체를 저장하는 TreeSet은 아래처럼 생성할 수 있다. 

```java
TreeSet<String> treeSet = new TreeSet<String>(); 
```

Set 인터페이스 타입 변수에 대입해도 되지만 TreeSet 클래스 타입으로 대입한 이유는 객체를 찾거나 범위 검색과 관련된 메소드를 사용하기 위해서이다. 아래는 TreeSet이 갖고 있는 검색 관련 메소드들이다. 

| 리턴 타입 | 메소드       | 설명                                                         | 

| --------- | ------------ | ------------------------------------------------------------ | 

| E         | first()      | 제일 낮은 객체를 리턴                                        | 

| E         | last()       | 제일 높은 객체를 리턴                                        | 

| E         | lower(E e)   | 주어진 객체보다 바로 아래 객체를 리턴                        | 

| E         | higher(E e)  | 주어진 객체보다 바로 위 객체를 리턴                          | 

| E         | floor(E e)   | 주어진 객체와 동등한 객체가 있으면 리턴, 만약 없다면 주어진 객체의 바로 아래의 객체를 리턴 | 

| E         | ceiling(E e) | 주어진 객체와 동등한 객체가 있으면 리턴, 만약 없다면 주어진 객체의 바로 위의 객체를 리턴 | 

| E         | pollFirst()  | 제일 낮은 객체를 꺼내오고 컬렉션에서 제거함                  | 

| E         | pollLast()   | 제일 높은 객체를 꺼내오고 컬렉션에서 제거함                  | 

아래는 TreeSet이 가지고 있는 정렬과 관련된 메소드들이다. 

| 리턴 타입         | 메소드               | 설명                                    | 

| ----------------- | -------------------- | --------------------------------------- | 

| Iterator< E >     | descendingIterator() | 내림차순으로 정렬된 Iterator를 리턴     | 

| NavigableSet< E > | descendingSet()      | 내림차순으로 정렬된 NavigableSet을 반환 | 

 

descendingIterator() 메소드는 내림차순으로 정렬된 Iterator 객체를 리턴하는데 Iterator는 이미 Set 컬렉션에서 사용 방법을 살펴보았다. descendingSet() 메소드는 내림차순으로 정렬된 NavigableSet 객체를 리턴하는데 NavigableSet은 TreeSet과 마찬가지로 first(), last(), lower(), higher(), floor(), ceiling() 메소드를 제공하고, 정렬 순서를 바꾸는 descendingSet() 메소드로 제공한다. 오름차순으로 정렬하고 싶다면 다음과 같이 descendingSet() 메소드를 두 번 호출하면 된다. 

```java
NavigableSet<E> descendingSet = treeSet.descendingSet(); 

NavigableSet<E> descendingSet = descendingSet.descendingSet(); 
```

아래는 TreeSet이 가지고 있는 범위 검색과 관련된 메소드들이다. 

| 리턴 타입         | 메소드                                                       | 설명                                                         | 

| ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | 

| NavigableSet< E > | headSet( E toElement, boolean inclusive )                    | 주어진 객체보다 낮은 객체들을 NavigableSet으로 리턴, 주어진 객체 포함 여부는 두 번째 매개값에 따라 달라짐 | 

| NavigableSet< E > | tailSet( E fromElement, boolean inclusive)                   | 주어진 객체보다 높은 객체들을 NavigableSet으로 리턴, 주어진 객체 포함 여부는 두 번째 매개값에 따라 달라짐 | 

| NavigableSet< E > | subSet( E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) | 시작과 끝으로 주어진 객체 사이의 객체들을 NavigableSet으로 리턴, 시작과 끝 객체의 포함 여부는 두 번째, 네 번째 매개값에 따라 달라짐 | 

 

subSet() 메소드의 사용 방법. 네 개의 매개 변수가 있다. 시작 객체와 끝 객체, 그리고 이 객체들을 포함할지 여부의 boolean 값을 받는다. 

```java
// 시작 객체 < 찾는 객체 < 끝 객체 

// 시작 객체 <= 찾는 객체 <= 끝 객체 

                  // 시작 객체    // 시작 객체의 포함 여부 // 끝 객체 

NavigableSet<E> set = treeSet.subSet( E fromElement, boolean fromInclusive, E toElement, boolean toInclusive ) 

// 끝 객체의 포함 여부 
```

 

#### TreeMap 

TreeMap은 이진 트리를 기반으로 한 Map 컬렉션이다. TreeSet과의 차이점은 키와 값이 저장된 Map.Entry를 저장한다는 점이다. TreeMap에 객체를 저장하면 자동으로 정렬되는데, 기본적으로 부모 키값과 비교해서 키 값이 낮은 것은 왼쪽 자식 노드에, 키 값이 높은 것은 오른쪽 자식 노드에 Map.Entry 객체를 저장한다. 

TreeMap을 생성하기 위해서는 키로 저장할 객체 타입과 값으로 저장할 객체 타입을 타입 파라미터로 주고 기본 생성자를 호출하면 된다. 

```java
TreeMap<K, V> treeMap = new TreeMap<K, V>(); 
```

키로 String 타입을 사용하고 값으로 Integer 타입을 사용하는 TreeMap은 다음과 같이 생성할 수 있다. 

```java
TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>(); 
```

Map 인터페이스 타입 변수에 대입해도 되지만 TreeMap 클래스 타입으로 대입한 이유는 특정 객체를 찾거나 범위 검색과 관련된 메소드를 사용하기 위해서다. 아래는 TreeMap이 가지고 있는 검색 관련 메소드들이다. 

| 리턴 타입       | 메소드              | 설명                                                         | 

| --------------- | ------------------- | ------------------------------------------------------------ | 

| Map.Entry<K, V> | firstEntry()        | 제일 낮은 Map.Entry를 리턴                                   | 

| Map.Entry<K, V> | lastEntry()         | 제일 높은 Map.Entry를 리턴                                   | 

| Map.Entry<K, V> | lowerEntry(K key)   | 주어진 키보다 바로 아래 Map.Entry를 리턴                     | 

| Map.Entry<K, V> | higherEntry(K key)  | 주어진 키보다 바로 위 Map.Entry를 리턴                       | 

| Map.Entry<K, V> | floorEntry(K key)   | 주어진 키와 동등한 키와 있으면 해당 Map.Entry를 리턴, 없다면 주어진 키 바로 아래의 Map.Entry를 리턴 | 

| Map.Entry<K, V> | ceilingEntry(K key) | 주어진 키와 동등한 키가 있으면 해당 Map.Entry를 리턴, 없다면 주어진 키 바로 위의 Map.Entry를 리턴 | 

| Map.Entry<K, V> | pollFirstEntry()    | 제일 낮은 Map.Entry를 꺼내오고 컬렉션에서 제거함             | 

| Map.Entry<K, V> | pollLastEntry()     | 제일 높은 Map.Entry를 꺼내오고 컬렉션에서 제거함             | 

 

아래는 TreeMap이 가지고 있는 정렬과 관련된 메소드들이다. 

| 리턴 타입          | 메소드             | 설명                                                | 

| ------------------ | ------------------ | --------------------------------------------------- | 

| NavigableSet< K >  | descendingKeySet() | 내림차순으로 정렬된 키의 NavigableSet을 리턴        | 

| NavigableMap<K, V> | descendingMap()    | 내림차순으로 정렬된 Map.Entry의 NavigableMap을 리턴 | 

 

NavigableMap() 메소드는 내림차순으로 정렬된 NavigableMap 객체를 리턴하는데 firstEntry(), lastEntry(), lowerEntry(), higherEntry(), floorEntry(), ceilingEntry() 메소드를 제공하고, 또한 오름차순과 내림차순을 번갈아가며 정렬 순서를 바꾸는 descendingMap() 메소드도 제공한다. 오름차순으로 정렬하고 싶다면 아래처럼 descendingMap() 메소드를 두 번 호출하면 된다. 

```java
NavigableMap<K, V> descendingMap = treeMap.descendingMap(); 

NavigableMap<K, V> ascendingMap = descendingMap.descendingMap(); 
```

아래는 TreeMap이 가지고 있는 범위 검색과 관련된 메소드들이다. 

| 리턴 타입          | 메소드                                                       | 설명                                                         | 

| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | 

| NavigableMap<K, V> | headSet( K toKey, boolean inclusive )                        | 주어진 키보다 낮은 Map.Entry들을 NavigableMap으로 리턴, 주어진 키의 Map.Entry 포함 여부는 두 번째 매개값에 따라 달라짐 | 

| NavigableMap<K, V> | tailMap( K fromKey, boolean inclusive )                      | 주어진 객체보다 높은 Map.Entry들을 NavigableMap으로 리턴, 주어진 객체 포함 여부는 두 번째 매개값에 따라 달라짐 | 

| NavigableMap<K, V> | subMap( K fromkey, boolean fromInclusive, K toKey, boolean toInclusive ) | 시작과 끝으로 주어진 키 사이의 Map.Entry들을 NavigableMap 컬렉션으로 반환, 시작과 끝 키의 Map.Entry 포함 여부는 두 번째, 네 번째 매개값에 따라 달라짐 | 

 

subMap() 메소드는 네 개의 매개 변수가 있다. 시작 키와 끝 키, 그리고 이들의 Map.Entry를 포함할지 여부의 boolean 값을 받는다. 

```java
// 시작 Map.Entry < 찾는 Map.Entry < 끝 Map.Entry 

// 시작 Map.Entry <= 찾는 Map.Entry <= 끝 Map.Entry 

                    // 시작 키 // 시작 Map.Entry의 포함 여부  // 끝 키 

NavigableMap<K, V> subMap = treeMap.subMap( K fromkey, boolean fromInclusive, K toKey, boolean toInclusive ); 

// 끝 Map.Entry의 포함 여부 
```