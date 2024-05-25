package hikingapp.data.dao;

import hikingapp.data.model.Hike;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = Hike.class)
@EnableJpaRepositories(basePackageClasses = JpaDao.class)
public class SpringDaoConfig {

}