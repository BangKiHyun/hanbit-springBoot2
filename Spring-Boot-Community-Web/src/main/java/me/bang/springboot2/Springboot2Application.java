package me.bang.springboot2;

import me.bang.springboot2.domain.Board;
import me.bang.springboot2.domain.User;
import me.bang.springboot2.enums.BoardType;
import me.bang.springboot2.repository.BoardRepository;
import me.bang.springboot2.repository.UserRepository;
import me.bang.springboot2.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class Springboot2Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2Application.class, args);
    }

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    //ConmmandLineRunner: 애플리케이션 구동 후 특정 코드를 실행시키고 싶을 때 직접 구현하는 인터페이스, 테스트 데이터 함께 생성할때 좋음
    @Bean
    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception {
        return (args) -> {
            User user = userRepository.save(User.builder()
                    .name("bang")
                    .password("test")
                    .email("bang@gmail.com")
                    .createdDate(LocalDateTime.now())
                    .build());

            IntStream.rangeClosed(1, 200).forEach(id ->
                    boardRepository.save(Board.builder()
                            .title("게시글" + id)
                            .subTitle("순서" + id)
                            .content("컨텐츠")
                            .boardType(BoardType.FREE)
                            .createdDate(LocalDateTime.now())
                            .updateeDate(LocalDateTime.now())
                            .user(user)
                            .build())
            );
        };
    }
}
