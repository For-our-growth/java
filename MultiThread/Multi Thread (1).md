# Multi Thread

##### - 책 '이것이 자바다'를 참고해서 작성한 문서입니다.



#### 개념

- 스레드(thread)는 사전적 의미로 한 가닥의 실이라는 뜻이다. 한 작업을 실행하기 위해 순차적으로 실행할 코드를 실처럼 이어 놓았다고 해 유래된 이름이다.
- 멀티 프로세스가 애플리케이션 단위의 멀티 테스킹이라면 **멀티 스레드**는 애플리케이션 **내부**에서의 **멀티 테스킹**이라고 볼 수 있다.
- 하나의 프로세스 내부에 생성되기 때문에 하나의 스레드가 예외를 발생시키면 프로세스 자체가 종료될 수 있어 다른 스레드에 영향을 미치게 된다.

#### 메인 스레드

- 모든 자바 애플리케이션은 메인 스레드(main thread)가 main() 메소드를 실행하면서 시작된다.
- 싱글 스레드 애플리케이션에선 메인 스레드가 종료하면 프로세스도 종료된다.
- 멀티 스레드 애플리케이션에선 실행 중인 스레드가 하나라도 있다면, 프로세스는 종료되지 않는다. (메인 스레드가 먼저 종료되더라도 동일하다.)



#### 생성과 실행

자바에서는 작업 스레드도 객체로 생성되기 때문에 클래스가 필요하다.

java.lang.Thread 클래스를 직접 객체화하거나 Thread를 상속해 하위 클래스를 만들어 생성할 수 있다.



#### 직접 객체화

java.lang.Thread 클래스로부터 직접 생성하려면 Runnable을 매개값으로 갖는 생성자를 호출해야한다.

```java
Thread thread = new Thread(Runnable target);
```

Runnable은 작업 스레드가 실행할 수 있는 코드를 갖고 있는 객체다. 이는 인터페이스 타입이기 때문에 구현 객체를 만들어 대입해야 한다. 

Runnable에는 run() 메소드 하나가 정의되어 있고, 구현 클래스에선 run()을 재정의해서 작업 스레드가 실행할 코드를 작성해야 한다.

```java
class Task implements Runnable {
	public void run() {
		// 스레드가 실행할 코드
	}
}
```

이렇게 생성한 구현 객체를 매개값으로 Thread 생성자를 호출하면 작업 스레드가 생성된다.

```java
Runnable task = new Task();
Thread thread = new Thread(task);
```

Thread 생성자를 호출할 때 Runnable 익명 객체를 매개값으로 사용할 수 있다.

```java
Thread thread = new Thread(new Runnable() {
	public void run() {
		// 스레드가 실행할 코드
	}
});
```

Runnable 인터페이스는 run() 메소드 하나만 정의되어 있기 때문에 함수적 인터페이스다. 따라서 람다식을 매개값으로 사용할 수도 있다.

```java
Thread thread = new Thread( () -> {
	// 스레드가 실행할 코드
});
```

이렇게 생성된 작업 스레드는 start() 메소드를 호출해야 실행된다.

```java
thread.start();
```



#### 하위 클래스로부터 생성

작업 스레드가 실행할 작업을 Runnable로 만들지 않고, Thread의 하위 클래스로 작업 스레드를 정의하면서 작업 내용을 포함시킬 수도 있다.

Thread 클래스를 상속한 후 run 소드를 재정의(overriding)해서 스레드가 실행할 코드를 작성하면 된다.

```java
public class WorkerThread extends Thread {
	@Override
	public void run() {
		// 스레드가 실행할 코드
	}
}
Thread thread = new WorkerThread();
```

코드 절약을 위해 Thread 익명 객체로 작업 스레드 객체를 생성할 수도 있다.

```java
Thread thread = new Thread() {
	public void run() {
		// 스레드가 실행할 코드
	}
};
```



#### 스레드 이름

스레드는 자신의 이름을 갖고 있다. 큰 역할을 하는 것은 아니지만, 디버깅할 때 어떤 스레드가 어떤 작업을 하는지 조사할 목적으로 가끔 사용된다.

메인 스레드는 "main"이라는 이름을 갖고 있고, 직접 생성한 스레드는 자동적으로 "Thread-n"이라는 이름으로 설정된다. 이름을 변경하려면 Thread 클래스의 setName() 메소드로 변경하면 된다.

```java
thread.setName("스레드 이름");
```

이름을 알고 싶은 경우 getName() 메소드를 호출한다.

```java
thread.getName();
```

위 두 메소드는 Thread의 인스턴스 메소드이므로 스레드 객체의 참조가 필요하다. 만약 갖고 있지 않다면, Thread 정적 메소드인 currentThread()로 코드를 실행하는 현재 스레드의 참조를 얻을 수 있다.

```java
Thread thread = Thread.currentThread();
```



#### 우선순위

멀티 스레드는 동시성(Concurrency) 또는 병렬성(Parallelism)으로 실행된다.

- 동시성: 멀티 작업을 위해 하나의 코어에서 멀티 스레드가 번갈아가며 실행하는 성질
- 병렬성: 멀티 작업을 위해 멀티 코어에서 개별 스레드를 동시에 실행하는 성질

싱글 코어 CPU를 이용한 멀티 스레드 작업은 병렬적으로 실행되는 것처럼 보이지만, 사실 번갈아가며 실행하는 동시성 작업이다. 번갈아 실행하는 것이 워낙 빨라 병렬성으로 보인다.



#### 스레드 스케줄링

스레드의 개수가 코어의 수보다 많을 경우, 스레드를 어떤 순서에 의해 동시성으로 실행할 것인가를 결정해야한다.

자바의 스레드 스케줄링은 우선순위(Priority) 방식과 순환 할당(Round-Robin) 방식을 사용한다. 

- 우선순위 방식: 우선순위가 높은 스레드가 실행 상태를 더 많이 가지도록 스케줄링하는 것
  - 개발자가 코드로 제어 가능
- 순환 할당 방식: 시간 할당량(Time Slice)을 정해서 하나의 스레드를 정해진 시간만큼 실행하고 다시 다른 스레드를 실행하는 방식
  - 코드로 제어 불가능

우선순위 방식에서 우선순위는 1에서 10까지 부여된다. 1이 가장 낮고 10이 가장 높다. 만약 우선순위를 부여하지 않으면 모든 스레드들은 기본적으로 5의 우선순위를 할당받는다.

Thread 클래스의 setPriority() 메소드를 이용하면 된다.

```java
thread.setPriority(우선순위);
```

코드의 가독성(이해도)을 높이기 위해 Thread의 상수를 사용할 수 있다.

```java
thread.setPriority(Thread.MAX_PRIORITY); // 10
thread.setPriority(Thread.NORM_PRIORITY); // 5
thread.setPriority(Thread.MIN_PRIORITY); // 1
```