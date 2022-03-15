package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.repositorio.RepositorioCategoria;
import com.tienda.licorera.repositorio.RepositorioLicor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface
 * ICategoriaServicio
 * 
 * @author Beelz
 */
@Service
public class CategoriaServImpl implements ICategoriaServicio {

    @Autowired
    private RepositorioCategoria categoriaRepositorio;

    
    @Autowired
    private RepositorioLicor licorRepositorio;

    @Override
    public Categoria buscarCat(int cod_cat) {
        return categoriaRepositorio.findById(cod_cat).orElse(null);
    }

    @Override
    public void eliminarCat(int cod_cat) throws ErrorServicio{
        List<Licor> listadoLicores = (List<Licor>)licorRepositorio.findByCod_cat(cod_cat);
        System.out.print(listadoLicores.toString());
        if (!listadoLicores.isEmpty()) {
            throw new ErrorServicio("Error al eliminar, Esta categoria contiene licores.");
        } else if (listadoLicores.isEmpty()) {
            categoriaRepositorio.deleteById(cod_cat);
        }
    }

    @Override
    public Categoria actualizarCategoria(Categoria categoria) throws ErrorServicio{
        categoria.setNomb_cat(categoria.getNomb_cat().toUpperCase());
        validarCategoria(categoria);
        return categoriaRepositorio.save(categoria);
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws ErrorServicio {
        categoria.setNomb_cat(categoria.getNomb_cat().toUpperCase());
        validarCategoria(categoria);
        return categoriaRepositorio.save(categoria);
    }

    @Override
    public List<Categoria> listarTodas() {

        return (List<Categoria>) categoriaRepositorio.findAll();
    }

    @Override
    public Categoria buscarPorNombre(String nomb_cat) {
        return categoriaRepositorio.findByNomb_cat(nomb_cat);
    }

    @Override
    public void validarCategoria(Categoria categoria) throws ErrorServicio {

        // validacion del nombre de la categoria
        Categoria cat = buscarPorNombre(categoria.getNomb_cat());
        if (categoria.getNomb_cat() == null || categoria.getNomb_cat().isEmpty()) {
            throw new ErrorServicio("El campo categoria no puede estar vacio, ingrese la categoria.");
        } else if (categoria.getNomb_cat().length() > 45) {
            throw new ErrorServicio("El campo categoria debe contener maximo 45 caracteres.");
        } else if (cat != null && categoria.getCod_cat() != cat.getCod_cat()) {
            throw new ErrorServicio(
                    "El nombre de la categoria: " + categoria.getNomb_cat() + " ya se encuentra registrado");
        }
    }
}