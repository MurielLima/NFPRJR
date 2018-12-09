/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;
import java.util.List;
import model.Empresa;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface EmpresaRepository extends MongoRepository<Empresa, String>{
     public List<Empresa> findByNomeFantasiaLikeIgnoreCaseOrCnpjOrRazaoSocialLikeIgnoreCase(String nomeFantasia,String Cnpj,String razaoSocial);

    public Empresa findByCnpj(String cnpj);
    public List<Empresa> findByCnpjLikeIgnoreCase(String cnpj);
    public int countByCnpj(String cnpj);
}