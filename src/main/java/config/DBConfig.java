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
    
//    @Override
//    protected String getDatabaseName() {
//        return "nfprjr";
//    }
//
//    @Override
//    public Mongo mongo() throws Exception {
//       
//            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//            credentials.add(
//                    MongoCredential.createCredential("muriel2", "nfprjr", "muriel2".toCharArray())
//            );
//            MongoClient client = new MongoClient(new ServerAddress("ds155243.mlab.com", 55243), credentials);
//
//
//            return client;
//        
//
//    }
        @Override
    protected String getDatabaseName() {
        return "nfprjr";
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient("localhost");
       
        return client;
    }
    
}
