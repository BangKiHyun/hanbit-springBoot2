## @WebMvcTest

- 웹에서 테스트하기 힘든 컨트롤러를 테스트하는데 적합하다.
- 웹 상에서 요청과 응답에 대해 테스트할 수 있다.
- 시큐리티 혹은 필터까지 자동으로 테스트하며 수동으로 추가/삭제까지 가능하다.
- @SpringBootTest 어노테이션보다 가볍게 테스트 할 수 있다.



### @WebMvcTest에서 로드되는 의존성

1. @Controller
2. @ControllerAdvice
3. @JsonComponent
4. Filter
5. WebMvcConfigurer
6. HandlerMethodrgumentResolver



### 사용방법

- @WebMvcTest를 사용하기 위해 테스트할 특정 컨트롤명(BookController)을 명시해주어야 한다.
- 주입된 MockMvc는 컨트롤러 테스트 시 모든 의존성을 로드하는 것이 아닌 BookController관련 빈만 로드하여 가벼운 MVC 테스트를 수행한다.
- @Service 어노테이션은 @WebMvcTest의 적용 대상이 아니기 때문에 @MockBean을 활용하여 컨트롤러 내부의 의존성 요소인 BookService를 가짜 객체로 대체할 수 있다.
  - 가짜 객체(목 객체) : 실제 객체는 아니지만 특정 행위를 지정하여 설제 객체처럼 동작하게 만들 수 있다.



### MockMvc

- andExpect(status().isOK()) : HTTP 상탯값이 200인지 테스트
- andExpect(view().name("book")) : 반환되는 뷰의 이름이 'book'인지 테스트
- andExpect(model().attributeExists("bookList")) : 모델의 프로피터 중 'bookLIst"라는 프로퍼티가 존재하는지 테스트
- andExpect(model().attribute("bookList", contains(book))) : 모델의 프로퍼티 중 
  'bookList'라는 프로피터에 book 객체가 담겨져 있는지 테스트