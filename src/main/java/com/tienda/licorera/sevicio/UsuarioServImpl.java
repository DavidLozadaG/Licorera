package com.tienda.licorera.sevicio;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.repositorio.RepositorioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface
 * IUsuarioServicio.
 * 
 * @author Beelz
 */
@Service
public class UsuarioServImpl implements IUsuarioServicio {

    @Autowired
    private BCryptPasswordEncoder PasswordEncoder;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    /**
     * Metodo que lista todos los usuarios del repositirio
     * 
     * @return retorna la lista con todos los usuarios
     * @author Beelz
     */
    @Override
    public List<Usuario> listarTodos() {

        return (List<Usuario>) usuarioRepositorio.findAll();
    }

    /**
     * Metodo para buscar un usuario por el numero de cedula
     * 
     * @param cedula
     * @return El metodo retorna el objeto de tipo usuario que cumple la condicion
     * @author Beelz
     */
    @Override
    public Usuario buscarPorCedula(String cedula) {

        return usuarioRepositorio.findById(cedula).orElse(null);
    }

    /**
     * Metodo para buscar un usario por email
     * 
     * @param email recibe como parametro el correo electronico del usuario
     * @return El metodo retorna el objeto de tipo usuario que cumple la condicion
     * @author Beelz
     */
    @Override
    public Usuario buscarPorEmail(String email) {

        return usuarioRepositorio.findByEmail(email);
    }

    /**
     * Metodo para guardar un objeto de tipo usuario
     * 
     * @param usuario recibe un objeto de tipo usuario
     * @return el objeto guardado
     * @author Beelz
     */
    @Override
    public Usuario guardar(Usuario usuario) throws ErrorServicio {
        usuario.setNombres(usuario.getNombres().toUpperCase());
        usuario.setApellidos(usuario.getApellidos().toUpperCase());
        usuario.setDireccion(usuario.getDireccion().toUpperCase());
        usuario.setEmail(usuario.getEmail().toUpperCase());
        validarUsuario(usuario);
        usuario.setClave(PasswordEncoder.encode(usuario.getClave()));
        return usuarioRepositorio.save(usuario);
    }

    /**
     * Metodo para eliminar un usuario identificado por el numero de cedula
     * 
     * @param cedula recibe como parametro la cedula del usuario a eliminar
     * @author Beelz
     */
    @Override
    public void eliminar(String cedula) {
        usuarioRepositorio.deleteById(cedula);
    }

    /**
     * Metodo que valida la informacion que se almacena en un objeto de tipo usuario
     * 
     * @param usuario Recibe como parametro un objeto de tipo usuario
     * @author Beelz
     */
    @Override
    public void validarUsuario(Usuario usuario) throws ErrorServicio {
        // validacion de la cedula del usuario
        if (usuario.getCedula() == null || usuario.getCedula().isEmpty()) {
            throw new ErrorServicio("El campo de cedula no puede estar vacio, ingrese su cedula.");
        } else if (usuario.getCedula().length() > 10 || usuario.getCedula().length() < 4) {
            throw new ErrorServicio("El campo de cedula debe contener maximo 10 digitos y minimo 4.");
        } else if (buscarPorCedula(usuario.getCedula()) != null) {
            throw new ErrorServicio("El numero de cedula: " + usuario.getCedula() + " ya se encuentra registrado");
        }

        // validacion del nombre del usuario
        if (usuario.getNombres() == null || usuario.getNombres().isEmpty()) {
            throw new ErrorServicio("El campo de nombres no puede estar vacio, ingrese su nombre.");
        } else if (usuario.getNombres().length() > 45) {
            throw new ErrorServicio("El campo de nombres debe contener maximo 45 letras.");
        }

        // validacion del apellido del usuario
        if (usuario.getApellidos() == null || usuario.getApellidos().isEmpty()) {
            throw new ErrorServicio("El campo de apellidos no puede estar vacio, ingrese su apellido.");
        } else if (usuario.getApellidos().length() > 45) {
            throw new ErrorServicio("El campo de apellidos debe contener maximo 45 letras.");
        }

        // validacion del apellido del usuario
        if (usuario.getApellidos() == null || usuario.getApellidos().isEmpty()) {
            throw new ErrorServicio("El campo de apellidos no puede estar vacio, ingrese su apellido.");
        } else if (usuario.getApellidos().length() > 45) {
            throw new ErrorServicio("El campo de apellidos debe contener maximo 45 letras.");
        }

        // validacion de la fecha de nacimiento del usuario
        if (usuario.getFecha_nac() == null) {
            throw new ErrorServicio("La fecha de nacimiento no puede estar vacia, ingrese su fecha de nacimiento.");
        } else if (calcularEdad(usuario.getFecha_nac()) < 18) {
            throw new ErrorServicio("La edad minima para realizar el registro debe ser de 18 años.");
        }
        // validacion del telefono del usuario
        if (usuario.getTelefono() == null || usuario.getTelefono().isEmpty()) {
            throw new ErrorServicio("El numero de telefono no puede estar vacio, ingrese su numero de telefono.");
        } else if (usuario.getTelefono().length() != 10) {
            throw new ErrorServicio("El numero de telefono debe contener 10 digitos.");
        }

        // validacion del email del usuario
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new ErrorServicio("El campo de email no puede estar vacio, ingrese su email.");
        } else if (usuario.getEmail().length() > 45) {
            throw new ErrorServicio("El email debe contener maximo 45 caracteres.");
        }
        if (buscarPorEmail(usuario.getEmail()) != null) {
            throw new ErrorServicio("El email: " + usuario.getEmail() + " ya se encuentra registrado");
        }

        // validacion de la direccion del usuario
        if (usuario.getDireccion() == null || usuario.getDireccion().isEmpty()) {
            throw new ErrorServicio("El campo de la direccion no puede estar vacio, ingrese su direccion.");
        } else if (usuario.getDireccion().length() > 45) {
            throw new ErrorServicio("La direccion debe contener maximo 45 caracteres.");
        }

        // validacion de la clave del usuario
        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            throw new ErrorServicio("El campo de la clave no puede estar vacio, ingrese su clave.");
        } else if (usuario.getClave().length() > 32 || usuario.getClave().length() < 8) {
            throw new ErrorServicio("La clave debe contener minimo 8 caracteres y maximo 32 caracteres");
        }
        // validacion de la claveConfirmacion del usuario
        if (usuario.getConfirmarClave() == null || usuario.getConfirmarClave().isEmpty()) {
            throw new ErrorServicio(
                    "El campo para confirmacion de la clave no puede estar vacio, ingrese la confirmacion de la clave.");
        } else if (usuario.getConfirmarClave() == usuario.getClave()) {
            throw new ErrorServicio("Las claves no coinciden, por favor rectifique la clave");
        }
    }

    /**
     * Metodo para calcular la edad de un usuario apartir de su fecha de nacimiento
     * 
     * @param fecha_nac recibe como parametro la fecha de nacimiento del usuario
     * @return retorna la edad del usuario
     */
    @Override
    public int calcularEdad(LocalDate fecha_nac) {
        if (fecha_nac != null) {
            LocalDate fecha_actual = LocalDate.now();
            Period periodo = Period.between(fecha_nac, fecha_actual);
            System.out.print("Su edad es : " + periodo.getYears() + " años " + periodo.getMonths() + " meses "
                    + periodo.getDays() + " dias");

            return periodo.getYears();
        }
        return 0;
    }
}
