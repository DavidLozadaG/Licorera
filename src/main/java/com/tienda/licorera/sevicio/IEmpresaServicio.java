package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Empresa;

/**
 * 
 * Esta interface define los servicios que tiene la empresa
 * 
 * @author Beelz
 */
public interface IEmpresaServicio {
    
    /**
     * Metodo que busca por nombre una categoria existente en el repositorio
     * 
     * @return List<Empresa>
     */
    public List<Empresa> listarTodas();

    /**
     * Metodo que guarda una empresa en el repositorio
     * 
     * @param empresa recibe el objeto empresa a guardar
     * @author Beelz
     */
    public void guardarEmp(Empresa empresa);

    /**
     * Metodo que busca una empresa en el repositorio
     * 
     * @param nit_empresa Recibe como parametro el nit de la empresa
     * @return Empresa
     * @author Beelz
     */
    public Empresa buscarEmp(String nit_empresa);

    /**
     * Metodo que elimina una empresa en el repositorio
     * 
     * @param nit_empresa recibe como parametro el nit de la empresa a eliminar
     * @author Beelz
     */
    public void elimiarEmp(String nit_empresa);

}
