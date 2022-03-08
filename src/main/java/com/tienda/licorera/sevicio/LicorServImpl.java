package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.repositorio.RepositorioLicor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface ILicorServicio.
 * 
 * @author Beelz
 */
@Service
public class LicorServImpl implements ILicorServicio {
    
    @Autowired
    private RepositorioLicor licorRepositorio;

    
    /** 
     * Metodo que busca una categoria en el repositorio
     * @param cod_licor recibe como parametro el codigo del licor a buscar
     * @return retorna el licor encontrado
     * @author Beelz
     */
    @Override
    public Licor buscarLicor(int cod_licor) {

        return licorRepositorio.findById(cod_licor).orElse(null);
    }

    
    /** 
     * Metodo que elimina un licor del repositorio
     * @param cod_licor recibe el codigo del licor a eliminar 
     * @author Beelz
     */
    @Override
    public void eliminarLicor(int cod_licor) {

        licorRepositorio.deleteById(cod_licor);
    }

    
    /** 
     * Metodo que elimina un licor del repositorio
     * @param licor recibe como parametro el objeto a guardar
     * @author Beelz  
     */
    @Override
    public void guardarLicor(Licor licor) {

        licorRepositorio.save(licor);
    }

    
    /** 
     * Metodo que lista todos los licores del repositorio
     * @return retorna la lista con los licores
     * @author Beelz
     */
    @Override
    public List<Licor> listarTodos() {
        
        return (List<Licor>)licorRepositorio.findAll();
    }

    
    /** 
     * Metodo que busca por nombre un licor en el repositorio
     * @param nomb_licor recibe como parametro el nombre del licor a buscar
     * @return retorna los licores encontrado
     * @author Beelz
     */
    @Override
    public Licor buscarPorNombre(String nomb_licor) {
        return  licorRepositorio.findByNomb_licor(nomb_licor);
    }

    
    /** 
     * Metodo que lista por categoria un licor en el repositorio
     * @param cod_cat recibe por parametro el codigo de la categoria a buscar
     * @return retorna la lista con los licores encontrados
     */
    @Override
    public List<Licor> buscarPorCategoria(int cod_cat) {
        
        return (List<Licor>)licorRepositorio.findByCod_cat(cod_cat);
    }

    
}
