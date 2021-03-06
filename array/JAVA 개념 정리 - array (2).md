##  JAVA 개념 정리 02 - array(2)

TCPschool.com 참고



### 다차원 배열

다차원 배열이란 2차원 이상의 배열을 의미하며, 배열 요소로 또 다른 배열을 가지는 배열을 의미합니다.



즉, 2차원 배열은 배열 요소로 1차원 배열을 가지는 배열이며,

3차원 배열은 배열 요소로 2차원 배열을 가지는 배열이고,

4차원 배열은 배열 요소로 3차원 배열을 가지는 배열인 것입니다.



#### 2차원 배열(two dimensional array)

2차원 배열이란 배열의 요소로 1차원 배열을 가지는 배열입니다.

자바에서는 2차원 배열을 나타내는 타입을 따로 제공하지 않습니다.

대신에 1차원 배열의 배열 요소로 또 다른 1차원 배열을 사용하여 2차원 배열을 나타낼 수 있습니다.



따라서 자바에서 2차원 배열은 다음과 같은 문법으로 선언할 수 있습니다.

문법

```java
1. 타입[][] 배열이름;
2. 타입 배열이름[][];
3. 타입[] 배열이름[];
```

[2차원 배열](http://tcpschool.com/lectures/img_java_array23.png)



예제

```java
int[][] arr = new int[2][3];

int k = 10;

for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = k; // 인덱스를 이용한 초기화
        k += 10;
    }
}

for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j] + " ");
    }
    System.out.println();
}
```



##### 실행 결과

```java
10 20 30
40 50 60
```



#### 배열의 선언과 동시에 초기화하는 방법

1차원 배열과 마찬가지로 2차원 배열도 선언과 동시에 초기화할 수 있습니다.

자바에서는 2차원 배열의 모든 요소를 좀 더 직관적으로 초기화할 수 있습니다.



문법

```java
타입 배열이름[열의길이][행의길이] = {
    {배열요소[0][0], 배열요소[0][1], ...},
    {배열요소[1][0], 배열요소[1][1], ...},
    {배열요소[2][0], 배열요소[2][1], ...},
    ...
};
```

