/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.commons.rest;

import dev.root101.commons.exceptions.ApiException;
import dev.root101.commons.exceptions.InternalServerErrorException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

/**
 *
 * @author Yo
 * @param <T>
 */
public class ResponseExtractor<T> {

    private static boolean logs = true;

    // ----------------------- Factory builder ----------------------- \\
    public static <T> ResponseExtractor<T> build(Supplier<ResponseEntity<T>> suplier) {
        return new ResponseExtractor<>(suplier);
    }

    public static <T> T extract(Supplier<ResponseEntity<T>> suplier) {
        return new ResponseExtractor<>(suplier).extract();
    }

    // ----------------------- Variables ----------------------- \\
    private final Supplier<ResponseEntity<T>> suplier;

    private Function<ResponseEntity<T>, T> onOkResponseCode = (response) -> {
        if (logs) {
            System.out.println(response.toString());
        }
        return response.getBody();
    };

    private Function<ResponseEntity<T>, T> onNotOkResponseCode = (response) -> {
        if (logs) {
            System.out.println(response.toString());
        }
        throw ApiException.build(response.getStatusCode());
    };

    private Function<org.springframework.web.client.HttpStatusCodeException, T> onHttpStatusCodeException = (exc) -> {
        if (logs) {
            System.out.println(exc.toString());
        }
        throw ApiException.build(exc.getStatusCode());
    };

    private Function<ApiException, T> onApiException = (apiExc) -> {
        if (logs) {
            System.out.println(apiExc.toString());
        }
        throw apiExc;
    };

    private Function<org.springframework.web.client.UnknownHttpStatusCodeException, T> onUnknownHttpStatusCodeException = (unk) -> {
        if (logs) {
            System.out.println(unk.toString());
        }
        throw new ApiException(unk.getRawStatusCode(), unk.getResponseBodyAsString());
    };

    private Function<Exception, T> onGeneralException = (exc) -> {
        if (logs) {
            System.out.println(exc.toString());
        }
        throw new InternalServerErrorException("Unknown error. Contact support.");
    };

    // ----------------------- Constructor ----------------------- \\
    public ResponseExtractor(Supplier<ResponseEntity<T>> suplier) {
        this.suplier = suplier;
    }

    // ----------------------- Extract Logic ----------------------- \\
    /**
     * <pre>
     * Flujo:
     * 0 - try{}catch(){} para mitigar todos los errores.
     * 1 - Ejecuta como tal la peticion, almacena el response.
     * 2 - Valida si la peticion fue 200 ok
     *  2.1 - Si la peticion fue 200 revuelve el body, el contenido real de la peticion
     *  2.2 - Si no fue 200 lanza un ApiException con el mismo codigo/mensaje que el error original
     *
     * catch:
     * 1 - ApiException: Si la peticion no es codigo 200 se lanza ApiException, que  se captura y se relanza, esto de primero para que el ultimo catch(Exception) no lo coja
     * 2 - HttpStatusCodeException: Literal cualquier error que Status Code que puede dar spring, tanto Client como Server.
     * 3 - UnknownHttpStatusCodeException: Otro error raro que lanza spring si no reconoce el error.4
     * 4 - Exception: super generico para que no se vaya nada.
     * </pre>
     *
     * @return
     */
    public T extract() {
        //step 0
        try {
            //step 1
            ResponseEntity<T> response = suplier.get();

            //step 2
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                //step 2.1
                return onOkResponseCode.apply(response);
            } else {
                //step 2.2
                return onNotOkResponseCode.apply(response);
            }
        } catch (ApiException apiException) {//catch 1
            return onApiException.apply(apiException);
        } catch (org.springframework.web.client.HttpStatusCodeException httpStatusCodeException) {//catch 2
            return onHttpStatusCodeException.apply(httpStatusCodeException);
        } catch (org.springframework.web.client.UnknownHttpStatusCodeException unknownHttpStatusCodeException) {//catch 3
            return onUnknownHttpStatusCodeException.apply(unknownHttpStatusCodeException);
        } catch (Exception generalException) {//catch 4
            return onGeneralException.apply(generalException);
        }
    }

    // ----------------------- Builder Setters ----------------------- \\
    public ResponseExtractor<T> onHttpStatusCodeException(Function<HttpStatusCodeException, T> onHttpStatusCodeException) {
        this.onHttpStatusCodeException = onHttpStatusCodeException;
        return this;
    }

    public ResponseExtractor<T> onApiException(Function<ApiException, T> onApiException) {
        this.onApiException = onApiException;
        return this;
    }

    public ResponseExtractor<T> onUnknownHttpStatusCodeException(Function<UnknownHttpStatusCodeException, T> onUnknownHttpStatusCodeException) {
        this.onUnknownHttpStatusCodeException = onUnknownHttpStatusCodeException;
        return this;
    }

    public ResponseExtractor<T> onGeneralException(Function<Exception, T> onGeneralException) {
        this.onGeneralException = onGeneralException;
        return this;
    }

    public ResponseExtractor<T> onOkResponseCode(Function<ResponseEntity<T>, T> onOkResponseCode) {
        this.onOkResponseCode = onOkResponseCode;
        return this;
    }

    public ResponseExtractor<T> onNotOkResponseCode(Function<ResponseEntity<T>, T> onNotOkResponseCode) {
        this.onNotOkResponseCode = onNotOkResponseCode;
        return this;
    }

    // ----------------------- Config Setters ----------------------- \\
    public static void info(boolean info) {
        ResponseExtractor.logs = info;
    }
}
