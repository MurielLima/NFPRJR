package config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.CidadeRepository;
import repository.EmpresaRepository;
import repository.MesEmpresaRepository;
import repository.MesesRepository;


public class DAO {

    private static final AnnotationConfigApplicationContext ctx
            = new AnnotationConfigApplicationContext(DBConfig.class);
    public static CidadeRepository cidadeRepository = ctx.getBean(CidadeRepository.class);
    public static EmpresaRepository empresaRepository = ctx.getBean(EmpresaRepository.class);
    public static MesEmpresaRepository mesEmpresaRepository = ctx.getBean(MesEmpresaRepository.class);
    public static MesesRepository mesesRepository = ctx.getBean(MesesRepository.class);


}
