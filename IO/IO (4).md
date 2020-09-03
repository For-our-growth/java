# IO

- ##### 책 '이것이 자바다'를 참고해서 작성한 문서입니다



### 파일 입출력

#### File 클래스

IO 패키지 (java.io)에서 제공하는 File 클래스는 파일 크기, 파일 속성, 파일 이름 등의 정보를 얻어내는 기능과 파일 생성 및 삭제 기능을 제공하고 있다. 그리고 디렉토리를 생성하고 디렉토리에 존재하는 파일 리스트를 얻어내는 기능도 있다. 그러나 파일의 데이터를 읽고 쓰는 기능은 지원하지 않는다. 파일의 입출력은 스트림을 사용해야 한다.

C:\Temp 디렉토리의 file.txt 파일을 File 객체로 생성하는 코드

```java
File file = new File("C:\\Temp\\file.txt");
File file = new File("C:/Temp/file.txt");
```

디렉토리 구분자는 운영체제마다 조금씩 다르다. 윈도우에서는 \ 또는 /를 사용할 수 있고, 유닉스나 리눅스에서는 /를 사용한다. File.separator 상수를 출력해보면 해당 운영체제에서 사용하는 디렉토리 구분자를 확인할 수 있다. 만약 \를 디렉토리 구분자로 사용한다면 이스케이프 문자( \ \ )로 기술해야 한다.

File 객체를 생성했다고 해서 파일이나 디렉토리가 생성되는 것은 아니다. 생성자 매개값으로 주어진 경로가 유효하지 않더라도 컴파일 에러나 예외가 발생하지 않는다. File 객체를 통해 해당 경로에 실제로 파일이나 디렉토리가 있는지 확인하려면 exists() 메소드를 호출할 수 있다. 디렉토리 또는 파일이 파일 시스템에 존재한다면 true를 리턴하고 존재하지 않는다면 false를 리턴한다.

```java
boolean isExist = file.exists();
```

exists() 메소드의 리턴값이 false라면 createNewFile(), mkdir(), mkdirs() 메소드로 파일 또는 디렉토리를 생성할 수 있다.

| 리턴 타입 | 메소드          | 설명                               |
| --------- | --------------- | ---------------------------------- |
| boolean   | createNewFile() | 새로운 파일을 생성                 |
| boolean   | mkdir()         | 새로운 디렉토리를 생성             |
| boolean   | mkdirs()        | 경로상에 없는 모든 디렉토리를 생성 |
| boolean   | delete()        | 파일 또는 디렉토리 삭제            |

파일 또는 디렉토리가 존재할 경우에는 아래 메소드를 통해 정보를 얻어낼 수 있다.

| 리턴 타입 | 메소드                           | 설명                                                         |
| --------- | -------------------------------- | ------------------------------------------------------------ |
| boolean   | canExecute()                     | 실행할 수 있는 파일인지 여부                                 |
| boolean   | canRead()                        | 읽을 수 있는 파일인지 여부                                   |
| boolean   | canWrite()                       | 수정 및 저장할 수 있는 파일인지 여부                         |
| String    | getName()                        | 파일의 이름을 리턴                                           |
| String    | getParent()                      | 부모 디렉토리를 리턴                                         |
| File      | getParentFile()                  | 부모 디렉토리를 File 객체로 생성 후 리턴                     |
| String    | getPath()                        | 전체 경로를 리턴                                             |
| boolean   | isDirectory()                    | 디렉토리인지 여부                                            |
| boolean   | isFile()                         | 파일인지 여부                                                |
| boolean   | isHidden()                       | 숨김 파일인지 여부                                           |
| long      | lastModified()                   | 마지막 수정 날짜 및 시간을 리턴                              |
| long      | length()                         | 파일의 크기를 리턴                                           |
| String[]  | list()                           | 디렉토리에 포함된 파일 및 서브디렉토리 목록 전부를 String 배열로 리턴 |
| String[]  | list(FilenameFilter filter)      | 디렉토리에 포함된 파일 및 서브디렉토리 목록 중에 FilenameFilter에 맞는 것만 String 배열로 리턴 |
| File[]    | listFiles()                      | 디렉토리에 포함된 파일 및 서브 디렉토리 목록 전부를 File 배열로 리턴 |
| File[]    | listFiles(FilenameFilter filter) | 디렉토리에 포함된 파일 및 서브디렉토리 목록 중에 FilenameFilter에 맞는 것만 File 배열로 리턴 |

#### FileInputStream

FileInputStream 클래스는 파일로부터 바이트 단위로 읽어들일 때 사용하는 바이트 기반 입력 스트림이다. 바이트 단위로 읽기 때문에 그림, 오디오, 비디오, 텍스트 파일 등 모든 종류의 파일을 읽을 수 있다.

FileInputStream을 생성하는 두 가지 방법을 보여준다.

```java
// 첫번째 방법
FileInputStream fis = new FileInputStream("C:/Temp/image.gif");

// 두번째 방법
File file = new File("C:/Temp/image.gif");
FileInputStream fis = new FileInputStream(file);
```