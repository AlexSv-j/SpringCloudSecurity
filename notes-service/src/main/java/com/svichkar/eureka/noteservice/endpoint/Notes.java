package com.svichkar.eureka.noteservice.endpoint;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CRUD REST APIs for Notes",
        description = "CRUD REST APIs in Notes to get noteservice"
)
@RestController
@RequestMapping("/notes")
public class Notes {

    private static final Logger logger = LoggerFactory.getLogger(Notes.class);
    private static Integer DELAY = 1500;

    @Operation(
            summary = "Get Notes",
            description = "REST API to tet Notes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notes returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }
            )
//            ,
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "HTTP Status Internal Server Error",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            )
    }
    )
    @GetMapping("/getNotes")
    @SneakyThrows
    public String getNotes(@RequestHeader("CorrelationId") String correlationId) {
        logger.debug("Debug log with CorrelationId - {}", correlationId);
//        TimeUnit.MILLISECONDS.sleep(DELAY += 50);
        System.out.println("Call for noteservice. Delay = " +  DELAY.toString());
//        throw new RuntimeException("wrong");
        return "Required Notes";
    }

    @RateLimiter(name = "rateLimiter", fallbackMethod = "rateLimiterFallback")
    @GetMapping("/rateLimiter")
    public String rateLimiter(){
        return "Rate Limiter endpoint";
    }

    public String rateLimiterFallback (Throwable t){
        return "rateLimiter fallback is used";
    }
}
