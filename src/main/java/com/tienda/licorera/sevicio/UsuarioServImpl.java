package com.tienda.licorera.sevicio;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import com.tienda.licorera.dto.CambiarClave;
import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.repositorio.RepositorioUsuario;
import java.util.regex.*;

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

    @Override
    public List<Usuario> listarTodos() {

        return (List<Usuario>) usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {

        return usuarioRepositorio.findById(cedula).orElse(null);
    }

    @Override
    public Usuario buscarPorEmail(String email) {

        return usuarioRepositorio.findByEmail(email);
    }

    @Override
    public Usuario guardar(Usuario usuario) throws ErrorServicio {
        usuario.setNombres(usuario.getNombres().toUpperCase());
        usuario.setApellidos(usuario.getApellidos().toUpperCase());
        usuario.setDireccion(usuario.getDireccion().toUpperCase());
        usuario.setEmail(usuario.getEmail().toUpperCase());
        validarUsuario(usuario, 0);
        usuario.setClave(PasswordEncoder.encode(usuario.getClave()));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario editar(Usuario usuario) throws ErrorServicio {
        usuario.setNombres(usuario.getNombres().toUpperCase());
        usuario.setApellidos(usuario.getApellidos().toUpperCase());
        usuario.setDireccion(usuario.getDireccion().toUpperCase());
        validarUsuario(usuario, 1);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(usuario.getCedula());
        if (respuesta.isPresent()) {
            Usuario user = respuesta.get();
            user.setCedula(usuario.getCedula());
            user.setNombres(usuario.getNombres());
            user.setApellidos(usuario.getApellidos());
            user.setFecha_nac(usuario.getFecha_nac());
            user.setTelefono(usuario.getTelefono());
            user.setEmail(usuario.getEmail());
            user.setDireccion(usuario.getDireccion());
            return usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontró el usuario solicitado.");
        }
    }

    @Override
    public void eliminar(String cedula) {
        usuarioRepositorio.deleteById(cedula);
    }

    @Override
    public Usuario cambiarClave(CambiarClave cambiarClave) throws Exception {
       
        Usuario usuario = buscarPorCedula(cambiarClave.getId());
        String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=.*[$@$!%*?&#/()='¡¿~{}+,_;.:<>-])"
        + "(?=\\S+$).{8,32}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cambiarClave.getClaveNueva());

        if (cambiarClave.getClaveActual() == null || cambiarClave.getClaveActual().isEmpty()) {
            throw new Exception("El campo de la clave actual no puede estar vacio, ingrese su clave.");
        } else if (!PasswordEncoder.matches(cambiarClave.getClaveActual(), usuario.getClave())) {
            throw new Exception("La contraseña no correspone a la actual.");
        }
        
        if (cambiarClave.getClaveNueva() == null || cambiarClave.getClaveNueva().isEmpty()) {
            throw new Exception("El campo de la clave nueva no puede estar vacio, ingrese su clave nueva.");
        }else if (PasswordEncoder.matches(cambiarClave.getClaveNueva(), usuario.getClave())) {
            throw new Exception("La contraseña nueva debe ser diferente a la actual.");
        }else if(!m.matches()){
            throw new Exception("La contraseña debe contener 8-32 caracteres(mayuscula,minuscula y almenos un numero y un simbolo), no se permiten espacios.");
        }  else if (!cambiarClave.getClaveNueva().equals(cambiarClave.getConfirmarClaveNueva())) {
            throw new Exception("La contraseña nueva y la confirmacion, deben ser iguales.");
        }

        String encodePassword = PasswordEncoder.encode(cambiarClave.getClaveNueva());
        usuario.setClave(encodePassword);
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void actualizarClave(Usuario usuario, String claveNueva , String confimarClave) throws Exception {
        String encodePassword = PasswordEncoder.encode(claveNueva);
        String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=.*[$@$!%*?&#/()='¡¿~{}+,_;.:<>-])"
        + "(?=\\S+$).{8,32}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(claveNueva);

        if (claveNueva == null || claveNueva.isEmpty()) {
            throw new Exception("El campo de la clave nueva no puede estar vacio, ingrese su clave.");
        }else if(!m.matches()){
            throw new Exception("La contraseña debe contener 8-32 caracteres(mayuscula,minuscula y almenos un numero y un simbolo), no se permiten espacios.");
        }else if (!claveNueva.equals(confimarClave)) {
            throw new Exception("La contraseña nueva y la confirmacion, deben ser iguales.");
        }
        usuario.setClave(encodePassword);
        usuario.setRestablecer_clave_token(null);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void actualizarTokenClave(String token, String email) throws Exception {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null) {
            usuario.setRestablecer_clave_token(token);
            usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("No hay usuarios registrados con el correo " + email);
        }
        
    }

    @Override
    public Usuario buscarToken(String token) {
        return usuarioRepositorio.findByRestablecer_clave_token(token);
    }

    @Override
    public void validarUsuario(Usuario usuario, int valor) throws ErrorServicio {

        // validacion de la cedula del usuario
        if (valor == 0) {
            if (usuario.getCedula() == null || usuario.getCedula().isEmpty()) {
                throw new ErrorServicio("El campo de cedula no puede estar vacio, ingrese su cedula.");
            } else if (usuario.getCedula().length() > 10 || usuario.getCedula().length() < 4) {
                throw new ErrorServicio("El campo de cedula debe contener maximo 10 digitos y minimo 4.");
            } else if (buscarPorCedula(usuario.getCedula()) != null) {
                throw new ErrorServicio("El numero de cedula: " + usuario.getCedula() + " ya se encuentra registrado");
            }
        }

        // validacion del nombre del usuario
        if (usuario.getNombres() == null || usuario.getNombres().isEmpty()) {
            throw new ErrorServicio("El campo de nombres no puede estar vacio, ingrese su nombre.");
        } else if (usuario.getNombres().length() > 45) {
            throw new ErrorServicio("El campo de nombres debe contener maximo 45 letras.");
        } else if (usuario.getNombres().matches(".*\\d.*")) {
            throw new ErrorServicio("El campo de nombres no debe contener digitos.");
        }

        // validacion del apellido del usuario
        if (usuario.getApellidos() == null || usuario.getApellidos().isEmpty()) {
            throw new ErrorServicio("El campo de apellidos no puede estar vacio, ingrese su apellido.");
        } else if (usuario.getApellidos().length() > 45) {
            throw new ErrorServicio("El campo de apellidos debe contener maximo 45 letras.");
        } else if (usuario.getApellidos().matches(".*\\d.*")) {
            throw new ErrorServicio("El campo de apellidos no debe contener digitos.");
        }

        // validacion de la fecha de nacimiento del usuario
        if (valor == 0) {
            if (usuario.getFecha_nac() == null) {
                throw new ErrorServicio("La fecha de nacimiento no puede estar vacia, ingrese su fecha de nacimiento.");
            } else if (calcularEdad(usuario.getFecha_nac()) < 18) {
                throw new ErrorServicio("La edad minima para realizar el registro debe ser de 18 años.");
            }
        }

        // validacion del telefono del usuario
        if (usuario.getTelefono() == null || usuario.getTelefono().isEmpty()) {
            throw new ErrorServicio("El numero de telefono no puede estar vacio, ingrese su numero de telefono.");
        } else if (usuario.getTelefono().length() != 10) {
            throw new ErrorServicio("El numero de telefono debe contener 10 digitos.");
        } else if (!usuario.getTelefono().matches("[0-9]+")) {
            throw new ErrorServicio("El campo de telefono no debe contener caracteres.");
        }

        // validacion del email del usuario
        if (valor == 0) {
            if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
                throw new ErrorServicio("El campo de email no puede estar vacio, ingrese su email.");
            } else if (usuario.getEmail().length() > 45) {
                throw new ErrorServicio("El email debe contener maximo 45 caracteres.");
            }
            if (buscarPorEmail(usuario.getEmail()) != null) {
                throw new ErrorServicio("El email: " + usuario.getEmail() + " ya se encuentra registrado");
            }
        }

        // validacion de la direccion del usuario
        if (usuario.getDireccion() == null || usuario.getDireccion().isEmpty()) {
            throw new ErrorServicio("El campo de la direccion no puede estar vacio, ingrese su direccion.");
        } else if (usuario.getDireccion().length() > 45) {
            throw new ErrorServicio("La direccion debe contener maximo 45 caracteres.");
        }

        // validacion de la clave del usuario
        if (valor == 0) {
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
    }

    @Override
    public int calcularEdad(LocalDate fecha_nac) {
        LocalDate fecha_actual = LocalDate.now();
        Period periodo = Period.between(fecha_nac, fecha_actual);
        // System.out.print("Su edad es : " + periodo.getYears() + " años " +
        // periodo.getMonths() + " meses "
        // + periodo.getDays() + " dias");

        return periodo.getYears();
    }
}
