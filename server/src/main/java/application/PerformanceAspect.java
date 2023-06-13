package application;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Around("execution(* application.*.controller.*.*(..)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "||  @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "||  @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "||  @annotation(org.springframework.web.bind.annotation.DeleteMapping) )")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 원본 메소드 실행
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long executionTime = end - start;

        System.out.println("수행시간: " + executionTime + " ms");

        return result;
    }
}