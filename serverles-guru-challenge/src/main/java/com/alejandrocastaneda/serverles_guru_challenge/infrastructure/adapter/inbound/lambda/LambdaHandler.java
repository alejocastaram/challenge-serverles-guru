package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.lambda;

import com.alejandrocastaneda.serverles_guru_challenge.ServerlesGuruChallengeApplication;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

public class LambdaHandler extends FunctionInvoker {
    public LambdaHandler() {
        // Pasa aquí tu clase principal que tiene el @SpringBootApplication
        super("com.alejandrocastaneda.serverles_guru_challenge.ServerlesGuruChallengeApplication");
    }
}
