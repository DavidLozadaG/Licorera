package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.repositorio.RepositorioCategoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface ICategoriaServicio
 * 
 * @author Beelz
 */
@Service
public class CategoriaServImpl implements ICategoriaServicio {

    @Autowired
    private RepositorioCategoria categoriaRepositorio;

    /**
     * Metodo que busca una categoria en el repositorio
     * 
     * @param cod_cat Recibe como parametro el codigo de la categoria a buscar
     * @return retorna el objeto categoria encontrado segun su codigo
     * @author Beelz
     */
    @Override
    public Categoria buscarCat(int cod_cat) {

        return categoriaRepositorio.findById(cod_cat).orElse(null);
    }

    /**
     * Metodo que elimina una categoria del repositorio
     * 
     * @param cod_cat Recibe como parametro el codigo de la categoria a eliminar
     * @author Beelz
     */
    @Override
    public void eliminarCat(int cod_cat) {
        categoriaRepositorio.deleteById(cod_cat);
    }
    /**
     * Metodo que guarda una categoria en el repositorio
     * 
     * @param categoria Recibe como parametro el objeto categoria a guardar
     * @author Beelz
     */
    @Override
    public void guardarCategoria(Categoria categoria) {
        categoriaRepositorio.save(categoria);
    }
    /**
     * Metodo que lista las categorias existentes en el repositorio
     * 
     * @return retorna la lista de categorias 
     * @author Beelz
     */
    @Override
    public List<Categoria> listarTodas() {

        return (List<Categoria>) categoriaRepositorio.findAll();
    }
    /**
     * Metodo que busca por nombre una categoria existente en el repositorio
     * 
     * @param nomb_cat Recibe como parametro el nombre de la categoria a buscar
     * @return retorna el nombre de la categoria (si la encuentra) 
     * @author Beelz
     */
    @Override
    public Categoria buscarPorNombre(String nomb_cat) {
        return categoriaRepositorio.findByNomb_cat(nomb_cat);
    }
}
