/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.MesEmpresa;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MesEmpresaRepository extends MongoRepository<MesEmpresa, String>{
     public List<MesEmpresa> findByMes (String mes);
}