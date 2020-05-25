## @JsonTest

- JSON의 직렬화와 역직렬화를 수행하는 라이브러리인 Gson과 Jackson API의 테스트를 제공한다.
- GsonTester와 JacksonTester를 사용하여 테스트를 수행한다.
- 여기서는 Jackson API를 이용해서 테스트한다.



### JSON 두 가지 테스트

1. 문자열로 나열된 JSON 데이터를 객체로 변환하여 변환된 객체값을 테스트
2. JSON 데이터를 문자열로 변환하여 변환된 문자열을 테스트



### json_테스트()

- 여기서는 테스트용 Book 객체와 JSON 포맷으로 된 String형의 변수 content를 생성
- parseObject() : 문자열인 content를 객체로 변환
  1. 변환된 객체의 title이 일치하는지 테스트
  2. publishedAt 값을 정의하지 않았기 때문에 null인지 테스트
- write() : 객체를 문자열로 변환
  1. 각 필드를 변환한 문자열이 test.json 파일에 정의한 내용과 일치하는지 테스트
  2. hasJsonPathStringValue() : title 값이 있는지 테스트
  3. extractingJsonPathStringValue() : title 값이 일치하는지 테스트