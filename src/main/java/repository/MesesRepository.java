/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import model.Meses;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MesesRepository extends MongoRepository<Meses, String>{
     public Meses findByMes (String mes);
     public int countByMesOrAno(String mes,String ano);

}