package ShoppingMall.hello.SpringSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/** → D:/homepage/ 에 있는 실제 파일
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///D:/homepage/images/");
    }
}
