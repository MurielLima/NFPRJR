package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
       
//            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//            credentials.add(
//                    MongoCredential.createCredential("muriel2", "NFPRJR", "muriel2".toCharArray())
//            );
//            MongoClient client = new MongoClient(new ServerAddress("ds125953.mlab.com", 25953), credentials);

            MongoClient client = new MongoClient("localhost",27017);

            return client;
        

    }
}
