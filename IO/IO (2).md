# IO

- ##### 책 '이것이 자바다'를 참고해서 작성한 문서입니다



#### Reader

Reader는 문서 기반 입력 스트림의 최상위 클래스로 추상 클래스이다. 모든 문자 기반 입력 스트림은 이 클래스를 상속받아서 만들어진다. FileReader, BufferedReader, InputStreamReader 클래스는 모두 Reader 클래스를 상속한다. Reader 클래스에는 문자 기반 입력 스트림이 기본적으로 가져야 할 메소드가 정의되어 있다.

Reader 클래스의 주요 메소드

| 메소드 | 메소드                              | 설명                                                         |
| ------ | ----------------------------------- | ------------------------------------------------------------ |
| int    | read()                              | 입력 스트림으로부터 한 개의 문자를 읽고 리턴한다.            |
| int    | read(char[] cbuf)                   | 입력 스트림으로부터 읽은 문자들을 매개값으로 주어진 문자 배열 cbuf에 저장하고 실제로 읽은 문자 수를 리턴한다. |
| int    | read(char[] cbuf, int off, int len) | 입력 스트림으로부터 len개의 문자를 읽고 매개값으로 주어진 문자 배열 cbuf[off]부터 len개까지 저장한다. 그리고 실제로 읽은 문자 수인 len개를 리턴한다. |
| void   | close()                             | 사용한 시스템 자원을 반납하고 입력 스트림을 닫는다.          |

##### read() 메소드

read() 메소드는 입력 스트림으로부터 한 개의 문자(2바이트)를 읽고 4바이트 int 타입으로 리턴한다. 따라서 리턴된 4바이트 중 끝에 있는 2바이트에 문자 데이터가 들어 있다.

read() 메소드가 리턴한 int 값을 char 타입으로 변환하면 읽은 문자를 얻을 수 있다.

```java
char charData = (char) read();
```

더 이상 입력 스트림으로부터 문자를 읽을 수 없다면 read() 메소드는 -1을 리턴하는데 이것을 이용하면 읽을 수 있는 마지막 문자까지 루프를 돌며 한 문자씩 읽을 수 있다.

```java
Reader reader = new FileReader("C:/test.txt");
int readData;
while ((readData=reader.read()) != -1) {
	char charData = (char) readData;
}
```

##### read(char[] cbuf) 메소드

read(char[] cbuf) 메소드는 입력 스트림으로부터 매개값으로 주어진 문자 배열의 길이만큼 문자를 읽고 배열에 저장한다. 그리고 읽은 문자 수를 리턴한다. 실제로 읽은 문자 수가 배열의 길이보다 작을 경우 읽은 수만큼만 리턴한다.

read(char[] cbuf) 역시 입력 스트림으로부터 문자를 더 이상 읽을 수 없다면 -1을 리턴한다. 이것을 이용하면 읽을 수 있는 마지막 문자까지 루프를 돌며 읽을 수 있다.

```java
Reader reader = new FileReader("C:/test.txt");
int readCharNo;
char[] cbuf = new char[2];
while ((readCharNo=reader.read(cbuf)) != -1) { ... }
```

입력 스트림으로부터 100개의 문자가 들어온다면 read() 메소드는 100번을 루핑해야 읽어들여야한다. 그러나 read(char[] cbuf) 메소드는 한 번 읽을 때 주어진 배열 길이만큼 읽기 때문에 루핑 횟수가 현저히 줄어든다. 그러므로 많은 양의 문자를 읽을 때는 read(char[] cbuf) 메소드를 사용하는 것이 좋다.

##### read(char[] cbuf, int off, int len) 메소드

read(char[] cbuf, int off, int len) 메소드는 입력 스트림으로부터 len개의 문자만큼 읽고 매개값으로 주어진 문자 배열 cbuf[off]부터 len개까지 저장한다. 그리고 읽은 문자 수인 len개를 리턴한다. 실제로 읽은 문자 수가 len개보다 작을 경우 읽은 수만큼 리턴한다.

read(char[] cbuf, int off, int len) 역시 입력 스트림으로부터 문자를 더 이상 읽을 수 없다면 -1을 리턴한다. read(char[] cbuf)와의 차이점은 한 번에 읽어들이는 문자 수를 len 매개값으로 조절할 수 있고, 배열에서 저장이 시작되는 인덱스를 지정할 수 있다는 점이다. 만약 off를 0으로, len을 배열의 길이로 준다면 read(char[] cbuf)와 동일하다.

```java
Reader reader = ...;
char[] cbuf = new char[100];
int readCharNo = reader.read(cbuf);
//////////////////////////////////////
Reader reader = ...;
char[] cbuf = new char[100];
int readCharNo=reader.read(cbuf,0,100);
```

##### close() 메소드

마지막으로 Reader를 더 이상 사용하지 않을 경우에는 close() 메소드를 호출해서 Reader에서 사용했던 시스템 자원을 풀어준다.

```java
reader.close();
```



#### Writer

Writer는 문자 기반 출력 스트림의 최상위 클래스로 추상 클래스이다. 모든 문자 기반 출력 스트림 클래스는 이 클래스를 상속받아서 만들어진다. FileWriter, BufferedWriter, PrintWriter, OutputStreamWriter 클래스는 모두 Writer 클래스를 상속하고 있다. Writer 클래스에는 모든 문자 기반 출력 스트림이 기본적으로 가져야 할 메소드가 정의되어 있다.

Writer 클래스의 주요 메소드

| 리턴 타입 | 메소드                               | 설명                                                         |
| --------- | ------------------------------------ | ------------------------------------------------------------ |
| void      | write(int c)                         | 출력 스트림으로 주어진 한 문자를 보낸다(c의 끝 2바이트).     |
| void      | write(char[] cbuf)                   | 출력 스트림으로 주어진 문자 배열 cbuf의 모든 문자를 보낸다.  |
| void      | write(char[] cbuf, int off, int len) | 출력 스트림으로 주어진 문자 배열 cbuf[off]부터 len개까지의 문자를 보낸다. |
| void      | write(String str)                    | 출력 스트림으로 주어진 문자열을 전부 보낸다.                 |
| void      | write(String str, int off, int len)  | 출력 스트림으로 주어진 문자열 off 순번부터 len개까지의 문자를 보낸다. |
| void      | flush()                              | 버퍼에 잔류하는 모든 문자열을 출력한다.                      |
| void      | close()                              | 사용한 시스템 자원을 반납하고 출력 스트림을 닫는다.          |

##### write(int c) 메소드

write(int c) 메소드는 매개 변수로 주어진 int 값에서 끝에 있는 2바이트(한 개의 문자)만 출력 스트림으로 보낸다. 매개 변수가 int 타입이므로 4바이트 모두를 보내는 것으로 오해할 수 있다.

```java
Writer writer = new FileWriter("C:/test.txt");
char[] data = "JAVA".toCharArray();
for(int i=0; i < data.length; i++) {
	writer.write(data[i]);	// "J", "A", "V", "A"를 하나씩 출력
}
```

##### write(char[] cbuf) 메소드

write(char[] cbuf) 메소드는 매개값으로 주어진 char[] 배열의 모든 문자를 출력 스트림으로 보낸다.

```java
Writer writer = new FileWriter("C:/test.txt");
char[] data = "JAVA".toCharArray();
writer.write(data);	// "JAVA" 모두 출력
```

##### write(char[] c, int off, int len) 메소드

write(char[] c, int off, int len)은 c[off]부터 len개의 문자를 출력스트림으로 보낸다.

```java
Writer writer = new FileWriter("C:/test.txt");
char[] data = "JAVA".toCharArray();
writer,write(data, 1, 2);	// "AV"만 출력
```

##### write(String str)와 write(String str, int off, int len) 메소드

Writer는 문자열을 좀 더 쉽게 보내기 위해서 write(String str)와 write(String str, int off, int len) 메소드를 제공한다. write(String str)은 문자열 전체를 출력 스트림으로 보내고, write(String str, int off, int len)은 주어진 문자열 off 순번부터 len개까지의 문자를 보낸다.

문자 출력 스트림은 내부에 작은 버퍼(buffer)가 있어서 데이터가 출력되기 전에 버퍼에 쌓여있다가 순서대로 출력된다. flush() 메소드는 버퍼에 잔류하고 있는 데이터를 모두 출력시키고 버퍼를 비우는 역할을 한다. 프로그램에서 더 이상 출력할 문자가 없다면 flush() 메소드를 마지막으로 호출하여 모든 문자 출력되도록 해야 한다. 마지막으로 Writer를 더 이상 사용하지 않을 경우에는 close() 메소드를 호출해서 Writer에서 사용했던 시스템 자원을 풀어준다.

```java
Writer writer = new FileWriter("C:/test.txt");
String data = "안녕 자바 프로그램";
writer.write(data);
writer.flush();
writer.close();
```

