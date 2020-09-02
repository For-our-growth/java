# IO

- ##### 책 '이것이 자바다'를 참고해서 작성한 문서입니다



#### 콘솔 입출력

콘솔(Console)은 시스템을 사용하기 위해 키보드로 입력을 받고 화면으로 출력하는 소프트웨어를 말한다. 유닉스나 리눅스 운영체제는 터미널(terminal)에 해당하고, Windows 운영체제는 명령 프롬프트에 해당한다.

자바는 콘솔로부터 데이터를 입력받을 때 System.in을 사용하고, 콘솔에 데이터를 출력할 때 System.out을 사용한다. 그리고 에러를 출력할 때에는 System.crr를 사용한다.

#### System.in 필드

자바는 프로그램이 콘솔로부터 데이터를 입력받을 수 있도록 System 클래스의 in 정적 필드를 제공하고 있다. System.in은 InputStream 타입의 필드이므로 아래와 같이 InputStream 변수로 참조가 가능하다.

```java
InputStream is = System.in;
```

키보드로부터 어떤 키가 입력되었는지 확인하려면 InputStream의 read() 메소드로 한 바이트를 읽으면 된다. 리턴된 int 값에는 십진수 아스키 코드가 들어 있다.

```java
int asciiCode = is.read();
```

컴퓨터는 0과 1만을 이해할 수 있다. 그래서 미국표준협회가 컴퓨터에서 문자를 숫자로 매칭하는 방법을 표준화시킨 것이 아스키 코드이다. 아스키 코드는 1byte로 표현되는 256가지의 숫자에 영어 알파벳, 아라비아 숫자, 특수 기호를 매칭하고 있다. 숫자로된 아스키 코드 대신에 입력한 문자를 직접 얻고 싶다면 read() 메소드로 읽은 아스키 코드를 char로 타입 변환하면 된다.

```java
char inputChar = (char) is.read();
```

InputStream의 read() 메소드는 1바이트만 읽기 때문에 1바이트의 아스키 코드로 표현되는  숫자, 영어, 특수문자는 프로그램에서 잘 읽을 수 있지만, 한글과 같이 2바이트를 필요로 하는 유니코드는 read() 메소드로 읽을 수 없다. 키보드로 입력된 한글을 얻기 위해서는 우선 read(byte[] b)나 read(byte[] b, int off, int len) 메소드로 전체 입력된 내용을 바이트 배열로 받고, 이 배열을 이용해서 String 객체를 생성하면 된다. read(byte[] b) 메소드를 사용하기 전에 우선 키보드에서 입력한 문자를 저장할 바이트 배열을 만들어야 한다.

```java
byte[] byteData = new byte[배열크기];
int readByteNo = System.in.read(byteData);
```

read(byte[] b) 메소드는 매개값으로 주어진 바이트 배열에 읽은 문자를 저장하고, 실제로 읽은 바이트 개수를 리턴한다.

프로그램에서 바이트 배열에 저장된 아스키 코드를 사용하려면 문자열로 변환해야 한다. 변환할 문자열은 바이트 배열의 0번 인덱스에서 시작해서 읽은 바이트 수 - 2만큼이다. 2를 빼는 이유는 Enter키에 해당하는 마지막 두 바이트를 제외하기 위해서이다. 바이트 배열을 문자열로 변환할 때에는 아래와 String 클래스의 생성자를 이용한다.

```java
String strData = new String( byteData, 0, readByteNo-2 );
					// 바이트 배열, 시작 인덱스, 읽은 바이트 수 -2
```



#### System.out 필드

콘솔에서 입력된 데이터를 System.in으로 읽었다면, 반대로 콘솔로 데이터를 출력하기 위해서는 System 클래스의 out 정적 필드를 사용한다. out은 PrintStream 타입의 필드이다. PrintStream이 OutputStream의 하위 클래스이므로 out 필드를 OutputStream 타입으로 변환해서 사용할 수 있다.

```java
OutputStream os = System.out;
```

콘솔로 1개의 바이트를 출력하려면 OutputStream의 write(int b) 메소드를 이용하면 된다. 이때 바이트 값은 아스키 코드인데, write() 메소드는 아스키 코드를 문자로 콘솔에 출력한다.

```java
byte b = 97;
os.write(b);
os.flush();
```

OutputStream의 write(int b) 메소드는 1바이트만 보낼 수 있기 때문에 1바이트로 표현 가능한 숫자, 영어, 특수문자는 출력이 가능하지만, 2바이트로 표현되는 한글은 출력할 수 없다. 한글을 출력하기 위해서는 우선 한글을 바이트 배열로 얻은 다음, write(byte[] b)나 write(byte[] b, int off, int len) 메소드로 콘솔에 출력하면 된다.

```java
String name = "가나다라";
byte[] nameBytes = name.getBytes();
os.write(nameBytes);
os.flush();
```



#### Console 클래스

자바 6부터는 콘솔에서 입력받은 문자열을 쉽게 읽을 수 있도록 java.io.Console 클래스를 제공하고 있다. Console  객체를 얻기 위해서는 System의 정적 메소드인 console()을 아래와 같이 호출하면 된다.

```java
Console console = System.console();
```

주의할 점은 이클립스에서 실행하면 System.console() 메소드는 null을 리턴하기 때문에 반드시 명령 프롬프트에서 실행해야 한다.

Console 클래스에서 읽기 메소드는 아래와 같다.

| 리턴 타입 | 메소드         | 설명                                                  |
| --------- | -------------- | ----------------------------------------------------- |
| String    | readLine()     | Enter 키를 입력하기 전의 모든 문자열을 읽음           |
| char[]    | readPassword() | 키보드 입력 문자를 콘솔에 보여주지 않고 문자열을 읽음 |



#### Scanner 클래스

Console 클래스는 콘솔로부터 문자열은 읽을 수 있지만 기본 타입(정수, 실수) 값을 바로 읽을 수는 없다. java.io 패키지의 클래스는 아니지만, java.util 패키지의 Scanner 클래스를 이용하면 콘솔로부터 기본 타입의 값을 바로 읽을 수 있다. Scanner 객체를 생성하려면 아래와 같이 생성자에 System.in 매개값을 주면 된다.

```java
Scanner scanner = new Scanner(System.in);
```

Scanner가 콘솔에서만 사용되는 것은 아니고, 생성자 매개값에는 File, InputStream, Path 등과 같이 다양한 입력 소스를 지정할 수도 있다. Scanner는 기본 타입의 값을 읽기 위해 다음과 같은 메소드를 제공한다.

| 리턴 타입 | 메소드        | 설명                             |
| --------- | ------------- | -------------------------------- |
| boolean   | nextBoolean() | boolean(true/false) 값을 읽는다. |
| byte      | nextByte()    | byte 값을 읽는다.                |
| short     | nextShort()   | short 값을 읽는다.               |
| int       | nextInt()     | int 값을 읽는다.                 |
| long      | nextLong()    | long 값을 읽는다.                |
| float     | nextFloat()   | float 값을 읽는다.               |
| double    | nextDouble()  | double 값을 읽는다.              |
| String    | nextLine()    | String 값을 읽는다.              |