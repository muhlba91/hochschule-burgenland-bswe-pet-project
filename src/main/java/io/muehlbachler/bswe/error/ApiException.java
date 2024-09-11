package io.muehlbachler.bswe.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception to be thrown when an API error occurs.
 */
@Getter
@AllArgsConstructor
public class ApiException extends Exception {
  private static final long serialVersionUID = 1L;
  private final ApiExceptionType type;

  /**
   * Constructor for the ApiException class.
   *
   * @param type   the type of the exception
   * @param parent the parent exception
   */
  public ApiException(final ApiExceptionType type, final Exception parent) {
    super(parent);
    this.type = type;
  }

  /**
   * Returns the HTTP status code for the exception.
   *
   * @return the HTTP status code
   */
  public HttpStatus getHttpStatus() {
    if (type == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    switch (type) {
      case GEOCODING_FAILED:
      case NO_AIRPORT_FOUND:
      case FORECAST_FAILED:
      case NO_DATA:
        return HttpStatus.BAD_REQUEST;
      case SAVE_ERROR:
        return HttpStatus.CONFLICT;
      default:
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }

  /**
   * Enumeration of possible API exception types.
   */
  public enum ApiExceptionType {
    SAVE_ERROR,
    NO_DATA,
    GEOCODING_FAILED,
    NO_AIRPORT_FOUND,
    FORECAST_FAILED,
    INTERNAL_SERVER_ERROR
  }
}
