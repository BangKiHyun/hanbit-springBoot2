## @RestClientTest

- REST 관련 테스트를 도와주는 어노테이션
- REST 통신의 데이터형으로 사용되는 JSON 형식이 예상되로 반환하는지 등을 테스트할 수 있다.
- 



### RestTemplateBuilder

- RestTemplate을 핸들링하는 빌더 객체
- connectionTimeOut, ReadTimeOut 설정뿐만 아니라 여러 설정을 간편하게 제공한다.



### RestTemplate

- Spring3 부터 지원되었으며, REST API 호출이후 응답을 받을 때까지 기다리는 동기방식이다.
- 스프링에서 제공하는 HTTP통신에 유용하게 쓸 수 있는 템플릿으로,
  HTTP 서버와의 통시을 단순화하고 RESTful 원칙을 지킨다.
- jdbcTemplate처럼 동일한 원칙에 따라 설계되어 단순한 방식의 호출로 복잡한 작업을 쉽게 하도록 제공한다.
  즉, 기계적이고 반복적인 코드를 깔끔하게 정리해준다.

- 여기서는 GET 방식으로 통신하는 getForObject() 메서드를 사용하여
  "/rest/test" URI에 요청을 보내고 요청에 대한 응답을 Book객체 형식으로 받아온다,
  - getForObject() 메서드는 GET을 수행하고 HTTP 응답을 객체 타입으로 변환해서 반환해주는 메서드다.



- @RestClientTest는 테스트 대상이 되는 빈을 주입받는다.
  - @RestClinedTest 어노테이션이 BookRestService.class를 파라미터로 주입받지 못하면 '빈이 없다'는 에러가 뜬다.
- @Rule로 지정한 필드값는 @Before, @After 어노테이션에 상관없이 하나의 테스트 메서드가 끝날 때마다 정의한 값으로 초기화시켜준다.
  - 테스트 자체적으로 규칙을 정의하여 재상요할 때 유용하다.
- @MockRestServiceServer는 클라이언트와 서버 사이의 REST 테스트를 위한 객체이다.
  - 내부에서 RestTEmplate을 바인딩하여 실제로 통신이 이루어지게끔 구성할 수도 있다.
  - 이 코드에서는 목 객체와 같이 실제로 통신이 이루어지지는 않지만 지정한 경로에 예상되는 반환값 혹은 에러를 반환하도록 명시하여 간단하게 테스트를 진행하도록 작성했다.
- 응답 파일인 test.json은 반드시 test 디렉토리 하위 경로로 생성해야 테스트 메서드에서 읽을 수 있다.
  테스트 코드의 리소스 루트 경로는 'test/resources'로 잡히기 때문이다.
- rest_error_테스트() 에서 서버 에러가 발생했을 경우를 테스트한다.
  - ExpectedException 객체의 expect() 메서드로 지정하여 테스트하며, HTTP 500 에러 발생 클래스인 HttpServerErrorException.class를 설정했다.
  - 마지막 줄에서 REST 요청을 발생시켜, 이때 발생하는 에러가 미리 작성해둔 에러(ExpectedExceoption에서 작성한 에러)와 일치하면 성공적으로 테스트를 마친다. 

