##  JAVA 개념 정리 06 - 인스턴스 멤버와 this 

이것이 자바다 참고



**인스턴스(instance) 멤버**란 객체를 생성한 후 사용할 수 있는 필드와 메소드를 말하는데, 이들을 각각 
인스턴스 필드, 인스턴스 메소드라고 부릅니다. 

인스턴스 필드와 메소드는  객체에 소속된 멤버이기 때문에 객체 없이는 사용할 수 없습니다.



```java
public class Car{
    
    int gas; // 필드
    
    void setSpeed(int speed) //메소드  
}
```

gas 필드와 setSpeed( ) 메소드는 인스턴스 멤버이기 때문에 외부 클래스에서 사용하기 위해서는 
우선 Car 객체를 생성하고 참조 변수 **myCar** 또는 **yourCar**로 접근해야 합니다.



```java
Car myCar = new Car();
myCar.gas = 10;
myCar.setSpeed(60);

Car yourCar = new Car();
yourCar.gas = 20;
yourCar.setSpeed(80);
```

위 코드가 실행된 후에 인스턴스 필드 gas는 객체마다 따로 존재합니다.

인스터스 메소드 setSpeed( ) 는 객체마다 존재하지 않고 메소드 영역에 저장되고 공유됩니당.





객체 외부에서 인스턴스 멤버에 접근하기 위해 참조 변수를 사용하는 것과 마찬가지로 객체 내부에서도
인스턴스 멤버에 접근하기 위해 **this**를 사용할 수 있습니다. 우리가 자신을 **"나"**라고 하듯이, 객체는 자신을
**"this"** 라고 합니다. 

따라서 ```this.model``` 은 자신이 가지고 있는 model 필드라는 뜻입니다. 

this는 주로 **생성자와 메소드의 매개 변수 이름이 필드와 동일한 경우**, 인스턴스 멤버인 필드임을 
명시하고자 할 때 사용합니다.



```java
Car(String model) {
	this.model = model;
}

void setModel(String model) {
	this.model = model;
}
```



예시 코드입니다.

예시

```java
[Car.java]

public class Car {
    
	// 필드
	String model;
    int speed;
    
    // 생성자
    Car(String model) {
        this.model = model;
    }
    
    // 메소드
    void setSpeed(int speed) {
        this.speed = speed;
    }
    
    void run() {
        for(int i=10; i<=50; i+=10) {
            this.setSpeed(i);
            System.out.println(this.model + "가 달립니다. (시속:" + this.speed + "km/h");
        }
    }
}
```



 ```java
[CarExample.java]

public class CarExample {
	public static void main(String[] args) {
		Car myCar = new Car("포르쉐");
		Car yourCar = new Car("아우디");
		
		myCar.run();
		yourCar.run();
	}
}
 ```



출력

```
포르쉐가 달립니다. (시속:10km/h)
포르쉐가 달립니다. (시속:20km/h)
포르쉐가 달립니다. (시속:30km/h)
포르쉐가 달립니다. (시속:40km/h)
포르쉐가 달립니다. (시속:50km/h)
아우디가 달립니다. (시속:10km/h)
아우디가 달립니다. (시속:20km/h)
아우디가 달립니다. (시속:30km/h)
아우디가 달립니다. (시속:40km/h)
아우디가 달립니다. (시속:50km/h)
```



