package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Empresa;
import com.tienda.licorera.repositorio.RepositorioEmpresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface IEmpresaServicio
 * 
 * @author Beelz
 */
@Service
public class EmpresaServImpl implements IEmpresaServicio {

    @Autowired
    private RepositorioEmpresa empresaRespositorio;

    /**
     * Metodo que busca una empresa en el repositorio
     * 
     * @param nit_empresa Recibe como parametro el nit de la empresa
     * @return Empresa
     * @author Beelz
     */
    @Override
    public Empresa buscarEmp(String nit_empresa) {

        return empresaRespositorio.findById(nit_empresa).orElse(null);
    }

    /**
     * Metodo que elimina una empresa en el repositorio
     * 
     * @param nit_empresa recibe como parametro el nit de la empresa a eliminar
     * @author Beelz
     */
    @Override
    public void elimiarEmp(String nit_empresa) {
        empresaRespositorio.deleteById(nit_empresa);
    }

    /**
     * Metodo que guarda una empresa en el repositorio
     * 
     * @param empresa recibe el objeto empresa a guardar
     * @author Beelz
     */
    @Override
    public void guardarEmp(Empresa empresa) {
        empresaRespositorio.save(empresa);
    }

    /**
     * Metodo que busca por nombre una categoria existente en el repositorio
     * 
     * @return List<Empresa>
     */
    @Override
    public List<Empresa> listarTodas() {

        return (List<Empresa>) empresaRespositorio.findAll();
    }

}
