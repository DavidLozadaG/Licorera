package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Categoria;

/**
 * 
 * Esta interface define los servicios que tiene la categoria
 * 
 * @author Beelz
 */
public interface ICategoriaServicio {

    /**
     * Metodo que lista las categorias existentes en el repositorio
     * 
     * @return retorna la lista de categorias
     * @author Beelz
     */
    public List<Categoria> listarTodas();

    /**
     * Metodo que busca por nombre una categoria existente en el repositorio
     * 
     * @param nomb_cat Recibe como parametro el nombre de la categoria a buscar
     * @return retorna el nombre de la categoria (si la encuentra)
     * @author Beelz
     */
    public Categoria buscarPorNombre(String nomb_licor);

    /**
     * Metodo que busca una categoria en el repositorio
     * 
     * @param cod_cat Recibe como parametro el codigo de la categoria a buscar
     * @return retorna el objeto categoria encontrado segun su codigo
     * @author Beelz
     */
    public Categoria buscarCat(int cod_cat);

    /**
     * Metodo que guarda una categoria en el repositorio
     * 
     * @param categoria Recibe como parametro el objeto categoria a guardar
     * @author Beelz
     */
    public Categoria guardarCategoria(Categoria categoria) throws ErrorServicio; 

     /**
     * Metodo que actualizar una categoria en el repositorio
     * 
     * @param categoria Recibe como parametro el objeto categoria a actualizar
     * @author Beelz
     */
    public Categoria actualizarCategoria(Categoria categoria) throws ErrorServicio;

    /**
     * Metodo que elimina una categoria del repositorio
     * 
     * @param cod_cat Recibe como parametro el codigo de la categoria a eliminar
     * @author Beelz
     */
    public void eliminarCat(int cod_cat) throws ErrorServicio;

    
    /**
     * Metodo que valida la informacion que se almacena en un objeto de tipo categoria
     * 
     * @param categoria Recibe como parametro un objeto de tipo categoria
     * @author Beelz
     */
    public void validarCategoria(Categoria categoria) throws ErrorServicio;

}
