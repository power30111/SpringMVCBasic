package hello.exception.exhandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;
}
