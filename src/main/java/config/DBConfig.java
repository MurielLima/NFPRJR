package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repository.EmpresaRepository;



@Configuration
@EnableMongoRepositories(basePackageClasses = EmpresaRepository.class)

public class DBConfig extends AbstractMongoConfiguration {
    
    @Override
    protected String getDatabaseName() {
        return "NFPRJR";
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient("localhost", 27017);
        return client;

    }
}
