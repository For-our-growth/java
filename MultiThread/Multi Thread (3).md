# Multi Thread

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



#### 데몬 스레드

데몬(daemon) 스레드는 주 스레드의 작업을 돕는 보조적인 역할을 수행하는 스레드이다. 주 스레드가 종료되면 데몬 스레드는 강제적으로 자동 종료되는데, 그 이유는 주 스레드의 보조 역할을 수행하므로 주 스레드가 종료되면 데몬 스레드의 존재 의미가 없어지기 때문이다. 이점을 제외하면 데몬 스레드는 일반 스레드와 크게 차이가 없다.

스레드를 데몬으로 만들기 위해서는 주 스레드가 데몬이 될 스레드의 setDaemon(true)를 호출해주면 된다. 아래 코드를 보면 메인 스레드가 주 스레드가 되고 AutoSaveThread가 데몬 스레드가 된다.

```java
public static void main(String[] args) {
	AutoSaveThread thread = new AutoSaveThread();
	thread.setDaemon(true);
	thread.start();
	...
}
```

주의할 점은 start() 메소드가 호출되고 나서 setDaemon(true)를 호출하면 IllegalThreadStateException이 발생하기 때문에 start() 메소드 호출 전에 setDaemon(true)를 호출해야 한다.

현재 실행 중인 스레드가 데몬 스레드인지 아닌지 구별하는 방법은 isDaemon() 메소드의 리턴값을 조사해보면 된다. 데몬 스레드인 경우 true를 리턴한다.



#### 스레드 그룹

관련된 스레드를 묶어서 관리할 목적으로 이용된다. JVM이 실행되면 system 스레드 그룹을 만들고, JVM 운영에 필요한 스레드들을 생성해서 system 스레드 그룹에 포함시킨다. 그리고 system의 하위 스레드 그룹으로 main을 만들고 메인 스레드를 main 스레드 그룹에 포함되는데, 명시적으로 스레드 그룹에 포함시키지 않으면 기본적으로 자신을 생성한 스레드와 같은 스레드 그룹에 속하게 된다.

##### 스레드 그룹 이름 얻기

스레드가 속한 그룹의 이름을 얻고 싶다면 아래와 같은 코드를 사용할 수 있다.

```java
ThreadGroup group = Thread.currentThread().getThreadGroup();
String groupName = group.getName();
```

Thread의 정적 메소드인 getAllStackTraces()을 이용하면 프로세스 내에서 실행하는 모든 스레드에 대한 정보를 얻을 수 있다.

```java
Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
```

getAllStackTraces() 메소드는 Map 타입의 객체를 리턴하는데, 키는 스레드 객체이고 값은 스레드의 상태 기록들을 갖고 있는 StackTraceElement[] 배열이다.



#### 스레드 그룹 생성

명시적으로 스레드 그룹을 만들고 싶다면 아래의 생성자 중 하나를 이용해 ThreadGroup 객체를 만들면 된다.

```java
ThreadGroup tg = new ThreadGroup(String name);	// ThreadGroup 이름만 매개값으로
ThreadGroup tg = new ThreadGroup(ThreadGroup parent, String name);	// 부모 ThreadGroup과 이름을 매개값으로
```

스레드 그룹 생성 시 부모 스레드 그룹을 지정하지 않으면 현재 스레드가 속한 그룹의 하위 그룹으로 생성된다.

새로운 스레드 그룹을 생성한 후, 이 그룹에 스레드를 포함시키려면  Thread 객체를 생성할 때 생성자 매개값으로 스레드 그룹을 지정하면 된다. 스레드 그룹을 매개값으로 갖는 Thread 생성자는 다음 네 가지가 있다.

```java
Thread t = new Thread(ThreadGroup group, Runnable target);
Thread t = new Thread(ThreadGroup group, Runnable target, String name);
Thread t = new Thread(ThreadGroup group, Runnable target, String name, long stackSize);
Thread t = new Thread(ThreadGroup group, String name);
```

target은 Runnable 구현 객체를 말하며, name은 스레드의 이름이다. stackSize는 JVM이 이 스레드에 할당할 stack 크기이다.



#### 스레드 그룹의 일괄 interrupt()

스레드 그룹에서 제공하는 interrupt() 메소드를 이용하면 그룹 내에 포함된 모든 스레드들을 일괄 interrupt할 수 있다. 스레드 그룹의 interrupt() 메소드는 포함된 모든 스레드의 interrupt() 메소드를 내부적으로 호출해주기 때문에 이것이 가능하다.

스레드 그룹의 interrupt() 메소드는 소속된 스레드의 interrupt() 메소드를 호출만 할 뿐 개별 스레드에서 발생하는 InterruptedException에 대한 예외 처리를 하지 않는다. 따라서 안전한 종료를 위해서는 개별 스레드가 예외 처리를 해야 한다.



#### 스레드풀

갑작스런 병렬 작업의 폭증으로 인한 스레드의 폭증을 막으려면 **스레드풀(ThreadPool)**을 사용해야 한다. 스레드풀은 작업 처리에 사용되는 스레드를 제한된 개수만큼 정해 놓고 작업 큐(Queue)에 들어오는 작업들을 하나씩 스레드가 맡아 처리한다. 작업 처리가 끝난 스레드는 다시 작업 큐에서 새로운 작업을 가져와 처리한다. 그렇기 때문에 작업 처리 요청이 폭증되어도 스레드의 전체 개수가 늘어나지 않으므로 애플리케이션의 성능이 급격히 저하되지 않는다.

자바는 스레드풀을 생성하고 사용할 수 있도록 java.util.concurrent 패키지에서 ExecutorService 인터페이스와 Executors 클래스를 제공하고 있다. Executors의 다양한 정적 메소드를 이용해서 ExecutorService 구현 객체를 만들 수 있는데, 이것이 바로 스레드풀이다.

![ExecutorService 동작 방식](C:\Users\a\Desktop\threadPool.png)