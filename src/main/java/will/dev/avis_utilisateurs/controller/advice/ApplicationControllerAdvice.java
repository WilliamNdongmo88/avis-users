package will.dev.avis_utilisateurs.controller.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import will.dev.avis_utilisateurs.dto.ErrorEntity;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ApplicationControllerAdvice {

    /**
     * Gère les exceptions de type RuntimeException.
     * Renvoie une réponse HTTP 400 (BAD_REQUEST) avec un message d'erreur.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ErrorEntity handleRuntimeException(RuntimeException exception) {
        return new ErrorEntity(HttpStatus.BAD_REQUEST.value(), "Erreur de traitement : " + exception.getMessage());
    }

    /**
     * Erreur 401 - Non autorisé (authentification échouée)
     */
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public @ResponseBody ErrorEntity handleUnauthorized(BadCredentialsException exception) {
        return new ErrorEntity(UNAUTHORIZED.value(), "Authentification échouée : " + exception.getMessage());
    }

    /**
     * Erreur 403 - Accès refusé
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public @ResponseBody ErrorEntity handleAccessDenied(AccessDeniedException exception) {
        return new ErrorEntity(HttpStatus.FORBIDDEN.value(), exception.getMessage() + ": Vous n'êtez pas autorisé a mener cette action.");
    }

    /**
     * Gère les exceptions de type EntityNotFoundException.
     * Renvoie une réponse HTTP 404 (NOT_FOUND) avec un message d'erreur.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public @ResponseBody ErrorEntity handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorEntity(HttpStatus.NOT_FOUND.value(), "Ressource non trouvée : " + exception.getMessage());
    }

    /**
     * Gère toutes les autres exceptions non gérées.
     * Renvoie une réponse HTTP 500 (INTERNAL_SERVER_ERROR) avec un message d'erreur générique.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorEntity handleGenericException(Exception exception) {
        return new ErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Une erreur interne est survenue : " + exception.getMessage());
    }
}
