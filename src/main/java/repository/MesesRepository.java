/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.Meses;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MesesRepository extends MongoRepository<Meses, String>{
    public List<Meses> findByMes (String mes);
     public Meses findByMesAndAno (String mes, String ano);
     public int countByMesAndAno(String mes,String ano);

}