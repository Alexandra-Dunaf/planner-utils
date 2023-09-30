package ru.dunaf.planner.utils.webclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.dunaf.planner.entity.User;

@Component
public class UserWebClientBuilder {

    private static final String baseUrlUser = "http://localhost:8765/planner-users/user/";
    private static final String baseUrlData = "http://localhost:8765/planner-todo/data/";

    //проверка: существует ли пользователь
    public Boolean userExists(Long userId) {
        try {
            User user = WebClient.create(baseUrlUser)//создать URL
                    .post()//указать тип поиска
                    .uri("id")//добавить URI, которая добавляется к baseUrl для запроса
                    .bodyValue(userId)//добавить в запрос
                    .retrieve()//вызывает микросервис
                    .bodyToFlux(User.class)//результат будет упакован в Flux
                    .blockFirst();//блокирует поток до получения 1ой записи, запрос становится синхронным

            if (user !=null) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Flux<User> userExistsAsync(Long userId) {

        Flux<User> fluxUser = WebClient.create(baseUrlUser)//создать URL
                .post()//указать тип поиска
                .uri("id")//добавить URI, которая добавляется к baseUrl для запроса
                .bodyValue(userId)//добавить в запрос
                .retrieve()//вызывает микросервис
                .bodyToFlux(User.class);
        return fluxUser;
    }

    // иниц. начальных данных
    public Flux<Boolean> initUserData(Long userId) {

        Flux<Boolean> fluxUser = WebClient.create(baseUrlData)
                .post()
                .uri("init")
                .bodyValue(userId)
                .retrieve()
                .bodyToFlux(Boolean.class);

        return fluxUser;

    }
}




