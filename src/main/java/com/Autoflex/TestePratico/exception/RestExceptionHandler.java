package com.Autoflex.TestePratico.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductAlredyExistException.class)
    public ResponseEntity<Object> handleProductAlredyExistException(ProductAlredyExistException e) {
        var retorno = new ErroResponse("Product already exists!");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(retorno);
    }

    @ExceptionHandler(AssociationAlredyExistsException.class)
    public ResponseEntity<Object> handleAssociationNotFoundException(AssociationAlredyExistsException e) {
        var retorno = new ErroResponse("Association Aleready Exists!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }


    @ExceptionHandler(AssociationNotFoundException.class)
    public ResponseEntity<Object> handleAssociationNotFoundException(AssociationNotFoundException e) {
        var retorno = new ErroResponse("Association not found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }


    @ExceptionHandler(MaterialNotFoundException.class)
    public ResponseEntity<Object> handleMaterialNotFoundException(MaterialNotFoundException e) {
        var retorno = new ErroResponse("Material not found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e) {
        var retorno = new ErroResponse("Product not found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }

    @ExceptionHandler(MaterialAlredyExistsException.class)
    public ResponseEntity<Object> handleMaterialAlredyExistsException(MaterialAlredyExistsException e) {
        var retorno = new ErroResponse("Material alredy exists!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }

    @ExceptionHandler(RegistroComNomeExistenteException.class)
    public ResponseEntity<Object> RegistroComNomeExistenteExceptionException(RegistroComNomeExistenteException e) {
        var retorno = new ErroResponse("Product already exists!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }

//    @ExceptionHandler(LivroIndisponivelException.class)
//    public ResponseEntity<Object> handleLivroIndisponivelException(LivroIndisponivelException e) {
//        var retorno = new ErroResponse("Livro não disponível para empréstimo!");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retorno);
//    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(e -> {
            Iterator<Path.Node> iterator = e.getPropertyPath().iterator();
            String fieldName = null;
            while (iterator.hasNext()) {
                fieldName = iterator.next().getName();
            }
            String errorMessage = e.getMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        log.error("Erros de validacao: {}", fieldErrors);
        List<String> mensagens = fieldErrors.keySet().stream().map(k -> k + ": " + fieldErrors.get(k)).toList();
        var retorno = new ErroResponse(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retorno);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        List<String> mensagens = fieldErrors.keySet().stream().map(k -> k + ": " + fieldErrors.get(k)).toList();
        var retorno = new ErroResponse(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retorno);
    }

    @Override   // catch any other exception for standard error message handling
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        Map<String, String> retorno = new HashMap<>();
        retorno.put("erro", "Erro no servidor! Contate o administrador do sistema!");
        return new ResponseEntity<>(retorno, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
