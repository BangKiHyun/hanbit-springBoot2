## @DataJpaTest

- 위 어노테이션은 JPA 관련 테스트 설정만 로드한다.
- 데이터소스의 설정이 정상적인지, JAP를 사용하여 데이터를 제대로 생성, 수정, 삭제하는지 등의 테스트가 가능하다.
- 내장형 데이터베이스를 사용하여 실제 데이터베이스를 사용하지 않고 테스트 데이터베이스로 테스트할 수 있다.
- 테스트가 끝난ㄹ 때마다 자동으로 테스트에 사용한 데이터를 롤백한다,



### 최적화 DataSource 주입

- @DataJpaTest는 기본적으로 인메모리 임베디드 데이터베이스를 사용한다.
- @Entity 클래스를 스캔하여 스프링 데이터 JPA 저장소를 구성한다.
- 별도의 데이터소스를 사용하여 테스트하고 싶다면 다음과 같이 설정하면 된다.

~~~
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("...")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest{
		...
}
~~~

- @AutoConfigureTestDatabase 어노테이션의 기본 설정값인 Replace.Any를 사용하면 기본적으로 내장된 데이터소스를 사용한다.
- 위와 같이 Replace.None으로 설정하면
  @ActiveProfiles에 설정한 프로파일 환경값에 따라 데이터소스가 적용된다.
- 또는 application.yml 파일에서 프로퍼티 설정을
  spring.test.database.replace: NONE 으로 변경하면 가능

