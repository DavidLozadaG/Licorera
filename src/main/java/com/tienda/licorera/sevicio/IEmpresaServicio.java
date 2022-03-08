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
    public List<Empresa>listarTodas();
    public void guardarEmp(Empresa empresa);
    public Empresa buscarEmp(String nit_empresa);
    public void elimiarEmp(String nit_empresa);

}
