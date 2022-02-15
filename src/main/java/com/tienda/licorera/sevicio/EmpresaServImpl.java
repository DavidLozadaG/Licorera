package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Empresa;
import com.tienda.licorera.repositorio.RepositorioEmpresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServImpl implements IEmpresaServicio{

    @Autowired
    private RepositorioEmpresa empresaRespositorio;

    @Override
    public Empresa buscarEmp(String nit_empresa) {
       
        return empresaRespositorio.findById(nit_empresa).orElse(null);
    }

    @Override
    public void elimiarEmp(String nit_empresa) {
        empresaRespositorio.deleteById(nit_empresa);
    }

    @Override
    public void guardarEmp(Empresa empresa) {
        empresaRespositorio.save(empresa);
    }

    @Override
    public List<Empresa> listarTodas() {
      
        return (List<Empresa>)empresaRespositorio.findAll();
    }
    

    

}
