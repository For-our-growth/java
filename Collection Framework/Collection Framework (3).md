# Collection Framework 

 

- ##### 책 '이것이 자바다'를 참고해서 작성한 문서입니다. 

 

 

### Set 컬렉션 

List 컬렉션은 저장 순서를 유지하지만, Set 컬렉션은 저장 순서가 유지되지 않는다. 또한 객체를 중복해서 저장할 수 없고, 하나의 null만 저장할 수 있다. Set 컬렉션에는 HashSet, LinkedHashSet, TreeSet 등이 있다. 

아래는 Set 컬렉션에서 공통적으로 사용 가능한 Set 인터페이스의 메소드들이다. 인덱스로 관리하지 않기 때문에 인덱스를 매개값으로 갖는 메소드가 없다. 

| 기능      | 메소드                     | 설명                                                         | 

| --------- | -------------------------- | ------------------------------------------------------------ | 

| 객체 추가 | boolean add(E e)           | 주어진 객체를 저장, 객체가 성공적으로 저장되면 true를 리턴하고 중복 객체면 false를 리턴 | 

| 객체 검색 | boolean contains(Object o) | 주어진 객체가 저장되어 있는지 여부                           | 

| "         | boolean isEmpty()          | 컬렉션이 비어 있는지 조사                                    | 

| "         | Iterator< E > iterator()   | 저장된 객체를 한 번씩 가져오는 반복자 리턴                   | 

| "         | int size()                 | 저장되어 있는 전체 객체 수 리턴                              | 

| 객체 삭제 | void clear()               | 저장된 모든 객체를 삭제                                      | 

| "         | boolean remove(Object o)   | 주어진 객체를 삭제                                           | 

메소드의 매개 변수 타입과 리턴 타입에 E라는 타입 파라미터가 있는데, 이것은 Set 인터페이스가 제네릭 타입이기 때문이다. 구체적인 타입은 구현 객체를 생성할 때 결정된다. 객체 추가는 add() 메소드를 사용하고, 객체 삭제는 remove() 메소드를 사용한다. 

 

Set 컬렉션은 인덱스로 객체를 검색해서 가져오는 메소드가 없다. 대신, 전체 객체를 대상으로 한 번씩 반복해서 가져오는 반복자(Iterator)를 제공한다. 반복자는 Iterator 인터페이스를 구현한 객체를 말하는데, iterator() 메소드를 호출하면 얻을 수 있다. 

```java
Set<String> set = ...; 

Iterator<String> iterator = set.iterator(); 
```

iterator 인터페이스에 선언된 메소드 

| 리턴 타입 | 메소드명  | 설명                                                         | 

| --------- | --------- | ------------------------------------------------------------ | 

| boolean   | hasNext() | 가져올 객체가 있으면 true를 리턴하고 없으면 false를 리턴한다. | 

| E         | next()    | 컬렉션에서 하나의 객체를 가져온다.                           | 

| void      | remove()  | Set 컬렉션에서 객체를 제거한다.                              | 

Iterator에서 하나의 객체를 가져올 때는 next() 메소드를 사용한다. next() 메소드를 사용하기 전에 먼저 가져올 객체가 있는지 확인하는 것이 좋다. hasNext() 메소드는 가져올 객체가 있으면 true를 리턴하고 더 이상 가져올 객체가 없으면 false를 리턴한다. 따라서 true가 리턴될 때 next() 메소드를 사용해야 한다. 아래는 Set 컬렉션에서 String 객체들을 반복해서 하나씩 가져오는 코드다. 

```java
Set<String> set = ...; 

Iterator<String> iterator = set.iterator(); 

while(iterator.hasNext()) { 

  // String 객체 하나를 가져옴 

  String str = iterator.next(); 

} 
```

Iterator를 사용하지 않더라도 향상된 for문을 이용해서 전체 객체를 대상으로 반복할 수 있다. 

```java
Set<String> set = ...; 

for(String str : set) { 

} 
```

Set 컬렉션에서 Iterator의 next() 메소드로 가져온 객체를 제거하고 싶다면 remove() 메소드를 호출하면 된다. Iterator의 메소드지만, ***\*실제 Set 컬렉션에서 객체가 제거\****된다. 

```java
while(iterator.hasNext()) { 

  String str = iterator.next(); 

  if(str.equals("java")) { 

    iterator.remove(); 

  } 

} 
```



#### HashSet 

HashSet은 Set 인터페이스의 구현클래스다. HashSet을 생성하기 위해서는 아래처럼 기본 생성자를 호출하면 된다. 

```java
Set<E> set = new HashSet<E>(); 
```

타입 파라미터 E에는 컬렉션에 저장할 객체 타입을 지정하면 된다. 아래는 String 객체를 저장하는 HashSet을 생성한다. 

```java
Set<String> set = new HashSet<String>(); 
```

HashSet은 객체들을 순서 없이 저장하고 동일한 객체는 중복 저장하지 않는다. 객체를 저장하기 전에 먼저 객체의 hashCode() 메소드를 호출해서 해시코드를 얻어낸다. 그리고 이미 저장돼 있는 객체들의 해시코드와 비교한다. 만약 동일한 해시코드가 있다면 다시 equals() 메소드로 두 객체를 비교해서 true가 나오면 동일한 객체로 판단하고 중복 저장을 하지 않는다.