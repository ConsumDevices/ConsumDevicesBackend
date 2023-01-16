package ro.tuc.ds2020.sensorReader;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/wsnotification", "/messageToAdmin", "/messageToClient", "/typingFromAdmin",
                "/typingFromClient", "/readFromAdmin", "/readFromClient");
        //config.enableSimpleBroker("/messageToAdmin");
        //config.enableSimpleBroker("/messageToClient");
        //asta cred ca e pentru cand primim inapoi de la frontend
        //config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // with sockjs
        registry.addEndpoint("/ws-message").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/chatClient").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/chatAdmin").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/typingAdmin").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/typingClient").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/readAdmin").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/readClient").setAllowedOrigins("*").withSockJS();
    }
}