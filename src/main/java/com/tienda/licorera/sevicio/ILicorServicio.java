package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Licor;

/**
 * 
 * Esta interface define los servicios que tiene el licor
 * 
 * @author Beelz
 */
public interface ILicorServicio {

    /**
     * Metodo que lista todos los licores del repositorio
     * 
     * @return retorna la lista con los licores
     * @author Beelz
     */
    public List<Licor> listarTodos();

    /**
     * Metodo que busca por nombre un licor en el repositorio
     * 
     * @param nomb_licor recibe como parametro el nombre del licor a buscar
     * @return retorna los licores encontrado
     * @author Beelz
     */
    public Licor buscarPorNombre(String nomb_licor);

    /**
     * Metodo que lista por categoria un licor en el repositorio
     * 
     * @param cod_cat recibe por parametro el codigo de la categoria a buscar
     * @return retorna la lista con los licores encontrados
     */
    public List<Licor> buscarPorCategoria(int cod_cat);

    /**
     * Metodo que guarda un licor en el repositorio
     * 
     * @param licor recibe como parametro el objeto a guardar
     * @author Beelz
     */
    public void guardarLicor(Licor licor);

    /**
     * Metodo que busca una licor en el repositorio
     * 
     * @param cod_licor recibe como parametro el codigo del licor a buscar
     * @return retorna el licor encontrado
     * @author Beelz
     */
    public Licor buscarLicor(int cod_licor);

    /**
     * Metodo que elimina un licor del repositorio
     * 
     * @param cod_licor recibe el codigo del licor a eliminar
     * @author Beelz
     */
    public void eliminarLicor(int cod_licor);
}
