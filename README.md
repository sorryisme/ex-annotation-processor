이 내용은 “더 자바, 코드를 조작하는 다양한 방법” - 백기선 님의 강의 기반으로 작성되었습니다. 

링크: https://www.inflearn.com/course/the-java-code-manipulation?attributionToken=ggHwgQoMCKfGmLgGEJ6p248DEAEaJDY3MDY2NjA3LTAwMDAtMjE2Ni05OTU4LWY0ZjVlODA1ZGM5NCoGMTQyMjg0MiijgJcitbeMLajlqi2Y1rctjr6dFcXL8xfC8J4V1LKdFZD3sjCb1rctOg5kZWZhdWx0X3NlYXJjaEABSAFoAXoCc2k

### Lombok이란

- 애노테이션과 애노테이션 프로세서를 통해서 작성해야할 보일러 플레이트 코드를 대신 생성해주는 라이브러리
- 동작원리
    - 컴파일 시점에 애노테이션 프로세서를 이용하여 소스 코드의 AST를 조작한다
- 이슈
    - 공개된 API가 아닌 컴파일러 내부 클래스를 사용하여 기존 소스 코드를 조작
    

### Annotation Processor란

- 컴파일 시점에 특정 애노테이션이 붙어있는 경우 소스코드를 생성할 수 있는 기능

### AST (abstract syntax tree)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ca9847a6-8fcf-4cec-8baa-2647352a9219/715c3dbe-72e5-40b1-8312-7c320c7dc264/image.png)

- 프로그래밍 언어로 작성된 소스코드를 분석하여 트리 구조로 구성한 것
    - 프로그래밍 언어로 작성된 소스는 컴파일 과정에서 AST구성
- 컴파일러 구문 분석된 결과물로 참조만 가능하다
    - 허나 하위 타입으로 변환해서 처리한다
    - `procces 메소드의 일부`
- 실제로 롬복 애노테이션은 컴파일 과정에서 AST를 재구성 한다

### 롬복 컴파일 결과 확인해보기

> Member.java
> 

```java
package com.example.demo;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private String name;
    private int age;

}
```

> Member.class
> 

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import lombok.Generated;

public class Member {
    private String name;
    private int age;

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getAge() {
        return this.age;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setAge(final int age) {
        this.age = age;
    }

    @Generated
    public String toString() {
        String var10000 = this.getName();
        return "Member(name=" + var10000 + ", age=" + this.getAge() + ")";
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Member)) {
            return false;
        } else {
            Member other = (Member)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getAge() != other.getAge()) {
                return false;
            } else {
                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof Member;
    }

    @Generated
    public int hashCode() {
        int PRIME = true;
        int result = 1;
        result = result * 59 + this.getAge();
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    @Generated
    public Member(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    @Generated
    public Member() {
    }
}

```

### AnnotationProcessor 예시코드

@Rombok을 붙일 경우 모든 필드를 가져와서 Getter를 생성하는 기능을 제작

> 작성코드 링크
> 

https://github.com/sorryisme/ex-annotation-processor/blob/master/annotaion/src/main/java/org/example/RombokProcessor.java

> MemberDto.class
> 

```java

@Rombok
public class MemberDto {

    String name;
    int age;
}

```

> MemberDtoGenerator.class
> 

```java
package org.example;

import java.lang.String;

public class MemberDtoGenerator {
  public String name;

  public int age;

  public String getName() {
    return this.name;
  }

  public int getAge() {
    return this.age;
  }
}

```

- 파일을 덮어쓰는 기능에 대해서 제공되는 기능이 없어서 신규 클래스 작성하는 것으로 대체하였습니다

### 출처:

https://www.inflearn.com/course/the-java-code-manipulation?attributionToken=ggHwgQoMCKfGmLgGEJ6p248DEAEaJDY3MDY2NjA3LTAwMDAtMjE2Ni05OTU4LWY0ZjVlODA1ZGM5NCoGMTQyMjg0MiijgJcitbeMLajlqi2Y1rctjr6dFcXL8xfC8J4V1LKdFZD3sjCb1rctOg5kZWZhdWx0X3NlYXJjaEABSAFoAXoCc2k

https://www.happykoo.net/@happykoo/posts/256