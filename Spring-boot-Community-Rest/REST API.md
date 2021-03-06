# REST API

## REST란?

- 웹과 같은 분산 하이퍼미디어 시스템에서 사용하는 통신 네트워크 아키텍처로, 네트워크 아키텍처의 원리 모음
- REST는 **HTTP와 URI의 단순하고 간결한 장점을 계승한 네트워크 아키텍처**
  - 전송 방식: HTTP
  - 식별 방법: URI
  - 프로토콜: GET, POST, PUT, DELETE

</br >

## RESTful 제약 조건

RESTful이란 REST의 구현 원칙을 제대로 지키면서 REST 아키텍처를 만드는 것이다.

- 클라이언트-서버(client-server)
  - 이 제약 조건의 기본 원칙은 **관심사의 명확한 분리**
  - 관심사의 명확한 분리가 선행되면 **서버의 구성요소가 단순화되고 확장성이 향상**되어 여러 플랫폼을 지원할 수 있음.
- 무상태성(stateless)
  - 서버에 **클라이언트의 상태 정보를 저장하지 않는 것**
  - **단순히 들어온 요청만 처리**하여 구현을 단순화
  - 단, 클라이언트의 모든 요청은 서버가 요청을 알아듣는 데 필요한 모든 정보를 담고 있어야 함
- 캐시 가능(cacheable)
  - 클라이언트의 응답을 캐시할 수 있어야 함
  - HTTP의 장점을 그대로 계승한 아키텍쳐가 REST이므로 **HTTP의 캐시 기능도 적용할 수 있어야 된다.**
- 계층화 시스템(layered system)
  - 서버는 중개 서버(게이트웨이, 프록시)나 로드 밸런싱, 공유 캐시 등의 기능을 사용하여 확장성 있는 시스템을 구성할 수 있음
- 코드 온 디멘드(code on demand)
  - 클라이언트는 서버에서 자바 애플릿, 자바스크립트 실행 코드를 전송받아 기능을 일시적으로 확장할 수 있음(선택적 제약 조건)
- 인터페이스 일관성(uniform interface)
  - **URI로 지정된 리소스에 균일하고 통일돤 인터페이스를 제공**
  - 아키텍처를 단순하게 분리하여 독립적으로 만들 수 있음

</br >

## 인터페이스 일관성

인터페이스의 일관성은 세부 원칙을 갖고 있다. 인터페이스의 일관성이 잘 지켜졌는지에 따라 REST를 제대로 사용했는지 판단할 수 있다.

1. ### 자원 식별

   - 웹 기반의 REST에서 리소스 접근은 주로 URI를 사용한다는 것을 나타냄.
   - 즉, 각각의 리소스는 요청에서 식별 가능하다.
   - 예로 다음 아래 URI에서 정보는'resource', 유일한 구분자는 '1'로 식별한다.
     - http://localhost:8080**/resource/1**

2. ### 메시지를 통한 리소스 조작

   - 클라이언트가 특정 메시지나 메타데이터를 가지고 있으면 자원을 수정, 삭제하는 충분한 정보를 갖고 있는 것으로 불 수 있다.
   - 예로 content-type은 리소스가 어떤 형식인지 지정한다.
   - 리소스는 HTML, XML, JSON등 다양한 형식으로 전송된다. 다음은 JSON 형식으로 지정한 예다.
     - content-type: application/json

3. ### 자기 서술적 메시지

   - 각 메시지는 자신을 어떻게 처리해야 하는지 충분한 정보를 포함해야 한다.

   - 웹 기반 REST에서는 HTTP Method와 Header를 활용한다.

   - 다음 예제는 GET 메서드를 활용하여 /resource/1의 정보를 받아온다.

     - **GET** http://localhost:8080/resource/1

       content-type: application/json

4. ### HATEOAS(에플리케이션 상태에 대한 엔진으로서의 하이퍼미디어)

   - 클라이언트에 응답할 때 단순히 결과 데이터만 제공해주기보다 URI를 함께 제공해야한다는 원칙

</br >

## REST API 설계

REST API는 다음과 같이 구성해야 한다.

- 자원(resource): URI
- 행위(verb): HTTP메서드
- 표현(representations): 리소스에 대한 표현(HTTP Message Body)

</br >

## URI 설계

URI는 URL을 포함하는 개념이다.

> URL(Uniform Resource Locator, 파일 식별자): 인터넷상에서 자원, 즉 특정 파일이 어디에 위치하는지 식별하는 방법

- URL이 웹상의 파일 위치를 표현하는 방법
  - http://localhost:8080/api/book.pdf
- URI는 웹에 있는 자원의 이름과 위치를 식별
  - http://localhost:8080/api/book/1

URL은 URI의 하위 개념이다. URL는 리소스를 가져오는 방법에 대한 위치라면, URI는 문자열을 식별하기 위한 표준이다.

</br >

### 명사를 사용하라

- URI는 명사를 사용해야하며 동사는 피해야 한다.
- 다음은 동사를 포함하는 URI 예제다.
  - http://localhost:8080/api**/read**/books
- 이처럼 동사를 표현할 때 HTTP 메서드인 GET, POST, PUT, DELETE 등으로 다음과 같에 대체해야 한다.
  - GET http://locahost:8080/api/books

동사를 항상 HTTP 메서드로 표현하라 했지만 세부적인 동사의 경우 URI에 포함될 수밖에 없다. 앞의 URI 설계에 대한 원칙은 어디까지나 불필요한 동사를 URI에 포함하는 것을 지양해야 한다는 것이지 완전히 배제시킨다는 것은 아니다.

</br >

### 복수형을 사용하라

URI에서는 명사에 단수형보다 복수형을 사용해야 한다.

- 복수형으로 리소스를 표현하면 컬렉션으로 명확하게 표현할 수 있어 확장성 측면에서 더 좋다.
  - 컬렉션은 객체의 그룹을 만들고 조작하는 자바 아니텍처를 뜻한다.
- 컬렉션으로 URI를 사용할 경우 컬렉션을 한번 더 감싼 중첩형식으로 사용하는 것이 좋다.
  - 중첩하지 않고 컬렉션을 반환하면 추후 수정할 때나 확장할 때 번거롭게 된다.

</br >

### 행위 설계

books의 동사 부분을 HTTP 메서드로 표현하면 다음과 같다.

| Resource | GET(read)          | POST(create)   | PUT(update)        | DELETE(delete)     |
| -------- | ------------------ | -------------- | ------------------ | ------------------ |
| /books   | book 목록 보기     | 해당 book 추가 |                    |                    |
| /books/1 | ID가 1인 book 보기 |                | ID가 1인 book 수정 | ID가 1인 book 삭제 |

/books의 경우 book의 목록을 표현한다는 기본 전제가 깔려 있다. 따라서 /books로 URI를 표현하면 기본적으로 복수를 나타내면 작게는 하나의 단위를 나타낸다.

