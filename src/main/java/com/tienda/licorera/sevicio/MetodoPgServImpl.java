package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Metodo_pago;
import com.tienda.licorera.repositorio.RepositorioMetodoPg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface IMetodoPgServicio.
 * 
 * @author Beelz
 */
@Service
public class MetodoPgServImpl implements IMetodoPgServicio {
    @Autowired
    private RepositorioMetodoPg metodopgRepositorio;

    
    /** 
     * Metodo que busca el metodo de pago en el repositorio 
     * @param cod_metodo recibe como parametro el codigo del metodo de pago a buscar
     * @return  retorna el metodo de pago encontrado
     * @author Beelz
     */
    @Override
    public Metodo_pago buscarMet(int cod_metodo) {
        
        return metodopgRepositorio.findById(cod_metodo).orElse(null);
    }

    
    /**  
     * Metodo que elimina un metodo de pago del repositorio 
     * @param cod_metodo recibe como parametro el codigo del metodo de pago a eliminar
     * @author Beelz 
     */
    @Override
    public void eliminarMet(int cod_metodo) {
        
        metodopgRepositorio.deleteById(cod_metodo);
    }

    
    /** 
     * Metodo que guarda un metodo de pago en el repositirio
     * @param metodo recibe como parametro el objeto a guardar
     * @author Beelz
     */
    @Override
    public void guardarMet(Metodo_pago metodo) {
        
        metodopgRepositorio.save(metodo);
    }

    
    /** 
     * Metodo que lista todos los metodos de pagos que hay en el repositorio
     * @return retorna la lista con todos los metodos de pago
     * @author Beelz
     */
    @Override
    public List<Metodo_pago> listarTodos() {
       
        return (List<Metodo_pago>)metodopgRepositorio.findAll();
    }
    
    
}
